package org.example.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "generos")
public class Generos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID") // Mapeo correcto para la columna "ID"
    private int id;

    @Column(name = "Genero") // Mapeo ajustado para apuntar a la columna "Genero" en la base de datos
    private String genero;  // Cambiar el nombre del atributo para que sea consistente con la columna

    public Generos() {}

    public Generos(int id, String genero) {
        this.id = id;
        this.genero = genero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenero() { // Cambiar a "getGenero" para reflejar el nuevo nombre del atributo
        return genero;
    }

    public void setGenero(String genero) { // Cambiar a "setGenero" para reflejar el nuevo nombre del atributo
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "Generos{" +
                "id=" + id +
                ", genero='" + genero + '\'' +
                '}';
    }
}
