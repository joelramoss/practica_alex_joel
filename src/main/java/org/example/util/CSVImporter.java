package org.example.util;
import org.apache.commons.csv.CSVFormat;

import org.example.entidades.*;
import org.example.daos.DaoGeneros;
import org.example.daos.DaoDesarolladores;
import org.example.daos.DaoJuego;
import org.example.daos.DaoJuegosGenerados;
import org.example.daos.DaoJuegoEquipo;
import org.example.daos.DaoRating;
import org.example.daos.DaoDetallesJuego;

import java.io.FileReader;
import java.io.IOException;


import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;


public class CSVImporter {


    public static void importarCSV(String filePath) throws IOException, SQLException {
        // Creación de los DAOs para interactuar con la base de datos
        DaoGeneros generosDAO = new DaoGeneros();
        DaoJuego daoJuego = new DaoJuego();
        DaoDesarolladores daoDesarrolladores = new DaoDesarrolladores();

        DaoJuegosGenerados juegosGeneradosDAO = new DaoJuegosGenerados();
        DaoJuegoEquipo daoJuegoEquipo = new DaoJuegoEquipo();
        DaoDetallesJuego detallesJuegoDAO = new daoDetallesJuego();

        // Lee el archivo CSV
        FileReader reader = new FileReader(filePath);

        // Configura el formato del CSV
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withFirstRecordAsHeader() // La primera fila es el encabezado
                .parse(reader);

        // Procesa cada registro (fila) en el CSV
        for (CSVRecord record : records) {
            // Extrae los datos de cada campo del CSV
            String id = record.get("ID");
            String title = record.get("Title");
            String releaseDate = record.get("Release Date");
            String team = record.get("Team");
            String rating = record.get("Rating");
            String timesListed = record.get("Times Listed");
            String numOfReviews = record.get("Number of Reviews");
            String genres = record.get("Genres");
            String summary = record.get("Summary");
            String reviews = record.get("Reviews");
            String plays = record.get("Plays");
            String playing = record.get("Playing");
            String backlogs = record.get("Backlogs");
            String wishlist = record.get("Wishlist");

            // Procesamos las columnas 'Team' y 'Genres' que contienen listas
            List<String> teamList = parseListFromString(team);
            List<String> genresList = parseListFromString(genres);

            // Crear el objeto Juego y establecer sus atributos
            Juego juego = new Juego();
            juego.setId(Integer.parseInt(id));
            juego.setTitle(title);
            try {
                juego.setReleaseDate(convertToDate(releaseDate)); // Conversión de fecha
            } catch (IllegalArgumentException e) {
                System.out.println("Error al convertir la fecha para el juego con ID " + id + ": " + e.getMessage());
                continue;
            }
            juego.setSummary(summary);
            juego.setPlays(parseIntegerWithK(plays));
            juego.setPlaying(parseIntegerWithK(playing));
            juego.setBacklogs(parseIntegerWithK(backlogs));
            juego.setTimesListed(parseIntegerWithK(timesListed));
            juego.setWishlist(parseIntegerWithK(wishlist));

            // Insertar el juego en la base de datos
            daoJuego.crearJuego(juego);

            // Procesamos los géneros del juego
            Map<String, Boolean> generosProcesadosMap = new HashMap<>();
            for (String genre : genresList) {
                if (genre == null || genre.trim().isEmpty()) {
                    genre = null;
                } else {
                    genre = genre.trim().toLowerCase();
                }

                if (genre != null && !generosProcesadosMap.containsKey(genre)) {
                    generosProcesadosMap.put(genre, true);
                    int generoExistente = generosDAO.obtenerGeneroPorNombre(genre);  // Comprobar si el género ya existe

                    if (generoExistente == -1) {
                        Generos nuevoGenero = new Generos();
                        nuevoGenero.setGeneros(genre);
                        generosDAO.crearGenero(nuevoGenero);
                        generoExistente = nuevoGenero.getId();  // Obtener el ID del género recién insertado
                    }

                    // Relacionar el juego con el género
                    juegosGeneradosDAO.crearRelacionJuegoGenero(juego.getId(), generoExistente);
                }
            }

            // Procesamos los equipos (desarrolladores)
            for (String developer : teamList) {
                Desarrolladores desarrollador = new Desarrolladores();
                desarrollador.setNombre(developer);
                daoDesarrolladores.crearDesarrollador(desarrollador);
                daoJuegoEquipo.crearRelacionJuegoDesarrollador(juego.getId(), desarrollador.getId());
            }

            // Procesamos la calificación
            DaoRating ratingDAO = new DaoRating();
            Rating ratingObj = new Rating();
            ratingObj.setJuegoId(juego.getId());
            try {
                if (rating == null || rating.trim().isEmpty()) {
                    ratingObj.setRating(0.0);
                } else {
                    ratingObj.setRating(Double.parseDouble(rating.trim()));
                }
                ratingObj.setNumberOfReviews(parseIntegerWithK(numOfReviews.trim()));
                ratingDAO.crearRating(ratingObj);
            } catch (NumberFormatException e) {
                System.out.println("Error en los datos de rating o reviews para el juego con ID " + id + ": " + e.getMessage());
            }

            // Procesamos los detalles del juego (reseñas)
            if (reviews != null && !reviews.trim().isEmpty()) {
                DetallesJuego detallesJuego = new DetallesJuego(juego.getId(), reviews);
                detallesJuegoDAO.crearDetallesJuego(detallesJuego);
            }
        }
    }

