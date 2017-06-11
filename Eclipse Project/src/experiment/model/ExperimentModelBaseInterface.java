/*
 * CSE 593 - Fall 2016 - Applied Project
 * Author  : Lucio Ortiz and Robert Blazewicz
 * Version : DEVSJAVA 3.0
 * Date    : 2016-10-11
 */
package experiment.model;

import experiment.toolkit.ExperimentState;
import experiment.toolkit.ValueSet;

/**
 * The Interface ExperimentModelBaseInterface.
 */
public interface ExperimentModelBaseInterface {

  /**
   * Manifest model.
   *
   * @return true, if successful
   */
  public boolean manifestModel();

  /**
   * Process value set.
   *
   * @param experimentState the experiment state
   * @param valueSet the value set
   */
  public void processValueSet(final ExperimentState experimentState, final ValueSet valueSet);
}
