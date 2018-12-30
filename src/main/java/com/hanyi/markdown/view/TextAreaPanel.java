package com.hanyi.markdown.view;

import com.hanyi.markdown.Config;
import com.hanyi.markdown.model.Text;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TextAreaPanel extends JPanel {

  private Text text;
  private JTextArea textArea;
  private int caretPosition;

  public void setText(String text) {
    this.textArea.setText(text);
    try {
      this.textArea.setCaretPosition(caretPosition);
    } catch (IllegalArgumentException e) {
      this.textArea.setCaretPosition(textArea.getDocument().getLength());
    }
  }

  private void textUpdated() {
    text.setContent(textArea.getText());
  }

  public TextAreaPanel(Text text) {
    this.caretPosition = 0;
    this.text = text;
    this.text.setTextAreaPanel(this);

    textArea = new JTextArea(Config.TEXT_AREA_ROWS, Config.TEXT_AREA_COLUMNS);
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    textArea.addCaretListener(
        new CaretListener() {
          @Override
          public void caretUpdate(CaretEvent e) {
            caretPosition = e.getDot();
          }
        }
    );
    textArea.getDocument().addDocumentListener(
        new DocumentListener() {
          @Override
          public void removeUpdate(DocumentEvent e) {
            textUpdated();
          }

          @Override
          public void insertUpdate(DocumentEvent e) {
            textUpdated();
          }

          @Override
          public void changedUpdate(DocumentEvent e) {
            textUpdated();
          }
        }
    );
    this.add(new JScrollPane(textArea));
  }
}
