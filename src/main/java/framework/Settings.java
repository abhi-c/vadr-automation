package framework;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Settings {
	private static Properties properties;
	
	public static synchronized Properties getProperties(){
		if (properties ==null) {
			loadPropertiesFile();
			overwriteSystemProps();
			
		}
		return properties;
	}
	

	public static void loadPropertiesFile(){
		FrameworkParameters parameters = FrameworkParameters.getInstance();
		
		//FrameworkParameters.setRelativePath();
		
		if (parameters.getRelativePath() == null) {
			throw new FrameworkException("Properties.relativePath is not set.");
		}
		
		properties = new Properties();
		try {
			properties.load(new FileInputStream(parameters
					.getRelativePath()
					+parameters.fileSeparator
					+"application.properties"));
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new FrameworkException("FileNotFoundException while loading the Global Settings File");
		}catch(IOException e){
			e.printStackTrace();
			throw new FrameworkException("IOException while loading the application.properties");
		}
	}
	
	private static void overwriteSystemProps() {
		// TODO Auto-generated method stub
		Properties props = System.getProperties();
		for(String key : props.stringPropertyNames()){
			properties.setProperty(key, props.getProperty(key));
		}
	}
	
	public Object clone() throws CloneNotSupportedException{
		throw new CloneNotSupportedException();
	}
	

}

