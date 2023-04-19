package com.tallerautomotizoptimus.application.controller;

import com.tallerautomotizoptimus.application.data.entity.Repuesto;

public interface RepuestoInteractor {
	
	void consultarRepuestos();
	void crearRepuestos(Repuesto nuevo);
	void actualizarRepuestos(Repuesto nuevo);
	void eliminarRepuestos(Repuesto nuevo);
}
