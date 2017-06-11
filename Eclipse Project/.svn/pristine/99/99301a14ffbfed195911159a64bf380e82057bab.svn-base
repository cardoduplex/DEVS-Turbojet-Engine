/*
 * CSE 593 - Fall 2016 - Applied Project
 * Author  : Lucio Ortiz and Robert Blazewicz
 * Version : DEVSJAVA 3.0
 * Date    : 2016-10-17
 */
package experiment.design.util;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class DesignExperiment.
 */
public class DesignExperiment {

  /** The factor list. */
  public List<DesignExperimentFactor> factorList = new ArrayList<DesignExperimentFactor>();

  /** The run list. */
  public List<DesignExperimentRun> runList = new ArrayList<DesignExperimentRun>();

  /**
   * Instantiates a new design experiment.
   */
  public DesignExperiment() {
  }

  /**
   * Gets the factor list.
   *
   * @return the factor list
   */
  public List<DesignExperimentFactor> getFactorList() {
    return factorList;
  }

  /**
   * Gets the run list.
   *
   * @return the run list
   */
  public List<DesignExperimentRun> getRunList() {
    return runList;
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
    System.out.println(prefix + "Design Experiment");
    for (DesignExperimentFactor factor : factorList)
      System.out.println(prefix + "  " + factor);
    for (DesignExperimentRun run : runList)
      System.out.println(prefix + "  " + run);
  }
}
