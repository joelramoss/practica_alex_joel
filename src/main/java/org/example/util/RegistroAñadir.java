package org.example.util;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import org.example.daos.*;
import org.example.entidades.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Clase encargada de gestionar la adición de registros en la base de datos desde un archivo CSV.
 *
 * <p>Esta clase proporciona un menú interactivo para que el usuario pueda cargar un archivo CSV
 * que contenga registros y agregarlos a la base de datos. Además, permite volver al menú principal
 * o salir del programa.</p>
 */
public class RegistroAñadir {

    private static final Scanner sc = new Scanner(System.in); // Instancia única de Scanner

    /**
     * Muestra el menú para añadir registros y permite al usuario importar datos desde un archivo CSV.
     *
     * <p>Si el archivo CSV es válido y existe, se eliminarán los registros previos de la base de datos,
     * y luego se importarán los nuevos datos desde el archivo. El usuario puede optar por volver al menú
     * principal o salir del programa.</p>
     *
     * @throws SQLException         Si ocurre un error al interactuar con la base de datos.
     * @throws IOException          Si ocurre un error al leer el archivo CSV.
     * @throws InterruptedException Si ocurre una interrupción durante la ejecución del programa.
     */
    public static void menu() throws SQLException, IOException, InterruptedException {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== Menú Leer Registros ===");
            System.out.println("1. Añadir registro Juego entero");
            System.out.println("2. Añadir equipo de desarollo");
            System.out.println("3. Añadir generos");
            System.out.println("2. Volver al menú principal");
            System.out.println("3. Salir del programa");
            int opcion = leerOpcion();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    anadirJuego();
                case 2:
                    System.out.println("Volviendo al menú principal...");
                    salir = true; // Cambia el comportamiento según lo que quieras lograr
                    break;
                case 3:
                    System.out.println("Saliendo del programa...");
                    Thread.sleep(1000); // Simula una pausa al cerrar
                    System.exit(0);

                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        }
    }

    /**
     * Lee una opción seleccionada por el usuario desde la entrada estándar.
     *
     * @return La opción seleccionada por el usuario.
     */
    private static int leerOpcion() {
        try {
            return sc.nextInt();
        } catch (Exception e) {
            sc.nextLine(); // Limpiar buffer
            System.out.println("Entrada inválida. Por favor, ingrese un número.");
            return -1;
        }
    }

    private static int leerEntero(String campo) {
        while (true) {
            System.out.println("Introduce el número de " + campo + ":");
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Introduce un número entero.");
            }
        }
    }
    public static void anadirJuego() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                DaoJuego daoJuego = new DaoJuego(session);
                DaoGeneros daoGeneros = new DaoGeneros(session);
                DaoDesarolladores daoDesarrolladores = new DaoDesarolladores(session);
                DaoJuegosGenerados daoJuegosGenerados = new DaoJuegosGenerados(session);
                DaoJuegoEquipo daoJuegoEquipo = new DaoJuegoEquipo(session);
                DaoDetallesJuego daoDetallesJuego = new DaoDetallesJuego(session); // DAO para detalles_juego
                DaoRating daoRating = new DaoRating(session); // DAO para rating

                Juego juego = new Juego();

                System.out.println("Introduce el título del juego:");
                juego.setTitle(sc.nextLine());

                System.out.println("Introduce la fecha de lanzamiento (yyyy-MM-dd):");
                juego.setReleaseDate(java.sql.Date.valueOf(sc.nextLine()));

                System.out.println("Introduce un resumen del juego:");
                juego.setSummary(sc.nextLine());

                System.out.println("Introduce el número de jugadores:");
                juego.setPlaying(leerEntero("jugadores actuales"));

                // Guardar el juego en la base de datos
                daoJuego.crearJuego(juego);
                session.flush(); // Asegura que el juego se guarda antes de obtener el ID

                // Obtener el último ID del juego
                int juegoId = daoJuego.obtenerUltimoId();

                // Capturar y guardar el rating del juego
                System.out.println("Introduce el rating del juego (ejemplo: 4.5):");
                double ratingValue = Double.parseDouble(sc.nextLine());
                System.out.println("Introduce el número de reseñas para este rating:");
                int numReviews = leerEntero("reseñas");

                Rating rating = new Rating();
                rating.setJuegoId(juegoId);
                rating.setRating(ratingValue);
                rating.setNumberOfReviews(numReviews);
                daoRating.crearRating(rating); // Guardar el rating en la base de datos

                // Capturar y guardar los detalles del juego
                System.out.println("Introduce los detalles del juego:");
                String detallesTexto = sc.nextLine();

                DetallesJuego detallesJuego = new DetallesJuego();
                detallesJuego.setJuegoId(juegoId);
                detallesJuego.setReviews(detallesTexto);
                daoDetallesJuego.i(detallesJuego); // Guardar detalles del juego en la base de datos

                // Procesar géneros
                System.out.println("Introduce el genero");
                String[] generos = sc.nextLine().split(",");
                for (String genero : generos) {
                    if (genero.trim().isEmpty()) continue;
                    Generos gen = daoGeneros.crearOObtenerGenero(genero.trim());
                    daoJuegosGenerados.crearRelacionJuegoGenero(juegoId, String.valueOf(gen.getId()));
                }

                // Procesar desarrolladores
                System.out.println("Introduce los desarrolladores del juego separados por comas:");
                String[] desarrolladores = sc.nextLine().split(",");
                for (String desarrollador : desarrolladores) {
                    if (desarrollador.trim().isEmpty()) continue;
                    Desarrolladores dev = daoDesarrolladores.crearOObtenerDesarrollador(desarrollador.trim());
                    daoJuegoEquipo.crearRelacionJuegoDesarrollador(juegoId, dev.getId());
                }

                transaction.commit();
                System.out.println("Juego añadido con éxito: " + juego.getTitle());
            } catch (Exception e) {
                transaction.rollback();
                System.err.println("Error al añadir el juego: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }







}

