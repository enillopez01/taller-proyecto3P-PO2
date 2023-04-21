package com.tallerautomotizoptimus.application.data.entity;

import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class AlmacenReport implements JRDataSource{
	
	private List<Almacen> almacenes;
	private int counter = -1;
	private int maxCounter = 0;
	
	 
	public List<Almacen> getAlmacen() {
		return almacenes;
	}

	public void setAlmacen(List<Almacen> almacen) {
		almacenes = almacen;
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
		if("Nombre".equals(jrField.getName())) {
			return almacenes.get(counter).getNombre();
		}else if("Repuesto".equals(jrField.getName())) {
			return almacenes.get(counter).getNmrepuesto();
		}else if("Cantidad".equals(jrField.getName())) {
			return almacenes.get(counter).getMinstock();
		}else if("MinStock".equals(jrField.getName())) {
			return almacenes.get(counter).getMaxstock();
		}else if("MaxStock".equals(jrField.getName())) {
			
		}
		return "";
	}


}
