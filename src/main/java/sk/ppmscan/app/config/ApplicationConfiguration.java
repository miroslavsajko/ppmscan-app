package sk.ppmscan.app.config;

import sk.ppmscan.application.config.PPMScanConfiguration;

public class ApplicationConfiguration extends PPMScanConfiguration {
	
	/**
	 * Format in which the result will be exported.
	 */
	private ExportFormat exportFormat = ExportFormat.EXCEL;
	
	/**
	 * Format which is used to import and export ignored managers.
	 */
	private IgnoredManagersFormat ignoredManagersFormat = IgnoredManagersFormat.JSON;

	public ApplicationConfiguration() {
		super();
	}

	public ExportFormat getExportFormat() {
		return exportFormat;
	}

	public void setExportFormat(ExportFormat exportFormat) {
		this.exportFormat = exportFormat;
	}

	public IgnoredManagersFormat getIgnoredManagersFormat() {
		return ignoredManagersFormat;
	}

	public void setIgnoredManagersFormat(IgnoredManagersFormat ignoredManagersFormat) {
		this.ignoredManagersFormat = ignoredManagersFormat;
	}
	
}
