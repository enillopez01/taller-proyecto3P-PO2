package com.tallerautomotizoptimus.application.controller;

import java.io.IOException;

import com.tallerautomotizoptimus.application.data.entity.Almacen;
import com.tallerautomotizoptimus.application.data.entity.AlmacenResponse;
import com.tallerautomotizoptimus.application.data.service.DataBaseRepositoryImpl;
import com.tallerautomotizoptimus.application.views.reportes.AlmacenViewModel;

public class AlmacenInteractorImpl implements AlmacenInteractor{
	
	private DataBaseRepositoryImpl modelo;
	private AlmacenViewModel vista;
	
	public AlmacenInteractorImpl (AlmacenViewModel vista){
		super();
		this.vista = vista;
		this.modelo = DataBaseRepositoryImpl.getInstance("https://apex.oracle.com", 30000L);
				
	}
	
	
	@Override
	public void consultarAlmacen() {
		try {
			AlmacenResponse respuesta = this.modelo.consultarAlmacen();
			this.vista.refrescarAlmacen(respuesta.getItems());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void crearAlmacen(Almacen nuevo) {
		try {
			boolean creado = this.modelo.crearAlmacen(nuevo);
			String mensaje = creado?"El Almacen fue creado correctamente":"Hubo un inconveniente al crear el Almacen";
			this.vista.msjCreacionAlmacen(nuevo, mensaje);
		}catch(IOException e) {
					e.printStackTrace();
				}		
	}

	@Override
	public void actualizarAlmacen(Almacen actualizar) {
		try {
			boolean actualizado = this.modelo.actualizarAlmacen(actualizar);
			String mensaje = actualizado?"El Almacen fue actualizado correctamente":"Hubo un inconveniente al actualizar el Almacen";
			this.vista.msjActualizarAlmacen(actualizar, mensaje);
			
		}catch(IOException e) {
			e.printStackTrace();
		}		
		
	}

	@Override
	public void eliminarAlmacen(Almacen eliminar) {
		try {
			boolean eliminado = this.modelo.eliminarAlmacen(eliminar.getIdalmacen());
			String mensaje = eliminado?"El almacen se elimino con exito":"hubo un error al intertar eliminar el almacen";
			this.vista.msjEliminarAlmacen(eliminar, mensaje);
		}catch(IOException e) {
			e.printStackTrace();
		}		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
