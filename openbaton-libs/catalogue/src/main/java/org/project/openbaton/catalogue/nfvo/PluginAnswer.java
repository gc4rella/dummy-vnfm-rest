package org.project.openbaton.catalogue.nfvo;

import java.io.Serializable;

/**
 * Created by lto on 13/08/15.
 */
public class PluginAnswer implements Serializable{
    private Serializable answer;
    private String selector;

    public Serializable getAnswer() {
        return answer;
    }

    public void setAnswer(Serializable answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "PluginAnswer{" +
                "answer=" + answer +
                '}';
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }
}
