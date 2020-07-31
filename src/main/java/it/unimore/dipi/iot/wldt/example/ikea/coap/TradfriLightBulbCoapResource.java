package it.unimore.dipi.iot.wldt.example.ikea.coap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unimore.dipi.iot.wldt.example.ikea.exception.CoapModuleException;
import it.unimore.dipi.iot.wldt.example.ikea.model.TradfriLightBulb;
import it.unimore.dipi.iot.wldt.utils.CoreInterfaces;
import it.unimore.dipi.iot.wldt.utils.SenML;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Author: Marco Picone, Ph.D. (marco.picone@unimore.it)
 * Date: 24/03/2020
 * Project: White Label Digital Twin Java Framework - (whitelabel-digitaltwin)
 */
public class TradfriLightBulbCoapResource extends CoapResource {

    private static final String TAG = "[WLDT-TradfriLightBulbCoapResource]";

    private static final Logger logger = LoggerFactory.getLogger(TradfriLightBulbCoapResource.class);

    private TradfriLightBulb tradfriLightBulb;

    private ObjectMapper objectMapper = null;

    public TradfriLightBulbCoapResource(TradfriLightBulb tradfriLightBulb) throws CoapModuleException {

        super(UUID.randomUUID().toString());

        if(tradfriLightBulb == null)
            throw new CoapModuleException("tradfriLightBulb = null !");

        this.tradfriLightBulb = tradfriLightBulb;

        if(tradfriLightBulb.getId() > 0)
            setName(Integer.toString(tradfriLightBulb.getId()));

        if(tradfriLightBulb.getName() != null)
            getAttributes().setTitle(tradfriLightBulb.getName());

        getAttributes().addAttribute("rt", tradfriLightBulb.getType());

        getAttributes().addAttribute("if", CoreInterfaces.CORE_A.getValue());

        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    @Override
    public void handleGET(CoapExchange exchange) {

        try{
            exchange.respond(CoAP.ResponseCode.CONTENT, getSenmlJsonResponse(), MediaTypeRegistry.APPLICATION_JSON);
        }catch (Exception e){
            logger.error("Error handling Get ! Msg: {}", e.getLocalizedMessage());
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    private String getSenmlJsonResponse() {

        try{

            SenML senmlData = new SenML();
            senmlData.setBaseName(String.format("%d:%s:%s", this.tradfriLightBulb.getId(), this.tradfriLightBulb.getName(), this.tradfriLightBulb.getFirmware()));
            senmlData.setBooleanValue(this.tradfriLightBulb.isOn());
            senmlData.setUpdateTime(System.currentTimeMillis());

            return  objectMapper.writeValueAsString(senmlData);

        }catch (Exception e){
            logger.error("Error generating SENML Response ! Msg: {}", e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public void handlePOST(CoapExchange exchange) {

        try{

            logger.debug("Received POST Request with body: {}", exchange.getRequestPayload());

            /*
            PhilipsHueLightDescriptor lightDescriptor = PhilipsHueBridgeConnector.getInstance().getBridgeLight(bridgeIp, bridgeUsername, twinCoapResourceDescriptor.getId());
            boolean result = PhilipsHueBridgeConnector.getInstance().changeLightState(bridgeIp, bridgeUsername, twinCoapResourceDescriptor.getId(), !lightDescriptor.isOn());

            if(result)
                exchange.respond(CoAP.ResponseCode.CHANGED);
            else
                exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
            */

            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);

        }catch (Exception e){
            logger.error("Error handling POST ! Msg: {}", e.getLocalizedMessage());
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        super.handlePUT(exchange);
    }

    @Override
    public void handleDELETE(CoapExchange exchange) {
        super.handleDELETE(exchange);
    }
}
