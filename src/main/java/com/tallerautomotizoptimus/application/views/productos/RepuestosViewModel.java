package com.tallerautomotizoptimus.application.views.productos;

import java.util.List;


import com.tallerautomotizoptimus.application.data.entity.Repuesto;

public interface RepuestosViewModel {

	void refrescarGridRepuestos(List<Repuesto> repuesto);
	void msjCrearRepuesto(Repuesto nuevo, String mensaje);
	void msjActualizarRepuesto(Repuesto actuliazar, String mensaje);
	void msjEliminarRepuesto(Repuesto eliminar, String mensaje);
	
}
