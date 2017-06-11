/*
 * CSE 593 - Fall 2016 - Applied Project
 * Author  : Lucio Ortiz and Robert Blazewicz
 * Version : DEVSJAVA 3.0
 * Date    : 2016-09-17
 */
package experiment.toolkit;

/**
 * The Class SettingsSingleton.
 */
public class SettingsSingleton {

  /** The Constant instance. */
  private static final Settings instance = new Settings();

  /**
   * Instantiates a new Settings singleton.
   */
  protected SettingsSingleton() {
  }

  /**
   * Gets the single instance of Settings.
   *
   * @return single instance of Settings
   * @throws RuntimeException the runtime exception
   */
  public static Settings getInstance() throws RuntimeException {
    if (instance == null)
      throw new RuntimeException("No singleton instance available");
    else
      return instance;
  }
}
