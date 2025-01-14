package org.example.entidades;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "juego")
public class Juego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "Title")
    private String title;

    @Column(name = "Release_Date")
    private Date releaseDate;

    @Column(name = "Summary")
    private String summary;

    @Column(name = "Plays")
    private int plays;

    @Column(name = "Playing")
    private int playing;

    @Column(name = "Backlogs")
    private int backlogs;

    @Column(name = "Wishlist")
    private int wishlist;

    @Column(name = "timesListed")
    private int timesListed;

    public Juego() {}

    public Juego(int id, String title, Date releaseDate, String summary, int plays, int playing, int backlogs, int wishlist, int timesListed) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.summary = summary;
        this.plays = plays;
        this.playing = playing;
        this.backlogs = backlogs;
        this.wishlist = wishlist;
        this.timesListed = timesListed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getPlays() {
        return plays;
    }

    public void setPlays(int plays) {
        this.plays = plays;
    }

    public int getPlaying() {
        return playing;
    }

    public void setPlaying(int playing) {
        this.playing = playing;
    }

    public int getBacklogs() {
        return backlogs;
    }

    public void setBacklogs(int backlogs) {
        this.backlogs = backlogs;
    }

    public int getWishlist() {
        return wishlist;
    }

    public void setWishlist(int wishlist) {
        this.wishlist = wishlist;
    }

    public int getTimesListed() {
        return timesListed;
    }

    public void setTimesListed(int timesListed) {
        this.timesListed = timesListed;
    }

    @Override
    public String toString() {
        return "Juego{" +
                "id=" + id +
                ", Título='" + title + '\'' +
                ", Fecha de lanzamiento=" + releaseDate +
                ", Resumen='" + summary + '\'' +
                ", Juegos jugados=" + plays +
                ", Juegos en juego=" + playing +
                ", Juegos pendientes=" + backlogs +
                ", Lista de deseos=" + wishlist +
                ", Veces listado=" + timesListed +
                '}';
    }
}
