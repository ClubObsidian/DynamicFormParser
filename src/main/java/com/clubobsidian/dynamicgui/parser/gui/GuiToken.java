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
package com.clubobsidian.dynamicgui.parser.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.clubobsidian.dynamicgui.parser.function.tree.FunctionTree;
import com.clubobsidian.dynamicgui.parser.macro.MacroParser;
import com.clubobsidian.dynamicgui.parser.macro.MacroToken;
import com.clubobsidian.dynamicgui.parser.slot.SlotToken;
import com.clubobsidian.wrappy.ConfigurationSection;

public class GuiToken implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1815626830683338944L;
	
	private static final int MAX_SLOT_SIZE = 100; //Temporary fix
	
	private String title;
	private List<String> alias;
	private List<String> locations;
	private Map<String, List<Integer>> npcs;
	private Map<Integer, SlotToken> slots;
	private MacroParser macroParser;
	private FunctionTree functions;
	private List<String> loadMacros;
	
	public GuiToken(ConfigurationSection section) {
		this(section, new ArrayList<MacroToken>());
	}
	
	public GuiToken(ConfigurationSection section, List<MacroToken> macroTokens) {
		List<MacroToken> copyMacroTokens = new ArrayList<MacroToken>();
		for(MacroToken token : macroTokens) {
			copyMacroTokens.add(token);
		}
		
		ConfigurationSection macrosSection = section.getConfigurationSection("macros");
		copyMacroTokens.add(new MacroToken(macrosSection));
		
		this.macroParser = new MacroParser(copyMacroTokens);
		this.title = this.macroParser.parseStringMacros(section.getString("title"));
		this.alias = this.macroParser.parseListMacros(section.getStringList("alias"));
		this.locations = this.macroParser.parseListMacros(section.getStringList("locations"));
		this.loadNpcs(section);
		this.loadSlots(section);
		
		ConfigurationSection guiFunctionsSection = section.getConfigurationSection("functions");
		this.functions = new FunctionTree(guiFunctionsSection, this.macroParser);
		this.loadMacros = section.getStringList("load-macros");
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
	
	public String getTitle() {
		return this.title;
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
}