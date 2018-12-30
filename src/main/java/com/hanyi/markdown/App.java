package com.hanyi.markdown;

import com.hanyi.markdown.model.TextFile;
import com.hanyi.markdown.model.Text;
import com.hanyi.markdown.view.MainFrame;

public class App {

  public static void main(String[] args) {

    Text text = new Text();
    TextFile textFile = new TextFile(text);
    MainFrame mainFrame = new MainFrame("Markdown Editor", text, textFile);
  }
}
