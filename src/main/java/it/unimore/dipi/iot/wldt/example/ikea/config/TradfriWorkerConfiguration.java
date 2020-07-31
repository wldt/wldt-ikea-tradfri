package it.unimore.dipi.iot.wldt.example.ikea.config;

import it.unimore.dipi.iot.wldt.worker.WldtWorkerConfiguration;

/**
 * Author: Marco Picone, Ph.D. (marco.picone@unimore.it)
 * Date: 12/06/2020
 * Project: White Label Digital Twin Java Framework - (whitelabel-digitaltwin)
 */
public class TradfriWorkerConfiguration implements WldtWorkerConfiguration {

    private String gatewayIp;
    private int gatewayPort;
    private String securityKey;

    public TradfriWorkerConfiguration() {
    }

    public String getGatewayIp() {
        return gatewayIp;
    }

    public void setGatewayIp(String gatewayIp) {
        this.gatewayIp = gatewayIp;
    }

    public int getGatewayPort() {
        return gatewayPort;
    }

    public void setGatewayPort(int gatewayPort) {
        this.gatewayPort = gatewayPort;
    }

    public String getSecurityKey() {
        return securityKey;
    }

    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
    }

    @Override
    public String toString() {
        return "TradfriWorkerConfiguration{" +
                "gatewayIp='" + gatewayIp + '\'' +
                ", gatewayPort=" + gatewayPort +
                ", securityKey='" + securityKey + '\'' +
                '}';
    }
}
