package org.example.daos;

import org.example.entidades.Rating;
import org.hibernate.Session;

public class DaoRating {

    private Session session;

    // Constructor que acepta una sesión activa
    public DaoRating(Session session) {
        this.session = session;
    }

    // Crear un nuevo rating para un juego
    public void crearRating(Rating rating) {
        try {
            session.persist(rating); // Guardar el objeto Rating usando la sesión activa
        } catch (Exception e) {
            System.err.println("Error al crear rating: " + e.getMessage());
            throw e; // Lanzar la excepción para que la transacción principal la maneje
        }
    }

    // Leer un rating para un juego dado su juegoId
    public Rating leerRating(int juegoId) {
        try {
            return session.get(Rating.class, juegoId); // Obtener rating por juegoId usando la sesión activa
        } catch (Exception e) {
            System.err.println("Error al leer rating: " + e.getMessage());
            throw e; // Lanzar la excepción para que la transacción principal la maneje
        }
    }

    // Actualizar un rating existente para un juego
    public void actualizarRating(Rating rating) {
        try {
            session.update(rating); // Actualizar el objeto Rating usando la sesión activa
        } catch (Exception e) {
            System.err.println("Error al actualizar rating: " + e.getMessage());
            throw e; // Lanzar la excepción para que la transacción principal la maneje
        }
    }

    // Eliminar un rating para un juego dado su juegoId
    public void eliminarRating(int juegoId) {
        try {
            Rating rating = session.get(Rating.class, juegoId); // Obtener el Rating por juegoId
            if (rating != null) {
                session.delete(rating); // Eliminar el rating usando la sesión activa
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar rating: " + e.getMessage());
            throw e; // Lanzar la excepción para que la transacción principal la maneje
        }
    }
}
