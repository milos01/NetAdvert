package com.mmmp.NetAdvert.DAO;

import java.util.List;

import com.mmmp.NetAdvert.model.Report;

public interface ReportDAO {

	public Report addNewReport(Report report);
	
	public List<Report> reportList();
	
	public Report updateReport(Report report);
	
	public Report findReport(int id);
}
