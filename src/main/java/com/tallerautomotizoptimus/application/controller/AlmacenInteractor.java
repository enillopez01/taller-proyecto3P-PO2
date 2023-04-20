package com.tallerautomotizoptimus.application.controller;

import com.tallerautomotizoptimus.application.data.entity.Almacen;

public interface AlmacenInteractor {

	void consultarAlmacen();
	void crearAlmacen(Almacen nuevo);
	void actualizarAlmacen(Almacen actualizar);
	void eliminarAlmacen(Almacen eliminar);
	
}
