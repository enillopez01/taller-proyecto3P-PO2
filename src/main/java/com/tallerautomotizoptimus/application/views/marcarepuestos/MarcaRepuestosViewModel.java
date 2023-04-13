package com.tallerautomotizoptimus.application.views.marcarepuestos;

import java.util.List;

import com.tallerautomotizoptimus.application.data.entity.MarcaRepuesto;

public interface MarcaRepuestosViewModel {

	void refrescarGridMarca(List<MarcaRepuesto> marca);
	void msjCreacionMarca(MarcaRepuesto nuevo, String mensaje);
	void msjActualizarMarca(MarcaRepuesto nuevo, String mensaje);
	void msjEliminarMarca(MarcaRepuesto nuevo, String mensaje);
}
