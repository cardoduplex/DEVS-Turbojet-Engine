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
 * The Class ExperimentBackPorch.
 */
public abstract class ExperimentEngineBackPorch extends ExperimentEngineBase implements ExperimentEngineBackPorchInterface {

  /**
   * Instantiates a new experiment back porch.
   */
  public ExperimentEngineBackPorch() {
    this("ExperimentBackPorch", false, "YELLOW");
  }

  /**
   * Instantiates a new experiment back porch.
   *
   * @param name the name
   * @param showModelState the show model state
   * @param colorName the color name
   */
  public ExperimentEngineBackPorch(final String name, final boolean showModelState, final String colorName) {
    super(name, showModelState, colorName);

    addOutport("xOut");
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
          valueSetPairQueueOut.add(valueSetPair);
          holdIn("transfer", 0);
        }
    } else
      passivate();
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
        m.add(makeContent("xOut", valueSetPair));
      }
    return m;
  }
}
