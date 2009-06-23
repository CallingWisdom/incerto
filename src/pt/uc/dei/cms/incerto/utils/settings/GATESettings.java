package pt.uc.dei.cms.incerto.utils.settings;

import pt.uc.dei.cms.incerto.exceptions.IncertoException;
import pt.uc.dei.cms.incerto.utils.InOutUtils;
import pt.uc.dei.cms.incerto.utils.LoggerUtils;

public class GATESettings {
	private static GATESettings settings;
	private PropertiesReader properties;
	public String settingsFile = "gate.properties";

	public static GATESettings getInstance()
	{
		if(settings==null)
			try {
				settings = new GATESettings();
			} catch (IncertoException e) {
				//TODO: Solve this problem...
				LoggerUtils.addError(e);
				throw new Error(e);
			}
		return settings;
	}
	
	public GATESettings() throws IncertoException
	{
		try {
			properties = new PropertiesReader(settingsFile);
		} catch (Exception e) {
			LoggerUtils.addError(e);
			throw new IncertoException("Problem loading property file "+settingsFile,e);
		}
		LOCATION = InOutUtils.normalizeFolderPath(properties.getString("Location"));
		LoggerUtils.addConfig("GATE location: "+LOCATION);
	}
	
	public String LOCATION;
	
	
}
