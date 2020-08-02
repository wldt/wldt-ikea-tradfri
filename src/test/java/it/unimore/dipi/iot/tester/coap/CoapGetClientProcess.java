package it.unimore.dipi.iot.tester.coap;

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

	private static final String COAP_ENDPOINT = "coap://127.0.0.1:5683/65538";

	public static void main(String[] args) {
		try{
			for(int i=0; i<10; i++){

				long startTimestamp = System.currentTimeMillis();
				executeGetRequest(COAP_ENDPOINT);
				long endTimestamp = System.currentTimeMillis();
				long timestampDiff = endTimestamp - startTimestamp;
				System.out.println("Elapsed Time: " + timestampDiff);
				Thread.sleep(10000);
			}

		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public static CoapResponse executeGetRequest(String endpointUrl){

		CoapClient coapClient = new CoapClient(endpointUrl);
		Request request = new Request(Code.GET);
		CoapResponse coapResp = null;

		try {
			coapResp = coapClient.advanced(request);
			System.out.println("Response: " + coapResp.getResponseText());
			return coapResp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}