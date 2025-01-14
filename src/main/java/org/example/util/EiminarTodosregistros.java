package org.example.util;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class EiminarTodosregistros  {

    public static void eliminarTodosLosDatos() {
        Session session = HibernateUtil.getSessionFactory().openSession();  // Obtén la sesión de Hibernate
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();  // Inicia la transacción

            // Eliminar todos los datos de las tablas mediante Hibernate
            String[] eliminarQueries = {
                    "DELETE FROM Rating",
                    "DELETE FROM JuegoEquipo",
                    "DELETE FROM JuegosGenerados",
                    "DELETE FROM DetallesJuego",
                    "DELETE FROM Desarrolladores",
                    "DELETE FROM Juego",
                    "DELETE FROM Generos"
            };


            // Ejecutar las consultas DELETE
            for (String query : eliminarQueries) {
                Query<?> hqlQuery = session.createQuery(query);
                hqlQuery.executeUpdate();
            }

            // Reiniciar los contadores de auto incremento
            // Hibernate generalmente maneja el auto-incremento de forma automática,
            // pero si deseas forzar el reinicio, puedes hacerlo mediante una consulta nativa.
            String[] autoIncrementaronQueries = {
                    "ALTER TABLE desarrolladores AUTO_INCREMENT = 1",
                    "ALTER TABLE juego AUTO_INCREMENT = 1",
                    "ALTER TABLE generos AUTO_INCREMENT = 1",
                    "ALTER TABLE juego_equipo AUTO_INCREMENT = 1",
            };

            // Ejecutar las consultas ALTER para reiniciar el auto incremento
            for (String query : autoIncrementaronQueries) {
                Query<?> hqlQuery = session.createNativeQuery(query);
                hqlQuery.executeUpdate();
            }

            transaction.commit();  // Confirmar la transacción
            System.out.println("Todos los datos han sido eliminados y los contadores de autoincremento reiniciados con éxito.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();  // Revierte la transacción en caso de error
            }

            System.err.println("Error al eliminar los datos: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) session.close();  // Cerrar la sesión
        }
    }
}
