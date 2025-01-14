package org.example.util;

import org.example.daos.DaoJuego;
import org.example.entidades.Juego;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EliminarRegistros {
    private static final Scanner sc = new Scanner(System.in);

    public static void menu() throws SQLException {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== Menú Eliminar Registros ===");
            System.out.println("1. Eliminar un juego por ID");
            System.out.println("2. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    eliminarJuego();
                    break;
                case 2:
                    System.out.println("Volviendo al Menú Principal...");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        }
    }

    private static void eliminarJuego() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                DaoJuego dao = new DaoJuego(session);

                System.out.print("Ingrese el ID del juego a eliminar: ");
                int id = sc.nextInt();
                sc.nextLine(); // Limpiar el buffer después de leer un entero

                // Confirmar la eliminación
                System.out.print("¿Está seguro de que desea eliminar este juego y todas sus relaciones? (S/N): ");
                String confirmacion = sc.nextLine().trim().toLowerCase();

                if (!confirmacion.equals("s")) {
                    System.out.println("Operación cancelada.");
                    return;
                }

                // Verificar si el juego existe antes de eliminarlo
                Juego juego = dao.obtenerPorId(id);
                if (juego == null) {
                    System.out.println("El juego con ID " + id + " no existe en la base de datos.");
                    return;
                }

                // Llamar a eliminarJuegoYRelaciones del DAO
                dao.eliminarJuegoYRelaciones(id);

                transaction.commit();
                System.out.println("El juego con ID " + id + " ha sido eliminado exitosamente, junto con todas sus relaciones.");
            } catch (Exception e) {
                transaction.rollback();
                System.out.println("Ocurrió un error inesperado durante la eliminación: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("Error al abrir la sesión de Hibernate: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static int leerOpcion() {
        try {
            return sc.nextInt();
        } catch (InputMismatchException e) {
            sc.nextLine(); // Limpiar buffer
            System.out.println("Entrada inválida. Por favor, ingrese un número.");
            return -1; // Valor inválido para manejar errores
        }
    }
}
