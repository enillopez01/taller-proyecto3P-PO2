package com.tallerautomotizoptimus.application.views.reportes;

import java.util.List;

import com.tallerautomotizoptimus.application.data.entity.Almacen;

public interface AlmacenViewModel {

	void refrescarAlmacen(List<Almacen> almacen);
	void msjCreacionAlmacen(Almacen nuevo, String mensaje);
	void msjActualizarAlmacen(Almacen actualizar, String mensaje);
	void msjEliminarAlmacen(Almacen eliminar, String mensaje);
}
