package sk.ppmscan.app.importexport.scanrun;

import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sk.ppmscan.app.hibernate.HibernateUtil;
import sk.ppmscan.application.beans.Manager;
import sk.ppmscan.application.beans.ScanRun;
import sk.ppmscan.application.beans.Team;
import sk.ppmscan.application.importexport.ScanRunExporter;

public class ScanRunHibernateExporter implements ScanRunExporter {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScanRunHibernateExporter.class);
	
	@Override
	public void exportData(ScanRun scanRun) throws Exception {
		long startTime = System.currentTimeMillis();
		
		sk.ppmscan.app.importexport.scanrun.hibernate.ScanRun hibernateScanRun = map(scanRun);
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(hibernateScanRun);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			LOGGER.error("Error during database communication!");
			throw e;
		} finally {
			session.close();
		}

		LOGGER.info("The operation took {} ms", System.currentTimeMillis() - startTime);

	}

	private sk.ppmscan.app.importexport.scanrun.hibernate.ScanRun map(ScanRun scanRun) {
		sk.ppmscan.app.importexport.scanrun.hibernate.ScanRun mappedScanRun = new sk.ppmscan.app.importexport.scanrun.hibernate.ScanRun();
		mappedScanRun.setScanTime(scanRun.getScanTime());
		mappedScanRun.setManagers(scanRun.getManagers().stream().map(manager -> map(manager, mappedScanRun)).collect(Collectors.toList()));
		return mappedScanRun;
	}
	
	private sk.ppmscan.app.importexport.scanrun.hibernate.Manager map(Manager manager, sk.ppmscan.app.importexport.scanrun.hibernate.ScanRun scanRun) {
		sk.ppmscan.app.importexport.scanrun.hibernate.Manager mappedManager = new sk.ppmscan.app.importexport.scanrun.hibernate.Manager();
		mappedManager.setManagerId(manager.getManagerId());
		mappedManager.setNickname(manager.getNickname());
		mappedManager.setRecentLogins(manager.getRecentLogins());
		mappedManager.setUrl(manager.getUrl());
		mappedManager.setTeams(manager.getTeams().stream().map(team -> map(team, mappedManager, scanRun)).collect(Collectors.toList()));
		return mappedManager;
	}
	
	private sk.ppmscan.app.importexport.scanrun.hibernate.Team map(Team team, sk.ppmscan.app.importexport.scanrun.hibernate.Manager manager, sk.ppmscan.app.importexport.scanrun.hibernate.ScanRun scanRun) {
		sk.ppmscan.app.importexport.scanrun.hibernate.Team mappedTeam = new sk.ppmscan.app.importexport.scanrun.hibernate.Team();
		mappedTeam.setName(team.getName());
		mappedTeam.setLeague(team.getLeague());
		mappedTeam.setLeagueCountry(team.getLeagueCountry());
		mappedTeam.setSport(team.getSport());
		mappedTeam.setTeamCountry(team.getTeamCountry());
		mappedTeam.setTeamStrength(team.getTeamStrength());
		mappedTeam.setUrl(team.getUrl());
		mappedTeam.setTeamId(team.getTeamId());
		mappedTeam.setScanRun(scanRun);
		mappedTeam.setManager(manager);
		return mappedTeam;
	}

}
