package remotedeploy.dialog;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FileChangeWatcherThread  extends Thread{

	public static Map<String, Thread> pathThread = new ConcurrentHashMap<String,Thread>();
	
	private String path = null;
	
	public static void stop(String path){
		if(pathThread.containsKey(path)){
			pathThread.get(path).interrupt();
			pathThread.remove(path);
		}
	}
	
	public static void start(String path){
		stop(path);
		FileChangeWatcherThread thread = new FileChangeWatcherThread(path);
		thread.start();
		pathThread.put(path, thread);
	}
	
	public FileChangeWatcherThread(String path){
		this.path = path;
		pathThread.put(path, this);
	}
	
	@Override
	public void run() {
		try {
			FileChangeWatcher watch = new FileChangeWatcher(Paths.get(this.path));
			watch.handleEvents();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
