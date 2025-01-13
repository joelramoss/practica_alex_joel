package org.example.entidades;


import javax.persistence.*;


@Entity
@Table(name = "detalles_juego")
public class DetallesJuego {

    @Id
    @Column(name = "juego_id")
    private int juegoId;

    @Column(name = "Reviews", length = 5000)
    private String reviews;

    public DetallesJuego() {}

    public DetallesJuego(int juegoId, String reviews) {
        this.juegoId = juegoId;
        this.reviews = reviews;
    }

    public int getJuegoId() {
        return juegoId;
    }

    public void setJuegoId(int juegoId) {
        this.juegoId = juegoId;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "DetallesJuego{" +
                "juegoId=" + juegoId +
                ", reviews='" + reviews + '\'' +
                '}';
    }
}
