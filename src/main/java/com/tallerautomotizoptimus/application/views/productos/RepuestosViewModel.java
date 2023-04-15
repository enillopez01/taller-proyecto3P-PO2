package com.tallerautomotizoptimus.application.views.productos;

import java.util.List;

import com.tallerautomotizoptimus.application.data.entity.MarcaRepuesto;
import com.tallerautomotizoptimus.application.data.entity.Repuesto;

public interface RepuestosViewModel {

	void refrescarGridRepuestos(List<Repuesto> repuesto);
	void refrescarMarca(List<MarcaRepuesto> marcas);
	void msjCrearRepuesto(Repuesto nuevo, String mensaje);
	void msjActualizarRepuesto(Repuesto nuevo, String mensaje);
	void msjEliminarRepuesto(Repuesto nuevo, String mensaje);
	
}
