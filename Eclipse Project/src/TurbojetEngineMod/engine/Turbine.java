/*
 * CSE 593 - Fall 2016 - Applied Project
 * Author  : Lucio Ortiz and Robert Blazewicz
 * Version : DEVSJAVA 3.0
 * Date    : 2016-10-03
 */
package TurbojetEngineMod.engine;

import experiment.toolkit.Calibration;
import experiment.toolkit.ExperimentState;
import experiment.toolkit.Value;
import experiment.toolkit.ValueSet;

/**
 * The Class Turbine.
 */
public class Turbine extends EngineBase {

  /** The fluid model. */
  private final String KeTJET_Str_FluidModel;

  /** Input: gamma. */
  private double gamma;

  /** Input: tao_r. */
  private double tao_r;

  /** Input: pi_c. */
  private double pi_c;

  /** Input: tao_c. */
  private double tao_c;

  /** Input: w_c. */
  private double w_c;

  /** Input: Wdot_c. */
  private double Wdot_c;

  /** Input: Tt4. */
  private double Tt4;

  /** Input: tao_lambda. */
  private double tao_lambda;

  /** Input: Pt4. */
  private double Pt4;

  /** Input: w_fan. */
  private double w_fan;

  /** Input: Wdot_fan. */
  private double Wdot_fan;

  /** Input: alpha. */
  private double alpha;

  /** Input: tao_fan. */
  private double tao_fan;

  /** Input: Cp_c. */
  private double Cp_c;

  /** Input: e_t. */
  private double e_t;

  /** Input: eta_b. */
  private double eta_b;

  /** Input: eta_m. */
  private double eta_m;

  /** Input: Tzero. */
  private double Tzero;

  /** Input: hpr. */
  private double hpr;

  /** Input: gamma_t. */
  private double gamma_t;

  /** Input: Cp. */
  private double Cp;

  /** Input: afterburnerOn. */
  private Boolean afterburnerOn;

  /** Output: w_t. */
  private double w_t;

  /** Output: Wdot_t. */
  private double Wdot_t;

  /** Output: tao_t. */
  private double tao_t;

  /** Output: pi_t. */
  private double pi_t;

  /** Output: Tt5. */
  private double Tt5;

  /** Output: Pt5. */
  private double Pt5;

  /** Output: f. */
  private double f;

