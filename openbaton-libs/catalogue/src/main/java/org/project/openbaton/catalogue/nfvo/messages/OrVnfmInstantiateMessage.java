package org.project.openbaton.catalogue.nfvo.messages;

import org.project.openbaton.catalogue.mano.common.VNFDeploymentFlavour;
import org.project.openbaton.catalogue.mano.descriptor.VirtualLinkDescriptor;
import org.project.openbaton.catalogue.mano.descriptor.VirtualNetworkFunctionDescriptor;
import org.project.openbaton.catalogue.nfvo.Action;
import org.project.openbaton.catalogue.nfvo.messages.Interfaces.OrVnfmMessage;

import java.util.Map;
import java.util.Set;

/**
 * Created by mob on 14.09.15.
 */
public class OrVnfmInstantiateMessage implements OrVnfmMessage {
    private VirtualNetworkFunctionDescriptor vnfd;
    private VNFDeploymentFlavour vnfdf;
    private String vnfInsanceName;
    private Set<VirtualLinkDescriptor> vlds;
    private Map<String,String> extention;

    public OrVnfmInstantiateMessage(VirtualNetworkFunctionDescriptor vnfd, VNFDeploymentFlavour vnfdf, String vnfInsanceName, Set<VirtualLinkDescriptor> vlds, Map<String, String> extention) {
        this.vnfd = vnfd;
        this.vnfdf = vnfdf;
        this.vnfInsanceName = vnfInsanceName;
        this.vlds = vlds;
        this.extention = extention;
    }

    public VirtualNetworkFunctionDescriptor getVnfd() {
        return vnfd;
    }

    public void setVnfd(VirtualNetworkFunctionDescriptor vnfd) {
        this.vnfd = vnfd;
    }

    public VNFDeploymentFlavour getVnfdf() {
        return vnfdf;
    }

    public void setVnfdf(VNFDeploymentFlavour vnfdf) {
        this.vnfdf = vnfdf;
    }

    public String getVnfInsanceName() {
        return vnfInsanceName;
    }

    public void setVnfInsanceName(String vnfInsanceName) {
        this.vnfInsanceName = vnfInsanceName;
    }

    public Set<VirtualLinkDescriptor> getVlds() {
        return vlds;
    }

    public void setVlds(Set<VirtualLinkDescriptor> vlds) {
        this.vlds = vlds;
    }

    public Map<String, String> getExtention() {
        return extention;
    }

    public void setExtention(Map<String, String> extention) {
        this.extention = extention;
    }

    @Override
    public Action getAction() {
        return Action.INSTANTIATE;
    }

    @Override
    public String toString() {
        return "OrVnfmInstantiateMessage{" +
                "vnfd=" + vnfd +
                ", vnfdf=" + vnfdf +
                ", vnfInsanceName='" + vnfInsanceName + '\'' +
                ", vlds=" + vlds +
                ", extention=" + extention +
                '}';
    }


}
