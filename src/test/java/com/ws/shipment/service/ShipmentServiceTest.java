package com.ws.shipment.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.ws.shipment.models.ZipCodeRange;

public class ShipmentServiceTest {

	ShipmentService shipmentService = new ShipmentService();

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void testIncorrectZipCodes() throws Exception {

		expectedEx.expect(RuntimeException.class);
		expectedEx.expectMessage(
				"The provided range 94133,941331 doesnt fit the criteria. Please fix the input and try again.");

		// A range with 6 digit zipcode
		String[] args = { "[94133,941331]", "[94200,94299]", "[94600,94699]" };
		shipmentService.processRanges(args);

	}

	@Test
	public void testMoreThanTwoZipCodesInOneRange() throws Exception {

		expectedEx.expect(RuntimeException.class);
		expectedEx.expectMessage(
				"The provided range 94133,94133,94133 doesnt fit the criteria. Please fix the input and try again.");

		String[] args = { "[94133,94133,94133]", "[94200,94299]", "[94600,94699]" };
		shipmentService.processRanges(args);

	}

	@Test
	public void testRangeWithbeginIndexGreaterThanEndIndex() throws Exception {

		expectedEx.expect(RuntimeException.class);
		expectedEx.expectMessage(
				"The starting zip code: 94299 should be smaller than or equal to the ending zip: 94200. Please fix the input and try again.");

		String[] args = { "[94133,94133]", "[94299,94200]", "[94600,94699]" };
		shipmentService.processRanges(args);

	}

	@Test
	public void testRangeWithEndIndexMissing() throws Exception {

		expectedEx.expect(RuntimeException.class);
		expectedEx.expectMessage(
				"The provided range 94200, doesnt fit the criteria. Please fix the input and try again.");

		String[] args = { "[94133,94133]", "[94200,]", "[94600,94699]" };
		shipmentService.processRanges(args);

	}

	@Test
	public void testRangeWithEndIndexAndStartIndexMissing() throws Exception {

		expectedEx.expect(RuntimeException.class);
		expectedEx.expectMessage("The provided range  doesnt fit the criteria. Please fix the input and try again.");

		String[] args = { "[94133,94133]", "[]", "[94600,94699]" };
		shipmentService.processRanges(args);

	}

	@Test
	public void testRangeWithBlankValues() throws Exception {

		expectedEx.expect(RuntimeException.class);
		expectedEx.expectMessage("The provided range  doesnt fit the criteria. Please fix the input and try again.");

		String[] args = { "[94133,94133]", "", "[94600,94699]" };
		shipmentService.processRanges(args);

	}

	@Test
	public void testNullInput() throws Exception {

		String[] args = null;
		List<ZipCodeRange> zipCodes = shipmentService.processRanges(args);
		assertThat(zipCodes, is(notNullValue()));
		assertThat(zipCodes.size(), is(0));
		assertThat(zipCodes.toString(), is("[]"));

	}

	@Test
	public void testBlankInput() throws Exception {

		String[] args = {};
		List<ZipCodeRange> zipCodes = shipmentService.processRanges(args);
		assertThat(zipCodes, is(notNullValue()));
		assertThat(zipCodes.size(), is(0));
		assertThat(zipCodes.toString(), is("[]"));

	}

	@Test
	public void testNonOverLappingRanges() throws Exception {

		String[] args = { "[94133,94133]", "[94200,94299]", "[94600,94699]" };
		List<ZipCodeRange> zipCodes = shipmentService.processRanges(args);
		assertThat(zipCodes, is(notNullValue()));
		assertThat(zipCodes.size(), is(3));
		assertThat(zipCodes.toString(), is("[[94133,94133], [94200,94299], [94600,94699]]"));

	}

	@Test
	public void testOverLappingRanges() throws Exception {

		String[] args = { "[94133,94133]", "[94200,94299]", "[94226,94699]" };
		List<ZipCodeRange> zipCodes = shipmentService.processRanges(args);
		assertThat(zipCodes, is(notNullValue()));
		assertThat(zipCodes.size(), is(2));
		assertThat(zipCodes.toString(), is("[[94133,94133], [94200,94699]]"));

	}
	
	@Test
	public void testOverLappingRangesWithSpacesAndTabs() throws Exception {

		String[] args = { "[94133   ,	94133]", "[94200,94299]", "[94226,94699]" };
		List<ZipCodeRange> zipCodes = shipmentService.processRanges(args);
		assertThat(zipCodes, is(notNullValue()));
		assertThat(zipCodes.size(), is(2));
		assertThat(zipCodes.toString(), is("[[94133,94133], [94200,94699]]"));

	}

}
