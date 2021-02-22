package com.clubobsidian.dynamicform.parser.test.gui;

import com.clubobsidian.dynamicform.parser.gui.GuiToken;
import com.clubobsidian.wrappy.Configuration;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class TestContentNewLine {

    @Test
    public void testContent() {
        File slotFolder = new File("test", "gui");
        File file = new File(slotFolder, "content.yml");
        Configuration config = Configuration.load(file);
        GuiToken token = new GuiToken(config);
        String content = token.getContent();
        System.out.println(content);
        assertEquals(content, "foo\nbar");
    }
}
