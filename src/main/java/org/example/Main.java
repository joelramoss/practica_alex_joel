package org.example;

import org.example.util.*;
import org.hibernate.*;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.SQLException;

public class Main {
    private static final Scanner sc = new Scanner(System.in); // Instancia única de Scanner
    private static Session session = null; // Sesión única compartida

    public static void main(String[] args) throws SQLException {
        try {
            menu(); // Inicia el menú principal
        } catch (InterruptedException IE) {
            System.out.println("Se produjo un error: " + IE.getMessage());
        } catch (IOException e) {
            System.out.println("Se produjo un error de I/O: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close(); // Cierra la sesión al finalizar la aplicación
                System.out.println("Conexión cerrada correctamente.");
            }
            sc.close(); // Cierra el Scanner al finalizar
        }
    }

    public static void menu() throws InterruptedException, SQLException, IOException {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== Menú Principal ===");
            System.out.println("1. Conectar a la base de datos");
            System.out.println("2. Crear un Registro");
            System.out.println("3. Leer Registros");
            System.out.println("4. Actualizar Registros");
            System.out.println("5. Eliminar Registros");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    conectarBaseDatos();
                    break;
                case 2:
                    RegistroAñadir.menu();
                    break;
                case 3:
                    LeerBBDD.menu();
                    break;
                case 4:
                    Actualizarregistro.menu();
                    break;
                case 5:
                    EliminarRegistros.menu();
                    break;
                case 6:
                    salir = true;
                    System.out.println("Cerrando aplicación...");
                    Thread.sleep(1000);
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        }
    }

    public static void conectarBaseDatos() {
        if (session == null || !session.isOpen()) {
            session = HibernateUtil.getSessionFactory().openSession();

            System.out.println("Conexión establecida con éxito.");
        } else {
            System.out.println("Ya estás conectado a la base de datos.");
        }
    }

    public static int leerOpcion() {
        try {
            return sc.nextInt();
        } catch (InputMismatchException e) {
            sc.nextLine();
            System.out.println("Entrada inválida. Por favor, ingrese un número.");
            return -1;
        }
    }
}
