/*
 * CSE 593 - Fall 2016 - Applied Project
 * Author  : Lucio Ortiz and Robert Blazewicz
 * Version : DEVSJAVA 3.0
 * Date    : 2016-10-28
 */
package TurbojetEngineMod.engine;

import experiment.design.util.RunMonitorInterface;
import experiment.toolkit.ExperimentState;
import experiment.toolkit.ValueSet;

/**
 * The Class MissionMonitor.
 */
public class MissionMonitor implements RunMonitorInterface {

  /* (non-Javadoc)
   * @see experiment.design.util.RunMonitorInterface#isRunComplete(experiment.toolkit.ExperimentState, experiment.toolkit.ValueSet)
   */
  @Override
  public boolean isRunComplete(final ExperimentState experimentState, final ValueSet valueSet) {
    valueSet.valuePop(this);
    boolean isRunComplete = false;

    // Run's value evaluation logic goes here.
    isRunComplete = true;

    return isRunComplete;
  }
}
