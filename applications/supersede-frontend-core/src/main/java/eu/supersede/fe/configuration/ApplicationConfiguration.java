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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class containing configuration properties for the application.
 */
public class ApplicationConfiguration
{
    private static String APPLICATION_PROPERTIES = "/wp5_application.properties";
    private static String APPLICATION_NAME = "application.name";

    // Used to read the default multitenancy configuration.
    public static String DEFAULT_APPLICATION_NAME = "supersede-frontend";

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(ApplicationConfiguration.class);

    /**
     * Return the application name, read from the corresponding configuration file.
     */
    public static String getApplicationName()
    {
        InputStream input = Runtime.class.getResourceAsStream(APPLICATION_PROPERTIES);

        if (input == null)
        {
            input = ApplicationConfiguration.class.getClassLoader().getResourceAsStream(APPLICATION_PROPERTIES);
        }

        Properties props = new Properties();

        try
        {
            props.load(input);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        String applicationName = props.getProperty(APPLICATION_NAME);

        if (applicationName == null)
        {
            throw new RuntimeException("application.name properties is missing!");
        }

        return applicationName;
    }
}