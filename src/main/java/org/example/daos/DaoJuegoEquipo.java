package org.example.daos;

import org.example.entidades.JuegoEquipo;

import org.example.util.HibernateUtil;
import org.hibernate.*;
import org.hibernate.query.Query;
import java.util.List;

public class DaoJuegoEquipo {

    // Crear una nueva relación entre un juego y un desarrollador
    public void crearRelacionJuegoDesarrollador(int juegoId, int desarrolladorId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            JuegoEquipo relacion = new JuegoEquipo(); // Creamos el objeto sin asignar id (será autogenerado)
            relacion.setJuegoId(juegoId);
            relacion.setDesarrolladorId(desarrolladorId);
            session.save(relacion); // Guardamos la nueva relación
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Leer una relación entre un juego y un desarrollador por su ID
    public JuegoEquipo leerJuegoEquipo(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(JuegoEquipo.class, id); // Obtener relación por ID
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Obtener todas las relaciones entre juegos y desarrolladores
    public List<JuegoEquipo> obtenerTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<JuegoEquipo> query = session.createQuery("FROM Juego_equipo", JuegoEquipo.class);
            return query.list(); // Retornar todas las relaciones
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Obtener todas las relaciones entre juegos y desarrolladores por el nombre del desarrollador
    public List<JuegoEquipo> obtenerPorNombreDesarrollador(String nombreDesarrollador) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT je FROM Juego_equipo je " +
                    "JOIN Desarrollador d ON je.desarrolladorId = d.id " +
                    "WHERE d.nombre = :nombreDesarrollador";
            Query<JuegoEquipo> query = session.createQuery(hql, JuegoEquipo.class);
            query.setParameter("nombreDesarrollador", nombreDesarrollador);
            return query.list(); // Retornar relaciones filtradas por el nombre del desarrollador
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Actualizar una relación entre un juego y un desarrollador
    public void actualizarRelacionJuegoDesarrollador(JuegoEquipo relacion) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.update(relacion); // Actualizar la relación
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Eliminar una relación entre un juego y un desarrollador por su ID
    public void eliminarRelacionJuegoDesarrollador(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            JuegoEquipo relacion = session.get(JuegoEquipo.class, id); // Obtener relación por ID
            if (relacion != null) {
                session.delete(relacion); // Eliminar relación
            }
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}