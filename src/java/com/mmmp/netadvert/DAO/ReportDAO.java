package com.mmmp.netadvert.DAO;

import java.util.List;

import com.mmmp.netadvert.model.Report;

public interface ReportDAO {

	public Report addNewReport(Report report);
	
	public List<Report> reportList();
	
	public Report updateReport(Report report);
	
	public Report findReport(int id);
	
	public List<Report> findReportsByAdvert(int advert_id);
}
