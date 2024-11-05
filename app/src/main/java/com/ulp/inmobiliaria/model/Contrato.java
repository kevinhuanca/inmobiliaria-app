package com.ulp.inmobiliaria.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Contrato implements Serializable {

    private int id;
    private int monto;
    private String fechaInicio;
    private String fechaFin;
    private int inquilinoId;
    private int inmuebleId;
    private Inquilino inquilino;
    private Inmueble inmueble;

    private static final SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private static final SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

    public Contrato() {
    }

    public Contrato(int id, int monto, String fechaInicio, String fechaFin, int inquilinoId, int inmuebleId, Inquilino inquilino, Inmueble inmueble) {
        this.id = id;
        this.monto = monto;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.inquilinoId = inquilinoId;
        this.inmuebleId = inmuebleId;
        this.inquilino = inquilino;
        this.inmueble = inmueble;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaInicio() {
        return formatFecha(fechaInicio);
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return formatFecha(fechaFin);
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getInquilinoId() {
        return inquilinoId;
    }

    public void setInquilinoId(int inquilinoId) {
        this.inquilinoId = inquilinoId;
    }

    public int getInmuebleId() {
        return inmuebleId;
    }

    public void setInmuebleId(int inmuebleId) {
        this.inmuebleId = inmuebleId;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    private String formatFecha(String fecha) {
        if (fecha != null) {
            try {
                Date date = inputFormat.parse(fecha);
                return outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
