package com.tallerautomotizoptimus.application.controller;

import java.io.IOException;

import com.tallerautomotizoptimus.application.data.entity.MarcaRepuestoResponse;
import com.tallerautomotizoptimus.application.data.entity.Repuesto;
import com.tallerautomotizoptimus.application.data.entity.RepuestoResponse;
import com.tallerautomotizoptimus.application.data.service.DataBaseRepositoryImpl;
import com.tallerautomotizoptimus.application.views.productos.RepuestosViewModel;

public class RepuestoInteractorImpl implements RepuestoInteractor {
	
	private DataBaseRepositoryImpl modelo;
	private RepuestosViewModel vista;
	
	public RepuestoInteractorImpl(RepuestosViewModel vista) {
		super();
		this.vista = vista;
		this.modelo = DataBaseRepositoryImpl.getInstance("https://apex.oracle.com", 30000L);
		
	}

	@Override
	public void consultarRepuestos() {
		try {
			RepuestoResponse respuesta = this.modelo.consultaRepuesto();
			this.vista.refrescarGridRepuestos(respuesta.getItems());			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void crearRepuestos(Repuesto nuevo) {
		
	}

	@Override
	public void actualizarRepuestos(Repuesto nuevo) {
		
		
	}

	@Override
	public void eliminarRepuestos(Repuesto eliminar) {
		try {
			boolean eliminado = this.modelo.eliminarRepuesto(eliminar.getIdrepuesto());
			String mensaje = eliminado?"El Repuesto %s fue eliminado correctamente":"Hubo un inconveniente al eliminar el Repuesto %s";
			this.vista.msjEliminarRepuesto(eliminar, mensaje);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}

	
	
	//TBL MARAC REPUESTOS PARA LISTA COMBOBOX
	@Override
	public void consultarMarcas() {
		try {
			MarcaRepuestoResponse respuesta = this.modelo.consultarmarcas();
			this.vista.refrescarMarca(respuesta.getItems());			
		}catch(IOException e) {
			e.printStackTrace();
		}
		 
		
	}


	
	
	

	

}
