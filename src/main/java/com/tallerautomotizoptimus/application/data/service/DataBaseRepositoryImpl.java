package com.tallerautomotizoptimus.application.data.service;


import java.io.IOException;

import com.tallerautomotizoptimus.application.data.entity.*;
import com.tallerautomotizoptimus.application.data.entity.MarcaRepuestoResponse;
import com.tallerautomotizoptimus.application.data.entity.RepuestoResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class DataBaseRepositoryImpl {
	
	
	private static DataBaseRepositoryImpl INSTANCE;
	private DatabaseClient client;	

	private DataBaseRepositoryImpl(String url, Long timeout) {
		client = new DatabaseClient(url, timeout);
	}
	
	//PATRON DE INGENIERÍA DE SOFTWARE SINGLETON
	public static DataBaseRepositoryImpl getInstance(String url, Long timeout) {
		if(INSTANCE == null) {
			synchronized (DataBaseRepositoryImpl.class) {
				if(INSTANCE == null) {
					INSTANCE = new DataBaseRepositoryImpl(url, timeout);
				}
			}
		}
		return INSTANCE;
	}
		
	
	// CRUD TABLA REPUESTOS 
	
	public RepuestoResponse consultaRepuesto() throws IOException {
		Call<RepuestoResponse> call = client.getDatabaseService().listarRepuestos();
		Response<RepuestoResponse> respuesta = call.execute();//AQUI ES DONDE SE HACE EL LLAMADO AL SERVICIO DE BASE DE DATOS
		if(respuesta.isSuccessful()){
			return respuesta.body();
		}else {
			return null;
		}
	}
	
	
	public boolean crearRepuesto(Repuesto nuevo) throws IOException {
		Call<ResponseBody> call = client.getDatabaseService().crearRepuesto(nuevo);
		Response<ResponseBody> respuesta = call.execute();
		
		return respuesta.isSuccessful();
		
	}
	
	public boolean actualizarRepuesto(Repuesto actualizar) throws IOException {
		Call<ResponseBody> call = client.getDatabaseService().actualizarRepuesto(actualizar);
		Response<ResponseBody> respuesta = call.execute();
		
		return respuesta.isSuccessful();
	}
	
	
	public boolean eliminarRepuesto(Integer idRepuesto) throws IOException {
		Call<ResponseBody> call = client.getDatabaseService().eliminarRepuesto(idRepuesto);
		Response<ResponseBody> respuesta = call.execute();//AQUI ES DONDE SE HACE EL LLAMADO AL SERVICIO DE BASE DE DATOS
		
		return respuesta.isSuccessful();
	}
	
	//FINAL CRUD TABLA REPUESTOS
	
	
	// CRUD TABLA MARCAREPUESTOS
	
	public MarcaRepuestoResponse consultarmarcas() throws IOException {
		Call<MarcaRepuestoResponse> call = client.getDatabaseService().listarMarcaRepuestos();
		Response<MarcaRepuestoResponse> respuesta = call.execute();//AQUI ES DONDE SE HACE EL LLAMADO AL SERVICIO DE BASE DE DATOS
		if(respuesta.isSuccessful()){
			return respuesta.body();
		}else {
			return null;
		}
	}
	
	public boolean crearMarca(MarcaRepuesto nuevo) throws IOException {
		Call<ResponseBody> call = client.getDatabaseService().crearMarca(nuevo);
		Response<ResponseBody> respuesta = call.execute();//AQUI ES DONDE SE HACE EL LLAMADO AL SERVICIO DE BASE DE DATOS
		
		return respuesta.isSuccessful();
		}
		
	
	public boolean actualizarMarca(MarcaRepuesto nuevo) throws IOException {
		Call<ResponseBody> call = client.getDatabaseService().actualizarMarca(nuevo);
		Response<ResponseBody> respuesta = call.execute();
		
		return respuesta.isSuccessful();
	}
	
	public boolean eliminarMarca(Integer idMarcaRepuesto) throws IOException {
		Call<ResponseBody> call = client.getDatabaseService().eliminarMarca(idMarcaRepuesto);
		Response<ResponseBody> respuesta = call.execute();
		
		return respuesta.isSuccessful();
	}
	
	//FINAL CRUD TABLA MARCAREPUESTOS
	
}
