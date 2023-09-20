package com.example.universidad.Model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Carrera implements Serializable {
    private Long id;
    private String nombre;

    public Carrera() {

    }

    public Carrera(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }


    @Override
    public String toString() {
        return "id: " + getId()+ ", Nombre: " + getNombre();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