    // Métodos auxiliares para convertir fechas, listas y manejar valores numéricos con 'K'

    /**
     * Convierte una cadena de texto a un objeto java.sql.Date considerando diferentes formatos de fecha.
     *
     * @param dateStr La cadena de texto con la fecha a convertir.
     * @return La fecha convertida a java.sql.Date.
     * @throws IllegalArgumentException Si el formato de la fecha no es válido.
     */
    public static java.sql.Date convertToDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            throw new IllegalArgumentException("El string de fecha no puede ser nulo o vacío.");
        }

        dateStr = dateStr.trim().replace(".", ""); // Eliminar puntos
        dateStr = capitalizeMonth(dateStr);

        DateTimeFormatter[] formatters = new DateTimeFormatter[]{
                DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH),
                DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.ENGLISH),
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                DateTimeFormatter.ofPattern("MM/dd/yyyy")
        };

        for (DateTimeFormatter formatter : formatters) {
            try {
                LocalDate parsedDate = LocalDate.parse(dateStr, formatter);
                return java.sql.Date.valueOf(parsedDate);
            } catch (DateTimeParseException ignored) {
            }
        }

        throw new IllegalArgumentException("Formato de fecha no válido: " + dateStr);
    }

    /**
     * Capitaliza el nombre del mes en una fecha (por ejemplo, "jan" se convierte en "Jan").
     *
     * @param dateStr La cadena que contiene la fecha.
     * @return La fecha con el mes capitalizado.
     */
    static String capitalizeMonth(String dateStr) {
        String[] parts = dateStr.split(" ", 3);
        if (parts.length < 3) return dateStr;
        parts[0] = parts[0].substring(0, 1).toUpperCase(Locale.ENGLISH) + parts[0].substring(1).toLowerCase(Locale.ENGLISH);
        return String.join(" ", parts);
    }

    /**
     * Convierte una cadena de texto que representa una lista separada por comas en una lista de strings.
     *
     * @param input La cadena de texto que contiene la lista.
     * @return Una lista de strings.
     */
    public static List<String> parseListFromString(String input) {
        input = input.replaceAll("[\\[\\]'\"]+", "").trim();
        String[] items = input.split(",");
        List<String> list = new ArrayList<>();
        for (String item : items) {
            list.add(item.trim());
        }
        return list;
    }

    /**
     * Convierte un valor de texto con formato 'K' (como "1.5K") a un valor entero.
     *
     * @param value La cadena con el valor a convertir.
     * @return El valor convertido a un entero.
     */
    public static int parseIntegerWithK(String value) {
        if (value.endsWith("K")) {
            return (int) (Double.parseDouble(value.replace("K", "").trim()) * 1000);
        } else {
            return Integer.parseInt(value.trim());
        }
    }
}
