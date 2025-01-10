package org.example.daos;


import org.example.entidades.*;
import org.example.util.HibernateUtil;
import org.hibernate.*;
import org.hibernate.query.Query;
import java.util.List;

public class DaoJuegosGenerados {

    // Crear una nueva relación entre un juego y un género
    public static void crearRelacionJuegoGenero(int juegoId, String genero) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            JuegosGenerados relacion = new JuegosGenerados(); // Creamos el objeto sin asignar id (será autogenerado)
            relacion.setJuegoId(juegoId);
            relacion.setGeneros(genero); // Asignamos un nombre de género
            session.save(relacion); // Guardamos la nueva relación
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }


    // Leer una relación entre un juego y un género por el ID del juego
    public JuegosGenerados leerJuegosGenerados(int juegoId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(JuegosGenerados.class, juegoId); // Obtener relación por ID del juego
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Obtener todas las relaciones entre juegos y géneros
    public List<JuegosGenerados> obtenerTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<JuegosGenerados> query = session.createQuery("FROM JuegosGenerados", JuegosGenerados.class);
            return query.list(); // Retornar todas las relaciones
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Obtener todas las relaciones entre juegos y géneros por el nombre del género
    public List<JuegosGenerados> obtenerPorNombreGenero(String nombreGenero) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT jg FROM JuegosGenerados jg " +
                    "JOIN jg.genero g " +
                    "WHERE g.genero = :nombreGenero";
            Query<JuegosGenerados> query = session.createQuery(hql, JuegosGenerados.class);
            query.setParameter("nombreGenero", nombreGenero);
            return query.list(); // Retornar relaciones filtradas por el nombre del género
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Actualizar una relación entre un juego y un género
    public void actualizarRelacionJuegoGenero(JuegosGenerados relacion) {
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

    // Eliminar una relación entre un juego y un género por su ID
    public void eliminarRelacionJuegoGenero(int juegoId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            JuegosGenerados relacion = session.get(JuegosGenerados.class, juegoId); // Obtener relación por ID
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