  /**
   * Instantiates a new turbine.
   *
   * @param name the name
   */
  public Turbine(final String name) {
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

    // Turbine work
    w_t = -(w_c); // [Btu/lbm] (turbine work is = compressor work)

    // Turbine Specific Work
    Wdot_t = -Wdot_c; //[hp]

    // Temperature ratio at turbine
    tao_t = (1-((tao_r/tao_lambda)*(tao_c-1)));

    // Pressure ratio at turbine
    pi_t = Math.pow(1- (tao_r/tao_lambda) * ((Math.pow(pi_c, (gamma-1)/gamma))-1),(gamma/(gamma-1)));

    // Static Temp. @ Turbine exit/Nozzle entrance
    Tt5 = Tt4*tao_t; // [degR]

    // Static Press. @ Turbine exit/Nozzle entrance
    Pt5 = Pt4*pi_t; // [lbf/ft^2]

    if (experimentState.isFirstPass()) {
      valueSet.addValue(name, "w_t", Value.theType.eDouble, w_t, "[Btu/lbm]", "(turbine work is = compressor work)", "", true);
      valueSet.addValue(name, "Wdot_t", Value.theType.eDouble, Wdot_t, "[hp]", "Turbine Specific Work", "", true);
      valueSet.addValue(name, "tao_t", Value.theType.eDouble, tao_t, "", "Temperature ratio at turbine", "", true);
      valueSet.addValue(name, "pi_t", Value.theType.eDouble, pi_t, "", "Pressure ratio at turbine", "", true);
      valueSet.addValue(name, "Tt5", Value.theType.eDouble, Tt5, "[degR]", "Static Temp. @ Turbine exit/Nozzle entrance", "", true);
      valueSet.addValue(name, "Pt5", Value.theType.eDouble, Pt5, "[lbf/ft^2]", "Static Press. @ Turbine exit/Nozzle entrance", "", true);
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

    // Turbine work
    w_t = -(w_c + w_fan);  // [Btu/lbm] (turbine work is = compressor work)

    // Turbine Specific Work
    Wdot_t = -(Wdot_c + Wdot_fan);  //[hp]

    // Temperature ratio at turbine
    tao_t = 1  - ((1/(1+alpha)*(tao_r/tao_lambda)))   *((tao_c-1)+(alpha*(tao_fan-1)));

    // Pressure ratio at turbine
    pi_t = Math.pow((tao_t),(gamma/(gamma-1)));

    // Static Temp. @ Turbine exit/Nozzle entrance
    Tt5 = Tt4*tao_t;// [degR]

    // Static Press. @ Turbine exit/Nozzle entrance
    Pt5 = Pt4*pi_t;// [lbf/ft^2]

    if (!afterburnerOn)
        // fuel flow (ratio)
        f = ((Cp*Tzero)/hpr)*(tao_lambda-(tao_c*tao_r));

    if (experimentState.isFirstPass()) {
      valueSet.addValue(name, "w_t", Value.theType.eDouble, w_t, "[Btu/lbm]", "(turbine work is = compressor work)", "", true);
      valueSet.addValue(name, "Wdot_t", Value.theType.eDouble, Wdot_t, "[hp]", "Turbine Specific Work", "", true);
      valueSet.addValue(name, "tao_t", Value.theType.eDouble, tao_t, "", "Temperature ratio at turbine", "", true);
      valueSet.addValue(name, "pi_t", Value.theType.eDouble, pi_t, "", "Pressure ratio at turbine", "", true);
      valueSet.addValue(name, "Tt5", Value.theType.eDouble, Tt5, "[degR]", "Static Temp. @ Turbine exit/Nozzle entrance", "", true);
      valueSet.addValue(name, "Pt5", Value.theType.eDouble, Pt5, "[lbf/ft^2]", "Static Press. @ Turbine exit/Nozzle entrance", "", true);
      if (!afterburnerOn)
        valueSet.addValue(name, "f", Value.theType.eDouble, f, "", "fuel flow (ratio)", "", true);
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

    // Turbine work
    w_t = -(w_c + w_fan)*eta_m;  // [Btu/lbm] (turbine work is = compressor work)

    // Turbine rate of work
    Wdot_t = -(Wdot_c + Wdot_fan);  //[hp]

    // Fuel flow rate before Afterburner
    f = (tao_lambda-(tao_r*tao_c))/(((hpr*eta_b)/(Cp_c*Tzero))-tao_lambda);

    // Temperature ratio at Turbine
    tao_t = 1  - (  (1/(eta_m*(1+f)))*(tao_r/tao_lambda)   * ((tao_c-1)+(alpha*(tao_fan-1))));

    // Pressure ratio at Turbine
    pi_t = Math.pow((tao_t),((gamma_t)/(e_t*(gamma_t-1))));

    // Total Temperature at Turbine
    Tt5 = Tt4*tao_t;// [degR]

    // Total Pressure at Turbine
    Pt5 = Pt4*pi_t;// [lbf/ft^2]

    if (experimentState.isFirstPass()) {
      valueSet.addValue(name, "w_t", Value.theType.eDouble, w_t, "[Btu/lbm]", "(turbine work is = compressor work)", "", true);
      valueSet.addValue(name, "Wdot_t", Value.theType.eDouble, Wdot_t, "[hp]", "Turbine Specific Work", "", true);
      valueSet.addValue(name, "f", Value.theType.eDouble, f, "", "fuel flow (ratio)", "", true);
      valueSet.addValue(name, "tao_t", Value.theType.eDouble, tao_t, "", "Temperature ratio at turbine", "", true);
      valueSet.addValue(name, "pi_t", Value.theType.eDouble, pi_t, "", "Pressure ratio at turbine", "", true);
      valueSet.addValue(name, "Tt5", Value.theType.eDouble, Tt5, "[degR]", "Static Temp. @ Turbine exit/Nozzle entrance", "", true);
      valueSet.addValue(name, "Pt5", Value.theType.eDouble, Pt5, "[lbf/ft^2]", "Static Press. @ Turbine exit/Nozzle entrance", "", true);
    }
    valueSet.valuePush(this);
  }
}
