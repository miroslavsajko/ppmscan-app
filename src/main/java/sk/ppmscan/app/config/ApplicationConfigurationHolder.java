package sk.ppmscan.app.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

public class ApplicationConfigurationHolder {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfigurationHolder.class);

	private static final String CONFIG_FILENAME = "PPMScanConfig.json";

	private static ApplicationConfigurationHolder configHolder = null;

	private ApplicationConfiguration configuration;

	private ApplicationConfigurationHolder() throws Exception {
		this.configuration = validateConfiguration(readConfiguration());
	}

	private static ApplicationConfiguration readConfiguration() throws Exception {
		LOGGER.info("Reading configuration file");
		long startTimestamp = System.currentTimeMillis();
		ApplicationConfiguration configuration = null;
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			FileReader json = new FileReader(CONFIG_FILENAME);
			configuration = gson.fromJson(json, ApplicationConfiguration.class);
			LOGGER.info("Configuration was successfully read");
		} catch (FileNotFoundException e) {
			LOGGER.info("Configuration file was not found");
		}
		if (configuration == null) {
			File configFile = new File(CONFIG_FILENAME);
			configFile.createNewFile();
			FileWriter writer = new FileWriter(configFile);
			JsonWriter jsonWriter = gson.newJsonWriter(writer);
			configuration = new ApplicationConfiguration();
			gson.toJson(gson.toJsonTree(configuration), jsonWriter);
			jsonWriter.close();
			writer.close();
			LOGGER.info("New configuration file with default values was created");
		}
		LOGGER.info("The operation took {} ms", System.currentTimeMillis() - startTimestamp);
		return configuration;
	}

	private static ApplicationConfiguration validateConfiguration(ApplicationConfiguration configuration) {
		if (configuration.getManagerIdFrom() > configuration.getManagerIdTo()) {
			long temp = configuration.getManagerIdFrom();
			configuration.setManagerIdFrom(configuration.getManagerIdTo());
			configuration.setManagerIdTo(temp);
		}
		if (configuration.getManagerIds() == null) {
			configuration.setManagerIds(new LinkedList<Long>());
		}
		if (configuration.getSizeOfThreadPool() < 1 || configuration.getSizeOfThreadPool() > 30) {
			configuration.setSizeOfThreadPool(10);
		}
		if (configuration.getExportFormat() == null) {
			configuration.setExportFormat(ExportFormat.EXCEL);
		}
		if (configuration.getMillisecondsBetweenPageLoads() < 100) {
			configuration.setMillisecondsBetweenPageLoads(100);
		}
		if (configuration.getIgnoreListLastLoginMonthsThreshold() < 0
				|| configuration.getIgnoreListLastLoginMonthsThreshold() > 360) {
			configuration.setIgnoreListLastLoginMonthsThreshold(36);
		}
		return configuration;
	}

	public static ApplicationConfigurationHolder getInstance() throws Exception {
		if (configHolder == null)
			configHolder = new ApplicationConfigurationHolder();

		return configHolder;
	}

	public ApplicationConfiguration getConfiguration() {
		return configuration;
	}

}
