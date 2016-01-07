package eu.supersede.fe.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:applications.properties")
public class ApplicationConfigLoader {

	private static final String[] langs = {"aa", "ab", "ae", "af", "ak", "am", "an", "ar", "as", "av", "ay", "az", "ba", "be", "bg", "bh", "bi", "bm", "bn", "bo", "br", "bs", "ca", "ce", "ch", "co", "cr", "cs", "cu", "cv", "cy", "da", "de", "dv", "dz", "ee", "el", "en", "eo", "es", "et", "eu", "fa", "ff", "fi", "fj", "fo", "fr", "fy", "ga", "gd", "gl", "gn", "gu", "gv", "ha", "he", "hi", "ho", "hr", "ht", "hu", "hy", "hz", "ia", "id", "ie", "ig", "ii", "ik", "io", "is", "it", "iu", "ja", "jv", "ka", "kg", "ki", "kj", "kk", "kl", "km", "kn", "ko", "kr", "ks", "ku", "kv", "kw", "ky", "la", "lb", "lg", "li", "ln", "lo", "lt", "lu", "lv", "mg", "mh", "mi", "mk", "ml", "mn", "mr", "ms", "mt", "my", "na", "nb", "nd", "ne", "ng", "nl", "nn", "no", "nr", "nv", "ny", "oc", "oj", "om", "or", "os", "pa", "pi", "pl", "ps", "pt", "qu", "rm", "rn", "ro", "ru", "rw", "sa", "sc", "sd", "se", "sg", "si", "sk", "sl", "sm", "sn", "so", "sq", "sr", "ss", "st", "su", "sv", "sw", "ta", "te", "tg", "th", "ti", "tk", "tl", "tn", "to", "tr", "ts", "tt", "tw", "ty", "ug", "uk", "ur", "uz", "ve", "vi", "vo", "wa", "wo", "xh", "yi", "yo", "za", "zh", "zu"};
	
	@Autowired
	private ApplicationUtil applicationUtil;
	
	@Autowired
	private Environment env;
	
	@PostConstruct
	public void load()
	{
		String applicationName = env.getProperty("application.name");
		String applicationLabel = env.getProperty("application.label");
		
		if(applicationName != null)
		{
			Map<String, String> applicationLabels = new HashMap<>();
			applicationLabels.put("", applicationLabel);
			for(String lang : langs)
			{
				String localApplicationLabel = env.getProperty("application.label." + lang);
				if(localApplicationLabel != null)
				{
					applicationLabels.put(lang, localApplicationLabel);
				}
			}
			
			String[] pageNames = env.getRequiredProperty("application.pages").split(",");
			
			for(String pageName : pageNames)
			{
				Map<String, String> pageLabels = new HashMap<>();
				List<String> trimmedPageRoles = new ArrayList<>();
				pageName = pageName.trim();
				
				String[] pageRoles = env.getRequiredProperty("application.page." + pageName + ".profiles").split(",");
				String pageLabel = env.getRequiredProperty("application.page." + pageName + ".label");
				pageLabels.put("", pageLabel);
				
				for(String lang : langs)
				{
					String localPageLabel = env.getProperty("application.page." + pageName + ".label." + lang);
					if(localPageLabel != null)
					{
						pageLabels.put(lang, localPageLabel);
					}
				}
				
				for(String pageRole : pageRoles)
				{
					trimmedPageRoles.add(pageRole.trim());
				}
				
				applicationUtil.addApplicationPage(applicationName, applicationLabels, pageName, pageLabels, trimmedPageRoles);
			}
		}
	}
	
}
