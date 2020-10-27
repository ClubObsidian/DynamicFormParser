package com.clubobsidian.dynamicform.parser.test.gui;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import com.clubobsidian.dynamicform.parser.gui.GuiToken;
import com.clubobsidian.wrappy.Configuration;

public class AliasTest {

	@Test
	public void testAlias() {
		File slotFolder = new File("test", "gui");
		File file = new File(slotFolder, "alias.yml");
		Configuration config = Configuration.load(file);
		GuiToken token = new GuiToken(config);
		String alias = token.getAlias().get(0);
		assertTrue("Gui alias is not 'test'", alias.equals("test"));
	}
}