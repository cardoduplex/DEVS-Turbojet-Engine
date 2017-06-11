/*
 * CSE 593 - Fall 2016 - Applied Project
 * Author  : Lucio Ortiz and Robert Blazewicz
 * Version : DEVSJAVA 3.0
 * Date    : 2016-08-31
 */
package experiment.model;

import experiment.toolkit.ValueSetPair;
import model.modeling.message;

/**
 * The Class ExperimentFrontPorch.
 */
public abstract class ExperimentEngineFrontPorch extends ExperimentEngineBase implements ExperimentEngineFrontPorchInterface {

  /**
   * Instantiates a new experiment front porch.
   */
  public ExperimentEngineFrontPorch() {
    this("ExperimentFrontPorch", false, "YELLOW");
  }

  /**
   * Instantiates a new experiment front porch.
   *
   * @param name the name
   * @param showModelState the show model state
   * @param colorName the color name
   */
  public ExperimentEngineFrontPorch(final String name, final boolean showModelState, final String colorName) {
    super(name, showModelState, colorName);

    addOutport("xOut");
  }

  /* (non-Javadoc)
   * @see ExperimentMod.ExperimentBase#initialize()
   */
  @Override
  public void initialize() {
    super.initialize();
    holdIn("start", 0);
  }

  /* (non-Javadoc)
   * @see model.modeling.atomic#deltint()
   */
  @Override
  public void deltint() {
    if (phaseIs("start")) {
      startExperiments(valueSetPairQueueOut);
      holdIn("transfer", 0);
    } else if (phaseIs("process")) {
      if (valueSetPairQueueIn.size() > 0) {
        processExperiments(valueSetPairQueueIn);
        while (valueSetPairQueueIn.size() > 0) {
          final ValueSetPair valueSetPair = valueSetPairQueueIn.remove();
          valueSetPairQueueOut.add(valueSetPair);
        }
        holdIn("transfer", 0);
      }
    } else {
      if (areRunsComplete())
        passivate();
      else
        holdIn("passive", INFINITY);
    }

    showState();
  }

  /* (non-Javadoc)
   * @see model.modeling.atomic#out()
   */
  @Override
  public message out() {
    final message m = new message();
    if (phaseIs("transfer"))
      while (valueSetPairQueueOut.size() > 0) {
        final ValueSetPair valueSetPair = valueSetPairQueueOut.remove();
        valueSetPair.incrementPass();
        m.add(makeContent("xOut", valueSetPair));
      }
    return m;
  }
}
