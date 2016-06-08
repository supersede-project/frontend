/*
   (C) Copyright 2015-2018 The SUPERSEDE Project Consortium

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
     http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

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
