package sk.ppmscan.app.config;

import sk.ppmscan.app.importexport.ignoredmanagers.IgnoredManagersHibernateImportExport;
import sk.ppmscan.app.importexport.ignoredmanagers.IgnoredManagersJsonImportExport;
import sk.ppmscan.app.importexport.ignoredmanagers.IgnoredManagersSQliteImportExport;
import sk.ppmscan.application.importexport.IgnoredManagersDao;

public enum IgnoredManagersFormat {

	JSON(new IgnoredManagersJsonImportExport()),
	SQLITE(new IgnoredManagersSQliteImportExport()),
	HIBERNATE(new IgnoredManagersHibernateImportExport());

	private IgnoredManagersDao ignoredManagersImportExporter;
	
	private IgnoredManagersFormat(IgnoredManagersDao ignoredManagersImportExporter) {
		this.ignoredManagersImportExporter = ignoredManagersImportExporter;
	}

	public IgnoredManagersDao getIgnoredManagersImportExporter() {
		return ignoredManagersImportExporter;
	}

}
