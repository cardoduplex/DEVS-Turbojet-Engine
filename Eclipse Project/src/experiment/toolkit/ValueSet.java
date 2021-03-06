/*
 * CSE 593 - Fall 2016 - Applied Project
 * Author  : Lucio Ortiz and Robert Blazewicz
 * Version : DEVSJAVA 3.0
 * Date    : 2016-09-24
 */
package experiment.toolkit;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import experiment.toolkit.Value.theType;

/**
 * The Class ValueSet.
 */
public class ValueSet implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -4014092840421072043L;

  /** The Constant defaultDumpMessageAlignment. */
  public static final int defaultDumpMessageAlignment = 60;

  /** The description. */
  private String description;

  /** The value map. */
  private Map<String, Value> valueMap = new LinkedHashMap<String, Value>();

  /** The dump message alignment. */
  private int dumpMessageAlignment;

  /**
   * Instantiates a new value set.
   */
  public ValueSet() {
    this.description = "";
    dumpMessageAlignment = defaultDumpMessageAlignment;
  }

  /**
   * Gets the dump message alignment.
   *
   * @return the dump message alignment
   */
  public int getDumpMessageAlignment() {
    return dumpMessageAlignment;
  }

  /**
   * Sets the dump message alignment.
   *
   * @param dumpMessageAlignment the new dump message alignment
   */
  public void setDumpMessageAlignment(final int dumpMessageAlignment) {
    this.dumpMessageAlignment = dumpMessageAlignment;
  }

  /**
   * Gets the map.
   *
   * @return the map
   */
  public Map<String, Value> getMap() {
    return valueMap;
  }

  /**
   * Gets the description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description.
   *
   * @param description the new description
   */
  public void setDescription(final String description) {
    this.description = description;
  }

  /**
   * Dump.
   */
  public void dump() {
    dump("");
  }

  /**
   * Dump.
   *
   * @param prefix the prefix
   */
  public void dump(final String prefix) {
    String group = null;
    final String format = "%-" + dumpMessageAlignment + "s ";
    for (Entry<String, Value> entry : valueMap.entrySet()) {
      final Value value = entry.getValue();
      if (value.getGroup() != null && (group == null || !group.equals(value.getGroup()))) {
        group = value.getGroup();
        System.out.println(prefix + group);
      }
      final String tmpName = StringUtils.isBlank(value.getAltName()) ? entry.getKey() : value.getAltName();
      final String message = tmpName + ": " + value.getValue() + " " + value.getUnits();
      System.out.println(prefix + "  " + String.format(format, message) + value.getDescription());
    }
  }

  /**
   * Adds the value.
   *
   * @param group the group
   * @param name the name
   * @param type the type
   * @param value the value
   * @param units the units
   * @param description the description
   * @param altName the alt name
   * @param report the report
   */
  public void addValue(final String group, final String name, final theType type, final Object value, final String units, final String description, final String altName, final boolean report) {
    valueMap.put(name, new Value(group, altName, type, value, units, description, report));
  }

  /**
   * File add value.
   *
   * @param group the group
   * @param name the name
   * @param type the type
   * @param value the value
   * @param units the units
   * @param description the description
   * @param altName the alt name
   * @param report the report
   */
  public void fileAddValue(final String group, final String name, final String type, final String value, final String units, final String description, final String altName, final boolean report) {
    switch (type) {
    case "boolean":
      valueMap.put(name, new Value(group, altName, Value.theType.eBoolean, Boolean.parseBoolean(value), units, description, report));
      break;
    case "double":
      valueMap.put(name, new Value(group, altName, Value.theType.eDouble, Double.parseDouble(value), units, description, report));
      break;
    default:
      throw new RuntimeException("Value type '" + type + "' for " + name + " is unsupported.");
    }
  }

  /**
   * Value pop.
   *
   * @param someClass the some class
   */
  public void valuePop(Object someClass) {
    final Field[] fields = someClass.getClass().getDeclaredFields();
    for (int i = 0; i < fields.length; i++) {
      final Field field = fields[i];
      final String valueName = field.getName();
      if (valueMap.containsKey(valueName)) {
        final int modifiers = field.getModifiers();
        if (!Modifier.isFinal(modifiers)) {
          final boolean accessible = field.isAccessible();
          if (!accessible)
            field.setAccessible(true);
          final Value value = valueMap.get(valueName);
          try {
            field.set(someClass, value.getValue());
          } catch (IllegalArgumentException|IllegalAccessException e) {
            throw new RuntimeException("ValueSet pop " + valueName + ": " + e.getMessage());
          }
          if (!accessible)
            field.setAccessible(false);
        }
      }
    }
  }

  /**
   * Value push.
   *
   * @param someClass the some class
   */
  public void valuePush(Object someClass) {
    final Field[] fields = someClass.getClass().getDeclaredFields();
    for (int i = 0; i < fields.length; i++) {
      final Field field = fields[i];
      final String valueName = field.getName();
      if (valueMap.containsKey(valueName)) {
        final int modifiers = field.getModifiers();
        if (!Modifier.isFinal(modifiers)) {
          final boolean accessible = field.isAccessible();
          if (!accessible)
            field.setAccessible(true);
          final Value value = valueMap.get(valueName);
          try {
            value.setValue(field.get(someClass));
          } catch (IllegalArgumentException|IllegalAccessException e) {
            throw new RuntimeException("ValueSet push " + valueName + ": " + e.getMessage());
          }
          if (!accessible)
            field.setAccessible(false);
        }
      }
    }
  }
}
