package org.example.daos;

import org.example.entidades.JuegosGenerados;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class DaoJuegosGenerados {

    private static Session session;

    // Constructor que acepta una sesión activa
    public DaoJuegosGenerados(Session session) {
        this.session = session;
    }

    // Crear una nueva relación entre un juego y un género
    public static void crearRelacionJuegoGenero(int juegoId, String genero) {
        try {
            JuegosGenerados relacion = new JuegosGenerados(); // Crear objeto sin asignar ID
            relacion.setJuegoId(juegoId);
            relacion.setGeneros(genero); // Asignar el nombre del género
            session.persist(relacion); // Guardar la relación usando la sesión activa
        } catch (Exception e) {
            System.err.println("Error al crear relación juego-género: " + e.getMessage());
            throw e; // Lanzar la excepción para que la transacción principal la maneje
        }
    }

    // Leer una relación entre un juego y un género por el ID del juego
    public JuegosGenerados leerJuegosGenerados(int juegoId) {
        try {
            return session.get(JuegosGenerados.class, juegoId); // Obtener relación por ID del juego
        } catch (Exception e) {
            System.err.println("Error al leer relación juego-género: " + e.getMessage());
            throw e; // Lanzar la excepción para que la transacción principal la maneje
        }
    }

    // Obtener todas las relaciones entre juegos y géneros
    public List<JuegosGenerados> obtenerTodos() {
        try {
            Query<JuegosGenerados> query = session.createQuery("FROM JuegosGenerados", JuegosGenerados.class);
            return query.list(); // Retornar todas las relaciones
        } catch (Exception e) {
            System.err.println("Error al obtener todas las relaciones: " + e.getMessage());
            throw e; // Lanzar la excepción para que la transacción principal la maneje
        }
    }

    // Obtener todas las relaciones entre juegos y géneros por el nombre del género
    public List<JuegosGenerados> obtenerPorNombreGenero(String nombreGenero) {
        try {
            String hql = "SELECT jg FROM JuegosGenerados jg " +
                    "WHERE jg.generos = :nombreGenero";
            Query<JuegosGenerados> query = session.createQuery(hql, JuegosGenerados.class);
            query.setParameter("nombreGenero", nombreGenero);
            return query.list(); // Retornar relaciones filtradas por el nombre del género
        } catch (Exception e) {
            System.err.println("Error al obtener relaciones por nombre del género: " + e.getMessage());
            throw e; // Lanzar la excepción para que la transacción principal la maneje
        }
    }

    // Actualizar una relación entre un juego y un género
    public void actualizarRelacionJuegoGenero(JuegosGenerados relacion) {
        try {
            session.update(relacion); // Actualizar la relación usando la sesión activa
        } catch (Exception e) {
            System.err.println("Error al actualizar relación juego-género: " + e.getMessage());
            throw e; // Lanzar la excepción para que la transacción principal la maneje
        }
    }

    // Eliminar una relación entre un juego y un género por su ID
    public void eliminarRelacionJuegoGenero(int juegoId) {
        try {
            JuegosGenerados relacion = session.get(JuegosGenerados.class, juegoId); // Obtener relación por ID
            if (relacion != null) {
                session.delete(relacion); // Eliminar la relación usando la sesión activa
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar relación juego-género: " + e.getMessage());
            throw e; // Lanzar la excepción para que la transacción principal la maneje
        }
    }
}
