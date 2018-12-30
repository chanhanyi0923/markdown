package com.hanyi.markdown.model;

import com.hanyi.markdown.view.TextAreaPanel;
import com.hanyi.markdown.view.WebPanel;
import java.util.concurrent.Semaphore;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class Text {

  private WebPanel webPanel;
  private TextAreaPanel textAreaPanel;
  private Client client;
  private String content;
  private Semaphore mutex;
  private boolean locked;

  public Text() {
    this.content = "";
    this.client = new Client();
    this.client.setText(this);
    this.mutex = new Semaphore(1);
    this.locked = false;
  }

  public Client getClient() {
    return this.client;
  }

  public void setContent(String content) {
    if (!this.locked) {
      try {
        this.mutex.acquire(1);
        this.content = content;
        this.client.send(this.content);
        this.webPanel.updateContent(content);
      } catch (InterruptedException e) {
      } finally {
        this.mutex.release(1);
      }
    }
  }

  public String toHtml()
  {
    Parser parser = Parser.builder().build();
    Node document = parser.parse(content);
    HtmlRenderer renderer = HtmlRenderer.builder().build();
    String html = renderer.render(document);
    return html;
  }

  public String getContent() {
    return this.content;
  }

  public void setTextAreaPanel(TextAreaPanel textAreaPanel) {
    this.textAreaPanel = textAreaPanel;
  }

  public void setWebPanel(WebPanel webPanel) {
    this.webPanel = webPanel;
  }

  public void textUpdated(String content) {
    try {
      this.locked = true;
      this.mutex.acquire(1);
      this.content = content;
      this.textAreaPanel.setText(content);
      this.webPanel.updateContent(content);
    } catch (InterruptedException e) {
    } finally {
      this.mutex.release(1);
      this.locked = false;
    }
  }
}
