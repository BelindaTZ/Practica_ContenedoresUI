package com.example.practicacontenedoresui;

public class Alumno {
    private String foto;
    private String nombres;
    private String correo;
    private String paralelo;
    private String telefono;

    public Alumno(String foto, String nombres, String correo, String paralelo, String telefono) {
        this.foto = foto;
        this.nombres = nombres;
        this.correo = correo;
        this.paralelo = paralelo;
        this.telefono = telefono;
    }

    public String getFoto()     { return foto; }
    public String getNombres()  { return nombres; }
    public String getCorreo()   { return correo; }
    public String getParalelo() { return paralelo; }
    public String getTelefono() { return telefono; }
}
