package sk.ppmscan.app.config;

import sk.ppmscan.app.importexport.scanrun.ScanRunHibernateExporter;
import sk.ppmscan.app.importexport.scanrun.ScanRunExcelExporter;
import sk.ppmscan.app.importexport.scanrun.ScanRunHtmlExporter;
import sk.ppmscan.app.importexport.scanrun.ScanRunJsonExporter;
import sk.ppmscan.application.importexport.ScanRunExporter;

public enum ExportFormat {
	
	JSON(new ScanRunJsonExporter()), 
	EXCEL(new ScanRunExcelExporter()),
	HTML(new ScanRunHtmlExporter()),
	HIBERNATE(new ScanRunHibernateExporter());
	
	private ScanRunExporter filteredTeamsExporter;
	
	private ExportFormat(ScanRunExporter filteredTeamsExporter) {
		this.filteredTeamsExporter = filteredTeamsExporter;
	}
	
	public ScanRunExporter getFilteredTeamsExporter() {
		return this.filteredTeamsExporter;
	}

}
