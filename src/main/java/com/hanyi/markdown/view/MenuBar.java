package com.hanyi.markdown.view;

import com.hanyi.markdown.model.Text;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar {

  public MenuBar(final Text text, final FilePanel filePanel) {
    JMenu menu = new JMenu("File");
    menu.add(
        new JMenuItem("open directory") {
          {
            this.addActionListener(
                new ActionListener() {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                      File file = fileChooser.getSelectedFile();
                      filePanel.setPath(file.getAbsolutePath());
                      filePanel.refresh();
                    }
                  }
                });
          }
        });
    menu.add(
        new JMenuItem("save") {
          {
            this.addActionListener(
                new ActionListener() {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                      File file = fileChooser.getSelectedFile();
                      String content = text.getContent();
                      try {
                        Files.write(Paths.get(file.getAbsolutePath()),
                            content.getBytes(StandardCharsets.UTF_8));
                      } catch (IOException exception) {
                        exception.printStackTrace();
                      }
                    }
                  }
                });
          }
        });
    menu.add(
        new JMenuItem("export html") {
          {
            this.addActionListener(
                new ActionListener() {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                      File file = fileChooser.getSelectedFile();
                      String content = text.toHtml();
                      try {
                        Files.write(Paths.get(file.getAbsolutePath()),
                            content.getBytes(StandardCharsets.UTF_8));
                      } catch (IOException exception) {
                        exception.printStackTrace();
                      }
                    }
                  }
                });
          }
        });
    this.add(menu);
  }
}
