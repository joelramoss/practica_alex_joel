package org.example.util;

import org.example.daos.*;
import org.example.entidades.Desarrolladores;
import org.example.entidades.Generos;
import org.example.entidades.Juego;
import org.hibernate.Session;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;

import static org.example.Main.leerOpcion;

public class Actualizarregistro {
    /**
     * Muestra el menú de opciones para actualizar registros en la base de datos.
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
                    Thread.sleep(1000);
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void actualizarJuego() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Scanner scanner = new Scanner(System.in);
            DaoJuego dao = new DaoJuego(session);
            DaoJuegoEquipo daoJuegoEquipo = new DaoJuegoEquipo(session);
            DaoJuegosGenerados daoJuegosGenerados = new DaoJuegosGenerados(session);

            session.beginTransaction();
            System.out.print("Ingrese el ID del juego a actualizar: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            Juego juego = dao.obtenerPorId(id);
            if (juego == null) {
                System.out.println("Juego no encontrado.");
                return;
            }

            // Mostrar y actualizar datos
            System.out.println("Datos actuales: " + juego);
            juego.setTitle(obtenerInputString(scanner, "Ingrese el nuevo título: "));
            System.out.println("Ingrese la nueva fecha de lanzamiento (yyyy-MM-dd):");
            String fechaInput = scanner.nextLine();
            juego.setReleaseDate(convertToDate(fechaInput));
            juego.setSummary(obtenerInputString(scanner, "Ingrese el nuevo resumen: "));
            juego.setPlays(obtenerInputInt(scanner, "Ingrese el nuevo número de juegos jugados: "));
            juego.setPlaying(obtenerInputInt(scanner, "Ingrese el nuevo número de jugadores actuales: "));
            juego.setBacklogs(obtenerInputInt(scanner, "Ingrese el nuevo número de juegos pendientes: "));
            juego.setWishlist(obtenerInputInt(scanner, "Ingrese el nuevo número de lista de deseos: "));
            juego.setTimesListed(obtenerInputInt(scanner, "Ingrese el nuevo número de veces listados: "));

            dao.actualizarJuego(juego);

            // Actualizar géneros asociados al juego
            actualizarGenerosJuego(scanner, juego.getId(), daoJuegosGenerados);

            // Actualizar desarrolladores asociados al juego
            actualizarDesarrolladoresJuego(scanner, juego.getId(), daoJuegoEquipo);

            session.getTransaction().commit();
            System.out.println("Juego actualizado con éxito.");
        } catch (Exception e) {
            System.out.println("Error al actualizar el juego: " + e.getMessage());
        }
    }

    private static void actualizarGenerosJuego(Scanner scanner, int juegoId, DaoJuegosGenerados daoJuegosGenerados) {
        System.out.println("¿Desea actualizar los géneros del juego? (sí/no)");
        String respuesta = scanner.nextLine().toLowerCase();

        if (respuesta.equals("sí")) {
            System.out.println("Ingrese los nuevos géneros del juego (separados por coma): ");
            String[] nuevosGeneros = scanner.nextLine().split(",");

            for (String genero : nuevosGeneros) {
                if (genero.trim().isEmpty()) continue;
                daoJuegosGenerados.crearRelacionJuegoGenero(juegoId, genero.trim());
            }
        }
    }

    private static void actualizarDesarrolladoresJuego(Scanner scanner, int juegoId, DaoJuegoEquipo daoJuegoEquipo) {
        System.out.println("¿Desea actualizar los desarrolladores del juego? (s/n)");
        String respuesta = scanner.nextLine().toLowerCase();

        if (respuesta.equals("s")) {
            System.out.println("Ingrese los nuevos desarrolladores del juego (separados por coma): ");
            String[] nuevosDesarrolladores = scanner.nextLine().split(",");

            for (String desarrollador : nuevosDesarrolladores) {
                if (desarrollador.trim().isEmpty()) continue;
                daoJuegoEquipo.crearRelacionJuegoDesarrollador(juegoId, desarrollador.trim().hashCode());
            }
        }
    }

    private static void actualizarDesarrollador() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Scanner scanner = new Scanner(System.in);
            DaoDesarolladores dao = new DaoDesarolladores(session);

            session.beginTransaction();
            System.out.print("Ingrese el ID del desarrollador a actualizar: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            Desarrolladores desarrollador = dao.s(id);
            if (desarrollador == null) {
                System.out.println("Desarrollador no encontrado.");
                return;
            }

            desarrollador.setNombre(obtenerInputString(scanner, "Ingrese el nuevo nombre: "));
            dao.u(desarrollador);
            session.getTransaction().commit();
            System.out.println("Desarrollador actualizado con éxito.");
        } catch (Exception e) {
            System.out.println("Error al actualizar el desarrollador: " + e.getMessage());
        }
    }

    private static void actualizarGenero() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Scanner scanner = new Scanner(System.in);
            DaoGeneros dao = new DaoGeneros(session);

            session.beginTransaction();
            System.out.print("Ingrese el ID del género a actualizar: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            Generos genero = dao.s(id);
            if (genero == null) {
                System.out.println("Género no encontrado.");
                return;
            }

            genero.setGenero(obtenerInputString(scanner, "Ingrese el nuevo nombre del género: "));
            dao.u(genero);
            session.getTransaction().commit();
            System.out.println("Género actualizado con éxito.");
        } catch (Exception e) {
            System.out.println("Error al actualizar el género: " + e.getMessage());
        }
    }

    private static String obtenerInputString(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private static int obtenerInputInt(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida. Ingrese un número entero.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    public static java.sql.Date convertToDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(dateStr, formatter);
            return java.sql.Date.valueOf(localDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha no válido. Use yyyy-MM-dd.");
        }
    }
}
