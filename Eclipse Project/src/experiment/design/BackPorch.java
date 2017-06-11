/*
 * CSE 593 - Fall 2016 - Applied Project
 * Author  : Lucio Ortiz and Robert Blazewicz
 * Version : DEVSJAVA 3.0
 * Date    : 2016-10-09
 */
package experiment.design;

import experiment.design.util.RunMonitorInterface;
import experiment.model.ExperimentEngineBackPorch;
import experiment.toolkit.ValueSetPair;

/**
 * The Class BackPorch.
 */
public class BackPorch extends ExperimentEngineBackPorch {

  /** The run monitor. */
  private final RunMonitorInterface runMonitor;

  /**
   * Instantiates a new back porch.
   *
   * @param name the name
   * @param showModelState the show model state
   * @param colorName the color name
   * @param runMonitor the run monitor
   */
  public BackPorch(final String name, final boolean showModelState, final String colorName, final RunMonitorInterface runMonitor) {
    super(name, showModelState, colorName);
    this.runMonitor = runMonitor;
  }

  /* (non-Javadoc)
   * @see experiment.model.ExperimentEngineBackPorch#initialize()
   */
  @Override
  public void initialize() {
    super.initialize();
  }

  /* (non-Javadoc)
   * @see experiment.model.ExperimentEngineBackPorchInterface#process(experiment.toolkit.ValueSet)
   */
  public void process(final ValueSetPair valueSetPair) {
    if (!valueSetPair.isRunTerminated() &&
        (valueSetPair.isRunCompleted() ||
         runMonitor.isRunComplete(valueSetPair.getExperimentState(), valueSetPair.getValueSet())))
      valueSetPair.completeRun();
  }
}
