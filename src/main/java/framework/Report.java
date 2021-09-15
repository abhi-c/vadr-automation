package framework;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.masterthought.cucumber.ReportBuilder;

public class Report {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		File reportOutputDirectory = new File("results/html");
		List<String> jsonReportFiles = new ArrayList<String>();
		jsonReportFiles.add("results/json/cucumber-report-selenium.json");
		
		String buildNumber = "1.0";
		String buildProjectName = "AA POC";
		boolean skippedFails = false;
		boolean undefinedFails = false;
		boolean flashCharts = true;
		boolean runWithJenkins = true;
		boolean artifactsEnabled = false;
		String artifactConfig="";
		boolean highCharts = true;
		
		ReportBuilder reportBuilder = new ReportBuilder(jsonReportFiles, reportOutputDirectory, "", buildNumber, buildProjectName, skippedFails, undefinedFails, flashCharts, runWithJenkins, artifactsEnabled, artifactConfig, highCharts);
		reportBuilder.generateReports();
	
	}

}
