/*
* CSE 593 - Fall 2016 - Applied Project
* Author  : Lucio Ortiz and Robert Blazewicz
* Version : DEVSJAVA 3.0
* Date    : 2016-08-31
 */
package TurbojetEngine.toolkit;

import util.Logging;

/**
 * The Class Log.
 */
public class Log {

  /** The name. */
  private String name;

  /**
   * Instantiates a new log.
   *
   * @param name
   *          the name
   */
  public Log(String name) {
    this.name = name;
  }

  /**
   * Out.
   *
   * @param message
   *          the message
   */
  public void out(String message) {
    System.out.println(name + ": " + message);
    Logging.log(name + ": " + message, Logging.full);
  }

  /**
   * Err.
   *
   * @param message
   *          the message
   */
  public void err(String message) {
    System.err.println(name + ": " + message);
    Logging.log(name + ": " + message, Logging.full);
  }

  /**
   * Err.
   *
   * @param message
   *          the message
   * @param e
   *          the e
   */
  public void err(String message, Exception e) {
    System.err.println(name + ": " + message + ": " + e.getMessage());
    Logging.log(name + ": " + message + ": " + e.getMessage(), Logging.full);
    e.printStackTrace();
  }

  /**
   * Sets the name.
   *
   * @param name
   *          the new name
   */
  public void setName(String name) {
    this.name = name;
  }

}
