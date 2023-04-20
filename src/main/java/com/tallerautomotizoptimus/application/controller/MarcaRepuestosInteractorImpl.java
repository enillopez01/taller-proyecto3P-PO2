package com.tallerautomotizoptimus.application.controller;

import java.io.IOException;

import com.tallerautomotizoptimus.application.data.entity.MarcaRepuesto;
import com.tallerautomotizoptimus.application.data.entity.MarcaRepuestoResponse;
import com.tallerautomotizoptimus.application.data.service.DataBaseRepositoryImpl;
import com.tallerautomotizoptimus.application.views.marcarepuestos.MarcaRepuestosViewModel;


public class MarcaRepuestosInteractorImpl implements MarcaRepuestosInteractor {

	private DataBaseRepositoryImpl modelo;
	private MarcaRepuestosViewModel vista;
	
	public MarcaRepuestosInteractorImpl (MarcaRepuestosViewModel vista) {
		super();
		this.vista = vista;
		this.modelo = DataBaseRepositoryImpl.getInstance("https://apex.oracle.com", 30000L);
				
	}
		
		
	@Override
	public void consultarMarcas() {
		try {
			MarcaRepuestoResponse respuesta = this.modelo.consultarmarcas();
			this.vista.refrescarGridMarca(respuesta.getItems());			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void crearMarca(MarcaRepuesto nuevo) {
		try {
			boolean creado = this.modelo.crearMarca(nuevo);
			String mensaje = creado?"La Marca del repuesto fue creado correctamente":"Hubo un inconveniente al crear la Marca del repuesto";
			this.vista.msjCreacionMarca(nuevo, mensaje);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void actualizarMarca(MarcaRepuesto actualizar) {
		try {
			  boolean actualiza = this.modelo.actualizarMarca(actualizar);
			  String mensaje = actualiza?"Campo actualizado con exito":"El Campo no pudo ser actualizado";
		      this.vista.msjActualizarMarca(actualizar, mensaje);
		 }catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void eliminarMarca(MarcaRepuesto eliminar) {
		 try {
		 	 boolean eliminado = this.modelo.eliminarMarca(eliminar.getIdmarca());
		 	 String mensaje = eliminado?"La Marca se elimino con exito":"hubo un error al intertar eliminar la Marca";
		 	 this.vista.msjEliminarMarca(eliminar, mensaje);
		 }catch(IOException e) {
			e.printStackTrace();
		}
		 
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


}
