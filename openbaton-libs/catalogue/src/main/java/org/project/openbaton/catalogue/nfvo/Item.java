package org.project.openbaton.catalogue.nfvo;

import org.project.openbaton.catalogue.util.IdGenerator;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Version;

/**
 * Created by lto on 05/08/15.
 */
@Entity
public class Item {
    @Id
    private String id;
    @Version
    private int version = 0;

    private String metric;

    private String hostExtId;

    private String vnfcInstanceId;
    private String lastValue;
    private String value;

    public Item() {
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", metric='" + metric + '\'' +
                ", hostExtId='" + hostExtId + '\'' +
                ", vnfcInstanceId='" + vnfcInstanceId + '\'' +
                ", value='" + value + '\'' +
                ", lastvalue='" + lastValue + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    @PrePersist
    public void ensureId(){
        id=IdGenerator.createUUID();
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

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public String getHostExtId() {
        return hostExtId;
    }

    public void setHostExtId(String hostExtId) {
        this.hostExtId = hostExtId;
    }

    public String getVnfcInstanceId() {
        return vnfcInstanceId;
    }

    public void setVnfcInstanceId(String vduId) {
        this.vnfcInstanceId = vnfcInstanceId;
    }

    public void setLastValue(String lastValue) {
        this.lastValue = lastValue;
    }

    public String getLastValue() {
        return lastValue;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
