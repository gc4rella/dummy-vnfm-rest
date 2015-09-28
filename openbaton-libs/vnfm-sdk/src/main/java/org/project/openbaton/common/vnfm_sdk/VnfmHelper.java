package org.project.openbaton.common.vnfm_sdk;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.project.openbaton.catalogue.mano.common.Event;
import org.project.openbaton.catalogue.mano.record.VNFRecordDependency;
import org.project.openbaton.catalogue.mano.record.VirtualNetworkFunctionRecord;
import org.project.openbaton.catalogue.nfvo.Action;
import org.project.openbaton.catalogue.nfvo.ConfigurationParameter;
import org.project.openbaton.catalogue.nfvo.messages.Interfaces.NFVMessage;
import org.project.openbaton.catalogue.nfvo.messages.OrVnfmErrorMessage;
import org.project.openbaton.catalogue.nfvo.messages.OrVnfmGenericMessage;
import org.project.openbaton.common.vnfm_sdk.exception.VnfmSdkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Created by lto on 23/09/15.
 */
public abstract class VnfmHelper {

    protected Logger log = LoggerFactory.getLogger(this.getClass());
    protected Gson parser = new GsonBuilder().setPrettyPrinting().create();

    @Async
    public Future<VirtualNetworkFunctionRecord> grantLifecycleOperation(VirtualNetworkFunctionRecord vnfr) throws VnfmSdkException {
        NFVMessage response;
        try {
            response = sendAndReceive(Action.GRANT_OPERATION, vnfr);
        } catch (Exception e) {
            throw new VnfmSdkException("Not able to grant operation", e);
        }
        log.debug("" + response);
        if (response.getAction().ordinal() == Action.ERROR.ordinal()) {
            throw new VnfmSdkException("Not able to grant operation because: " + ((OrVnfmErrorMessage) response).getMessage() , ((OrVnfmErrorMessage) response).getVnfr());
        }
        OrVnfmGenericMessage orVnfmGenericMessage = (OrVnfmGenericMessage) response;
        return new AsyncResult<>(orVnfmGenericMessage.getVnfr());
    }

    @Async
    public Future<VirtualNetworkFunctionRecord> allocateResources(VirtualNetworkFunctionRecord vnfr) throws VnfmSdkException {
        NFVMessage response;
        try {
            response = sendAndReceive(Action.ALLOCATE_RESOURCES, vnfr);
        } catch (Exception e) {
            log.error("" + e.getMessage());
            throw new VnfmSdkException("Not able to allocate Resources", e);
        }
        if (response.getAction().ordinal() == Action.ERROR.ordinal()) {
            OrVnfmErrorMessage errorMessage = (OrVnfmErrorMessage) response;
            log.error(errorMessage.getMessage());
            throw new VnfmSdkException("Not able to allocate Resources because: " + errorMessage.getMessage() , errorMessage.getVnfr());
        }
        OrVnfmGenericMessage orVnfmGenericMessage = (OrVnfmGenericMessage) response;
        log.debug("Received from ALLOCATE: " + orVnfmGenericMessage.getVnfr());
        return new AsyncResult<>(orVnfmGenericMessage.getVnfr());
    }

    public abstract void sendMessageToQueue(String sendToQueueName, Serializable message);

    public abstract void sendToNfvo(NFVMessage nfvMessage);

    public abstract Iterable<String> executeScriptsForEvent(VirtualNetworkFunctionRecord virtualNetworkFunctionRecord, Event event) throws Exception;

    public abstract String executeScriptsForEvent(VirtualNetworkFunctionRecord virtualNetworkFunctionRecord, Event event, VNFRecordDependency dependency) throws Exception;

    public abstract void saveScriptOnEms(VirtualNetworkFunctionRecord virtualNetworkFunctionRecord, Object scriptsLink) throws Exception;

    protected JsonObject getJsonObject(String action, String payload) {
        JsonObject jsonMessage = new JsonObject();
        jsonMessage.addProperty("action", action);
        jsonMessage.addProperty("payload", payload);
        return jsonMessage;
    }

    protected JsonObject getJsonObject(String action, String payload, Map<String, String> dependencyParameters) {
        JsonObject jsonMessage = new JsonObject();
        jsonMessage.addProperty("action", action);
        jsonMessage.addProperty("payload", payload);
        jsonMessage.add("env", parser.fromJson(parser.toJson(dependencyParameters), JsonObject.class));
        return jsonMessage;
    }

    public abstract NFVMessage sendAndReceive(Action action, VirtualNetworkFunctionRecord virtualNetworkFunctionRecord) throws Exception;

    protected JsonObject getJsonObjectForScript(String save_scripts, String payload, String name) {
        JsonObject jsonMessage = new JsonObject();
        jsonMessage.addProperty("action", save_scripts);
        jsonMessage.addProperty("payload", payload);
        jsonMessage.addProperty("name", name);
        return jsonMessage;
    }

    protected Map<String, String> getMap(VirtualNetworkFunctionRecord virtualNetworkFunctionRecord) {
        Map<String, String> res = new HashMap<>();
        for (ConfigurationParameter configurationParameter : virtualNetworkFunctionRecord.getProvides().getConfigurationParameters())
            res.put(configurationParameter.getConfKey(),configurationParameter.getValue());
        for (ConfigurationParameter configurationParameter : virtualNetworkFunctionRecord.getConfigurations().getConfigurationParameters()){
            res.put(configurationParameter.getConfKey(),configurationParameter.getValue());
        }
        return res;
    }
}
