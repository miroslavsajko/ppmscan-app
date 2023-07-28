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
		sk.ppmscan.app.importexport.scanrun.hibernate.ScanRun mapped = new sk.ppmscan.app.importexport.scanrun.hibernate.ScanRun();
		mapped.setScanTime(scanRun.getScanTime());
		mapped.setManagers(scanRun.getManagers().stream().map(this::map).collect(Collectors.toList()));
		return mapped;
	}
	
	private sk.ppmscan.app.importexport.scanrun.hibernate.Manager map(Manager manager) {
		sk.ppmscan.app.importexport.scanrun.hibernate.Manager mapped = new sk.ppmscan.app.importexport.scanrun.hibernate.Manager();
		mapped.setManagerId(manager.getManagerId());
		mapped.setNickname(manager.getNickname());
		mapped.setRecentLogins(manager.getRecentLogins());
		mapped.setUrl(manager.getUrl());
		mapped.setTeams(manager.getTeams().stream().map(this::map).collect(Collectors.toList()));
		return mapped;
	}
	
	private sk.ppmscan.app.importexport.scanrun.hibernate.Team map(Team team) {
		sk.ppmscan.app.importexport.scanrun.hibernate.Team mapped = new sk.ppmscan.app.importexport.scanrun.hibernate.Team();
		mapped.setName(team.getName());
		mapped.setLeague(team.getLeague());
		mapped.setLeagueCountry(team.getLeagueCountry());
		mapped.setSport(team.getSport());
		mapped.setTeamCountry(team.getTeamCountry());
		mapped.setTeamStrength(team.getTeamStrength());
		mapped.setUrl(team.getUrl());
		mapped.setTeamId(team.getTeamId());
		return mapped;
	}

}
