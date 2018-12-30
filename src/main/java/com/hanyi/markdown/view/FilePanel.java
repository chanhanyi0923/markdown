package com.hanyi.markdown.view;

import com.hanyi.markdown.Config;
import com.hanyi.markdown.model.TextFile;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.apache.commons.vfs2.FileChangeEvent;
import org.apache.commons.vfs2.FileListener;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.impl.DefaultFileMonitor;

public class FilePanel extends JPanel {

  private TextFile textFile;
  private DefaultMutableTreeNode root;
  private DefaultTreeModel treeModel;
  private JTree tree;
  private JScrollPane scrollPane;
  private File fileRoot;
  private String path;
  private DefaultFileMonitor fileMonitor;

  private class FileNode {

    private File file;

    public FileNode(File file) {
      this.file = file;
    }

    public File getFile() {
      return this.file;
    }

    @Override
    public String toString() {
      String name = file.getName();
      if (name.equals("")) {
        return file.getAbsolutePath();
      } else {
        return name;
      }
    }
  }

  private void appendChildren(File fileRoot,
      DefaultMutableTreeNode node) {
    File[] files = fileRoot.listFiles();
    if (files == null) {
      return;
    }

    for (File file : files) {
      DefaultMutableTreeNode childNode =
          new DefaultMutableTreeNode(new FileNode(file));
      node.add(childNode);
      if (file.isDirectory()) {
        appendChildren(file, childNode);
      }
    }
  }

  private void createTree() {
    fileRoot = new File(path);
    root = new DefaultMutableTreeNode(new FileNode(fileRoot));
    treeModel = new DefaultTreeModel(root);

    tree = new JTree(treeModel);
    tree.setShowsRootHandles(true);

    tree.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          DefaultMutableTreeNode node = (DefaultMutableTreeNode)
              tree.getLastSelectedPathComponent();
          if (node != null && node.getUserObject() != null) {
            File file = ((FileNode) node.getUserObject()).getFile();
            if (!file.isDirectory()) {
              textFile.open(file);
            }
          }
        }
      }
    });

    this.scrollPane.setViewportView(tree);

    // append children
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        root.removeAllChildren();
        appendChildren(fileRoot, root);
      }
    });
  }

  private void listenFileSystem() {
    try {
      FileSystemManager fsm = VFS.getManager();
      FileObject file = fsm.resolveFile(new File(path).getAbsolutePath());
      fileMonitor = new DefaultFileMonitor(new FileListener() {
        @Override
        public void fileCreated(FileChangeEvent event) {
          createTree();
        }

        @Override
        public void fileDeleted(FileChangeEvent event) {
          createTree();
        }

        @Override
        public void fileChanged(FileChangeEvent event) {
          createTree();
        }
      });
      fileMonitor.addFile(file);
      fileMonitor.start();
    } catch (FileSystemException e) {
      e.printStackTrace();
    }
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void refresh() {
    this.fileMonitor.stop();
    this.createTree();
    this.listenFileSystem();
  }

  public FilePanel(TextFile textFile) {
    this.path = Config.DEFAULT_PATH;
    this.textFile = textFile;
    this.setLayout(new BorderLayout());
    this.scrollPane = new JScrollPane();
    this.add(this.scrollPane, BorderLayout.CENTER);
    this.createTree();
    this.listenFileSystem();
  }
}
