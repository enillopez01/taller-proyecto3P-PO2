package com.tallerautomotizoptimus.application.data.service;

import com.tallerautomotizoptimus.application.data.entity.Almacen;
import com.tallerautomotizoptimus.application.data.entity.AlmacenResponse;
import com.tallerautomotizoptimus.application.data.entity.MarcaRepuesto;
import com.tallerautomotizoptimus.application.data.entity.MarcaRepuestoResponse;
import com.tallerautomotizoptimus.application.data.entity.Repuesto;
import com.tallerautomotizoptimus.application.data.entity.RepuestoResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface DataBaseRepository {

	//TBL_REPUESTOS
	@Headers({ 
		"Accept: application/json", 
		"User-Agent: Retrofit-Sample-App"
	})
	@GET("/pls/apex/pav2_201610070128/tallervehiculo/repuesto")
	Call<RepuestoResponse> listarRepuestos();

	@Headers({ 
		"Accept: application/json", 
		"User-Agent: Retrofit-Sample-App"
	})
	@POST("/pls/apex/pav2_201610070128/tallervehiculo/repuesto")
	Call<ResponseBody> crearRepuesto(@Body Repuesto nuevo);

	@Headers({ 
		"Accept: application/json", 
		"User-Agent: Retrofit-Sample-App"
	})
	@PUT("/pls/apex/pav2_201610070128/tallervehiculo/repuesto")
	Call<ResponseBody> actualizarRepuesto(@Body Repuesto nuevo);	
	
	@Headers({ 
		"Accept: application/json", 
		"User-Agent: Retrofit-Sample-App"
	})
	@DELETE("/pls/apex/pav2_201610070128/tallervehiculo/repuesto")
	Call<ResponseBody> eliminarRepuesto(@Query("id") Integer id);
	

	//TBL_MARCAREPUESTOS
	
	@Headers({ 
		"Accept: application/json", 
		"User-Agent: Retrofit-Sample-App"
	})
	@GET("/pls/apex/pav2_201610070128/tallervehiculo/marcarepuesto")
	Call<MarcaRepuestoResponse> listarMarcaRepuestos();
	
	@Headers({ 
		"Accept: application/json", 
		"User-Agent: Retrofit-Sample-App"
	})
	@POST("/pls/apex/pav2_201610070128/tallervehiculo/marcarepuesto")
	Call<ResponseBody> crearMarca(@Body MarcaRepuesto nuevo);

	@Headers({ 
		"Accept: application/json", 
		"User-Agent: Retrofit-Sample-App"
	})
	@PUT("/pls/apex/pav2_201610070128/tallervehiculo/marcarepuesto")
	Call<ResponseBody> actualizarMarca(@Body MarcaRepuesto actualizar);
	
		@Headers({
		"Accept: application/json", 
		"User-Agent: Retrofit-Sample-App"
	})
	@DELETE("/pls/apex/pav2_201610070128/tallervehiculo/marcarepuesto")
	Call<ResponseBody> eliminarMarca(@Query("id") Integer id);
	
	//TBL_ALMACEN
	@Headers({ 
		"Accept: application/json", 
		"User-Agent: Retrofit-Sample-App"
	})
	@GET("/pls/apex/pav2_201610070128/tallervehiculo/almacenes")
	Call<AlmacenResponse> listarAlmacen();
		
	@Headers({ 
		"Accept: application/json", 
		"User-Agent: Retrofit-Sample-App"
	})
	@POST("/pls/apex/pav2_201610070128/tallervehiculo/almacenes")
	Call<ResponseBody> crearAlmacen(@Body Almacen nuevo);

	@Headers({ 
		"Accept: application/json", 
		"User-Agent: Retrofit-Sample-App"
	})
	@PUT("/pls/apex/pav2_201610070128/tallervehiculo/almacenes")
	Call<ResponseBody> actualizarAlmacen(@Body Almacen actualizar);
			
		
	@Headers({
		"Accept: application/json", 
		"User-Agent: Retrofit-Sample-App"
	})
	@DELETE("/pls/apex/pav2_201610070128/tallervehiculo/almacenes")
	Call<ResponseBody> eliminarAlmacen(@Query("id") Integer id);
}
