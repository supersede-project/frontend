package eu.supersede.fe.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationConfiguration {

	private static String APPLICATION_PROPERTIES = "/wp5_application.properties";
	private static String APPLICATION_NAME = "application.name";
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ApplicationConfiguration.class);
	
	public static void init()
	{
		InputStream input = Runtime.class.getResourceAsStream(APPLICATION_PROPERTIES);
		if(input == null)
		{
			input = ApplicationConfiguration.class.getClassLoader().getResourceAsStream(APPLICATION_PROPERTIES);
		}
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

