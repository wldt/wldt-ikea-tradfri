package it.unimore.dipi.iot.coap.tester;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.CoAP.Code;
import org.eclipse.californium.core.coap.Request;

/**
 * A simple CoAP Synchronous Client implemented using Californium Java Library
 * The simple client send a GET request to a target CoAP Resource with some custom request parameters
 */
public class CoapGetClientProcess {

	private static final String COAP_ENDPOINT = "coap://127.0.0.1:5683/temperature-sensor";

	public static void main(String[] args) {
		
		//Initialize coapClient
		CoapClient coapClient = new CoapClient(COAP_ENDPOINT);

		//Request Class is a generic CoAP message: in this case we want a GET.
		//"Message ID", "Token" and other header's fields can be set 
		Request request = new Request(Code.GET);

		//Set MID
		request.setMID(8888);

		//Set Token
		byte[] token = "a".getBytes();
		request.setToken(token);

		//Set Options
		//request.setOptions(new OptionSet().setAccept(MediaTypeRegistry.APPLICATION_JSON));
		
		//Synchronously send the GET message (blocking call)
		CoapResponse coapResp = null;

		try {

			coapResp = coapClient.advanced(request);

			//The "CoapResponse" message contains the response.
			String text = coapResp.getResponseText();
			System.out.println(text);
			System.out.println(Utils.prettyPrint(coapResp));
			System.out.println("Message ID: " + coapResp.advanced().getMID());
			System.out.println("Token: " + coapResp.advanced().getTokenString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}