package com.hanyi.markdown.view;

import com.hanyi.markdown.model.Text;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientPanel extends JPanel {

  public ClientPanel(final Text text) {
    GridLayout layout = new GridLayout(1, 0);
    this.setLayout(layout);

    JLabel label = new JLabel("Client");
    this.add(label);

    final JTextField ipField = new JTextField();
    this.add(ipField);

    final JTextField hostField = new JTextField();
    this.add(hostField);

    this.add(new JButton("Connect") {{
      this.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent event) {
          try {
            String host = ipField.getText();
            int port = Integer.valueOf(hostField.getText());
            try {
              text.getClient().connect(host, port);
            } catch (IOException e) {
              JOptionPane.showMessageDialog(null, "failed to connect");
              return;
            }
            JOptionPane.showMessageDialog(null, "connected");
          } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "invalid port");
          }
        }
      });
    }});

    this.add(new JButton("Disconnect") {{
      this.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          text.getClient().disconnect();
        }
      });
    }});
  }
}
