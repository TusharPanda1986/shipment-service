package com.ws.shipment;

import java.util.List;

import com.ws.shipment.models.ZipCodeRange;
import com.ws.shipment.service.ShipmentService;

/**
 * @author tushpa
 *
 */
public class EntryPoint {

	
	/**
	 * @param args
	 * 
	 * @description Entrypoint of the application. Takes input from command line and feeds it to ShipmentService.
	 */
	public static void main(String[] args) {
		ShipmentService shipmentService = new ShipmentService();
		List<ZipCodeRange> range = shipmentService.processRanges(args);
		System.out.println(range);
	}


	

}
