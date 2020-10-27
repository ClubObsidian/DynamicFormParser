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
package com.clubobsidian.dynamicgui.parser.slot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clubobsidian.dynamicgui.parser.function.tree.FunctionTree;
import com.clubobsidian.dynamicgui.parser.macro.MacroParser;
import com.clubobsidian.dynamicgui.parser.macro.MacroToken;
import com.clubobsidian.wrappy.ConfigurationSection;

public class SlotToken implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1898426889177654844L;
	
	private int index;
	private String icon;
	private String name;
	private MacroParser macroParser;
	private FunctionTree functionTree;
	private Map<String, String> metadata;
	
	public SlotToken(int index, ConfigurationSection section) {
		this(index, section, new ArrayList<MacroToken>());
	}
	
	public SlotToken(int index, ConfigurationSection section, List<MacroToken> macroTokens) {
		List<MacroToken> copyMacroTokens = new ArrayList<>();
		for(MacroToken macroToken : macroTokens) {
			copyMacroTokens.add(macroToken);
		}
		
		ConfigurationSection macrosSection = section.getConfigurationSection("macros");
		copyMacroTokens.add(new MacroToken(macrosSection));
		
		this.macroParser = new MacroParser(copyMacroTokens);
		
		this.index = index;
		this.icon = this.macroParser.parseStringMacros(section.getString("icon"));
		this.name = this.macroParser.parseStringMacros(section.getString("name"));
		
		ConfigurationSection functionsSection = section.getConfigurationSection("functions");
		this.functionTree = new FunctionTree(functionsSection, this.macroParser);
		
		ConfigurationSection metadataSection = section.getConfigurationSection("metadata");
		this.metadata = this.parseMetadata(metadataSection);		
	}
	
	/*private boolean parseBoolean(String data) {
		if(data == null) {
			return false;
		}
		
		String parsed = this.macroParser.parseStringMacros(data);
		if(data.equals("true")) {
			return Boolean.parseBoolean(parsed);
		}
		
		return false;
	}
	
	private int parseInteger(String data) {
		if(data == null) {
			return 0;
		}
		
		try {
			String parsed = this.macroParser.parseStringMacros(data);
			return Integer.valueOf(parsed);
		} catch(Exception ex) {
			return 0;
		}
	}*/
	
	private Map<String, String> parseMetadata(ConfigurationSection section) {
		Map<String, String> metadata = new HashMap<>();
		for(String key : section.getKeys()) {
			String parsedKey = this.macroParser.parseStringMacros(key);
			String value = section.getString(parsedKey);
			value = this.macroParser.parseStringMacros(value);
			metadata.put(parsedKey, value);
		}
		return metadata;
	}
	
	public int getIndex() {
		return this.index;
	}
	
	public String getIcon() {
		return this.icon;
	}
	
	public String getName() {
		return this.name;
	}
	
	public FunctionTree getFunctionTree() {
		return this.functionTree;
	}
	
	public MacroParser getMacroParser() {
		return this.macroParser;
	}
	
	public Map<String, String> getMetadata() {
		return this.metadata;
	}
}