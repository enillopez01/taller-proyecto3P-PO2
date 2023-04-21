package com.tallerautomotizoptimus.application.data.entity;

import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class MarcaRepuestoReport implements JRDataSource{

	private List<MarcaRepuesto> marcarepuesto;
	private int counter = -1;
	private int maxCounter = 0;
	
	
	public List<MarcaRepuesto> getMarcarepuesto() {
		return marcarepuesto;
	}
	public void setMarcarepuesto(List<MarcaRepuesto> marcarepuesto) {
		this.marcarepuesto = marcarepuesto;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}
	public int getMaxCounter() {
		return maxCounter;
	}
	public void setMaxCounter(int maxCounter) {
		this.maxCounter = maxCounter;
	}
	
	
	@Override
	public boolean next() throws JRException {
		if(counter < maxCounter) {
			counter++;
			return true;
		}
		return false;
	}
	
	@Override
	public Object getFieldValue(JRField jrField) throws JRException {
		if("Marca".equals(jrField.getName())) {
			return marcarepuesto.get(counter).getNombremarca();
		}else if("Clasificacion".equals(jrField.getName())) {
			return marcarepuesto.get(counter).getClasifi();
		
		}
		
		return null;
	}
	
	
	
	
}
