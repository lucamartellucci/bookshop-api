package com.absontheweb.bookshop.service.impl;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.absontheweb.bookshop.i18n.model.Language;
import com.absontheweb.bookshop.i18n.model.MessageResource;
import com.absontheweb.bookshop.i18n.model.MessageResourceLocale;
import com.absontheweb.bookshop.service.I18nService;
import com.absontheweb.bookshop.service.exception.I18nServiceException;

@Service
public class I18nServiceImpl implements I18nService {

	private static Logger logger = LoggerFactory.getLogger(I18nServiceImpl.class);

	private String directoryPath;
	private String propertiesDirectoryPath;
    private String availableLanguagesBundleName = "console";

	private MessageResource messageResource;
	private List<Language> supportedLanguages = new ArrayList<Language>();

	public void init() throws I18nServiceException {
		refreshMessageResource();
		refreshSupportedLanguages();
	}

	public MessageResource retrieveMessageResource() throws I18nServiceException {
		return messageResource;
	}

	public boolean isChanged(int hashCode) throws I18nServiceException {
		logger.debug("hashCode:{} + messageResource.hashCode():  {}", hashCode, messageResource.hashCode());
		boolean isChanged = messageResource.hashCode() != hashCode;
		logger.debug("isChanged:{}", isChanged);
		return isChanged;
	}

	public void fireChanges(String dirName) {
		try {
			logger.debug("fireChanges: {}", dirName);

			refreshMessageResource();

			refreshSupportedLanguages();
		} catch (I18nServiceException e) {
			logger.error("Unable to fireChanges: ", e);
		}
	}

	public void refreshMessageResource() throws I18nServiceException {
		refreshMessageResource(true);
	}

	protected void refreshMessageResource(boolean solveReferences) throws I18nServiceException {
		logger.debug("Start createMessageResource");
		try {
			MessageResource messageResourceTemp = new MessageResource();
			File directory = new File(directoryPath);
			if (!directory.isDirectory())
				throw new I18nServiceException(directoryPath + " is not directory");
			if (!directory.exists())
				throw new I18nServiceException(directoryPath + " not found");
			for (File f : directory.listFiles()) {
				if (f.isDirectory()) {
					MessageResourceLocale messageResourceLocale = new MessageResourceLocale();
					Collection<File> listFiles = FileUtils.listFiles(f, new RegexFileFilter("^.+\\.properties"), DirectoryFileFilter.DIRECTORY);
					for (File innerFile : listFiles) {
						Properties props = new Properties();
						props.load(new FileReader(innerFile));
						if (solveReferences) {
							props = solveReferences(props, innerFile.getName());
						}
						messageResourceLocale.getPropertiesMap().put(getLocale(innerFile.getName())[1], props);
					}
					messageResourceTemp.getNameSpaceMap().put(f.getName(), messageResourceLocale);
				}
			}

			messageResource = messageResourceTemp;
			logger.trace("messageResource: {}", messageResource);
		} catch (Exception e) {
			logger.error("Unable to refreshMessageResource: ", e);
			throw new I18nServiceException(e);
		} finally {
			logger.debug("End createMessageResource");
		}
	}

	/**
	 * This method return an array that contains in position 0 base filename, in
	 * position 1 locale code. For example getLocale(messages_IT.properties)
	 * return {messages,IT}
	 * 
	 * @param fileName
	 * @return
	 * @throws I18nServiceException
	 */
	public String[] getLocale(String fileName) throws I18nServiceException {
		try {
			logger.trace("Start getLocale");
			if (fileName == null || fileName.trim().isEmpty())
				throw new I18nServiceException("wrong input parameter: " + fileName);
			String[] split = fileName.split(".properties");
			int fromIndex = split[0].indexOf("_");
			if (fromIndex == -1) {
				return new String[] { split[0] };
			}
			return new String[] { split[0].substring(0, fromIndex), (split[0].substring(++fromIndex).toUpperCase()) };
		} catch (Exception e) {
			logger.error("Unable to getLocale");
			throw new I18nServiceException("Unable to getLocale", e);
		} finally {
			logger.trace("End getLocale");
		}
	}

	public Properties solveReferences(Properties propertiesWithReferences, String fileName) throws I18nServiceException {
		logger.debug("Start solveReference");
		try {
			Properties propertiesWithoutReferences = new Properties();
			for (Object keyTemp : propertiesWithReferences.keySet()) {
				String key = (String) keyTemp;
				String value = propertiesWithReferences.getProperty(key);
				String extractedReference = extractReference(value);
				if (isReference(value)) {
					value = propertiesWithReferences.getProperty(extractedReference);
				}
				if (value == null) {
					logger.warn("File: {}", fileName);
					logger.warn("Key {} not solved. {} not found.", key, extractedReference);
					value = propertiesWithReferences.getProperty(key);
				} else if (isReference(value)) {
					logger.warn("File: {}", fileName);
					logger.warn("The key {} has multiple references", key);
					logger.warn(key + " not solved");
					value = key;
				}
				propertiesWithoutReferences.setProperty(key, value);
			}
			return propertiesWithoutReferences;
		} finally {
			logger.debug("End solveReference");
		}
	}

