/*
 * CSE 593 - Fall 2016 - Applied Project
 * Author  : Lucio Ortiz and Robert Blazewicz
 * Version : DEVSJAVA 3.0
 * Date    : 2016-10-28
 */
package experiment.design.util;

import experiment.toolkit.ExperimentState;
import experiment.toolkit.ValueSet;

/**
 * The Interface RunMonitorInterface.
 */
public interface RunMonitorInterface {

  /**
   * Checks if is run complete.
   *
   * @param experimentState the experiment state
   * @param valueSet the value set
   * @return true, if is run complete
   */
  public boolean isRunComplete(final ExperimentState experimentState, final ValueSet valueSet);
}
