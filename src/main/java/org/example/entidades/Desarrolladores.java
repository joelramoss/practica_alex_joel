package org.example.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "desarrolladores")
public class Desarrolladores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // La clave primaria se generará automáticamente
    private int id;

    private String nombre;

    // Constructor vacío
    public Desarrolladores() {}

    // Constructor con parámetros
    public Desarrolladores(String nombre) {
        this.nombre = nombre;
    }

    // Getter y setter para 'id'
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter y setter para 'nombre'
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Método toString
    @Override
    public String toString() {
        return "Desarrolladores{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
