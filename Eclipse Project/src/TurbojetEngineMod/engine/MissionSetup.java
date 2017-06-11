/*
 * CSE 593 - Fall 2016 - Applied Project
 * Author  : Lucio Ortiz and Robert Blazewicz
 * Version : DEVSJAVA 3.0
 * Date    : 2016-10-02
 */
package TurbojetEngineMod.engine;

import experiment.toolkit.Calibration;
import experiment.toolkit.ExperimentState;
import experiment.toolkit.Value;
import experiment.toolkit.ValueSet;

/**
 * The Class MissionSetup.
 */
public class MissionSetup extends EngineBase {

  /** The fluid model. */
  private final String KeTJET_Str_FluidModel;

  /** Input: Tzero. */
  private double Tzero;

  /** Input: gamma. */
  private double gamma;

  /** Input: gamma_c. */
  private double gamma_c;

  /** Input: gamma_t. */
  private double gamma_t;

  /** Input: Rair. */
  private double Rair;

  /** Input: gc. */
  private double gc;

  /** Input: Cp_c. */
  private double Cp_c;

  /** Input: Cp_t. */
  private double Cp_t;

  /** Output: azero. */
  private double azero;

  /** Output: Rair_c. */
  private double Rair_c;

  /** Output: Rair_t. */
  private double Rair_t;

  /**
   * Instantiates a new mission setup.
   *
   * @param name the name
   */
  public MissionSetup(final String name) {
    super(name);
    KeTJET_Str_FluidModel = "undefined";
  }

  /* (non-Javadoc)
   * @see TurbojetEngineMod.engine.EngineBase#manifestModel(experiment.toolkit.Calibration)
   */
  @Override
  public boolean manifestModel(final Calibration calibration) {
    calibration.update(this);
    return true;
  }

  /* (non-Javadoc)
   * @see TurbojetEngineMod.engine.EngineBase#process(experiment.toolkit.ExperimentState, experiment.toolkit.ValueSet)
   */
  public void process(final ExperimentState experimentState, final ValueSet valueSet) {
    switch (KeTJET_Str_FluidModel) {
    case "Isentropic/Static (subsonic)":
      IsentropicStaticSubsonic(experimentState, valueSet);
      break;
    case "Isentropic/Static (supersonic)":
      IsentropicStaticSupersonic(experimentState, valueSet);
      break;
    case "Polytropic/Static (supersonic)":
      PolytropicStaticSupersonic(experimentState, valueSet);
      break;
    default:
      System.out.println(name + "Undefined fluid model '" + KeTJET_Str_FluidModel + "'");
      break;
    }
  }

  /**
   * Isentropic static subsonic.
   *
   * @param experimentState the experiment state
   * @param valueSet the value set
   */
  public void IsentropicStaticSubsonic(final ExperimentState experimentState, final ValueSet valueSet) {
    valueSet.valuePop(this);

    // [ft/s] speed of sound
    azero = Math.sqrt(gamma*Rair*gc*Tzero);

    if (experimentState.isFirstPass()) {
      valueSet.addValue(name, "azero", Value.theType.eDouble, azero, "[ft/s]", "Speed of sound", "", true);
    }
    valueSet.valuePush(this);
  }

  /**
   * Isentropic static supersonic.
   *
   * @param experimentState the experiment state
   * @param valueSet the value set
   */
  public void IsentropicStaticSupersonic(final ExperimentState experimentState, final ValueSet valueSet) {
    valueSet.valuePop(this);

    // [ft/s] speed of sound
    azero = Math.sqrt(gamma*Rair*gc*Tzero);

    if (experimentState.isFirstPass()) {
      valueSet.addValue(name, "azero", Value.theType.eDouble, azero, "[ft/s]", "Speed of sound", "", true);
    }
    valueSet.valuePush(this);
  }

  /**
   * Polytropic static supersonic.
   *
   * @param experimentState the experiment state
   * @param valueSet the value set
   */
  public void PolytropicStaticSupersonic(final ExperimentState experimentState, final ValueSet valueSet) {
    valueSet.valuePop(this);

    // [ft-lb/degR-lbm] Specific gas cnst. dry air (cool)
    Rair_c = ((gamma_c-1)/gamma_c)*Cp_c*778.17;

    // [ft-lb/degR-lbm] Specific gas cnst. dry air (heated)
    Rair_t = ((gamma_t-1)/gamma_t)*Cp_t*778.17;

    // [ft/s] speed of sound
    azero = Math.sqrt(gamma_c*Rair_c*gc*Tzero);

    if (experimentState.isFirstPass()) {
      valueSet.addValue(name, "Rair_c", Value.theType.eDouble, Rair_c, "[ft-lb/degR-lbm]", "Specific gas cnst. dry air (cool)", "", true);
      valueSet.addValue(name, "Rair_t", Value.theType.eDouble, Rair_t, "[ft-lb/degR-lbm]", "Specific gas cnst. dry air (heated)", "", true);
      valueSet.addValue(name, "azero", Value.theType.eDouble, azero, "[ft/s]", "Speed of sound", "", true);
    }
    valueSet.valuePush(this);
  }
}
