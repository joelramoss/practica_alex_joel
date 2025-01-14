package org.example.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "juego_equipo")
public class JuegoEquipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "juego_id")
    private int juegoId;

    @Column(name = "desarrollador_id")
    private int desarrolladorId;

    public JuegoEquipo() {}

    public JuegoEquipo(int id, int juegoId, int desarrolladorId) {
        this.id = id;
        this.juegoId = juegoId;
        this.desarrolladorId = desarrolladorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJuegoId() {
        return juegoId;
    }

    public void setJuegoId(int juegoId) {
        this.juegoId = juegoId;
    }

    public int getDesarrolladorId() {
        return desarrolladorId;
    }

    public void setDesarrolladorId(int desarrolladorId) {
        this.desarrolladorId = desarrolladorId;
    }

    @Override
    public String toString() {
        return "Juego_equipo{" +
                "id=" + id +
                ", juegoId=" + juegoId +
                ", desarrolladorId=" + desarrolladorId +
                '}';
    }
}
