package com.tallerautomotizoptimus.application.controller;

import com.tallerautomotizoptimus.application.data.entity.MarcaRepuesto;

public interface MarcaRepuestosInteractor {
	
	void consultarMarcas();
	void crearMarca(MarcaRepuesto nuevo);
	void actualizarMarca(MarcaRepuesto nuevo);
	void eliminarMarca(MarcaRepuesto nuevo);
	

}
