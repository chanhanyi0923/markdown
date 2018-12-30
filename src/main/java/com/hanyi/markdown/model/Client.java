package com.hanyi.markdown.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

@SuppressWarnings("serial")
public class Client {

  private Text text;
  private DataOutputStream dataOut;
  private DataInputStream dataIn;
  private Socket socket;
  private boolean connected;
  private Thread thread;

  public Client() {
    this.connected = false;
    this.dataOut = null;
    this.dataIn = null;
    this.socket = null;
    this.thread = null;
  }

  public void setText(Text text) {
    this.text = text;
  }

  public void connect(String host, int port) throws IOException {
    try {
      connected = true;
      socket = new Socket(host, port);
      dataOut = new DataOutputStream(socket.getOutputStream());
      dataIn = new DataInputStream(socket.getInputStream());
      thread = new Thread(new TextThread());
      thread.start();
    } catch (IOException e) {
      connected = false;
      throw e;
    }
  }

  public void disconnect() {
    if (connected) {
      connected = false;
      try {
        dataOut.close();
        dataIn.close();
        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void send(String message) {
    if (connected) {
      try {
        dataOut.writeUTF(message);
        dataOut.flush();
      } catch (IOException exception) {
        exception.printStackTrace();
      }
    }
  }

  private class TextThread implements Runnable {

    @Override
    public void run() {
      try {
        while (connected) {
          String message = dataIn.readUTF();
          text.textUpdated(message);
        }
      } catch (SocketException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
