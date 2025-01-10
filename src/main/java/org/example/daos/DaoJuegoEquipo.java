package org.example.daos;

import org.example.entidades.Juego_equipo;
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

            Juego_equipo relacion = new Juego_equipo(); // Creamos el objeto sin asignar id (será autogenerado)
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
    public Juego_equipo leerJuegoEquipo(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Juego_equipo.class, id); // Obtener relación por ID
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Obtener todas las relaciones entre juegos y desarrolladores
    public List<Juego_equipo> obtenerTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Juego_equipo> query = session.createQuery("FROM Juego_equipo", Juego_equipo.class);
            return query.list(); // Retornar todas las relaciones
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Obtener todas las relaciones entre juegos y desarrolladores por el nombre del desarrollador
    public List<Juego_equipo> obtenerPorNombreDesarrollador(String nombreDesarrollador) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT je FROM Juego_equipo je " +
                    "JOIN Desarrollador d ON je.desarrolladorId = d.id " +
                    "WHERE d.nombre = :nombreDesarrollador";
            Query<Juego_equipo> query = session.createQuery(hql, Juego_equipo.class);
            query.setParameter("nombreDesarrollador", nombreDesarrollador);
            return query.list(); // Retornar relaciones filtradas por el nombre del desarrollador
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Actualizar una relación entre un juego y un desarrollador
    public void actualizarRelacionJuegoDesarrollador(Juego_equipo relacion) {
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

            Juego_equipo relacion = session.get(Juego_equipo.class, id); // Obtener relación por ID
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