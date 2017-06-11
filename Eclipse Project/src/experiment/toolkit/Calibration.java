/*
 * CSE 593 - Fall 2016 - Applied Project
 * Author  : Lucio Ortiz and Robert Blazewicz
 * Version : DEVSJAVA 3.0
 * Date    : 2016-08-31
 */
package experiment.toolkit;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * The Class Calibration.
 */
public class Calibration {

  /** The settings. */
  private final Settings settings = SettingsSingleton.getInstance();

  /** The pattern. */
  public static final String thePattern = "^K([ea])([A-Z]+)_(\\w+)_([\\w_]+)$";

  /** The calibration pattern. */
  public final Pattern calibrationPattern = Pattern.compile(thePattern);

  /** The calibration map. */
  private Map<String, String> calibrationMap = new HashMap<String, String>();

  /** The type mapping. */
  private TypeMapping typeMapping;

  /** The Constant defaultFilename. */
  private static final String defaultFilename = "Calibration.json";

  /** The pathname. */
  private String pathname;

  /** The filename. */
  private String filename;

  /** The debug. */
  private final boolean debug;

  /**
   * Instantiates a new calibration.
   *
   * @param singleton the singleton
   * @throws RuntimeException the runtime exception
   */
  public Calibration(final boolean singleton) throws RuntimeException {
    // Resolve path and file name for calibration data file.
    pathname = settings.lookupString("CalibrationPath", settings.getPathnameConfig());
    filename = settings.lookupString("CalibrationFile", defaultFilename);

    // Display extra output for debugging
    debug = settings.lookupBool("CalibrationDebug", "true");

    if (singleton)
      loadCalibrations();
  }

  /**
   * Load.
   */
  public void load() {
    loadCalibrations();
  }

  /**
   * Load.
   *
   * @param filename the filename
   */
  public void load(final String filename) {
    this.filename = filename;
    loadCalibrations();
  }

  /**
   * Load.
   *
   * @param pathname the pathname
   * @param filename the filename
   */
  public void load(final String pathname, final String filename) {
    this.pathname = pathname;
    this.filename = filename;
    loadCalibrations();
  }

  /**
   * Load calibrations.
   *
   * @throws RuntimeException the runtime exception
   */
  private void loadCalibrations() throws RuntimeException {
    // Resolve path and file name for type mapping data file, and load type map.
    typeMapping = new TypeMapping();
    typeMapping.setPathname(settings.lookupString("TypeMappingPath", settings.getPathnameConfig()));
    typeMapping.setFilename(settings.lookupString("TypeMappingFile", typeMapping.getFilename()));
    typeMapping.parseFile();

    // Load calibration data
    final JSONParser parser = new JSONParser();
    final Path filePath = Paths.get(pathname, filename);
    try (FileReader fileReader = new FileReader(filePath.toFile())) {
      final Object obj = parser.parse(fileReader);
      final JSONObject jsonObject = (JSONObject) obj;
      for (Object key : jsonObject.keySet()) {
        final String keyName = (String)key;
        final Matcher matcher = calibrationPattern.matcher(keyName);
        if (matcher.matches()) {
          final Object keyValue = jsonObject.get(keyName);
          switch (matcher.group(1)) {
          case "e":
            if (keyValue instanceof JSONArray)
              System.err.print("Calibration: Calibration type element can't support array value: " + keyName);
            else if (keyValue instanceof JSONObject)
              System.err.print("Calibration: Calibration type element can't support nested-object value: " + keyName);
            else {
              final TypeMapping.theType valueType = getValueType(matcher.group(3));
              if (valueType == null)
                System.err.print("Calibration: Calibration value type not in type mapping: " + keyName);
              else
                calibrationMap.put(keyName, keyValue.toString());
            }
            break;
          case "a":
            if (keyValue instanceof JSONArray)
              System.err.print("Calibration: Calibration type array unsupported: " + keyName);
            else if (keyValue instanceof JSONObject)
              System.err.print("Calibration: Calibration type nested-object unsupported: " + keyName);
            else
              System.err.print("Calibration: Calibration type array can't support element value: " + keyName);
            break;
          default:
            System.err.print("Calibration: Calibration type must be element or array: " + keyName);
            break;
          }
        } else {
          System.err.print("Calibration: Invalid calibration name: " + keyName);
        }
      }
    } catch (IOException | ParseException e) {
      throw new RuntimeException("Calibration file " + filePath + ": " + e.getMessage());
    }
  }

