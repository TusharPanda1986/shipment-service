package com.ws.shipment;

import java.util.List;

import com.ws.shipment.models.ZipCodeRange;
import com.ws.shipment.service.ShipmentService;

public class EntryPoint {

	public static void main(String[] args) {
		ShipmentService shipmentService = new ShipmentService();
		List<ZipCodeRange> range = shipmentService.processRanges(args);
		System.out.println(range);
	}


	

}
