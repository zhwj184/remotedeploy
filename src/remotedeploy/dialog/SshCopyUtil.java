package remotedeploy.dialog;

import java.io.File;
import java.io.IOException;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;

public class SshCopyUtil {
	
	private static SCPClient client;
	
	public static void executeCommand() {
		if (ConfigDialog.host == null || ConfigDialog.user == null || ConfigDialog.passwd == null
				|| ConfigDialog.source == null || ConfigDialog.desc == null) {
			System.err.println("Can't execute SCP command. Please check \"hostname\" \"username\" and \"password\"");
			System.exit(1);
		}
		Connection conn = new Connection(ConfigDialog.host);
		try {
			conn.connect();
			boolean isAuthenticated = conn.authenticateWithPassword(ConfigDialog.user, ConfigDialog.passwd);
			if (isAuthenticated == false) {
				System.err.println("Authenticated false!!!");
			}
			client = new SCPClient(conn);
			client.put(ConfigDialog.source, ConfigDialog.desc); // 这里是将本地文件上传到服务器端的目录下
//			client.get("/home/liuwei/lwtest.txt", "/home/liuwei/shelltest/"); // 这里是将服务器端的文件下载到本地的目录下
			conn.close();
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}

	public static void put(String sourceFile){
		try {
			client.put(sourceFile, ConfigDialog.desc + "/" + sourceFile.substring(ConfigDialog.source.length()).replace(File.separator, "/"));
		} catch (IOException e) {
			System.err.println(e);
		}
	}
	
}
