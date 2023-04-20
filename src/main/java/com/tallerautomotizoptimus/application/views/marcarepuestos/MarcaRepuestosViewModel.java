package com.tallerautomotizoptimus.application.views.marcarepuestos;

import java.util.List;

import com.tallerautomotizoptimus.application.data.entity.MarcaRepuesto;

public interface MarcaRepuestosViewModel {

	void refrescarGridMarca(List<MarcaRepuesto> marca);
	void msjCreacionMarca(MarcaRepuesto nuevo, String mensaje);
	void msjActualizarMarca(MarcaRepuesto actualizar, String mensaje);
	void msjEliminarMarca(MarcaRepuesto eliminar, String mensaje);
}
