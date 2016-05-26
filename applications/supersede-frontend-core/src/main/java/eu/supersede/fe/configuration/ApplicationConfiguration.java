package eu.supersede.fe.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationConfiguration {

	private static String APPLICATION_PROPERTIES = "/wp5_application.properties";
	private static String APPLICATION_NAME = "application.name";
	
	public static void init()
	{
		InputStream input = Runtime.class.getResourceAsStream(APPLICATION_PROPERTIES);
		Properties props = new Properties();
		try {
			props.load(input);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		String applicationName = props.getProperty(APPLICATION_NAME);
		if(applicationName == null)
		{
			throw new RuntimeException("application.name properties is missing!");
		}
		
		System.setProperty(APPLICATION_NAME, applicationName);
	}
	
}
