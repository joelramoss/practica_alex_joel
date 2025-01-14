package org.example.util;

import org.example.daos.*;
import org.example.entidades.Desarrolladores;
import org.example.entidades.Juego;


import org.example.entidades.JuegoEquipo;

import org.example.entidades.JuegosGenerados;
import org.hibernate.Session;

import java.sql.*;
import java.util.List;
import java.util.Scanner;


/**
 * Clase encargada de gestionar la lectura de registros de la base de datos.
 *
 * <p>Esta clase proporciona un menú para que el usuario lea datos de la base de datos según diferentes criterios,
 * como la lectura de registros específicos de juegos, equipos de desarrollo o géneros. El usuario puede elegir
 * el tipo de consulta y el ID o nombre para obtener la información deseada.</p>
 */
public class LeerBBDD {
    private static final Scanner sc = new Scanner(System.in);
    private static final Session session = HibernateUtil.getSessionFactory().openSession();

    /**
     * Muestra el menú principal para leer registros de la base de datos y gestiona las opciones seleccionadas.
     */
    public static void menu() throws InterruptedException {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== Menú Leer Registros ===");
            System.out.println("1. Leer todos los registros de juegos");
            System.out.println("2. Leer todos los registros de desarrolladores");
            System.out.println("3. Volver al menú principal");
            System.out.println("4. Salir del programa");

            int opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    leerJuegos();
                    break;

                case 2:
                    leerDesarrolladores();
                    break;

                case 3:
                    salir = true; // Salir del menú de lectura, y volver al menú principal
                    break;

                case 4:
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
     * Lee todos los registros de juegos desde la base de datos y los muestra en consola.
     */
    private static void leerJuegos() {
        try {
            DaoJuego daoJuego = new DaoJuego(session);
            List<Juego> juegos = daoJuego.obtenerTodos(); // Recupera todos los juegos

            if (juegos.isEmpty()) {
                System.out.println("No hay juegos registrados en la base de datos.");
            } else {
                System.out.println("\n=== Lista de Juegos ===");
                for (Juego juego : juegos) {
                    System.out.println(juego.toString()); // Imprime el toString de cada juego
                }
            }
        } catch (Exception e) {
            System.err.println("Error al leer los juegos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Lee todos los registros de desarrolladores desde la base de datos y los muestra en consola.
     */
    private static void leerDesarrolladores() {
        try {
            DaoDesarolladores daoDesarrolladores = new DaoDesarolladores(session);
            List<Desarrolladores> desarrolladores = daoDesarrolladores.obtenerTodos(); // Recupera todos los desarrolladores

            if (desarrolladores.isEmpty()) {
                System.out.println("No hay desarrolladores registrados en la base de datos.");
            } else {
                System.out.println("\n=== Lista de Desarrolladores ===");
                for (Desarrolladores desarrollador : desarrolladores) {
                    System.out.println(desarrollador.toString()); // Imprime el toString de cada desarrollador
                }
            }
        } catch (Exception e) {
            System.err.println("Error al leer los desarrolladores: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Lee la opción ingresada por el usuario.
     *
     * @return La opción seleccionada.
     */
    private static int leerOpcion() {
        try {
            int opcion = sc.nextInt();
            sc.nextLine(); // Limpiar el buffer después de leer un número
            return opcion;
        } catch (Exception e) {
            sc.nextLine(); // Limpiar el buffer en caso de error
            System.out.println("Entrada inválida. Por favor, ingrese un número.");
            return -1;
        }
    }
}

