package org.example.util;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

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
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     * @throws IOException Si ocurre un error al leer el archivo CSV.
     * @throws InterruptedException Si ocurre una interrupción durante la ejecución del programa.
     */
    public static void menu() throws SQLException, IOException, InterruptedException {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== Menú Leer Registros ===");
            System.out.println("1. Añadir registro por csv");
            System.out.println("2. Volver al menú principal");
            System.out.println("3. Salir del programa");
            int opcion = leerOpcion();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    String filePath = "src/main/java/org/example/games.csv";
                    File file = new File(filePath);
                    if (!file.exists() || !file.isFile()) {
                        System.err.println("El archivo no existe o no es válido: " + filePath);
                        break;
                    }
                    try {
                        EiminarTodosregistros.eliminarTodosLosDatos();
                        CSVImporter.importarCSV(filePath);
                        System.out.println("Archivo CSV importado correctamente.");
                    } catch (IOException e) {
                        System.err.println("Error al leer el archivo CSV: " + e.getMessage());
                    } catch (SQLException e) {
                        System.err.println("Error al interactuar con la base de datos: " + e.getMessage());
                    }
                    break;

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
}
