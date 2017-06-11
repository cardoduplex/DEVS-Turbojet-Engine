/*
 * CSE 593 - Fall 2016 - Applied Project
 * Author  : Lucio Ortiz and Robert Blazewicz
 * Version : DEVSJAVA 3.0
 * Date    : 2016-10-16
 */
package experiment.toolkit;

import org.apache.commons.lang3.StringUtils;

/**
 * The Class PathConfiguration.
 */
public class PathConfiguration {

  /** The Constant defaultPathnameConfigEnv. */
  private static final String defaultPathnameConfigEnv = "DEVS_EXPERIMENT_ENGINE_CONFIG";

  /** The Constant defaultPathnameOutputEnv. */
  private static final String defaultPathnameOutputEnv = "DEVS_EXPERIMENT_ENGINE_OUTPUT";

  /** The Constant defaultPathnameConfig. */
  private static final String defaultPathnameConfig = ".";

  /** The Constant defaultPathnameOutput. */
  private static final String defaultPathnameOutput = ".";

  /** The pathname config. */
  private String pathnameConfig;

  /** The pathname output. */
  private String pathnameOutput;

  /**
   * Instantiates a new path configuration.
   *
   * @throws RuntimeException the runtime exception
   */
  public PathConfiguration() throws RuntimeException {
    final String envPathnameConfig = System.getenv(defaultPathnameConfigEnv);
    final String envPathnameOutput = System.getenv(defaultPathnameOutputEnv);
    if (StringUtils.isBlank(envPathnameConfig))
      pathnameConfig = SwingSelectDirectory.getPathname("Select the DEVS Experiment Engine config directory.", defaultPathnameConfig);
    else
      pathnameConfig = envPathnameConfig;
    if (StringUtils.isBlank(envPathnameOutput))
      pathnameOutput = SwingSelectDirectory.getPathname("Select the DEVS Experiment Engine output directory.", defaultPathnameOutput);
    else
      pathnameOutput = envPathnameOutput;
  }

  /**
   * Gets the pathname config.
   *
   * @return the pathname config
   */
  public String getPathnameConfig() {
    return pathnameConfig;
  }

  /**
   * Gets the pathname output.
   *
   * @return the pathname output
   */
  public String getPathnameOutput() {
    return pathnameOutput;
  }
}
