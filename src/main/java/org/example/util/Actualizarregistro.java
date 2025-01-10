package org.example.util;

import org.example.daos.*;

import org.example.entidades.Desarrolladores;
import org.example.entidades.Generos;
import org.example.entidades.Juego;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;

import static org.example.util.CSVImporter.capitalizeMonth;
import static org.example.Main.leerOpcion;


/**
 * Clase que proporciona funcionalidades para actualizar registros en la base de datos.
 * Incluye opciones para actualizar registros en las tablas de juegos, desarrolladores y géneros.
 *
 * <p>Esta clase presenta un menú interactivo donde el usuario puede elegir qué registro desea actualizar.
 * Para cada tipo de registro, se permite modificar atributos específicos, como el título, fecha de lanzamiento, y otros
 * parámetros de los juegos, así como los desarrolladores y géneros asociados.</p>
 */
public class Actualizarregistro {

    /**
     * Muestra el menú de opciones para actualizar registros en la base de datos.
     * Permite seleccionar la tabla a actualizar (Juego, Desarrollador o Género), o volver al menú principal.
     *
     * @throws InterruptedException Si la ejecución se ve interrumpida.
     */
    public static void menu() throws InterruptedException {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== Menú Actualizar Registros ===");
            System.out.println("1. Actualizar registro de la tabla juego");
            System.out.println("2. Actualizar registro de la tabla desarrolladores");
            System.out.println("3. Actualizar registro de la tabla géneros");
            System.out.println("4. Volver al menú principal");
            System.out.println("5. Salir del programa");
            int opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    actualizarJuego();
                    break;
                case 2:
                    actualizarDesarrollador();
                    break;
                case 3:
                    actualizarGenero();
                    break;
                case 4:
                    salir = true; // Volver al menú principal
                    break;
                case 5:
                    System.out.println("Saliendo del programa...");
                    Thread.sleep(1000); // Simula una pausa al cerrar
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    /**
     * Permite actualizar un juego en la base de datos.
     * Solicita la entrada del usuario para modificar atributos como el título, fecha de lanzamiento, resumen, y otros.
     * Además, permite actualizar los géneros y desarrolladores asociados al juego.
     */
    private static void actualizarJuego() {
        try {
            Scanner scanner = new Scanner(System.in);
            DaoJuego dao = new DaoJuego();
            DaoJuegoEquipo daoJuegoEquipo = new DaoJuegoEquipo();
            DaoJuegosGenerados daoJuegosGenerados = new DaoJuegosGenerados();

            System.out.print("Ingrese el ID del juego a actualizar: ");
            int id = scanner.nextInt();

            Juego juego = dao.obtenerPorId(id);
            if (juego == null) {
                System.out.println("Juego no encontrado.");
                return;
            }

            // Mostrar y actualizar datos
            System.out.println("Datos actuales: " + juego.toString());
            juego.setTitle(obtenerInputString(scanner, "Ingrese el nuevo título: "));

            // Usar el convertidor de fecha
            System.out.println("Ingrese la nueva fecha de lanzamiento (Ejemplo: Nov 29, 2024 o November 29, 2024): ");
            String fechaInput = scanner.next();
            java.sql.Date nuevaFecha = convertToDate(fechaInput);
            juego.setReleaseDate(nuevaFecha);
            juego.setSummary(obtenerInputString(scanner, "Ingrese el nuevo resumen: "));
            juego.setPlays(obtenerInputInt(scanner, "Ingrese el nuevo número de juegos jugados: "));
            juego.setPlaying(obtenerInputInt(scanner, "Ingrese el nuevo número de jugadores actuales: "));
            juego.setBacklogs(obtenerInputInt(scanner, "Ingrese el nuevo número de juegos pendientes: "));
            juego.setWishlist(obtenerInputInt(scanner, "Ingrese el nuevo número de lista de deseos: "));
            juego.setTimesListed(obtenerInputInt(scanner, "Ingrese el nuevo número de veces listados: "));

            // Actualizar juego
            dao.actualizarJuego(juego);

            // Actualizar géneros asociados al juego
            actualizarGenerosJuego(scanner, juego.getId(), daoJuegosGenerados);

            // Actualizar desarrolladores asociados al juego
            actualizarDesarrolladoresJuego(scanner, juego.getId(), daoJuegoEquipo);

        } catch (Exception e) {
            System.out.println("Error al actualizar el juego: " + e.getMessage());
        }
    }

    /**
     * Permite actualizar los géneros asociados a un juego.
     * Solicita los nuevos géneros y los guarda en la base de datos.
     *
     * @param scanner El objeto Scanner para leer las entradas del usuario.
     * @param juegoId El ID del juego que se va a actualizar.
     * @param daoJuegosGenerados El objeto DAO para interactuar con los géneros asociados al juego.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    private static void actualizarGenerosJuego(Scanner scanner, int juegoId, DaoJuegosGenerados daoJuegosGenerados) throws SQLException {
        System.out.println("¿Desea actualizar los géneros del juego? (S/no)");
        String respuesta = scanner.nextLine().toLowerCase();

        if (respuesta.equals("sí")) {
            System.out.println("Ingrese los nuevos géneros del juego (separados por coma): ");
            String nuevosGeneros = scanner.nextLine();
            daoJuegosGenerados.crearRelacionJuegoGenero(juegoId, Integer.parseInt(nuevosGeneros));
        }
    }

    /**
     * Permite actualizar los desarrolladores asociados a un juego.
     * Solicita el ID del nuevo desarrollador y lo asocia al juego.
     *
     * @param scanner El objeto Scanner para leer las entradas del usuario.
     * @param juegoId El ID del juego que se va a actualizar.
     * @param daoJuegoEquipo El objeto DAO para interactuar con los desarrolladores asociados al juego.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    private static void actualizarDesarrolladoresJuego(Scanner scanner, int juegoId, DaoJuegoEquipo daoJuegoEquipo) throws SQLException {
        System.out.println("¿Desea actualizar los desarrolladores del juego? (sí/no)");
        String respuesta = scanner.nextLine().toLowerCase();

        if (respuesta.equals("sí")) {
            System.out.print("Ingrese el ID del nuevo desarrollador: ");
            int nuevoDesarrolladorId = scanner.nextInt();
            daoJuegoEquipo.crearRelacionJuegoDesarrollador(juegoId, nuevoDesarrolladorId);
        }
    }

    /**
     * Permite actualizar un desarrollador en la base de datos.
     * Solicita la entrada del usuario para modificar el nombre del desarrollador.
     */
    private static void actualizarDesarrollador() {
        try {
            Scanner scanner = new Scanner(System.in);
            DaoDesarolladores dao = new DaoDesarolladores();

            System.out.print("Ingrese el ID del desarrollador a actualizar: ");
            int id = scanner.nextInt();

            Desarrolladores desarrollador = dao.leerDesarrollador(id);
            if (desarrollador == null) {
                System.out.println("Desarrollador no encontrado.");
                return;
            }

            System.out.println("Datos actuales: " + desarrollador);
            desarrollador.setNombre(obtenerInputString(scanner, "Ingrese el nuevo nombre: "));

            dao.actualizarDesarrollador(desarrollador);
        } catch (Exception e) {
            System.out.println("Error al actualizar el desarrollador: " + e.getMessage());
        }
    }

    /**
     * Permite actualizar un género en la base de datos.
     * Solicita la entrada del usuario para modificar el nombre del género.
     */
    private static void actualizarGenero() {
        try {
            Scanner scanner = new Scanner(System.in);
            DaoGeneros dao = new DaoGeneros();

            System.out.print("Ingrese el ID del género a actualizar: ");
            int id = scanner.nextInt();

            Generos genero = dao.leerGenero(id);
            if (genero == null) {
                System.out.println("Género no encontrado.");
                return;
            }

            System.out.println("Datos actuales: " + genero);
            genero.setGeneros(obtenerInputString(scanner, "Ingrese el nuevo nombre del género: "));

            dao.actualizarGenero(genero);
        } catch (Exception e) {
            System.out.println("Error al actualizar el género: " + e.getMessage());
        }
    }

    /**
     * Obtiene un valor de tipo String del usuario.
     *
     * @param scanner El objeto Scanner para leer la entrada.
     * @param mensaje El mensaje que se muestra al usuario.
     * @return El valor de tipo String ingresado por el usuario.
     */
    private static String obtenerInputString(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        scanner.nextLine(); // Limpiar buffer
        return scanner.nextLine();
    }

    /**
     * Obtiene un valor de tipo int del usuario.
     *
     * @param scanner El objeto Scanner para leer la entrada.
     * @param mensaje El mensaje que se muestra al usuario.
     * @return El valor de tipo int ingresado por el usuario.
     */
    private static int obtenerInputInt(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        return scanner.nextInt();
    }

    /**
     * Convierte una cadena de texto representando una fecha en un objeto java.sql.Date.
     *
     * @param dateStr La cadena de texto representando la fecha.
     * @return Un objeto java.sql.Date que representa la fecha.
     * @throws IllegalArgumentException Si el formato de la fecha no es válido.
     */
    public static java.sql.Date convertToDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            throw new IllegalArgumentException("El string de fecha no puede ser nulo o vacío.");
        }

        dateStr = dateStr.trim().replace(".", ""); // Elimina puntos y recorta espacios
        dateStr = capitalizeMonth(dateStr);

        DateTimeFormatter[] formatters = new DateTimeFormatter[]{
                DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH),
                DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.ENGLISH),
                DateTimeFormatter.ofPattern("yyyy-MM-dd"), // ISO 8601
                DateTimeFormatter.ofPattern("dd/MM/yyyy"), // Día/Mes/Año
                DateTimeFormatter.ofPattern("MM/dd/yyyy")  // Mes/Día/Año
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

}
