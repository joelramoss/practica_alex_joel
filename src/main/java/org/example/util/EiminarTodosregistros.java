package org.example.util;





public class EiminarTodosregistros {


    public static void eliminarTodosLosDatos() {
        try {
            connection.setAutoCommit(false); // Inicia la transacción

            // Eliminar todos los datos de las tablas
            String[] eliminarQueries = {
                    "DELETE FROM rating",
                    "DELETE FROM juego_equipo",
                    "DELETE FROM juegos_generos",
                    "DELETE FROM detalles_juego",
                    "DELETE FROM desarrolladores",
                    "DELETE FROM juego",
                    "DELETE FROM generos"
            };

            // Ejecutar las consultas DELETE
            for (String query : eliminarQueries) {
                try (PreparedStatement stmt = connection.prepareStatement(query)) {
                    stmt.executeUpdate();
                }
            }

            // Reiniciar los contadores de auto incremento
            String[] autoIncrementaronQueries = {
                    "ALTER TABLE desarrolladores AUTO_INCREMENT = 1",
                    "ALTER TABLE juego AUTO_INCREMENT = 1",
                    "ALTER TABLE generos AUTO_INCREMENT = 1",
                    "ALTER TABLE juego_equipo AUTO_INCREMENT = 1",
                    "ALTER TABLE juegos_generos AUTO_INCREMENT = 1"
            };

            // Ejecutar las consultas ALTER para reiniciar el auto incremento
            for (String query : autoIncrementaronQueries) {
                try (PreparedStatement stmt = connection.prepareStatement(query)) {
                    stmt.executeUpdate();
                }
            }

            connection.commit(); // Confirmar la transacción
            System.out.println("Todos los datos han sido eliminados y los contadores de autoincremento reiniciados con éxito.");
        } catch (SQLException e) {
            try {
                connection.rollback(); // Revierte la transacción en caso de error
            } catch (SQLException ex) {
                System.err.println("Error al revertir la transacción: " + ex.getMessage());
            }
            System.err.println("Error al eliminar los datos: " + e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true); // Restablece el modo por defecto
            } catch (SQLException e) {
                System.err.println("Error al restablecer el modo de autocommit: " + e.getMessage());
            }
        }
    }
}
