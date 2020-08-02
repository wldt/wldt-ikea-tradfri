package it.unimore.dipi.iot.tester.coap;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.CoAP.Code;
import org.eclipse.californium.core.coap.Request;

/**
 * A simple CoAP Synchronous Client implemented using Californium Java Library
 * The simple client send a GET request to a target CoAP Resource with some custom request parameters
 */
public class CoapPostClientProcess {

	private static final String COAP_ENDPOINT = "coap://127.0.0.1:5683/65538";

	public static void main(String[] args) {
		try{
			for(int i=0; i<10; i++){
				long startTimestamp = System.currentTimeMillis();
				executePostRequest(COAP_ENDPOINT);
				long endTimestamp = System.currentTimeMillis();
				long timestampDiff = endTimestamp - startTimestamp;
				System.out.println("Elapsed Time: " + timestampDiff);
				Thread.sleep(20000);
			}

		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public static CoapResponse executePostRequest(String endpointUrl){

		CoapClient coapClient = new CoapClient(endpointUrl);
		Request request = new Request(Code.POST);

		CoapResponse coapResp = null;

		try {

			coapResp = coapClient.advanced(request);
			return coapResp;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}