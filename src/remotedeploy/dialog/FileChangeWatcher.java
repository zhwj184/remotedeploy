package remotedeploy.dialog;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class FileChangeWatcher {

    private WatchService watcher;     
    
    public FileChangeWatcher(Path path)throws IOException{     
        watcher = FileSystems.getDefault().newWatchService();     
        path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,StandardWatchEventKinds.ENTRY_DELETE,StandardWatchEventKinds.ENTRY_MODIFY);     
    }     
         
    public void handleEvents() throws InterruptedException{     
        while(true){     
            WatchKey key = watcher.take();     
            for(WatchEvent event : key.pollEvents()){     
                WatchEvent.Kind kind = event.kind();     
                     
                if(kind == StandardWatchEventKinds.OVERFLOW){//事件可能lost or discarded     
                    continue;     
                }     
                     
                WatchEvent e = (WatchEvent)event;     
                Path fileName = (Path) e.context();     
                if(e.kind() == StandardWatchEventKinds.ENTRY_CREATE || e.kind() == StandardWatchEventKinds.ENTRY_MODIFY){
                	SshCopyUtil.put(fileName.getFileName().toFile().getPath());
                }
                System.out.printf("Event %s has happened,which fileName is %s%n",kind.name(),fileName);     
            }     
            if(!key.reset()){     
                break;     
            }     
        }     
    }  
}
