/*
 * CSE 593 - Fall 2016 - Applied Project
 * Author  : Lucio Ortiz and Robert Blazewicz
 * Version : DEVSJAVA 3.0
 * Date    : 2016-08-31
 */
package experiment.model;

import experiment.toolkit.ValueSetPair;

/**
 * The Class ExperimentDataRecorder.
 */
public abstract class ExperimentEngineDataRecorder extends ExperimentEngineBase implements ExperimentEngineDataRecorderInterface {

  /**
   * Instantiates a new experiment data recorder.
   */
  public ExperimentEngineDataRecorder() {
    this("ExperimentDataRecorder", false, "YELLOW");
  }

  /**
   * Instantiates a new experiment data recorder.
   *
   * @param name the name
   * @param showModelState the show model state
   * @param colorName the color name
   */
  public ExperimentEngineDataRecorder(final String name, final boolean showModelState, final String colorName) {
    super(name, showModelState, colorName);
  }

  /* (non-Javadoc)
   * @see ExperimentMod.ExperimentBase#initialize()
   */
  @Override
  public void initialize() {
    super.initialize();
    passivate();
  }

  /* (non-Javadoc)
   * @see model.modeling.atomic#deltint()
   */
  @Override
  public void deltint() {
    if (phaseIs("process")) {
      if (valueSetPairQueueIn.size() > 0)
        while (valueSetPairQueueIn.size() > 0) {
          final ValueSetPair valueSetPair = valueSetPairQueueIn.remove();
          process(valueSetPair);
        }
    }
    passivate();
  }
}
