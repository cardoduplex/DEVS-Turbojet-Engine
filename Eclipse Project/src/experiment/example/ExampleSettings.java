/*
 * CSE 593 - Fall 2016 - Applied Project
 * Author  : Lucio Ortiz and Robert Blazewicz
 * Version : DEVSJAVA 3.0
 * Date    : 2016-09-17
 */
package experiment.example;

import experiment.toolkit.Settings;
import experiment.toolkit.SettingsSingleton;

/**
 * The Class ExampleSettings.
 */
public class ExampleSettings {

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    final Settings settings = SettingsSingleton.getInstance();
    System.out.println("Config pathname: " + settings.getPathnameConfig());
    System.out.println("Output pathname: " + settings.getPathnameOutput());
    System.out.println("Filename: " + settings.getFilename());
    System.out.println("Total: " + settings.getMap().size());
    settings.dump();
  }
}
