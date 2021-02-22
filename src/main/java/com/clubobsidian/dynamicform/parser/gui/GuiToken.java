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
package com.clubobsidian.dynamicform.parser.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.clubobsidian.dynamicform.parser.function.tree.FunctionTree;
import com.clubobsidian.dynamicform.parser.macro.MacroParser;
import com.clubobsidian.dynamicform.parser.macro.MacroToken;
import com.clubobsidian.dynamicform.parser.slot.SlotToken;
import com.clubobsidian.wrappy.ConfigurationSection;

public class GuiToken implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1815626830683338944L;
	
	private static final int MAX_SLOT_SIZE = 100; //Temporary fix
	
	private String title;
	private String content;
	private List<String> alias;
	private List<String> locations;
	private Map<String, List<Integer>> npcs;
	private Map<Integer, SlotToken> slots;
	private MacroParser macroParser;
	private FunctionTree functions;
	private List<String> loadMacros;
	private Map<String, String> metadata;
	
	public GuiToken(ConfigurationSection section) {
		this(section, new ArrayList<MacroToken>());
	}
	
	public GuiToken(ConfigurationSection section, List<MacroToken> macroTokens) {
		List<MacroToken> copyMacroTokens = new ArrayList<>();
		for(MacroToken token : macroTokens) {
			copyMacroTokens.add(token);
		}
		
		ConfigurationSection macrosSection = section.getConfigurationSection("macros");
		copyMacroTokens.add(new MacroToken(macrosSection));
		
		this.macroParser = new MacroParser(copyMacroTokens);
		this.title = this.macroParser.parseStringMacros(section.getString("title"));
		this.content = this.macroParser.parseStringMacros(section.getString("content"));
		this.alias = this.macroParser.parseListMacros(section.getStringList("alias"));
		this.locations = this.macroParser.parseListMacros(section.getStringList("locations"));
		this.loadNpcs(section);
		this.loadSlots(section);
		
		ConfigurationSection guiFunctionsSection = section.getConfigurationSection("functions");
		this.functions = new FunctionTree(guiFunctionsSection, this.macroParser);
		this.loadMacros = section.getStringList("load-macros");

		ConfigurationSection metadataSection = section.getConfigurationSection("metadata");
		this.metadata = this.parseMetadata(metadataSection);
	}
	
	private void loadNpcs(ConfigurationSection section) {
		this.npcs = new HashMap<>();
		ConfigurationSection npcSection = section.getConfigurationSection("npcs");
		for(String key : npcSection.getKeys()) {
			List<Integer> npcIds = npcSection.getIntegerList(key);
			this.npcs.put(key, npcIds);
		}
	}
	
	
	private void loadSlots(ConfigurationSection section) {
		this.slots = new LinkedHashMap<>();
		for(int i = 0; i < MAX_SLOT_SIZE; i++) {
			ConfigurationSection slotSection = section.getConfigurationSection(String.valueOf(i));
			if(!slotSection.isEmpty()) {
				SlotToken token = new SlotToken(i, slotSection, this.macroParser.getTokens());
				this.slots.put(i, token);
			}
		}
	}

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

	public String getTitle() {
		return this.title;
	}

	public String getContent() {
		return this.content;
	}
	
	public List<String> getAlias() {
		return this.alias;
	}
	
	public List<String> getLocations() {
		return this.locations;
	}
	
	public Map<String, List<Integer>> getNpcs() {
		return this.npcs;
	}
	
	public Map<Integer, SlotToken> getSlots() {
		return this.slots;
	}
	
	public FunctionTree getFunctions() {
		return this.functions;
	}
	
	public MacroParser getMacroParser() {
		return this.macroParser;
	}
	
	public List<String> getLoadMacros() {
		return this.loadMacros;
	}

	public Map<String, String> getMetadata() {
		return this.metadata;
	}
}