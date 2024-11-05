package com.ulp.inmobiliaria.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pago {

    private int id;
    private int numero;
    private String fecha;
    private int importe;
    private int contratoId;
    private Contrato contrato;

    private static final SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private static final SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

    public Pago() {
    }

    public Pago(int id, int numero, String fecha, int importe, int contratoId, Contrato contrato) {
        this.id = id;
        this.numero = numero;
        this.fecha = fecha;
        this.importe = importe;
        this.contratoId = contratoId;
        this.contrato = contrato;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getFecha() {
        return formatFecha(fecha);
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getImporte() {
        return importe;
    }

    public void setImporte(int importe) {
        this.importe = importe;
    }

    public int getContratoId() {
        return contratoId;
    }

    public void setContratoId(int contratoId) {
        this.contratoId = contratoId;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
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
