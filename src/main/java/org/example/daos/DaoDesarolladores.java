package org.example.daos;

import org.example.entidades.Juego;
import org.example.util.HibernateUtil;
import org.hibernate.*;
import org.example.entidades.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoDesarolladores {
    //insert
    public void i(Desarrolladores desarrollador) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Guardar el objeto Desarrolladores
            session.save(desarrollador);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
    //update
    public void u(Desarrolladores desarrollador) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Actualizar el objeto Desarrolladores
            session.update(desarrollador);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
    //delete
    public void d(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Obtener el Desarrollador por ID
            Desarrolladores desarrollador = session.get(Desarrolladores.class, id);
            if (desarrollador != null) {
                // Eliminar el objeto Desarrolladores
                session.delete(desarrollador);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    //select
    public Desarrolladores s(int id) {
        Desarrolladores desarrollador = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Obtener el Desarrollador por ID
            desarrollador = session.get(Desarrolladores.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return desarrollador;
    }



}