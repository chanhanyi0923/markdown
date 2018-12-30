//package com.hanyi.markdown;
//
//
//import org.apache.commons.vfs2.FileChangeEvent;
//import org.apache.commons.vfs2.FileListener;
//import org.apache.commons.vfs2.FileName;
//import org.apache.commons.vfs2.FileObject;
//import org.apache.commons.vfs2.FileSystemManager;
//import org.apache.commons.vfs2.VFS;
//import org.apache.commons.vfs2.impl.DefaultFileMonitor;
//
//
//import java.io.File;
//
///**
// * Created by dushangkui on 2017/4/24.
// */
//public class ListenerTest {
//
//  public void test() {
//    try {
//      FileSystemManager fsm = VFS.getManager();
//      FileObject file = fsm.resolveFile(new File("./").getAbsolutePath());
//      DefaultFileMonitor fileMonitor = new DefaultFileMonitor(new FileListener() {
//        @Override
//        public void fileCreated(FileChangeEvent event) throws Exception {
//          resolveEvent("Created", event);
//        }
//
//        @Override
//        public void fileDeleted(FileChangeEvent event) throws Exception {
//          resolveEvent("Deleted", event);
//        }
//
//        @Override
//        public void fileChanged(FileChangeEvent event) throws Exception {
//          resolveEvent("Changed", event);
//        }
//
//        private void resolveEvent(String type, FileChangeEvent event) {
//          FileObject fileObject = event.getFile();
//          FileName fileName = fileObject.getName();
//          System.out.println(type + ": " + fileName.toString());
//        }
//      });
//      fileMonitor.addFile(file);
//      fileMonitor.start();
//      while (true) {
//        Thread.sleep(1000);
//      }
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//  }
//}
