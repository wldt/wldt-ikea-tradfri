package it.unimore.dipi.iot.wldt.example.ikea.worker;

import it.unimore.dipi.iot.wldt.example.ikea.coap.TradfriTwinCoapManager;
import it.unimore.dipi.iot.wldt.example.ikea.config.TradfriWorkerConfiguration;
import it.unimore.dipi.iot.wldt.example.ikea.connector.TradfriConnector;
import it.unimore.dipi.iot.wldt.example.ikea.model.TradfriLightBulb;
import it.unimore.dipi.iot.wldt.exception.WldtConfigurationException;
import it.unimore.dipi.iot.wldt.exception.WldtRuntimeException;
import it.unimore.dipi.iot.wldt.worker.WldtWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class TradfriLightWorker extends WldtWorker<TradfriWorkerConfiguration, String, String> {

    private static final Logger logger = LoggerFactory.getLogger(TradfriLightWorker.class);

    private String wldtId = null;

    private TradfriTwinCoapManager tradfriTwinCoapManager = null;

    private TradfriConnector tradfriConnector = null;

    public TradfriLightWorker(String wldtId, TradfriWorkerConfiguration configuration) {
        super(configuration);
        this.wldtId = wldtId;
    }

    @Override
    public void startWorkerJob() throws WldtConfigurationException, WldtRuntimeException {

        try{

            if(this.getWldtWorkerConfiguration().getGatewayIp() != null && this.getWldtWorkerConfiguration().getSecurityKey() != null){

                String gatewayIp = this.getWldtWorkerConfiguration().getGatewayIp();
                String securityKey = this.getWldtWorkerConfiguration().getSecurityKey();

                tradfriConnector = new TradfriConnector(gatewayIp, securityKey);

                List<TradfriLightBulb> bulbList = this.tradfriConnector.dicoverBulbs();

                logger.info("Discovered {} IKEA Bulbs", bulbList.size());

                this.tradfriTwinCoapManager = new TradfriTwinCoapManager(bulbList);
                this.tradfriTwinCoapManager.activate();

            }
            else
                throw new WldtConfigurationException("Wrong Configuration parameters !");

        }catch (Exception e){
            e.printStackTrace();
            throw new WldtRuntimeException(e.getLocalizedMessage());
        }
    }

}
