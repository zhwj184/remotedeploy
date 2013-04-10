package remotedeploy.dialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
	
	public static String host = "host";
	public static String port = "port";
	public static String user = "user";
	public static String passwd = "passwd";
	public static String desc = "desc";
	public static String source = "source";
	public static String filemode="filemode";
	
	private static Properties pro = new Properties();
	
	static{
		try {
			pro.load(new FileInputStream(System.getProperty("java.io.tmpdir")+ File.separator + "remotedeploy.properties"));
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}
	
	public static String get(String key){
		if(pro.containsKey(key)){
			return pro.get(key).toString();
		}
		return "";
	}
	
	public static void init(){
		ConfigDialog.host = get(host);
		ConfigDialog.port = get(port);
		ConfigDialog.user = get(user);
		ConfigDialog.passwd = get(passwd);
		ConfigDialog.desc = get(desc);
		ConfigDialog.source = get(source);
		ConfigDialog.filemode = get(filemode);
	}
	
	public static void save(){
		pro.put(host, ConfigDialog.host);
		pro.put(port, ConfigDialog.port);
		pro.put(user, ConfigDialog.user);
		pro.put(passwd, ConfigDialog.passwd);
		pro.put(desc, ConfigDialog.desc);
		pro.put(source, ConfigDialog.source);
		pro.put(filemode, ConfigDialog.filemode);
		try {
			pro.store(new FileOutputStream(System.getProperty("java.io.tmpdir") + File.separator + "remotedeploy.properties"), "update");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
		}
	}
}
