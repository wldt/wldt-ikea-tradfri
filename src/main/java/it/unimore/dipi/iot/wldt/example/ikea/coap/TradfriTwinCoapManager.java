package it.unimore.dipi.iot.wldt.example.ikea.coap;

import it.unimore.dipi.iot.wldt.example.ikea.exception.CoapModuleException;
import it.unimore.dipi.iot.wldt.example.ikea.model.TradfriLightBulb;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.List;
import java.util.Random;

/**
 * Author: Marco Picone, Ph.D. (marco.picone@unimore.it)
 * Date: 24/03/2020
 * Project: White Label Digital Twin Java Framework - (whitelabel-digitaltwin)
 */
public class TradfriTwinCoapManager {

    private static final String CALIFORNIUM_CONF_FILE = "Californium.properties";

    private static final String WELL_KNOWN_RESOURCE_ID = ".well-known/core";

    private static final Logger logger = LoggerFactory.getLogger(TradfriTwinCoapManager.class);

    private static final String TAG = "[WLDT-PhilipsHueCoapManager]";

    private List<TradfriLightBulb> tradfriLightBulbList;

    private CoapServer coapServer = null;

    private Random random = null;

    public TradfriTwinCoapManager(List<TradfriLightBulb> tradfriLightBulbList) {
        this.random = new Random();
        this.tradfriLightBulbList = tradfriLightBulbList;
        initCoapConfiguration();
    }

    private void initCoapConfiguration(){
        try{
            NetworkConfig config = NetworkConfig.createStandardWithFile(new File(CALIFORNIUM_CONF_FILE));
            NetworkConfig.setStandard(config);
            logger.info("Californium Configuration Correctly Loaded ! ");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void activate() throws CoapModuleException {

        try{

            if(this.tradfriLightBulbList == null || this.tradfriLightBulbList.size() == 0)
                throw new CoapModuleException("Impossible to start CoAP Module ! CoAP Resource List is Empty or Null, run a discovery or check device info");

            if(this.coapServer != null)
                throw new CoapModuleException("CoAP Module already active, reset before discovery and the new activation !");

            this.coapServer = new CoapServer();

            this.tradfriLightBulbList.forEach(tradfriLightBulb -> {
                try {
                    logger.info("Adding new CoAP Resource for Tradfri Light Bulb: {}-{}", tradfriLightBulb.getId(), tradfriLightBulb.getName());
                    this.coapServer.add(new TradfriLightBulbCoapResource(tradfriLightBulb));
                } catch (CoapModuleException e) {
                    logger.error("{} Error adding resource with descriptor: {} Reason: {}", TAG, tradfriLightBulb, e.getLocalizedMessage());
                }
            });

            this.coapServer.start();

        }catch (Exception e){
            throw new CoapModuleException(e.getLocalizedMessage());
        }
    }
}
