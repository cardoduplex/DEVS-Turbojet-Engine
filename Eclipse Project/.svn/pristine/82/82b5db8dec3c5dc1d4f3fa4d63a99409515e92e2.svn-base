/*
 * CSE 593 - Fall 2016 - Applied Project
 * Author  : Lucio Ortiz and Robert Blazewicz
 * Version : DEVSJAVA 3.0
 * Date    : 2016-10-11
 */
package experiment.model;

import java.util.Queue;

import experiment.toolkit.ValueSetPair;

/**
 * The Interface ExperimentEngineBaseInterface.
 */
public interface ExperimentEngineFrontPorchInterface {

  /**
   * Start experiments.
   *
   * @param valueSetPairQueueOut the value set pair queue out
   */
  public void startExperiments(final Queue<ValueSetPair> valueSetPairQueueOut);

  /**
   * Process experiments.
   *
   * @param valueSetPairQueueIn the value set pair queue in
   */
  public void processExperiments(final Queue<ValueSetPair> valueSetPairQueueIn);

  /**
   * Are runs complete.
   *
   * @return true, if successful
   */
  public boolean areRunsComplete();

  /**
   * Gets the model count.
   *
   * @return the model count
   */
  public int getModelCount();
}
