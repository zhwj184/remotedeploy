package remotedeploy.dialog;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

public class FileChangeWatcher {

    private WatchService watcher;     
    
    public FileChangeWatcher(Path path)throws IOException{     
        watcher = FileSystems.getDefault().newWatchService();     
        path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,StandardWatchEventKinds.ENTRY_DELETE,StandardWatchEventKinds.ENTRY_MODIFY);     
    }     
         
    public void handleEvents() throws InterruptedException{   
    	
    	  for(;;) {
              WatchKey watckKey = watcher.take();

              List<WatchEvent<?>> events = watckKey.pollEvents();
              for (WatchEvent event : events) {
            	  Path child = Paths.get(ConfigDialog.source).resolve(event.context().toString());//¾ø¶ÔÂ·¾¶  
                 if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                    SshCopyUtil.put(child.toFile().getPath());
                 }
                 if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                    System.out.println("Delete: " + event.context().toString());
                 }
                 if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                	 SshCopyUtil.put(child.toFile().getPath());
                 }
               }

               boolean valid = watckKey.reset();
               if(!valid) {
                  // do some log work
                  break;
               }
           }       
    }  
    
    public static void main(String[] args) {

        //define a folder root
        Path myDir = Paths.get("C:/Users/weijian.zhongwj/workspace1/WeiboMarket/res/aaa");       

        try {
           WatchService watcher = myDir.getFileSystem().newWatchService();
           myDir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, 
           StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

           for(;;) {
              WatchKey watckKey = watcher.take();

              List<WatchEvent<?>> events = watckKey.pollEvents();
              for (WatchEvent event : events) {
                 if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                    System.out.println("Created: " + event.context().toString());
                 }
                 if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                    System.out.println("Delete: " + event.context().toString());
                 }
                 if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                    System.out.println("Modify: " + event.context().toString());
                 }
               }

               boolean valid = watckKey.reset();
               if(!valid) {
                  // do some log work
                  break;
               }
           }   // outer for loop ends           

        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }
}
