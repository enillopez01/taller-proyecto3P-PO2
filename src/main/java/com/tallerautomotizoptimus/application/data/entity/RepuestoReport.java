package com.tallerautomotizoptimus.application.data.entity;

import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class RepuestoReport implements JRDataSource{
	
	private List<Repuesto> repuestos;
	private int counter = -1;
	private int maxCounter = 0;
	
	
	public List<Repuesto> getRepuestos() {
		return repuestos;
	}
	
	public void setRepuestos(List<Repuesto> repuestos) {
		this.repuestos = repuestos;
		this.maxCounter = this.repuestos.size() -1;
	}
	
	public int getCounter() {
		return counter;
	}
	
	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	public int getMaxcounter() {
		return maxCounter;
	}
	
	public void setMaxcounter(int maxcounter) {
		this.maxCounter = maxcounter;
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
		if("NombreRepuesto".equals(jrField.getName())) {
			return repuestos.get(counter).getNombre();
		}else if("NumeroSerie".equals(jrField.getName())){
			return repuestos.get(counter).getNumeroserie();
		}else if("Cantidad".equals(jrField.getName())){
			return repuestos.get(counter).getCantidad();
		}
		return "";
	}
	
	
	
	
	
}
