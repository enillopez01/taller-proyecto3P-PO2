package com.tallerautomotizoptimus.application.data.entity;

public class Almacen {
	
	private int idalmacen;
	private String nmrepuesto;
	private String alnombre;
	private int cantstock;
	private int minstock;
	private int maxstock;
	
	
	
	
	public int getIdalmacen() {
		return idalmacen;
	}
	public void setIdalmacen(int idalmacen) {
		this.idalmacen = idalmacen;
	}
	public String getNmrepuesto() {
		return nmrepuesto;
	}
	public void setNmrepuesto(String nmrepuesto) {
		this.nmrepuesto = nmrepuesto;
	}
	public String getNombre() {
		return alnombre;
	}
	public void setNombre(String nombre) {
		this.alnombre = nombre;
	}
	public int getCantstock() {
		return cantstock;
	}
	public void setCantstock(int cantstock) {
		this.cantstock = cantstock;
	}
	public int getMinstock() {
		return minstock;
	}
	public void setMinstock(int minstock) {
		this.minstock = minstock;
	}
	public int getMaxstock() {
		return maxstock;
	}
	public void setMaxstock(int maxstock) {
		this.maxstock = maxstock;
	}
	
	
	
	

}
