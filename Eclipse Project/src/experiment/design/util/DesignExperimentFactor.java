/*
 * CSE 593 - Fall 2016 - Applied Project
 * Author  : Lucio Ortiz and Robert Blazewicz
 * Version : DEVSJAVA 3.0
 * Date    : 2016-10-23
 */
package experiment.design.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The Class DesignExperimentFactor.
 */
public class DesignExperimentFactor {

  /** The description. */
  private String description;

  /** The name. */
  private String name;

  /** The levels. */
  private Map<String, String> levels = new HashMap<String, String>();

  /**
   * Instantiates a new design experiment factor.
   *
   * @param description the description
   */
  public DesignExperimentFactor(final String description) {
    this.description = description;
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
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * Gets the levels.
   *
   * @return the levels
   */
  public Map<String, String> getLevels() {
    return levels;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString() {
    boolean first = true;
    String message = "Factor: '" + description +"' " + name + " [";
    for (Entry<String, String> level : levels.entrySet()) {
      if (first)
        first = false;
      else
        message += ", ";
      message += level.getKey() + "=" + level.getValue();
    }
    message += "]";
    return message;
  }
}
