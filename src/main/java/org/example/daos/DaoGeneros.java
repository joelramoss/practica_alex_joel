package org.example.daos;

import org.example.entidades.*;
import org.example.util.HibernateUtil;
import org.hibernate.*;
import org.hibernate.query.Query;


public class DaoGeneros {

    //crud tabla generos
    //insert(hibernate javax)
    public int i(Generos genero) {
        Transaction transaction = null;
        int idGenerado = -1;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Guardar el objeto Genero
            session.save(genero);
            idGenerado = genero.getId(); // Obtener el ID generado
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return idGenerado;
    }
    //update(hibernate javax)
    public void u(Generos genero) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Actualizar el objeto Género
            session.update(genero);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
    //delete(hibernate javax)
    public void eliminarGenero(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Obtener el Genero por ID
            Generos genero = session.get(Generos.class, id);
            if (genero != null) {
                // Eliminar el objeto género
                session.delete(genero);
                transaction.commit();
            }

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
    //select(hibernate javax)
    public Generos s(int id) {
        Generos genero = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Obtener el Genero por ID
            genero = session.get(Generos.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return genero;
    }

    // 2 select
    public Generos s2(String nombreGenero) {
        Generos genero = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Buscar el género usando HQL
            Query<Generos> query = session.createQuery("FROM Generos WHERE genero = :nombreGenero", Generos.class);
            query.setParameter("nombreGenero", nombreGenero);
            genero = query.uniqueResult(); // Obtiene el único resultado
        } catch (Exception e) {
            e.printStackTrace();
        }

        return genero;
    }

}