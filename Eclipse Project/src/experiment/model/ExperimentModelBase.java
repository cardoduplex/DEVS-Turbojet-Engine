/*
 * CSE 593 - Fall 2016 - Applied Project
 * Author  : Lucio Ortiz and Robert Blazewicz
 * Version : DEVSJAVA 3.0
 * Date    : 2016-09-24
 */
package experiment.model;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Queue;

import GenCol.entity;
import experiment.toolkit.Settings;
import experiment.toolkit.SettingsSingleton;
import experiment.toolkit.ValueSetPair;
import model.modeling.message;
import view.modeling.ViewableAtomic;

/**
 * The Class EngineBase.
 */
public abstract class ExperimentModelBase extends ViewableAtomic implements ExperimentModelBaseInterface {

  /** The settings. */
  private final Settings settings = SettingsSingleton.getInstance();

  /** The value set pair queue in. */
  protected final Queue<ValueSetPair> valueSetPairQueueIn = new LinkedList<ValueSetPair>();

  /** The value set pair queue out. */
  protected final Queue<ValueSetPair> valueSetPairQueueOut = new LinkedList<ValueSetPair>();

  /** The show model state. */
  protected boolean showModelState;

  /** The clock. */
  protected double clock;

  /** The model. */
  private final char model;

  /**
   * Instantiates a new engine base.
   */
  public ExperimentModelBase() {
    this("EngineBase", 'A');
  }

  /**
   * Instantiates a new engine base.
   *
   * @param name the name
   * @param model the model
   */
  public ExperimentModelBase(final String name, final char model) {
    super(Character.valueOf(model) + ": " + name);
    showModelState = settings.lookupBool("ExperimentEngine_ShowModelState", "true");
    this.model = model;

    addInport("xIn");
    addOutport("xOut");
    addInport("yIn");
    addOutport("yOut");
  }

  /* (non-Javadoc)
   * @see model.modeling.atomic#initialize()
   */
  @Override
  public void initialize() {
    super.initialize();
    valueSetPairQueueIn.clear();
    valueSetPairQueueOut.clear();
    clock = 0;
    passivate();
  }

  /* (non-Javadoc)
   * @see model.modeling.atomic#deltext(double, model.modeling.message)
   */
  @Override
  public void deltext(double e, message x) {
    clock = clock + e;
    Continue(e);
    if (phaseIs("passive"))
      for (int i = 0; i < x.getLength(); i++)
        if (messageOnPort(x, "xIn", i)) {
          final entity job = x.getValOnPort("xIn", i);
          final ValueSetPair valueSetPair = (ValueSetPair)job;
          if (valueSetPair.getModel() == model) {
            valueSetPairQueueIn.add(valueSetPair);
            holdIn("process", 0);
          }
        }
  }

  /* (non-Javadoc)
   * @see model.modeling.atomic#deltint()
   */
  @Override
  public void deltint() {
    clock = clock + sigma;
    if (phaseIs("process")) {
      if (valueSetPairQueueIn.size() > 0)
        while (valueSetPairQueueIn.size() > 0) {
          final ValueSetPair valueSetPair = valueSetPairQueueIn.remove();
          if (!valueSetPair.isRunTerminated())
            processValueSet(valueSetPair.getExperimentState(), valueSetPair.getValueSet());
          valueSetPairQueueOut.add(valueSetPair);
          holdIn("transfer", sigma);
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

  /* (non-Javadoc)
   * @see model.modeling.atomic#showState()
   */
  @Override
  public void showState() {
    if (showModelState) {
      super.showState();

      final DecimalFormat deltaFormat = new DecimalFormat("#0.00");
      String state = "phase=" + phase + "; " +
          "sigma=" + deltaFormat.format(sigma) + "; " +
          "clock=" + deltaFormat.format(clock) + "; " +
          "QueueIn=" + valueSetPairQueueIn.size() + "; " +
          "QueueOut=" + valueSetPairQueueOut.size();
      System.out.print(getName() + ": " + state);
    }
  }

  /* (non-Javadoc)
   * @see view.modeling.ViewableAtomic#getTooltipText()
   */
  @Override
  public String getTooltipText() {
    final DecimalFormat deltaFormat = new DecimalFormat("#0.00");
    String tooltip = super.getTooltipText() + "\nclock: " + deltaFormat.format(clock);
    for (ValueSetPair valueSetPair : valueSetPairQueueIn)
      tooltip += "\nQueueIn: "+ valueSetPair.toString();
    for (ValueSetPair valueSetPair : valueSetPairQueueOut)
      tooltip += "\nQueueOut: "+ valueSetPair.toString();
    return tooltip;
  }
}
