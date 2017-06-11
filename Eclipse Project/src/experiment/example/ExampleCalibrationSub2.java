/*
 * CSE 593 - Fall 2016 - Applied Project
 * Author  : Lucio Ortiz and Robert Blazewicz
 * Version : DEVSJAVA 3.0
 * Date    : 2016-09-17
 */
package experiment.example;

import experiment.toolkit.CalibrationSingleton;

/**
 * The Class ExampleCalibrationSub2.
 */
public class ExampleCalibrationSub2 {

  /** The KeABC_Str_Example2. */
  private final String  KeABC_Str_Example2;

  /** The KeABC_ms_Example2. */
  private final int     KeABC_ms_Example2;

  /** The KeABC_Bool_Example2. */
  private final boolean KeABC_Bool_Example2;

  /**
   * Instantiates a new test calibration sub 2.
   */
  ExampleCalibrationSub2() {
    this.KeABC_Str_Example2 = "Example 2";
    this.KeABC_ms_Example2 = 12345;
    this.KeABC_Bool_Example2 = true;
    CalibrationSingleton.getInstance().update(this);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return getClass() + " : " + KeABC_Str_Example2 + " : " +
        KeABC_ms_Example2 + " : " + KeABC_Bool_Example2;
  }
}