	public boolean isReference(String value) {
		logger.trace("Start isReference({})", value);
		try {
			if (value == null)
				return false;
			value = removeBlanks(value);
			if (value.isEmpty() || value.length() < 4)
				return false;
			return (value.indexOf("${") == 0 && value.indexOf('}') == value.length() - 1);
		} finally {
			logger.trace("End isReference");
		}
	}

	public String extractReference(String value) {
		logger.trace("Start extractReference({})", value);
		if (isReference(value)) {
			value = removeBlanks(value);
			return value.substring(2, value.length() - 1);
		} else
			return null;
	}

	public void refreshSupportedLanguages() {
		logger.debug("Start refreshSupportedLanguages");
		try {
			List<Language> newSupportedLanguages = new ArrayList<Language>();
			Properties filterProperties = new Properties();
			boolean filterEnabled = false;
			ArrayList<String> languagesAllowedList = new ArrayList<String>();
			logger.debug("propertiesDirectoryPath: {}", propertiesDirectoryPath);
			File cradleCoreService = new File(propertiesDirectoryPath);
			filterProperties.load(new FileReader(cradleCoreService));
			logger.debug("Properties loaded");
			if ("true".equalsIgnoreCase(filterProperties.getProperty("filter.languages")))
				filterEnabled = true;
			logger.debug("Filter enabled: {}", filterEnabled);
			if (filterEnabled) {
				StringTokenizer languagesAllowed = new StringTokenizer(filterProperties.getProperty("allowed.languages"), ",");
				while (languagesAllowed.hasMoreTokens())
					languagesAllowedList.add(languagesAllowed.nextToken().toUpperCase());
				logger.debug("Languages allowed: {}", languagesAllowedList.toString());
			}
			MessageResourceLocale nameSpaceMap = messageResource.getNameSpaceMap().get( availableLanguagesBundleName );
			logger.trace("nameSpaceMap: {}", nameSpaceMap);
			if (nameSpaceMap != null && nameSpaceMap.getPropertiesMap() != null) {
				Map<String, Properties> propertiesMap = nameSpaceMap.getPropertiesMap();
				logger.trace("propertiesMap: {}", propertiesMap);
				for (String languageCode : propertiesMap.keySet()) {
					if (!"DEFAULT".equalsIgnoreCase(languageCode)) {
						if (!filterEnabled || languagesAllowedList.contains(languageCode)) {
							Properties properties = nameSpaceMap.getPropertiesMap().get(languageCode);
							String languageName = properties.getProperty("languageName");
							Locale languageLocale = new Locale(languageCode);
							Language toAdd = new Language(languageName, languageLocale.getLanguage());
							newSupportedLanguages.add(toAdd);
						}
					}
				}
			}
			supportedLanguages = newSupportedLanguages;
			logger.debug("SupportedLanguages: {}", supportedLanguages);
		} catch (Exception e) {
			logger.error("Unable to refresh supported languages", e);
		} finally {
			logger.debug("End refreshSupportedLanguages");
		}
	}

	public String removeBlanks(String oldString) {
		while ((!oldString.isEmpty()) && (oldString.charAt(0) == ' ')) {
			oldString = oldString.substring(1);
		}
		int lastCharIndex = oldString.length() - 1;
		while ((lastCharIndex > 0) && (oldString.charAt(lastCharIndex) == ' ')) {
			oldString = oldString.substring(0, lastCharIndex--); // lastCharIndex
																	// is post
																	// decremented
																	// (Vincenzo)
		}
		return oldString;
	}

	public void setDirectoryPath(String directoryPath) {
		this.directoryPath = directoryPath;
	}

	public void setMessageResource(MessageResource messageResource) {
		this.messageResource = messageResource;
	}

	public List<Language> retrieveSupportedLanguages() {
		return supportedLanguages;
	}

	public void setPropertiesDirectoryPath(String propertiesDirectoryPath) {
		this.propertiesDirectoryPath = propertiesDirectoryPath;
	}

	public String getDirectoryPath() {
		return directoryPath;
	}

    public void setAvailableLanguagesBundleName( final String availableLanguagesBundleName ) {
        this.availableLanguagesBundleName = availableLanguagesBundleName;
    }
}
