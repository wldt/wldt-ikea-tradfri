package it.unimore.dipi.iot.wldt.example.ikea;

import it.unimore.dipi.iot.wldt.example.ikea.model.LightBulb;
import it.unimore.dipi.iot.wldt.example.ikea.model.Room;
import it.unimore.dipi.iot.wldt.example.ikea.connector.TradfriConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class WldtIkeaProcess {

    private static final String TAG = "[WLDT-IKEA-Tradfri-Process]";

    private static final Logger logger = LoggerFactory.getLogger(WldtIkeaProcess.class);

    private static final String GATEWAY_IP = "192.168.0.19";

    private static final int GATEWAY_PORT = 5684;

    public static void main(String[] args) {

        try{
            logger.info("{} - Starting ...", TAG);

            TradfriConnector tradfriConnector = new TradfriConnector(GATEWAY_IP, "YOUR_GATEWAY_SECRET");

            List<Room> roomList = tradfriConnector.discoverRooms();
            roomList.forEach(room -> logger.info(room.name));

            List<LightBulb> bulbList = tradfriConnector.dicoverBulbs();
            bulbList.forEach(bulb -> logger.info("Bulb: {}", bulb.getName()));

            bulbList.get(0).setOn(true);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
