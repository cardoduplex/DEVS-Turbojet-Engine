/*
 * CSE 593 - Fall 2016 - Applied Project
 * Author  : Lucio Ortiz and Robert Blazewicz
 * Version : DEVSJAVA 3.0
 * Date    : 2016-09-18
 */
package experiment.toolkit;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * The Class Settings.
 */
public class TypeMapping {

  /**
   * The Enum theType.
   */
  public enum theType {
    /** The boolean. */ eBoolean,
    /** The char. */ eChar,
    /** The byte. */ eByte,
    /** The short. */ eShort,
    /** The int. */ eInt,
    /** The long. */ eLong,
    /** The float. */ eFloat,
    /** The double. */ eDouble,
    /** The string. */ eString
  };

  /** The type map. */
  private Map<String, theType> typeMap = new HashMap<String, theType>();

  /** The Constant defaultPathname. */
  private static final String defaultPathname = ".";

  /** The Constant defaultFilename. */
  private static final String defaultFilename = "TypeMapping.json";

  /** The pathname. */
  private String pathname;

  /** The filename. */
  private String filename;

  /**
   * Instantiates a new type mapping.
   */
  public TypeMapping() {
    pathname = defaultPathname;
    filename = defaultFilename;
  }

  /**
   * Parses the file.
   *
   * @throws RuntimeException the runtime exception
   */
  public void parseFile() throws RuntimeException {
    JSONParser parser = new JSONParser();
    final Path filePath = Paths.get(pathname, filename);
    try (FileReader fileReader = new FileReader(filePath.toFile())) {
      final Object obj = parser.parse(fileReader);
      final JSONObject jsonObject = (JSONObject) obj;
      parseType(jsonObject, "boolean", theType.eBoolean);
      parseType(jsonObject, "char", theType.eChar);
      parseType(jsonObject, "byte", theType.eByte);
      parseType(jsonObject, "short", theType.eShort);
      parseType(jsonObject, "int", theType.eInt);
      parseType(jsonObject, "long", theType.eLong);
      parseType(jsonObject, "float", theType.eFloat);
      parseType(jsonObject, "double", theType.eDouble);
      parseType(jsonObject, "String", theType.eString);
    } catch (IOException | ParseException e) {
      throw new RuntimeException("TypeMapping file " + filePath + ": " + e.getMessage());
    }
  }

  /**
   * Parses the type.
   *
   * @param jsonObject the json object
   * @param typeLabel the type label
   * @param type the type
   */
  public void parseType(final JSONObject jsonObject, final String typeLabel, final theType type) {
    final JSONArray arrayBoolean = (JSONArray) jsonObject.get(typeLabel);
    if (arrayBoolean != null)
      for (int i = 0; i < arrayBoolean.size(); i++)
        typeMap.put((String) arrayBoolean.get(i), type);
  }

  /**
   * Gets the pathname.
   *
   * @return the pathname
   */
  public String getPathname() {
    return pathname;
  }

  /**
   * Gets the filename.
   *
   * @return the filename
   */
  public String getFilename() {
    return filename;
  }

  /**
   * Sets the pathname.
   *
   * @param pathname the new pathname
   */
  public void setPathname(String pathname) {
    this.pathname = pathname;
  }

  /**
   * Sets the filename.
   *
   * @param filename the new filename
   */
  public void setFilename(String filename) {
    this.filename = filename;
  }

/**
   * Gets the map.
   *
   * @return the map
   */
  public Map<String, theType> getMap() {
    return typeMap;
  }

  /**
   * Dump.
   */
  public void dump() {
    dump("Type: ");
  }

  /**
   * Dump.
   *
   * @param prefix the prefix
   */
  public void dump(final String prefix) {
    for (Entry<String, theType> entry : typeMap.entrySet())
      System.out.println(prefix + String.format("%-8s", entry.getValue()) + " = "+ entry.getKey());
  }
}
