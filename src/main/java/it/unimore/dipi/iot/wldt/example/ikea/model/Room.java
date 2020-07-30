package it.unimore.dipi.iot.wldt.example.ikea.model;

import it.unimore.dipi.iot.wldt.example.ikea.exception.TradfriException;
import it.unimore.dipi.iot.wldt.example.ikea.connector.TradfriConnector;
import org.eclipse.californium.core.CoapResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Room {

    public final TradfriConnector gateway;
    public final int roomId;
    public final String name;

    public Room(TradfriConnector gateway, int roomId, CoapResponse response) throws TradfriException {
        this.gateway = gateway;
        this.roomId = roomId;
        try {
            JSONObject json = new JSONObject(response.getResponseText());
            name = json.getString(TradfriConstants.NAME);
        } catch (JSONException ex) {
            throw new TradfriException(ex);
        }
    }

    public List<Scene> discoverScenes() throws TradfriException {
        List<Scene> scenes = new ArrayList<Scene>();
        try {
            CoapResponse response = gateway.get(TradfriConstants.SCENE + "/" + roomId);
            if (response == null) return null;
            JSONArray scenesData = new JSONArray(response.getResponseText());
            for (int i = 0; i < scenesData.length(); i++) {
                int sceneId = scenesData.getInt(i);
                response = gateway.get(TradfriConstants.SCENE + "/" + roomId + "/" + sceneId);
                scenes.add(new Scene(gateway, sceneId, response));
            }
        } catch (JSONException ex) {
            throw new TradfriException(ex);

        }
        return scenes;
    }
}
