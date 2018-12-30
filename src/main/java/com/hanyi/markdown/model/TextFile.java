package com.hanyi.markdown.model;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TextFile {
  private Text text;

  public TextFile(Text text) {
    this.text = text;
  }

  public void open(File file) {
    try {
      byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
      String content = new String(encoded, StandardCharsets.UTF_8);
      text.textUpdated(content);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
