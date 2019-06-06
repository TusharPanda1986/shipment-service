package com.ws.shipment.service;

import static com.ws.shipment.constants.ServiceConstants.INCORRECT_RANGE_ERROR;
import static com.ws.shipment.constants.ServiceConstants.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ws.shipment.models.ZipCodeRange;

public class ShipmentService {
	
    public List<ZipCodeRange> processRanges(String[] args) {
		List<ZipCodeRange> range = new ArrayList<>();
		Arrays.stream(args).forEach(i -> {
			i = i.replaceAll(SQUARE_BRACKET_REGEX, BLANKS);
			validateInput(i.trim());
			String[] rangeArr = i.split(COMMA);
			range.add(validateRangeOrder(new ZipCodeRange(rangeArr[0], rangeArr[1])));
		});

		//Find overlapping zip ranges
		fetchRestrictedZipRanges(range);
		return range;
	}
	
	private void fetchRestrictedZipRanges(List<ZipCodeRange> range) {
		range.sort((ZipCodeRange o1, ZipCodeRange  o2) -> o1.getStartingZip().compareTo(o2.getStartingZip()));
		for(int i = 0; i < range.size()-1 ; i ++) {
			if(isOverlapping(range.get(i),range.get(i+1))) {
				mergeRanges(range.get(i),range.get(i+1));
				range.remove(i+1);
				i--;
			}
		}
		
	}
	
	
	private void mergeRanges(ZipCodeRange first, ZipCodeRange later) {
		if (first.getStartingZip() <= later.getStartingZip()) {
			first.setStartingZip(first.getStartingZip());
		} else {
			first.setStartingZip(later.getStartingZip());
		}

		if (first.getEndingZip() >= later.getEndingZip()) {
			first.setEndingZip(first.getEndingZip());
		} else {
			first.setEndingZip(later.getEndingZip());
		}
	}
	
	
	private boolean isOverlapping(ZipCodeRange first, ZipCodeRange later) {
		return (later.getStartingZip() >= first.getStartingZip() && later.getStartingZip() <= first.getEndingZip())
				|| (later.getEndingZip() >= first.getStartingZip() && later.getEndingZip() <= first.getEndingZip());
	}

	private ZipCodeRange validateRangeOrder(ZipCodeRange zipCodeRange) {
		if (zipCodeRange.getStartingZip() > zipCodeRange.getEndingZip()) {
			throw new RuntimeException(MessageFormat.format(INCORRECT_RANGE_ERROR,
					zipCodeRange.getStartingZip().toString(), zipCodeRange.getEndingZip().toString()));
		}
		return zipCodeRange;
	}

	private void validateInput(String range) {
		if (!range.matches(VALID_RANGE_REGEX)) {
			throw new RuntimeException(MessageFormat.format(RANGE_VALIDATION_ERROR, range));
		}

	}

}
