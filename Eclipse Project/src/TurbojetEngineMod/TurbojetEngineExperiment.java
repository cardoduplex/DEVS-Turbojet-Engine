/*
 * CSE 593 - Fall 2016 - Applied Project
 * Author  : Lucio Ortiz and Robert Blazewicz
 * Version : DEVSJAVA 3.0
 * Date    : 2016-09-16
 */
package TurbojetEngineMod;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Vector;

import TurbojetEngineMod.engine.MissionMonitor;
import experiment.design.BackPorch;
import experiment.design.DataRecorder;
import experiment.design.FrontPorch;
import experiment.toolkit.Settings;
import experiment.toolkit.SettingsSingleton;
import experiment.toolkit.ValueSet;
import view.modeling.ViewableAtomic;
import view.modeling.ViewableDigraph;

/**
 * The Class ExperimentEngine.
 */
public class TurbojetEngineExperiment extends ViewableDigraph {

  /** The settings. */
  protected final Settings settings = SettingsSingleton.getInstance();

  /** The front porch. */
  private ViewableAtomic frontPorch;

  /** The back porch. */
  private ViewableAtomic backPorch;

  /** The data recorder. */
  private ViewableAtomic dataRecorder;

  /** The models. */
  private final Vector<TurbojetEngine> models = new Vector<TurbojetEngine>();

  /** The black box model width. */
  private final int blackBoxModelWidth;

  /**
   * Instantiates a new experiment engine.
   */
  public TurbojetEngineExperiment() {
    this("Experiment Engine");
  }

  /**
   * Instantiates a new experiment engine.
   *
   * @param name the name
   */
  public TurbojetEngineExperiment(String name) {
    super(name);
    blackBoxModelWidth = settings.lookupInt("ExperimentEngine_BlackBoxModelWidth", "300");
    defineLayout();
  }

  /**
   * Define layout.
   */
  private void defineLayout() {
    final boolean showModelState = settings.lookupBool("ExperimentEngine_ShowModelState", "true");
    final String colorName = settings.lookupString("ExperimentEngine_BackgroundColor", "yellow");
    final boolean dumpValueSet = settings.lookupBool("ExperimentEngine_DumpValueSet", "true");
    final boolean dumpEachPass = settings.lookupBool("ExperimentEngine_DumpEachPass", "false");
    final boolean dumpDesignExperiment = settings.lookupBool("ExperimentEngine_DumpDesignExperiment", "true");
    final int dumpMessageAlignment = settings.lookupInt("ValueSet_MessageAlignment", String.valueOf(ValueSet.defaultDumpMessageAlignment));
    final int modelCount = settings.lookupInt("ExperimentEngine_ModelCount");

    frontPorch = new FrontPorch("FrontPorch", showModelState, colorName, dumpDesignExperiment, dumpMessageAlignment, modelCount);
    backPorch = new BackPorch("BackPorch", showModelState, colorName, new MissionMonitor());
    dataRecorder = new DataRecorder("DataRecorder", showModelState, colorName, dumpValueSet, dumpEachPass);

    add(frontPorch);
    add(backPorch);
    add(dataRecorder);
    addCoupling(backPorch, "xOut", frontPorch, "xIn");
    addCoupling(backPorch, "xOut", dataRecorder, "xIn");

    char initialVersion = 'A';
    for (int i = 0; i < ((FrontPorch)frontPorch).getModelCount(); i++, initialVersion++) {
      final TurbojetEngine model = new TurbojetEngine("TurbojetEngine", initialVersion);
      models.add(model);
    }

    for (ViewableDigraph model : models) {
      add(model);
      addCoupling(frontPorch, "xOut", model, "xIn");
      addCoupling(model, "xOut", backPorch, "xIn");
    }
  }

  /* (non-Javadoc)
   * @see view.modeling.ViewableDigraph#layoutForSimViewOverride()
   */
  @Override
  public boolean layoutForSimViewOverride()
  {
    int maxModelWidth = 0;
    for (TurbojetEngine model : models) {
      int modelWidth;
      if (model.isBlackBox())
        modelWidth = blackBoxModelWidth;
      else
        modelWidth = model.getWidth();

      if (maxModelWidth < modelWidth)
          maxModelWidth = modelWidth;
    }

    final int modelStartX = 20;
    final int modelStartY = 100;
    int pointX = 0;
    int pointY = 20;
    frontPorch.setPreferredLocation(new Point(pointX, pointY));
    pointX += maxModelWidth + 30;
    backPorch.setPreferredLocation(new Point(pointX, pointY));
    dataRecorder.setPreferredLocation(new Point(pointX, pointY + 80));
    pointX += 200;

    pointY = modelStartY;
    for (TurbojetEngine model : models) {
      model.setPreferredLocation(new Point(modelStartX, pointY));
      if (model.isBlackBox())
        pointY += 30;
      else
        pointY += model.getHeight() + 4;
    }

    preferredSize = new Dimension(pointX, pointY);
    return true;
  }
}
