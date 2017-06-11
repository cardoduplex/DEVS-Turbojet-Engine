/*
 * CSE 593 - Fall 2016 - Applied Project
 * Author  : Lucio Ortiz and Robert Blazewicz
 * Version : DEVSJAVA 3.0
 * Date    : 2016-10-16
 */
package experiment.toolkit;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.apache.commons.lang3.StringUtils;

/**
 * The Class SwingSelectDirectory.
 */
public class SwingSelectDirectory {

  /**
   * Gets the pathname.
   *
   * @param initialPathname the initial pathname
   * @return the pathname
   */
  public static String getPathname(final String dialogTitle, final String initialPathname) {
    final SelectDirectory selectPath = new SelectDirectory(dialogTitle, initialPathname);
    selectPath.setVisible(true);
    selectPath.dispose();
    return selectPath.getPathname();
  }

  /**
   * The Class SelectDirectory.
   */
  private static class SelectDirectory extends JFrame {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4104446245647053655L;

    /** The pathname. */
    private String pathname;

    /**
     * Instantiates a new select directory.
     *
     * @param initialPathname the initial pathname
     */
    public SelectDirectory(final String dialogTitle, final String initialPathname) {
      final File currentDirectory = StringUtils.isBlank(initialPathname) ? null : new File(initialPathname);
      final JFileChooser fileChooser = new JFileChooser();
      fileChooser.setDialogTitle(dialogTitle);
      fileChooser.setCurrentDirectory(currentDirectory);
      fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      final int result = fileChooser.showOpenDialog(this);
      if (result == JFileChooser.APPROVE_OPTION) {
        final File selectedFile = fileChooser.getSelectedFile();
        pathname = selectedFile.getAbsolutePath();
      }
    }

    /**
     * Gets the pathname.
     *
     * @return the pathname
     */
    public String getPathname() {
      return pathname;
    }
  }
}
