package org.example.entidades;

import javax.persistence.*;

@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @Column(name = "juego_id")
    private int juegoId;

    @Column(name = "rating")
    private double rating;

    @Column(name = "number_of_reviews")
    private int numberOfReviews;

    public Rating() {}

    public Rating(int juegoId, double rating, int numberOfReviews) {
        this.juegoId = juegoId;
        this.rating = rating;
        this.numberOfReviews = numberOfReviews;
    }

    public int getJuegoId() {
        return juegoId;
    }

    public void setJuegoId(int juegoId) {
        this.juegoId = juegoId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews(int numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "juegoId=" + juegoId +
                ", rating=" + rating +
                ", numberOfReviews=" + numberOfReviews +
                '}';
    }
}
