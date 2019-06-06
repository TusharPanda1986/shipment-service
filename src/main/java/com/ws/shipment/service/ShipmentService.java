package com.ws.shipment.service;

import static com.ws.shipment.constants.ServiceConstants.BLANKS;
import static com.ws.shipment.constants.ServiceConstants.COMMA;
import static com.ws.shipment.constants.ServiceConstants.INCORRECT_RANGE_ERROR;
import static com.ws.shipment.constants.ServiceConstants.RANGE_VALIDATION_ERROR;
import static com.ws.shipment.constants.ServiceConstants.NON_WORD_CHAR_REGEX;
import static com.ws.shipment.constants.ServiceConstants.VALID_RANGE_REGEX;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ws.shipment.models.ZipCodeRange;

public class ShipmentService {


	/**
	 * @param args - Array containing the zip code ranges coming in from command line
	 * @return - List of the zip code ranges representing overlaps in the input ranges, if any.
	 */
	public List<ZipCodeRange> processRanges(String[] args) {
		List<ZipCodeRange> range = new ArrayList<>();
		if (args != null) {
			Arrays.stream(args).forEach(i -> {
				// replaces all [ or ] and spaces from the input.
				i = i.replaceAll(NON_WORD_CHAR_REGEX, BLANKS);
				validateInput(i);
				
				// splits the input range by comma
				String[] rangeArr = i.split(COMMA);
				ZipCodeRange zipCodeRange = new ZipCodeRange(rangeArr[0], rangeArr[1]);
				validateRangeOrder(zipCodeRange);
				range.add(zipCodeRange);
			});

			// Find overlapping zip ranges
			computeZipRanges(range);
		}
		return range;
	}

	/**
	 * @param range - List of all valid zip code ranges which needs to be sorted and merged,
	 * in case the consecutive list elements are overlapping
	 */
	private void computeZipRanges(List<ZipCodeRange> range) {
		// sorts the zip codes
		range.sort((ZipCodeRange o1, ZipCodeRange o2) -> o1.getStartingZip().compareTo(o2.getStartingZip()));
		
		// find overlaps and merge
		for (int i = 0; i < range.size() - 1; i++) {
			if (isOverlapping(range.get(i), range.get(i + 1))) {
				mergeRanges(range.get(i), range.get(i + 1));
				range.remove(i + 1);
				i--;
			}
		}

	}

	/**
	 * @param first - ZipCodeRange
	 * @param later - ZipCodeRange
	 */
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

	/**
	 * @param first -ZipCodeRange
	 * @param later - ZipCodeRange
	 * @return returns true if the two zip code ranges have an overlap, else returns false.
	 */
	private boolean isOverlapping(ZipCodeRange first, ZipCodeRange later) {
		return (later.getStartingZip() >= first.getStartingZip() && later.getStartingZip() <= first.getEndingZip())
				|| (later.getEndingZip() >= first.getStartingZip() && later.getEndingZip() <= first.getEndingZip());
	}

	/**
	 * @param zipCodeRange
	 * @description Checks if the ZipCodeRange has starting zipCode greater than ending zipCode.
	 * If true, throws a RuntimeException
	 */
	private void validateRangeOrder(ZipCodeRange zipCodeRange) {
		if (zipCodeRange.getStartingZip() > zipCodeRange.getEndingZip()) {
			throw new RuntimeException(MessageFormat.format(INCORRECT_RANGE_ERROR,
					zipCodeRange.getStartingZip().toString(), zipCodeRange.getEndingZip().toString()));
		}
	}

	/**
	 * @param range
	 * @description Checks if the zipcode range provided are 5 digit each and are separated by a comma.
	 */
	private void validateInput(String range) {
		if (!range.matches(VALID_RANGE_REGEX)) {
			throw new RuntimeException(MessageFormat.format(RANGE_VALIDATION_ERROR, range));
		}

	}

}
