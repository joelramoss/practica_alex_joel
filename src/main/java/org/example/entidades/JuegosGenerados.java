package org.example.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "juegos_generados")
public class JuegosGenerados {

    @Id
    @Column(name = "juego_id")
    private int juegoId;

    @Column(name = "generos")
    private String generos;

    public JuegosGenerados() {}

    public JuegosGenerados(int juegoId, String generos) {
        this.juegoId = juegoId;
        this.generos = generos;
    }

    public int getJuegoId() {
        return juegoId;
    }

    public void setJuegoId(int juegoId) {
        this.juegoId = juegoId;
    }

    public String getGeneros() {
        return generos;
    }

    public void setGeneros(String generos) {
        this.generos = generos;
    }

    @Override
    public String toString() {
        return "JuegosGenerados{" +
                "juegoId=" + juegoId +
                ", generos='" + generos + '\'' +
                '}';
    }
}
