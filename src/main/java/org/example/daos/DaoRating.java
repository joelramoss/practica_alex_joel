package org.example.daos;

import org.example.entidades.*;
import org.example.util.HibernateUtil;
import org.hibernate.*;
import org.hibernate.query.Query;
import java.util.List;

public class DaoRating {

    // Crear un nuevo rating para un juego
    public void crearRating(Rating rating) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(rating); // Guardar el objeto Rating
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Leer un rating para un juego dado su juegoId
    public Rating leerRating(int juegoId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Rating.class, juegoId); // Obtener rating por juegoId
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Actualizar un rating existente para un juego
    public void actualizarRating(Rating rating) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(rating); // Actualizar el objeto Rating
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Eliminar un rating para un juego dado su juegoId
    public void eliminarRating(int juegoId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Rating rating = session.get(Rating.class, juegoId); // Obtener el Rating por juegoId
            if (rating != null) {
                session.delete(rating); // Eliminar el rating
            }
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}