  /**
   * Gets the value type.
   *
   * @param calibrationType the calibration type
   * @return the value type
   */
  private TypeMapping.theType getValueType(final String calibrationType) {
    TypeMapping.theType theType = null;
    if (typeMapping.getMap().containsKey(calibrationType))
      theType = typeMapping.getMap().get(calibrationType);
    return theType;
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
   * Debug.
   */
  public void debug() {
    for (Entry<String, String> entry : calibrationMap.entrySet())
      System.out.println("Calibration: " + entry.getKey() + " = "+ entry.getValue());
  }

  /**
   * Update.
   *
   * @param someClass the some class
   */
  public void update(final Object someClass) {
    try {
      final Field[] fields = someClass.getClass().getDeclaredFields();
      for (int i = 0; i < fields.length; i++) {
        final Field field = fields[i];
        final String calibrationName = field.getName();
        if (calibrationMap.containsKey(calibrationName)) {
          final Matcher matcher = calibrationPattern.matcher(calibrationName);
          if (matcher.matches()) {
            final int modifiers = field.getModifiers();
            if (Modifier.isFinal(modifiers)) {
              field.setAccessible(true);
              final TypeMapping.theType valueType = getValueType(matcher.group(3));
              if (debug)
                System.err.print(someClass.getClass().getName() + ": " + calibrationName + " = (" + valueType + ") " + calibrationMap.get(calibrationName));
              switch (valueType) {
              case eBoolean:
                resolveBoolean(someClass, field, calibrationName);
                break;
              case eChar:
                resolveChar(someClass, field, calibrationName);
                break;
              case eByte:
                resolveByte(someClass, field, calibrationName);
                break;
              case eShort:
                resolveShort(someClass, field, calibrationName);
                break;
              case eInt:
                resolveInt(someClass, field, calibrationName);
                break;
              case eLong:
                resolveLong(someClass, field, calibrationName);
                break;
              case eFloat:
                resolveFloat(someClass, field, calibrationName);
                break;
              case eDouble:
                resolveDouble(someClass, field, calibrationName);
                break;
              case eString:
                resolveString(someClass, field, calibrationName);
                break;
              default:
                break;
              }
              field.setAccessible(false);
            } else
              System.err.print(someClass.getClass() + ": Calibration is not final: " + calibrationName);
          } else {
            System.err.print(someClass.getClass() + ": Invalid calibration name: " + calibrationName);
          }
        }
      }
    } catch (Exception e) {
      throw new RuntimeException("Calibration update failure in class " + someClass.getClass().getName());
    }
  }

  /**
   * Resolve boolean.
   *
   * @param someClass the some class
   * @param field the field
   * @param calibrationName the calibration name
   * @throws IllegalArgumentException the illegal argument exception
   * @throws IllegalAccessException the illegal access exception
   */
  private void resolveBoolean(Object someClass, final Field field, final String calibrationName) throws IllegalArgumentException, IllegalAccessException {
    final String jsonValue = calibrationMap.get(calibrationName);
    field.set(someClass, Boolean.parseBoolean(jsonValue));
  }

  /**
   * Resolve char.
   *
   * @param someClass the some class
   * @param field the field
   * @param calibrationName the calibration name
   * @throws IllegalArgumentException the illegal argument exception
   * @throws IllegalAccessException the illegal access exception
   */
  private void resolveChar(Object someClass, final Field field, final String calibrationName) throws IllegalArgumentException, IllegalAccessException {
    final String jsonValue = calibrationMap.get(calibrationName);
    field.set(someClass, jsonValue.charAt(0));
  }

  /**
   * Resolve byte.
   *
   * @param someClass the some class
   * @param field the field
   * @param calibrationName the calibration name
   * @throws IllegalArgumentException the illegal argument exception
   * @throws IllegalAccessException the illegal access exception
   */
  private void resolveByte(Object someClass, final Field field, final String calibrationName) throws IllegalArgumentException, IllegalAccessException {
    final String jsonValue = calibrationMap.get(calibrationName);
    field.set(someClass, parseByte(jsonValue));
  }

  /**
   * Resolve short.
   *
   * @param someClass the some class
   * @param field the field
   * @param calibrationName the calibration name
   * @throws IllegalArgumentException the illegal argument exception
   * @throws IllegalAccessException the illegal access exception
   */
  private void resolveShort(Object someClass, final Field field, final String calibrationName) throws IllegalArgumentException, IllegalAccessException {
    final String jsonValue = calibrationMap.get(calibrationName);
    field.set(someClass, parseShort(jsonValue));
  }

  /**
   * Resolve int.
   *
   * @param someClass the some class
   * @param field the field
   * @param calibrationName the calibration name
   * @throws IllegalArgumentException the illegal argument exception
   * @throws IllegalAccessException the illegal access exception
   */
  private void resolveInt(Object someClass, final Field field, final String calibrationName) throws IllegalArgumentException, IllegalAccessException {
    final String jsonValue = calibrationMap.get(calibrationName);
    field.set(someClass, parseInt(jsonValue));
  }

  /**
   * Resolve long.
   *
   * @param someClass the some class
   * @param field the field
   * @param calibrationName the calibration name
   * @throws IllegalArgumentException the illegal argument exception
   * @throws IllegalAccessException the illegal access exception
   */
  private void resolveLong(Object someClass, final Field field, final String calibrationName) throws IllegalArgumentException, IllegalAccessException {
    final String jsonValue = calibrationMap.get(calibrationName);
    field.set(someClass, parseLong(jsonValue));
  }

  /**
   * Resolve float.
   *
   * @param someClass the some class
   * @param field the field
   * @param calibrationName the calibration name
   * @throws IllegalArgumentException the illegal argument exception
   * @throws IllegalAccessException the illegal access exception
   */
  private void resolveFloat(Object someClass, final Field field, final String calibrationName) throws IllegalArgumentException, IllegalAccessException {
    final String jsonValue = calibrationMap.get(calibrationName);
    field.set(someClass, Float.parseFloat(jsonValue));
  }

  /**
   * Resolve double.
   *
   * @param someClass the some class
   * @param field the field
   * @param calibrationName the calibration name
   * @throws IllegalArgumentException the illegal argument exception
   * @throws IllegalAccessException the illegal access exception
   */
  private void resolveDouble(Object someClass, final Field field, final String calibrationName) throws IllegalArgumentException, IllegalAccessException {
    final String jsonValue = calibrationMap.get(calibrationName);
    field.set(someClass, Double.parseDouble(jsonValue));
  }

  /**
   * Resolve string.
   *
   * @param someClass the some class
   * @param field the field
   * @param calibrationName the calibration name
   * @throws IllegalArgumentException the illegal argument exception
   * @throws IllegalAccessException the illegal access exception
   */
  private void resolveString(Object someClass, final Field field, final String calibrationName) throws IllegalArgumentException, IllegalAccessException {
    final String jsonValue = calibrationMap.get(calibrationName);
    field.set(someClass, jsonValue);
  }

  /**
   * Parses the byte.
   *
   * @param jsonValue the json value
   * @return the byte
   */
  private byte parseByte(final String jsonValue) {
    String value = jsonValue;
    final int radix = getRadix(value);
    switch (radix) {
    case 8:
      value = StringUtils.substring(value, 1);
      break;
    case 16:
      value = StringUtils.substring(value, 2);
      break;
    }
    return Byte.parseByte(value, radix);
  }

  /**
   * Parses the short.
   *
   * @param jsonValue the json value
   * @return the short
   */
  private short parseShort(final String jsonValue) {
    String value = jsonValue;
    final int radix = getRadix(value);
    switch (radix) {
    case 8:
      value = StringUtils.substring(value, 1);
      break;
    case 16:
      value = StringUtils.substring(value, 2);
      break;
    }
    return Short.parseShort(value, radix);
  }

  /**
   * Parses the int.
   *
   * @param jsonValue the json value
   * @return the int
   */
  private int parseInt(final String jsonValue) {
    String value = jsonValue;
    final int radix = getRadix(value);
    switch (radix) {
    case 8:
      value = StringUtils.substring(value, 1);
      break;
    case 16:
      value = StringUtils.substring(value, 2);
      break;
    }
    return Integer.parseInt(value, radix);
  }

  /**
   * Parses the long.
   *
   * @param jsonValue the json value
   * @return the long
   */
  private long parseLong(final String jsonValue) {
    String value = jsonValue;
    final int radix = getRadix(value);
    switch (radix) {
    case 8:
      value = StringUtils.substring(value, 1);
      break;
    case 16:
      value = StringUtils.substring(value, 2);
      break;
    }
    return Long.parseLong(value, radix);
  }

  /**
   * Gets the radix.
   *
   * @param number the number
   * @return the radix
   */
  private int getRadix(final String number) {
    int radix = 10;
    if (StringUtils.startsWith(number, "0x"))
      radix = 16;
    else if (StringUtils.startsWith(number, "0"))
      radix = 8;
    return radix;
  }
}
