/*
 * CSE 593 - Fall 2016 - Applied Project
 * Author  : Lucio Ortiz and Robert Blazewicz
 * Version : DEVSJAVA 3.0
 * Date    : 2016-09-17
 */
package experiment.toolkit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

/**
 * The Class Settings.
 */
public class Settings {

  /** The setting map. */
  private Map<String, String> settingMap = new LinkedHashMap<String, String>();

  /** The Constant defaultFilename. */
  private static final String defaultFilename = "Settings.properties";

  /** The path configuration. */
  private PathConfiguration pathConfiguration = new PathConfiguration();

  /** The filename. */
  private String filename;

  /**
   * Instantiates a new settings.
   *
   * @throws RuntimeException the runtime exception
   */
  public Settings() throws RuntimeException {
    filename = defaultFilename;

    final Path filePath = Paths.get(getPathnameConfig(), filename);
    try (FileReader fileReader = new FileReader(filePath.toFile())) {
      try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
        int lineNumber = 0;
        String line;
        while ((line = bufferedReader.readLine()) != null) {
          lineNumber++;
          if (line.isEmpty() || line.startsWith("#"))
            continue;
          line = StringUtils.strip(line);
          line = line.trim();
          if (line.isEmpty())
            continue;
          final String[] pair = line.split("=");
          if (pair.length == 2)
            settingMap.put(pair[0].trim(), pair[1].trim());
          else
            throw new ParseException("Settings file " + filePath + " line " + lineNumber + " has invalid format", 0);
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * Gets the pathname config.
   *
   * @return the pathname config
   */
  public String getPathnameConfig() {
    return pathConfiguration.getPathnameConfig();
  }

  /**
   * Gets the pathname output.
   *
   * @return the pathname output
   */
  public String getPathnameOutput() {
    return pathConfiguration.getPathnameOutput();
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
   * Gets the map.
   *
   * @return the map
   */
  public Map<String, String> getMap() {
    return settingMap;
  }

  /**
   * Dump.
   */
  public void dump() {
    dump("Setting: ");
  }

  /**
   * Dump.
   *
   * @param prefix the prefix
   */
  public void dump(final String prefix) {
    for (Entry<String, String> entry : settingMap.entrySet())
      System.out.println(prefix + entry.getKey() + " = " + entry.getValue());
  }

  /**
   * Lookup string.
   *
   * @param args the args
   * @return the string
   * @throws IllegalArgumentException the illegal argument exception
   */
  public String lookupString(final String... args) throws IllegalArgumentException {
    String value;
    switch (args.length) {
    case 1:
      value = settingMap.get(args[0]);
      break;
    case 2:
       value = settingMap.getOrDefault(args[0], args[1]);
      break;
    default:
      throw new IllegalArgumentException();
    }
    return value;
  }

  /**
   * Lookup bool.
   *
   * @param args the args
   * @return true, if successful
   * @throws IllegalArgumentException the illegal argument exception
   */
  public boolean lookupBool(final String... args) throws IllegalArgumentException {
      return Boolean.parseBoolean(lookupString(args));
    }

  /**
   * Lookup char.
   *
   * @param args the args
   * @return the char
   * @throws IllegalArgumentException the illegal argument exception
   */
  public char lookupChar(final String... args) throws IllegalArgumentException {
    return lookupString(args).charAt(0);
  }

  /**
   * Lookup byte.
   *
   * @param args the args
   * @return the byte
   * @throws IllegalArgumentException the illegal argument exception
   * @throws NumberFormatException the number format exception
   */
  public byte lookupByte(final String... args) throws IllegalArgumentException, NumberFormatException {
    byte value;
    switch (args.length) {
      case 1:
      case 2:
        value = Byte.parseByte(lookupString(args));
        break;
      case 3:
        final int radix = Integer.parseInt(args[2]);
        value = Byte.parseByte(lookupString(args[0], args[1]), radix);
        break;
      default:
        throw new IllegalArgumentException();
    }
    return value;
  }

  /**
   * Lookup int.
   *
   * @param args the args
   * @return the int
   * @throws IllegalArgumentException the illegal argument exception
   * @throws NumberFormatException the number format exception
   */
  public int lookupInt(final String... args) throws IllegalArgumentException, NumberFormatException {
    int value;
    switch (args.length) {
      case 1:
      case 2:
        value = Integer.parseInt(lookupString(args));
        break;
      case 3:
        final int radix = Integer.parseInt(args[2]);
        value = Integer.parseInt(lookupString(args[0], args[1]), radix);
        break;
      default:
        throw new IllegalArgumentException();
    }
    return value;
  }

  /**
   * Lookup short.
   *
   * @param args the args
   * @return the short
   * @throws IllegalArgumentException the illegal argument exception
   * @throws NumberFormatException the number format exception
   */
  public short lookupShort(final String... args) throws IllegalArgumentException, NumberFormatException {
    short value;
    switch (args.length) {
      case 1:
      case 2:
        value = Short.parseShort(lookupString(args));
        break;
      case 3:
        final int radix = Integer.parseInt(args[2]);
        value = Short.parseShort(lookupString(args[0], args[1]), radix);
        break;
      default:
        throw new IllegalArgumentException();
    }
    return value;
  }

  /**
   * Lookup long.
   *
   * @param args the args
   * @return the long
   * @throws IllegalArgumentException the illegal argument exception
   * @throws NumberFormatException the number format exception
   */
  public long lookupLong(final String... args) throws IllegalArgumentException, NumberFormatException {
    long value;
    switch (args.length) {
      case 1:
      case 2:
        value = Long.parseLong(lookupString(args));
        break;
      case 3:
        final int radix = Integer.parseInt(args[2]);
        value = Long.parseLong(lookupString(args[0], args[1]), radix);
        break;
      default:
        throw new IllegalArgumentException();
    }
    return value;
  }

  /**
   * Lookup float.
   *
   * @param args the args
   * @return the float
   * @throws IllegalArgumentException the illegal argument exception
   * @throws NumberFormatException the number format exception
   */
  public float lookupFloat(final String... args) throws IllegalArgumentException, NumberFormatException {
    return Float.parseFloat(lookupString(args));
  }

  /**
   * Lookup double.
   *
   * @param args the args
   * @return the double
   * @throws IllegalArgumentException the illegal argument exception
   * @throws NumberFormatException the number format exception
   */
  public double lookupDouble(final String... args) throws IllegalArgumentException, NumberFormatException {
    return Double.parseDouble(lookupString(args));
  }
}
