package com.example.practicacontenedoresui;

public class Materia {
    private int id;
    private String nombre;
    private int nivel;

    public Materia(int id, String nombre, int nivel) {
        this.id = id;
        this.nombre = nombre;
        this.nivel = nivel;
    }

    public int getId()      { return id; }
    public String getNombre() { return nombre; }
    public int getNivel()   { return nivel; }

    @Override
    public String toString() { return nombre; }
}
