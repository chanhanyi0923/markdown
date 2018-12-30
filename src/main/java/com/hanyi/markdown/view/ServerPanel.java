package com.hanyi.markdown.view;

import com.hanyi.markdown.model.Server;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ServerPanel extends JPanel {

  public ServerPanel() {
    GridLayout layout = new GridLayout(1, 0);
    this.setLayout(layout);

    JLabel label = new JLabel("Server");
    this.add(label);

    final JTextField portField = new JTextField();
    this.add(portField);

    this.add(new JButton("Establish") {{
      this.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          try {
            final int port = Integer.valueOf(portField.getText());
            (new Thread() {
              @Override
              public void run() {
                Server server = new Server();
                server.start(port);
              }
            }).start();
            JOptionPane.showMessageDialog(null, "server established");
          } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, "invalid port");
          }
        }
      });
    }});

  }
}
