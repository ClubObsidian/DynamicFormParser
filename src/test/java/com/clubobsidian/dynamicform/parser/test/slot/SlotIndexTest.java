package com.clubobsidian.dynamicform.parser.test.slot;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import com.clubobsidian.dynamicform.parser.slot.SlotToken;
import com.clubobsidian.wrappy.Configuration;
import com.clubobsidian.wrappy.ConfigurationSection;

public class SlotIndexTest {

	@Test
	public void testSlotIndex() {
		File slotFolder = new File("test", "slot");
		File file = new File(slotFolder, "slot-index.yml");
		Configuration config = Configuration.load(file);
		ConfigurationSection section = config.getConfigurationSection("13");
		SlotToken token = new SlotToken(13, section);
		assertTrue("Slot index is not 13", token.getIndex() == 13);
	}
}