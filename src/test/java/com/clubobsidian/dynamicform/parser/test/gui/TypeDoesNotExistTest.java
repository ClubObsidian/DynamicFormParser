package com.clubobsidian.dynamicform.parser.test.gui;

import com.clubobsidian.dynamicform.parser.gui.GuiToken;
import com.clubobsidian.wrappy.Configuration;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TypeDoesNotExistTest {

	@Test
	public void testDoesNotExist() {
		File slotFolder = new File("test", "gui");
		File file = new File(slotFolder, "alias.yml");
		Configuration config = Configuration.load(file);
		GuiToken token = new GuiToken(config);
		String type = token.getType();
		assertEquals(type, "simple");
	}
}