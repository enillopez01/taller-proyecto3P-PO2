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
		try {
			boolean creado = this.modelo.crearRepuesto(nuevo);
			String mensaje = creado?"El repuesto fue creado correctamente":"Hubo un inconveniente al crear el repuesto";
			this.vista.msjCrearRepuesto(nuevo, mensaje);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void actualizarRepuestos(Repuesto actualizar) {
		try {
		boolean actualiza = this.modelo.actualizarRepuesto(actualizar);
		String mensaje = actualiza?"Los datos se actualizaron con exito":"Hubo un inconveniente en la actualizacion";
		this.vista.msjActualizarRepuesto(actualizar, mensaje);
		}catch(IOException e) {
			e.printStackTrace();
		}
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

	


	
	
	

	

}
