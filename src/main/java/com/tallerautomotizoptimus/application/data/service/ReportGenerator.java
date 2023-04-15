package com.tallerautomotizoptimus.application.data.service;

import java.io.File;
import java.util.Map;

import org.springframework.util.ResourceUtils;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class ReportGenerator {
	

	//UBICACIÓN DEL ARCHIVO DEL REPORTE
	private String reportPath;
	
	public boolean generarReportePDF(String reportName, JRDataSource datasource, Map<String, Object> parameters) {
		boolean reporteGenerado = false;
		try {
			JasperReport reporte = (JasperReport)JRLoader.loadObjectFromFile(fetchReportPath(reportName+".jasper"));
			JasperPrint impresora = JasperFillManager.fillReport(reporte, parameters, datasource);
			String rutaPDF = generateReportSavePath() + reportName + ".pdf";
			this.reportPath = rutaPDF;
			JasperExportManager.exportReportToPdfFile(impresora, rutaPDF);
			reporteGenerado = true;
		}catch(Exception e) {
			e.printStackTrace();
			reporteGenerado = false;
		}
		return reporteGenerado;
	}

	private String generateReportSavePath() {
		String path = null;
		try {
			path = File.createTempFile("temp", ".pdf").getAbsolutePath();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return path;
	}

	private String fetchReportPath(String fileName) {
		String path = null;
		try {
			path = ResourceUtils.getFile("classpath:"+fileName).getAbsolutePath();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return path;
	}

	public String getReportPath() {
		return reportPath;
	}
}
