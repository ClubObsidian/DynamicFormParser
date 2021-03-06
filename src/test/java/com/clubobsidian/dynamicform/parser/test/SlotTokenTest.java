/*
   Copyright 2019 Club Obsidian and contributors.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.clubobsidian.dynamicform.parser.test;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import com.clubobsidian.dynamicform.parser.function.tree.FunctionTree;
import com.clubobsidian.dynamicform.parser.macro.MacroToken;
import com.clubobsidian.dynamicform.parser.slot.SlotToken;
import com.clubobsidian.wrappy.Configuration;
import com.clubobsidian.wrappy.ConfigurationSection;

public class SlotTokenTest {

	private static SlotToken token;
	
	@BeforeClass
	public static void loadSlotToken() {
		File file = new File("test.yml");
		Configuration config = Configuration.load(file);
		ConfigurationSection section = config.getConfigurationSection("1");
		token = new SlotToken(1, section);
	}
	
	@Test
	public void testSlotName() {
		String name = token.getLabel();
		assertTrue("Name was not test for slot token", name.equals("test"));
	}
	
	@Test
	public void testSlotIcon() {
		String icon = token.getIcon();
		assertTrue("Icon was not dirt", icon.equals("DIRT"));
	}
	
	@Test
	public void testSlotFunctionTree() {
		FunctionTree tree = token.getFunctionTree();
		int nodeSize = tree.getRootNodes().size();
		assertTrue("Node were not parsed for the function tree", nodeSize == 1);
	}
	
	@Test
	public void testMacroToken() {
		MacroToken macroToken = token.getMacroParser().getTokens().get(0);
		assertTrue("Macro token was not initialized", macroToken != null);
	}
}