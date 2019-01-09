package com.hanyi.markdown.view;

import com.hanyi.markdown.Config;
import com.hanyi.markdown.model.Text;
import com.hanyi.markdown.model.TextFile;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

  private ClientPanel clientPanel;
  private ServerPanel serverPanel;
  private TextAreaPanel textAreaPanel;
  private MenuBar menuBar;
  private WebPanel webPanel;
  private ContentsPanel contentsPanel;
  private FilePanel filePanel;

  public MainFrame(String title, Text text, TextFile textFile) {
    super(title);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
    this.setResizable(true);
    this.setResizable(false);

    Container contentPane = this.getContentPane();
    contentPane.setLayout(new BorderLayout());

    // add server panel
    this.serverPanel = new ServerPanel();
    // add client panel
    this.clientPanel = new ClientPanel(text);

    JPanel top = new JPanel();
    top.setLayout(new BorderLayout());
    top.add(this.clientPanel, BorderLayout.NORTH);
    top.add(this.serverPanel, BorderLayout.SOUTH);
    contentPane.add(top, BorderLayout.NORTH);
    this.pack();

    // add text area panel
    this.textAreaPanel = new TextAreaPanel(text);
    contentPane.add(this.textAreaPanel, BorderLayout.CENTER);
    this.pack();
    final Dimension textAreaSize = this.textAreaPanel.getSize();

    // add web (preview) panel
    this.contentsPanel = new ContentsPanel();
    this.contentsPanel.setPreferredSize(new Dimension(100, textAreaSize.height));
    this.webPanel = new WebPanel(text, this.contentsPanel);
    this.webPanel.setPreferredSize(textAreaSize);
//    this.add(this.webPanel, BorderLayout.EAST);
//    this.pack();
    JPanel right = new JPanel();
    right.setLayout(new BorderLayout());
    right.add(this.webPanel, BorderLayout.WEST);
    right.add(this.contentsPanel, BorderLayout.EAST);
    this.add(right, BorderLayout.EAST);
    this.pack();


    // add file panel
    this.filePanel = new FilePanel(textFile);
    this.filePanel.setPreferredSize(new Dimension(Config.FILE_PANEL_WIDTH, textAreaSize.height));
    contentPane.add(filePanel, BorderLayout.WEST);
    this.pack();

    // menu bar
    this.menuBar = new MenuBar(text, this.filePanel);
    this.setJMenuBar(menuBar);
    this.pack();

    this.revalidate();
    this.repaint();
  }
}
