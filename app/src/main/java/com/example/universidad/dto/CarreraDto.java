package com.example.universidad.dto;

public class CarreraDto {
    private String nombre;

    public CarreraDto() {
    }

    public CarreraDto(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
