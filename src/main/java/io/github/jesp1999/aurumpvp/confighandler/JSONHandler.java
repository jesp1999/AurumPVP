package io.github.jesp1999.aurumpvp.confighandler;

import io.github.jesp1999.aurumpvp.kit.Kit;
import io.github.jesp1999.aurumpvp.map.MapInfo;

import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Used to load from JSON config files
 * @author Ian McDowell
 *
 */
public class JSONHandler extends JSONConstants{
	
	/**
	 * Takes JSON file of kits and translates it to Java objects
	 * @return List of valid kits with all
	 */
	public static Kit[] importKits() {
		throw new UnsupportedOperationException("TODO finish JSONHandler.importKits()");
	}
	
	/**
	 * Takes JSON file of maps and translates it to Java objects
	 * @return List of valid maps
	 */
	public static MapInfo[] importMaps() {
		throw new UnsupportedOperationException("TODO finish JSONHandler.importMaps()");
	}
}
