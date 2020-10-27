package com.clubobsidian.dynamicform.parser.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.clubobsidian.dynamicform.parser.function.FunctionType;

public class FunctionTypeTest {

	@Test
	public void clickIsClickTest() {
		assertTrue(FunctionType.CLICK.isClick());
	}
	
	@Test
	public void loadIsNotClickTest() {
		assertFalse(FunctionType.LOAD.isClick());
	}
	
	@Test
	public void failIsNotClickTest() {
		assertFalse(FunctionType.FAIL.isClick());
	}
	
	@Test
	public void switchMenuNotClickTest() {
		assertFalse(FunctionType.SWITCH_MENU.isClick());
	}
	
	@Test
	public void exitMenuNotClickTest() {
		assertFalse(FunctionType.EXIT_MENU.isClick());
	}
}