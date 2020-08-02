package it.unimore.dipi.iot.wldt.example.ikea;

import it.unimore.dipi.iot.wldt.engine.WldtConfiguration;
import it.unimore.dipi.iot.wldt.engine.WldtEngine;
import it.unimore.dipi.iot.wldt.example.ikea.config.TradfriWorkerConfiguration;
import it.unimore.dipi.iot.wldt.example.ikea.worker.TradfriLightWorker;
import it.unimore.dipi.iot.wldt.exception.WldtConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WldtIkeaTradfriProcess {

    private static final String TAG = "[WLDT-IKEA-Tradfri-Process]";

    private static final Logger logger = LoggerFactory.getLogger(WldtIkeaTradfriProcess.class);

    private static final String GATEWAY_IP = "192.168.0.15";

    private static final int GATEWAY_PORT = 5684;

    public static void main(String[] args) {

        try{
            logger.info("{} - Starting ...", TAG);

            logger.info("{} Initializing WLDT-Engine ... ", TAG);

            //Manual creation of the WldtConfiguration
            WldtConfiguration wldtConfiguration = new WldtConfiguration();
            wldtConfiguration.setDeviceNameSpace("it.unimore.dipi.things");
            wldtConfiguration.setWldtBaseIdentifier("wldt");
            wldtConfiguration.setWldtStartupTimeSeconds(10);
            wldtConfiguration.setApplicationMetricsEnabled(false);

            TradfriWorkerConfiguration tradfriWorkerConfiguration = new TradfriWorkerConfiguration();
            tradfriWorkerConfiguration.setGatewayIp(GATEWAY_IP);
            tradfriWorkerConfiguration.setSecurityKey("btMCT1ECdrzM8uet");

            WldtEngine wldtEngine = new WldtEngine(wldtConfiguration);
            wldtEngine.addNewWorker(new TradfriLightWorker(wldtEngine.getWldtId(), tradfriWorkerConfiguration));
            wldtEngine.startWorkers();

        }catch (Exception | WldtConfigurationException e){
            e.printStackTrace();
        }
    }

}
