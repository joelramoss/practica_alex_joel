package org.example.daos;

import org.example.entidades.JuegoEquipo;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class DaoJuegoEquipo {

    private Session session;

    // Constructor que acepta una sesión activa
    public DaoJuegoEquipo(Session session) {
        this.session = session;
    }

    // Crear una nueva relación entre un juego y un desarrollador
    public void crearRelacionJuegoDesarrollador(int juegoId, int desarrolladorId) {
        try {
            JuegoEquipo relacion = new JuegoEquipo(); // Crear objeto sin asignar ID (se autogenera)
            relacion.setJuegoId(juegoId);
            relacion.setDesarrolladorId(desarrolladorId);
            session.persist(relacion); // Guardar la relación usando la sesión activa
        } catch (Exception e) {
            System.err.println("Error al crear relación juego-desarrollador: " + e.getMessage());
            throw e; // Lanzar la excepción para que la transacción principal la maneje
        }
    }

    // Leer una relación entre un juego y un desarrollador por su ID
    public JuegoEquipo leerJuegoEquipo(int id) {
        try {
            return session.get(JuegoEquipo.class, id); // Obtener relación por ID usando la sesión activa
        } catch (Exception e) {
            System.err.println("Error al leer relación juego-desarrollador: " + e.getMessage());
            throw e; // Lanzar la excepción para que la transacción principal la maneje
        }
    }

    // Obtener todas las relaciones entre juegos y desarrolladores
    public List<JuegoEquipo> obtenerTodos() {
        try {
            Query<JuegoEquipo> query = session.createQuery("FROM JuegoEquipo", JuegoEquipo.class);
            return query.list(); // Retornar todas las relaciones
        } catch (Exception e) {
            System.err.println("Error al obtener todas las relaciones: " + e.getMessage());
            throw e; // Lanzar la excepción para que la transacción principal la maneje
        }
    }

    // Obtener todas las relaciones entre juegos y desarrolladores por el nombre del desarrollador
    public List<JuegoEquipo> obtenerPorNombreDesarrollador(String nombreDesarrollador) {
        try {
            String hql = "SELECT je FROM JuegoEquipo je " +
                    "JOIN Desarrolladores d ON je.desarrolladorId = d.id " +
                    "WHERE d.nombre = :nombreDesarrollador";
            Query<JuegoEquipo> query = session.createQuery(hql, JuegoEquipo.class);
            query.setParameter("nombreDesarrollador", nombreDesarrollador);
            return query.list(); // Retornar relaciones filtradas por el nombre del desarrollador
        } catch (Exception e) {
            System.err.println("Error al obtener relaciones por nombre del desarrollador: " + e.getMessage());
            throw e; // Lanzar la excepción para que la transacción principal la maneje
        }
    }

    // Actualizar una relación entre un juego y un desarrollador
    public void actualizarRelacionJuegoDesarrollador(JuegoEquipo relacion) {
        try {
            session.merge(relacion); // Actualizar la relación usando la sesión activa
        } catch (Exception e) {
            System.err.println("Error al actualizar relación juego-desarrollador: " + e.getMessage());
            throw e; // Lanzar la excepción para que la transacción principal la maneje
        }
    }

    public void actualizarDesarolladordeJuego(String nombreDesarrollador) {
        try {
            String hql = "UPDATE JuegoEquipo je SET je.desarrolladorId = " +
                    "(SELECT d.id FROM Desarrolladores d WHERE d.nombre = :nombreDesarrollador) " +
                    "WHERE je.desarrolladorId IS NOT NULL";
            Query query = session.createQuery(hql);
            query.setParameter("nombreDesarrollador", nombreDesarrollador);
            int filasActualizadas = query.executeUpdate();
            System.out.println(filasActualizadas + " registros actualizados.");
        } catch (Exception e) {
            System.err.println("Error al actualizar relación juego-desarrollador: " + e.getMessage());
            throw e; // Lanzar la excepción para que la transacción principal la maneje
        }
    }

    // Eliminar una relación entre un juego y un desarrollador por su ID
    public void eliminarRelacionJuegoDesarrollador(int id) {
        try {
            JuegoEquipo relacion = session.get(JuegoEquipo.class, id);
            if (relacion != null) {
                session.remove(relacion); // Eliminar relación usando la sesión activa
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar relación juego-desarrollador: " + e.getMessage());
            throw e; // Lanzar la excepción para que la transacción principal la maneje
        }
    }
}
