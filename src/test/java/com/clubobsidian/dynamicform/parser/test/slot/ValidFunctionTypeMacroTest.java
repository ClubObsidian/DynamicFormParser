package com.clubobsidian.dynamicform.parser.test.slot;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import com.clubobsidian.dynamicform.parser.function.FunctionToken;
import com.clubobsidian.dynamicform.parser.function.FunctionType;
import com.clubobsidian.dynamicform.parser.function.tree.FunctionTree;
import com.clubobsidian.dynamicform.parser.slot.SlotToken;
import com.clubobsidian.wrappy.Configuration;
import com.clubobsidian.wrappy.ConfigurationSection;

public class ValidFunctionTypeMacroTest {

	
	@Test
	public void validFunctionTypeMacroTest()
	{
		File slotFolder = new File("test", "slot");
		File file = new File(slotFolder, "valid-function-type-macro.yml");
		Configuration config = Configuration.load(file);
		ConfigurationSection section = config.getConfigurationSection("0");
		SlotToken token = new SlotToken(0, section);
		FunctionTree tree = token.getFunctionTree();
		FunctionToken functionToken = tree.getRootNodes().get(0).getToken();
		assertTrue("Function node parsed incorrect function type", functionToken.getTypes().get(0) == FunctionType.CLICK);
	}
}