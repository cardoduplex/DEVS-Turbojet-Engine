/*
 * CSE 593 - Fall 2016 - Applied Project
 * Author  : Lucio Ortiz and Robert Blazewicz
 * Version : DEVSJAVA 3.0
 * Date    : 2016-10-09
 */
package experiment.design;

import experiment.design.util.ValueSet2Csv;
import experiment.design.util.ValueSet2Txt;
import experiment.model.ExperimentEngineDataRecorder;
import experiment.toolkit.ExperimentState;
import experiment.toolkit.ValueSet;
import experiment.toolkit.ValueSetPair;

/**
 * The Class DataRecorder.
 */
public class DataRecorder extends ExperimentEngineDataRecorder {

  /** The value set to CSV. */
  private final ValueSet2Csv valueSet2Csv = new ValueSet2Csv();

  /** The value set 2 txt. */
  private final ValueSet2Txt valueSet2Txt = new ValueSet2Txt();

  /** The dump value set. */
  private final boolean dumpValueSet;

  /** The dump each pass. */
  private final boolean dumpEachPass;

  /**
   * Instantiates a new data recorder.
   *
   * @param name the name
   */
  public DataRecorder(final String name, final boolean showModelState, final String colorName, final boolean dumpValueSet, final boolean dumpEachPass) {
    super(name, showModelState, colorName);
    this.dumpValueSet = dumpValueSet;
    this.dumpEachPass = dumpEachPass;
  }

  /* (non-Javadoc)
   * @see experiment.model.ExperimentEngineDataRecorderInterface#process(experiment.toolkit.ValueSetPair)
   */
  public void process(final ValueSetPair valueSetPair) {
    final ExperimentState experimentState = valueSetPair.getExperimentState();
    final ValueSet valueSet = valueSetPair.getValueSet();
    final boolean processFlag = experimentState.isRunTerminated() || experimentState.isPassRecorded() || experimentState.isRunCompleted();

    if (dumpValueSet && (dumpEachPass || processFlag)) {
      final String prefix = "[" + experimentState.getModel() + "]  ";
      System.out.println(name + ": " + prefix + "ValueSet dump for key '" + experimentState.getKey() + "' " +
          (experimentState.isRunTerminated() ? "(run complete)" : "(intermediate results)"));
      experimentState.dump(prefix);
      valueSet.dump(prefix);
    }

    if (processFlag) {
      valueSet2Csv.process(experimentState, valueSet);
      valueSet2Txt.process(experimentState, valueSet);
    }
  }
}
