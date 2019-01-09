package com.hanyi.markdown.view;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javax.swing.SwingUtilities;

public class ContentsPanel extends JFXPanel {

  private WebView webView;

  public ContentsPanel() {
    this.setSceneLater();
  }

  private void setSceneLater() {
    final ContentsPanel panel = this;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        webView = new WebView();
        panel.setScene(new Scene(webView));
        ((MainFrame) SwingUtilities.getRoot(panel)).pack();
      }
    });
  }

  public void updateContent(final String html) {
    final String style = "<style>"
        + "body { background: antiquewhite; color: maroon; }"
        + "h1, h2, h3, h4, h5, h6 { margin: 0; padding: 0; line-height: 100%; }"
        + "h1 { font-size: 12pt; }"
        + "h2 { font-size: 10pt; }"
        + "h3 { font-size: 8pt; }"
        + "h4 { font-size: 8pt; }"
        + "h5 { font-size: 8pt; }"
        + "h6 { font-size: 8pt; }"
        + "</style>";
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        webView.getEngine().loadContent(style + html);
      }
    });
  }
}