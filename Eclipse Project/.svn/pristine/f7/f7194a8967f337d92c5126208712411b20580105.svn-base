/*
 * CSE 593 - Fall 2016 - Applied Project
 * Author  : Lucio Ortiz and Robert Blazewicz
 * Version : DEVSJAVA 3.0
 * Date    : 2016-10-18
 */
package TurbojetEngineMod.engine;

import experiment.toolkit.Calibration;
import experiment.toolkit.ExperimentState;
import experiment.toolkit.Value;
import experiment.toolkit.ValueSet;

/**
 * The Class BypassFan.
 */
public class BypassFan extends EngineBase {

  /** The fluid model. */
  private final String KeTJET_Str_FluidModel;

  /** The afterburner flag. */
  private final boolean KeTJET_Bool_Afterburner;

  /** The gamma. */
  private double gamma;

  /** Input: Cp. */
  private double Cp;

  /** Input: Tt2. */
  private double Tt2;

  /** Input: Pt2. */
  private double Pt2;

  /** Input: pi_fan. */
  private double pi_fan;

  /** Input: alpha. */
  private double alpha;

  /** Input: mdot_zero. */
  private double mdot_zero;

  /** The gamma_c. */
  private double gamma_c;

  /** Input: Cp_c. */
  private double Cp_c;

  /** Input: e_f. */
  private double e_f;

  /** Output: tao_fan. */
  private double tao_fan;

  /** Output: mdot_fan. */
  private double mdot_fan;

  /** Output: w_fan. */
  private double w_fan;

  /** Output: Wdot_fan. */
  private double Wdot_fan;

  /** Output: Pt8. */
  private double Pt8;

  /** Output: Tt8. */
  private double Tt8;

  /**
   * Instantiates a new bypass fan.
   *
   * @param name the name
   */
  public BypassFan(final String name) {
    super(name);
    KeTJET_Str_FluidModel = "undefined";
    KeTJET_Bool_Afterburner = false;
  }

  /* (non-Javadoc)
   * @see TurbojetEngineMod.engine.EngineBase#manifestModel(experiment.toolkit.Calibration)
   */
  @Override
  public boolean manifestModel(final Calibration calibration) {
    calibration.update(this);
    return KeTJET_Bool_Afterburner;
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
  }

  /**
   * Isentropic static supersonic.
   *
   * @param experimentState the experiment state
   * @param valueSet the value set
   */
  public void IsentropicStaticSupersonic(final ExperimentState experimentState, final ValueSet valueSet) {
    valueSet.valuePop(this);

    // Temp. Ratio @ station 8
    tao_fan = Math.pow(pi_fan,((gamma-1)/gamma));

    // mass flow thru Fan
    mdot_fan = (alpha/(1+alpha))*mdot_zero;  //[lbm/s]

    // Specific Work, Fan
    w_fan = Cp*(Tt2*(tao_fan-1));

    // Fan Work
    // Wdot_fan = mdot_fan*w_fan;  //[Btu/s]

    // Fan Work (in hp)
    Wdot_fan = mdot_fan*w_fan*3600*(1/2545.7); //[hp]

    // Stagnation Pressure @ stage 8
    Pt8 = Pt2*pi_fan;

    // Stagnation Temp. @ stage 8
    Tt8 = Tt2*tao_fan;

    if (experimentState.isFirstPass()) {
      valueSet.addValue(name, "tao_fan", Value.theType.eDouble, tao_fan, "", "Temp. Ratio @ station 8", "", true);
      valueSet.addValue(name, "mdot_fan", Value.theType.eDouble, mdot_fan, "[lbm/s]", "mass flow thru Fan", "", true);
      valueSet.addValue(name, "w_fan", Value.theType.eDouble, w_fan, "[Btu/lbm]", "Specific Work, Fan", "", true);
      valueSet.addValue(name, "Wdot_fan", Value.theType.eDouble, Wdot_fan, "[hp]", "Fan Work (in hp)", "", true);
      valueSet.addValue(name, "Pt8", Value.theType.eDouble, Pt8, "[lbf/ft^2]", "Stagnation Pressure @ stage 8", "", true);
      valueSet.addValue(name, "Tt8", Value.theType.eDouble, Tt8, "[degR]", "Stagnation Temp. @ stage 8", "", true);
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

    // Temp. ratio at stage 8
    tao_fan = Math.pow(pi_fan,((gamma_c-1)/(gamma_c*e_f)));

    // mass flow rate thru fan
    mdot_fan = (alpha/(1+alpha))*mdot_zero;  //[lbm/s]

    // fan work  [Btu/lbm]
    w_fan = Cp_c*(Tt2*(tao_fan-1));

    // Fan Rate of Work
    // Wdot_fan = mdot_fan*w_fan;  //[Btu/s]

    // Fan Rate of Work (in hp)
    Wdot_fan = mdot_fan*w_fan*3600*(1/2545.7);  //[hp]

    // Total Press. and Temp. @ station 8
    Pt8 = Pt2*pi_fan;  //[lbf/ft^2]
    Tt8 = Tt2*tao_fan;  //[degR]

    if (experimentState.isFirstPass()) {
      valueSet.addValue(name, "tao_fan", Value.theType.eDouble, tao_fan, "", "Temp. Ratio @ station 8", "", true);
      valueSet.addValue(name, "mdot_fan", Value.theType.eDouble, mdot_fan, "[lbm/s]", "mass flow thru Fan", "", true);
      valueSet.addValue(name, "w_fan", Value.theType.eDouble, w_fan, "[Btu/lbm]", "Specific Work, Fan", "", true);
      valueSet.addValue(name, "Wdot_fan", Value.theType.eDouble, Wdot_fan, "[hp]", "Fan Work (in hp)", "", true);
      valueSet.addValue(name, "Pt8", Value.theType.eDouble, Pt8, "[lbf/ft^2]", "Stagnation Pressure @ stage 8", "", true);
      valueSet.addValue(name, "Tt8", Value.theType.eDouble, Tt8, "[degR]", "Stagnation Temp. @ stage 8", "", true);
    }
    valueSet.valuePush(this);
  }
}
