/*
 * CSE 593 - Fall 2016 - Applied Project
 * Author  : Lucio Ortiz and Robert Blazewicz
 * Version : DEVSJAVA 3.0
 * Date    : 2016-09-18
 */
package experiment.example;

import experiment.toolkit.TypeMapping;

/**
 * The Class ExampleTypeMapping.
 */
public class ExampleTypeMapping {

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    final TypeMapping typeMapping = new TypeMapping();
    typeMapping.setPathname(".\\src\\experiment\\example");
    System.out.println("Pathname: " + typeMapping.getPathname());
    System.out.println("Filename: " + typeMapping.getFilename());
    typeMapping.parseFile();
    System.out.println("Total: " + typeMapping.getMap().size());
    typeMapping.dump();
  }
}
