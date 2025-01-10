package org.example.util;

import org.example.DAOs.daoJuegoEquipo;
import org.example.entidades.Juego;
import org.example.DAOs.daoJuego;
import org.example.entidades.juego_equipo;
import org.example.entidades.JuegosGenerados;
import org.example.DAOs.daoJuegosGenerados;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

import static org.example.DAOs.daoJuego.obtenerTituloPorId;

/**
 * Clase encargada de gestionar la lectura de registros de la base de datos.
 *
 * <p>Esta clase proporciona un menú para que el usuario lea datos de la base de datos según diferentes criterios,
 * como la lectura de registros específicos de juegos, equipos de desarrollo o géneros. El usuario puede elegir
 * el tipo de consulta y el ID o nombre para obtener la información deseada.</p>
 */
public class LeerBBDD {
    private static final Scanner sc = new Scanner(System.in);
    private static final Connection connection;
    private static final daoJuego juegoDAO;

    static {
        try {
            // Inicializa la conexión a la base de datos
            connection = ConnectionDB.getInstance().getConnection();
            juegoDAO = new daoJuego(); // Inicializa el DAO después de la conexión
        } catch (SQLException e) {
            throw new RuntimeException("Error al establecer la conexión a la base de datos", e);
        }
    }

    /**
     * Muestra el menú principal para leer registros de la base de datos y gestiona las opciones seleccionadas.
     *
     * <p>Permite al usuario elegir entre leer un registro específico de la base de datos, volver al menú principal
     * o salir del programa. Cada opción ejecuta una acción específica según la elección del usuario.</p>
     *
     * @throws InterruptedException Si ocurre una interrupción mientras se espera el cierre del programa.
     */
    public static void menu() throws InterruptedException {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== Menú Leer Registros ===");
            System.out.println("1. Leer un registro específico de la base de datos");
            System.out.println("2. Volver al menú principal");
            System.out.println("3. Salir del programa");

            int opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    leerRegistro();
                    break;

                case 2:
                    salir = true; // Salir del menú de lectura, y volver al menú principal
                    break;

                case 3:
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
     * Muestra las opciones para leer registros específicos (juegos, equipos o géneros) y gestiona las selecciones.
     *
     * <p>Dependiendo de la opción seleccionada, se llama a métodos específicos para leer los registros de juegos,
     * equipos de desarrollo o géneros.</p>
     *
     * @throws SQLException Si ocurre un error al consultar la base de datos.
     */
    private static void leerRegistro() {
        System.out.println("\nSeleccione una opción de lectura:");
        System.out.println("1. Datos de un ID de juego específico");
        System.out.println("2. Datos de un equipo de desarrollo específico");
        System.out.println("3. Datos de un género específico");

        int opcion = leerOpcion();

        try {
            switch (opcion) {
                case 1:
                    extracted1();
                    break;

                case 2:
                    extracted();
                    break;

                case 3:
                    extracted2();
                    break;

                default:
                    System.out.println("Opción no válida. Regresando al menú.");
            }
        } catch (SQLException e) {
            System.out.println("Error al leer el registro: " + e.getMessage());
        }
    }

    /**
     * Lee los registros relacionados con los géneros de los juegos, mostrando los juegos asociados a un género específico.
     *
     * @throws SQLException Si ocurre un error al consultar la base de datos.
     */
    private static void extracted2() throws SQLException {
        System.out.println("Ingrese el género de los juegos: ");
        String nombreGenero = sc.nextLine();

        // Instancia del DAO
        daoJuegosGenerados juegosGeneradosDAO = new daoJuegosGenerados(connection);

        // Llamar al método para obtener los juegos asociados al género
        List<JuegosGenerados> juegosPorGenero = juegosGeneradosDAO.obtenerJuegosPorGenero(nombreGenero);

        // Mostrar los resultados
        if (juegosPorGenero.isEmpty()) {
            System.out.println("No se han encontrado más juegos para el género: " + nombreGenero);
        } else {
            System.out.println("Juegos asociados al género " + nombreGenero + ":");
            for (JuegosGenerados juego : juegosPorGenero) {
                // Realizar una consulta directa para obtener el título del juego
                String tituloJuego = obtenerTituloPorId(juego.getJuegoId());
                System.out.println("Título: " + tituloJuego + ", Géneros: " + juego.getGeneros());
            }
        }
    }

    /**
     * Lee un registro de juego utilizando su ID y muestra la información del juego.
     *
     * @throws SQLException Si ocurre un error al consultar la base de datos.
     */
    private static void extracted1() throws SQLException {
        System.out.print("Ingrese el ID de juego: ");
        int idJuego = sc.nextInt();
        sc.nextLine(); // Limpia el buffer

        // Llamamos al método para leer el juego por su ID
        Juego juego = juegoDAO.obtenerPorId(idJuego);

        if (juego != null) {
            System.out.println("Juego encontrado: " + juego.toString());
        } else {
            System.out.println("No se encontró el juego con el ID " + idJuego);
        }
    }

    /**
     * Lee los registros de juegos relacionados con un equipo de desarrollo utilizando el nombre del equipo.
     *
     * @throws SQLException Si ocurre un error al consultar la base de datos.
     */
    private static void extracted() throws SQLException {
        System.out.println("Ingrese el nombre del equipo de desarrollo: ");
        String nombreEquipo = sc.nextLine();

        // Instancia del DAO
        daoJuegoEquipo juegoEquipoDAO = new daoJuegoEquipo(connection);

        // Llamar al método para obtener los juegos relacionados con el nombre del equipo
        List<juego_equipo> listaJuegosEquipo = juegoEquipoDAO.obtenerPorNombreDesarrollador(nombreEquipo);

        // Mostrar los resultados
        if (listaJuegosEquipo.isEmpty()) {
            System.out.println("No se encontraron juegos para el equipo con nombre: " + nombreEquipo);
        } else {
            System.out.println("Juegos relacionados con el equipo " + nombreEquipo + ":");
            for (juego_equipo je : listaJuegosEquipo) {
                System.out.println(je.toString());
            }
        }
    }

    /**
     * Lee una opción del usuario (número entero) desde la consola.
     *
     * @return La opción seleccionada por el usuario.
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
