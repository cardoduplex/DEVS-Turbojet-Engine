/*
 * CSE 593 - Fall 2016 - Applied Project
 * Author  : Lucio Ortiz and Robert Blazewicz
 * Version : DEVSJAVA 3.0
 * Date    : 2016-09-24
 */
package experiment.toolkit;

import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * The Class ValueSetLoad.
 */
public class ValueSetLoad {

  /** The settings. */
  private final Settings settings = SettingsSingleton.getInstance();

  /** The Constant defaultFilename. */
  private static final String defaultFilename = "ValueSet.json";

  /** The pathname. */
  private String pathname;

  /** The filename. */
  private String filename;

  /**
   * Instantiates a new value set load.
   */
  public ValueSetLoad() {
    pathname = settings.lookupString("ValueSetPath", settings.getPathnameConfig());
    filename = settings.lookupString("ValueSetFile", defaultFilename);
  }

  /**
   * Load.
   *
   * @param valueSet the value set
   */
  public void load(final ValueSet valueSet) {
    loadValueSet(valueSet);
  }

  /**
   * Load.
   *
   * @param valueSet the value set
   * @param filename the filename
   */
  public void load(final ValueSet valueSet, final String filename) {
    this.filename = filename;
    loadValueSet(valueSet);
  }

  /**
   * Load.
   *
   * @param valueSet the value set
   * @param pathname the pathname
   * @param filename the filename
   */
  public void load(final ValueSet valueSet, final String pathname, final String filename) {
    this.pathname = pathname;
    this.filename = filename;
    loadValueSet(valueSet);
  }

  /**
   * Load value set.
   *
   * @param valueSet the value set
   */
  private void loadValueSet(final ValueSet valueSet) {
    final JSONParser parser = new JSONParser();
    final Path filePath = Paths.get(pathname, filename);
    try (FileReader fileReader = new FileReader(filePath.toFile())) {
      final Object obj = parser.parse(fileReader);
      final JSONObject jsonObject = (JSONObject) obj;
      for (final Object jsonKey : jsonObject.keySet()) {
        final String typeDescription = (String)jsonKey;
        valueSet.setDescription(typeDescription);
        final Object typeValue = jsonObject.get(typeDescription);
        if (typeValue instanceof JSONArray) {
          final JSONArray typeArray = (JSONArray)typeValue;
          for (int i = 0; i < typeArray.size(); i++) {
            final JSONObject typeObject = (JSONObject) typeArray.get(i);
            for (final Object typeKey : typeObject.keySet()) {
            final String groupName = (String)typeKey;
            final Object groupValue = typeObject.get(typeKey);
            if (groupValue instanceof JSONArray) {
              final JSONArray valueArray = (JSONArray)groupValue;
              for (int j = 0; j < valueArray.size(); j++) {
                final JSONObject valueObject = (JSONObject) valueArray.get(j);
                for (final Object valueKey : valueObject.keySet()) {
                  final String valueName = (String)valueKey;
                  final Object keyValue = valueObject.get(valueKey);
                  if (keyValue instanceof JSONArray) {
                    final JSONArray jsonArray = (JSONArray)keyValue;
                    if (jsonArray.size() <= 6) {
                      final Object value0 = jsonArray.get(0);
                      final Object value1 = jsonArray.get(1);
                      final Object value2 = jsonArray.get(2);
                      final Object value3 = jsonArray.get(3);
                      final Object value4 = jsonArray.get(4);
                      final Object value5 = jsonArray.get(5);
                      if (!(value0 instanceof String))
                        throw new Exception("Array index 0 must be a value-type string: " + valueName);
                      if (!(value1 instanceof String))
                        throw new Exception("Array index 1 must be a string of the selected value-type: " + valueName);
                      if (!(value2 instanceof String))
                        throw new Exception("Array index 2 must be a string: " + valueName);
                      if (!(value3 instanceof String))
                        throw new Exception("Array index 3 must be a string: " + valueName);
                      if (!(value4 instanceof String))
                        throw new Exception("Array index 4 must be a string: " + valueName);
                      if (!(value5 instanceof Boolean))
                        throw new Exception("Array index 5 must be a boolean: " + valueName);
                      valueSet.fileAddValue(groupName, valueName, (String)value0, (String)value1, (String)value2, (String)value3, (String)value4, (Boolean)value5);
                    } else
                      throw new Exception("Array must have 4 entries: " + valueName);
                  } else if (keyValue instanceof JSONObject)
                    throw new Exception("Nested-object unsupported: " + valueName);
                  else
                    throw new Exception("Element unsupported: " + valueName);
                }
              }
            } else if (groupValue instanceof JSONObject)
              throw new Exception("Nested-object unsupported: " + groupName);
            else
              throw new Exception("Element unsupported: " + groupName);
            }
          }
        } else if (typeValue instanceof JSONObject)
          throw new Exception("Nested-object unsupported: " + typeDescription);
        else
          throw new Exception("Element unsupported: " + typeDescription);
      }
    } catch (Exception e) {
      throw new RuntimeException("ValueSet file " + filePath + ": " + e.getMessage());
    }
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
}
