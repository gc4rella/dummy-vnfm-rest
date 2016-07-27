#!/bin/bash

_openbaton_base="/opt/openbaton"
_dummy_vnfm_base="${_openbaton_base}/dummy-vnfm-rest"
source ${_dummy_vnfm_base}/gradle.properties

_version=${version}

_dummy_config_file=/etc/openbaton/dummy-vnfm-rest.properties


function check_already_running {
        result=$(screen -ls | grep dummy-vnfm | wc -l);
        if [ "${result}" -ne "0" ]; then
                echo "a dummy-vnfm is already running..."
		exit;
        fi
}

function start {

    if [ ! -d ${_dummy_vnfm_base}/build/  ]
        then
            compile
    fi

    check_already_running
    if [ 0 -eq $? ]; then
	    screen_exists=$(screen -ls | grep openbaton | wc -l);
        if [ "${screen_exists}" -eq "0" ]; then
            echo "Starting the Dummy-VNFM-Rest in a new screen session (attach to the screen with screen -x openbaton)"
            if [ -f ${_dummy_config_file} ]; then
                echo "Using external configuration file ${_dummy_config_file}"
                screen -c screenrc -d -m -S openbaton -t dummy-vnfm-rest java -jar "${_dummy_vnfm_base}/build/libs/rest-vnfm-${_version}.jar" --spring.config.location=file:${_dummy_config_file}
            else
                screen -c screenrc -d -m -S openbaton -t dummy-vnfm-rest java -jar "${_dummy_vnfm_base}/build/libs/rest-vnfm-${_version}.jar"
            fi
        elif [ "${screen_exists}" -ne "0" ]; then
            echo "Starting the Dummy-VNFM-Rest in the existing screen session (attach to the screen with screen -x openbaton)"
            if [ -f ${_dummy_config_file} ]; then
                echo "Using external configuration file ${_dummy_config_file}"
                screen -S openbaton -p 0 -X screen -t dummy-vnfm-rest java -jar "${_dummy_vnfm_base}/build/libs/rest-vnfm-${_version}.jar" --spring.config.location=file:${_dummy_config_file}
            else
                screen -S openbaton -p 0 -X screen -t dummy-vnfm-rest java -jar "${_dummy_vnfm_base}/build/libs/rest-vnfm-${_version}.jar"
            fi
        fi
    fi

}

function stop {
echo stop
    if screen -list | grep "openbaton"; then
	    screen -S openbaton -p 0 -X stuff "exit$(printf \\r)"
    fi
}

function restart {
    kill
    start
}


function kill {
    if screen -list | grep "openbaton"; then
	    screen -ls | grep openbaton | cut -d. -f1 | awk '{print $1}' | xargs kill
    fi
}


function compile {
    ./gradlew build -x test
}

function tests {
    ./gradlew test
}

function clean {
    ./gradlew clean
}

function end {
    exit
}
function usage {
    echo -e "Open-Baton dummy-vnfm\n"
    echo -e "Usage:\n\t ./dummy-vnfm.sh [compile|start|stop|test|kill|clean]"
}

##
#   MAIN
##

if [ $# -eq 0 ]
   then
        usage
        exit 1
fi

declare -a cmds=($@)
for (( i = 0; i <  ${#cmds[*]}; ++ i ))
do
    case ${cmds[$i]} in
        "clean" )
            clean ;;
        "sc" )
            clean
            compile
            start ;;
        "start" )
            start ;;
        "stop" )
            stop ;;
        "restart" )
            restart ;;
        "compile" )
            compile ;;
        "kill" )
            kill ;;
        "test" )
            tests ;;
        * )
            usage
            end ;;
    esac
    if [[ $? -ne 0 ]];
    then
	    exit 1
    fi
done

