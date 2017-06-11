/*
 * CSE 593 - Fall 2016 - Applied Project
 * Author  : Lucio Ortiz and Robert Blazewicz
 * Version : DEVSJAVA 3.0
 * Date    : 2016-09-24
 */
package experiment.model;

import java.awt.Color;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Queue;

import GenCol.entity;
import experiment.toolkit.ValueSetPair;
import model.modeling.message;
import view.modeling.ViewableAtomic;

/**
 * The Class ExperimentBase.
 */
public abstract class ExperimentEngineBase extends ViewableAtomic {

  /** The value set pair queue in. */
  protected final Queue<ValueSetPair> valueSetPairQueueIn = new LinkedList<ValueSetPair>();

  /** The value set pair queue out. */
  protected final Queue<ValueSetPair> valueSetPairQueueOut = new LinkedList<ValueSetPair>();

  /** The show model state. */
  protected final boolean showModelState;

  /**
   * Instantiates a new experiment base.
   */
  public ExperimentEngineBase() {
    this("ExperimentBase", false, "YELLOW");
  }

  /**
   * Instantiates a new experiment base.
   *
   * @param name the name
   * @param showModelState the show model state
   * @param colorName the color name
   */
  public ExperimentEngineBase(final String name, final boolean showModelState, final String colorName) {
    super(name);
    this.showModelState = showModelState;

    // Set background color
    try {
      final Field field = Color.class.getField(colorName);
      final Color backgroundColor = (Color)field.get(null);
      setBackgroundColor(backgroundColor);
    } catch (Exception e) {
      // Color not defined
    }

    addInport("xIn");
  }

  /* (non-Javadoc)
   * @see model.modeling.atomic#initialize()
   */
  @Override
  public void initialize() {
    super.initialize();
    valueSetPairQueueIn.clear();
    valueSetPairQueueOut.clear();
    passivate();
  }

  /* (non-Javadoc)
   * @see model.modeling.atomic#deltext(double, model.modeling.message)
   */
  @Override
  public void deltext(final double e, final message x) {
    Continue(e);
    if (phaseIs("passive"))
      for (int i = 0; i < x.getLength(); i++)
        if (messageOnPort(x, "xIn", i)) {
          final entity job = x.getValOnPort("xIn", i);
          final ValueSetPair valueSetPair = (ValueSetPair)job;
          valueSetPairQueueIn.add(valueSetPair);
          holdIn("process", 0);
        }
  }

  /* (non-Javadoc)
   * @see model.modeling.atomic#showState()
   */
  @Override
  public void showState() {
    if (showModelState) {
      super.showState();

      final DecimalFormat deltaFormat = new DecimalFormat("#0.00");
      final String state = "phase=" + phase +
          "sigma=" + deltaFormat.format(sigma) + "; " +
          "QueueIn=" + valueSetPairQueueIn.size() + "; " +
          "QueueOut=" + valueSetPairQueueOut.size();
      System.out.println(name + ": " + state);
    }
  }

  /* (non-Javadoc)
   * @see view.modeling.ViewableAtomic#getTooltipText()
   */
  @Override
  public String getTooltipText() {
    String tooltip = super.getTooltipText();
    for (ValueSetPair valueSetPair : valueSetPairQueueIn)
      tooltip += "\nQueueIn: "+ valueSetPair.toString();
    for (ValueSetPair valueSetPair : valueSetPairQueueOut)
      tooltip += "\nQueueOut: "+ valueSetPair.toString();
    return tooltip;
  }
}
