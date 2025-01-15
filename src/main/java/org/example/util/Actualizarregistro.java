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

    public static boolean prueba = false;
    /**
     * Muestra el menú de opciones para actualizar registros en la base de datos.
     */
    public static void menu() throws InterruptedException {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== Menú Actualizar Registros ===");
            System.out.println("1. Actualizar registro de la tabla juego");
            System.out.println("2. Volver al menú principal");
            System.out.println("3. Salir del programa");
            int opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    actualizarJuego();
                    break;
                case 2:
                    salir = true;
                    break;
                case 3:
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
                session.getTransaction().rollback();
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
            scanner.nextLine(); // Limpiar buffer
            juego.setPlaying(obtenerInputInt(scanner, "Ingrese el nuevo número de jugadores actuales: "));
            scanner.nextLine(); // Limpiar buffer
            juego.setBacklogs(obtenerInputInt(scanner, "Ingrese el nuevo número de juegos pendientes: "));
            scanner.nextLine(); // Limpiar buffer
            juego.setWishlist(obtenerInputInt(scanner, "Ingrese el nuevo número de lista de deseos: "));
            scanner.nextLine(); // Limpiar buffer
            juego.setTimesListed(obtenerInputInt(scanner, "Ingrese el nuevo número de veces listados: "));
            scanner.nextLine(); // Limpiar buffer

            dao.actualizarJuego(juego);

            session.getTransaction().commit();
            System.out.println("Juego actualizado con éxito.");
        } catch (Exception e) {
            System.out.println("Error al actualizar el juego: " + e.getMessage());
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

            // Obtener el año actual
            int currentYear = LocalDate.now().getYear();

            // Validar que la fecha no sea más antigua de 200 años ni posterior al año actual
            if (localDate.getYear() < currentYear - 100 || localDate.getYear() > currentYear) {
                throw new IllegalArgumentException("La fecha debe estar entre " + (currentYear - 100) + " y " + currentYear + ".");
            }

            return java.sql.Date.valueOf(localDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha no válido. Use yyyy-MM-dd.");
        }
    }

}
