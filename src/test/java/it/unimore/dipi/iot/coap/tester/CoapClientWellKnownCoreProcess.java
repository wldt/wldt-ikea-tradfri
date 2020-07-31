package it.unimore.dipi.iot.coap.tester;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.WebLink;
import org.eclipse.californium.core.coap.CoAP.Code;
import org.eclipse.californium.core.coap.LinkFormat;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.Request;
import java.io.IOException;
import java.util.Set;

/**
 * A simple CoAP Synchronous Client implemented using Californium Java Library
 * The simple client send a GET request to a target CoAP Resource with some custom request parameters
 */
public class CoapClientWellKnownCoreProcess {


	private static final String COAP_ENDPOINT = "coap://127.0.0.1:5683/.well-known/core";

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

		//Synchronously send the GET message (blocking call)
		CoapResponse coapResp = null;

		try {

			coapResp = coapClient.advanced(request);

			if(coapResp != null){

				if(coapResp.getOptions().getContentFormat() == MediaTypeRegistry.APPLICATION_LINK_FORMAT){

					Set<WebLink> links = LinkFormat.parse(coapResp.getResponseText());

					links.stream().forEach(link -> {

						System.out.println(String.format("Link: %s", link.getURI()));

						link.getAttributes().getAttributeKeySet().stream().forEach(attributeKey ->{
							System.out.println(String.format("Attribute (%s): %s", attributeKey, link.getAttributes().getAttributeValues(attributeKey)));
						});

					});

				} else {

					//The "CoapResponse" message contains the response.
					String text = coapResp.getResponseText();
					System.out.println(text);
					System.out.println(Utils.prettyPrint(coapResp));
					System.out.println("Message ID: " + coapResp.advanced().getMID());
					System.out.println("Token: " + coapResp.advanced().getTokenString());
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}