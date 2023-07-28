package sk.ppmscan.app;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sk.ppmscan.app.config.ApplicationConfiguration;
import sk.ppmscan.app.config.ApplicationConfigurationHolder;
import sk.ppmscan.app.hibernate.HibernateUtil;
import sk.ppmscan.application.PPMScanner;
import sk.ppmscan.application.importexport.IgnoredManagersDao;
import sk.ppmscan.application.importexport.ScanRunExporter;

/**
 * 
 * @author miroslav.sajko
 *
 */
public class MainApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainApplication.class);

	public static void main(String[] args) {
		LOGGER.info("PPMScan start");

		// Obtain a session from the SessionFactory
		try (SessionFactory sessionFactory = HibernateUtil.getSessionFactory("hibernate.sqlite.cfg.xml")) {

			ApplicationConfigurationHolder configurationHolder = ApplicationConfigurationHolder.getInstance();
			ApplicationConfiguration configuration = configurationHolder.getConfiguration();

			ScanRunExporter filteredTeamsExporter = configuration.getExportFormat().getFilteredTeamsExporter();

			IgnoredManagersDao ignoredManagersImportExport = configuration.getIgnoredManagersFormat()
					.getIgnoredManagersImportExporter();

			PPMScanner application = new PPMScanner();
			application.scan(configuration, filteredTeamsExporter, ignoredManagersImportExport);

		} catch (Exception e) {
			LOGGER.error("Scanning ended with an error", e);
		}
		LOGGER.info("PPMScan end");
	}

}
