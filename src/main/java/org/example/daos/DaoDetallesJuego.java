package org.example.daos;

import org.example.entidades.*;
import org.example.util.HibernateUtil;
import org.hibernate.*;


public class DaoDetallesJuego {

    //crud tabla detallesjuego
    //insert(hibernate javax)
    public void i(DetallesJuego detallesJuego) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Guardar el objeto DetallesJuego
            session.save(detallesJuego);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
    //update(hibernate javax)
    public void u(DetallesJuego detallesJuego) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Actualizar el objeto DetallesJuego
            session.update(detallesJuego);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
    //delete(hibernate javax)
    public void d(int juegoId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Obtener el DetallesJuego por juegoId
            DetallesJuego detallesJuego = session.get(DetallesJuego.class, juegoId);
            if (detallesJuego != null) {
                // Eliminar el objeto DetallesJuego
                session.delete(detallesJuego);
                transaction.commit();
            }

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
    //select(hibernate javax)
    public DetallesJuego s(int juegoId) {
        DetallesJuego detallesJuego = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Obtener el DetallesJuego por juegoId
            detallesJuego = session.get(DetallesJuego.class, juegoId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return detallesJuego;
    }

}