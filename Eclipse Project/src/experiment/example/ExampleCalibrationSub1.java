/*
 * CSE 593 - Fall 2016 - Applied Project
 * Author  : Lucio Ortiz and Robert Blazewicz
 * Version : DEVSJAVA 3.0
 * Date    : 2016-09-17
 */
package experiment.example;

import experiment.toolkit.Calibration;

/**
 * The Class ExampleCalibrationSub1.
 */
public class ExampleCalibrationSub1 {

  /** The KeABC_Str_Example1. */
  private final String  KeABC_Str_Example1;

  /** The KeABC_Cnt_Example1. */
  private final int     KeABC_Cnt_Example1;

  /** The KeABC_Bool_Example1. */
  private final boolean KeABC_Bool_Example1;

  /**
   * Instantiates a new test calibration sub 1.
   */
  ExampleCalibrationSub1() {
    this.KeABC_Str_Example1 = "Example 1";
    this.KeABC_Cnt_Example1 = 12345;
    this.KeABC_Bool_Example1 = true;

    final Calibration calibration = new Calibration(false);
    calibration.load();
    calibration.update(this);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return getClass() + " : " + KeABC_Str_Example1 + " : " +
        KeABC_Cnt_Example1 + " : " + KeABC_Bool_Example1;
  }
}
