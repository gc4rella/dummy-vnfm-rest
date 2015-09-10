package org.project.openbaton.catalogue.nfvo;

import org.project.openbaton.catalogue.util.IdGenerator;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Version;
import java.io.Serializable;

/**
 * Created by lto on 18/05/15.
 */
@Entity
public class ConfigurationParameter implements Serializable{
    @Id
    private String id;
    @Version
    private int version;

    private String confKey;
    private String value;

    @PrePersist
    public void ensureId(){
        id=IdGenerator.createUUID();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getConfKey() {
        return confKey;
    }

    public void setConfKey(String confKey) {
        this.confKey = confKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ConfigurationParameter{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", confKey='" + confKey + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
