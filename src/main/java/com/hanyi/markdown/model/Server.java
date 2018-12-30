package com.hanyi.markdown.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

  private ArrayList<TextThread> textThreads;

  public Server() {
    this.textThreads = new ArrayList<>();
  }

  public void start(int port) {
    try {
      ServerSocket serverSocket = new ServerSocket(port);
      while (true) {
        Socket socket = serverSocket.accept();
        TextThread thread = new TextThread(socket);
        textThreads.add(thread);
        new Thread(thread).start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private class TextThread implements Runnable {

    private Socket socket;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;

    public TextThread(Socket socket) {
      super();
      this.socket = socket;
      this.dataIn = null;
      this.dataOut = null;
    }

    private void send(String message) {
      try {
        dataOut.writeUTF(message);
        dataOut.flush();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    @Override
    public void run() {
      try {
        dataIn = new DataInputStream(socket.getInputStream());
        dataOut = new DataOutputStream(socket.getOutputStream());
        while (true) {
          String message = dataIn.readUTF();
          for (TextThread thread : textThreads) {
            thread.send(message);
          }
        }
      } catch (IOException e) {
        try {
          socket.close();
          textThreads.remove(this);
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      } finally {
        try {
          dataIn.close();
          dataOut.close();
          socket.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

  }
}

