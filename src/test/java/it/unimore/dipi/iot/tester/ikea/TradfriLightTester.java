package it.unimore.dipi.iot.tester.ikea;

import it.unimore.dipi.iot.wldt.example.ikea.connector.TradfriConnector;
import it.unimore.dipi.iot.wldt.example.ikea.model.TradfriLightBulb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TradfriLightTester {

    private static final String TAG = "[WLDT-IKEA-Tradfri-Process]";

    private static final Logger logger = LoggerFactory.getLogger(TradfriLightTester.class);

    private static final String GATEWAY_IP = "192.168.0.15";

    private static final int GATEWAY_PORT = 5684;

    public static void main(String[] args) {

        try{
            logger.info("{} - Starting ...", TAG);

            TradfriConnector tradfriConnector = new TradfriConnector(GATEWAY_IP, "btMCT1ECdrzM8uet");

            List<TradfriLightBulb> bulbList = retrieveLightBulbList(tradfriConnector);

            //bulbList.get(0).setOn(true);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static List<TradfriLightBulb> retrieveLightBulbList(TradfriConnector tradfriConnector){

        try{

            long start = System.currentTimeMillis();

            List<TradfriLightBulb> bulbList = tradfriConnector.dicoverBulbs();

            long end = System.currentTimeMillis();
            long diff = end - start;

            System.out.println("RetrieveLightBulbList Time[ms]: " + diff);

            bulbList.forEach(bulb -> logger.info("Bulb: {}", bulb.getName()));

            return bulbList;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
