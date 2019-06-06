package com.ws.shipment.constants;

public class ServiceConstants {
	
	public static final String RANGE_VALIDATION_ERROR = "The provided range {0} doesn't fit the criteria. Please fix the input and try again.";
	public static final String INCORRECT_RANGE_ERROR = "The starting zip code: {0} should be smaller than or equal to the ending zip: {1}. Please fix the input and try again.";
	public static final String BLANKS = "";
	public static final String NON_WORD_CHAR_REGEX = "^\\W|\\W$|\\s";
	public static final String VALID_RANGE_REGEX = "^[0-9]{5},[0-9]{5}$";
	public static final String COMMA = ",";
	
}
