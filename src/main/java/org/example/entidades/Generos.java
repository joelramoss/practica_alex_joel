package org.example.entidades;

import javax.persistence.*;

@Entity
@Table(name = "generos")
public class Generos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "Genero")
    private String generos;

    public Generos() {}

    public Generos(int id, String generos) {
        this.id = id;
        this.generos = generos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGeneros() {
        return generos;
    }

    public void setGeneros(String generos) {
        this.generos = generos;
    }

    @Override
    public String toString() {
        return "Generos{" +
                "id=" + id +
                ", generos='" + generos + '\'' +
                '}';
    }
}
