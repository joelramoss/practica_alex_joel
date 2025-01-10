package org.example.daos;

import org.example.entidades.Juego;
import org.example.util.HibernateUtil;
import org.hibernate.*;
import org.hibernate.query.Query;

import java.util.List;

public class DaoJuego {

    // Crear un nuevo juego en la base de datos
    public void crearJuego(Juego juego) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.save(juego); // Guardamos el juego, el ID se genera automáticamente
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Obtener todos los juegos de la base de datos
    public List<Juego> obtenerTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Juego> query = session.createQuery("FROM Juego", Juego.class);
            return query.list(); // Retorna la lista de juegos
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Obtener un juego por su ID
    public Juego obtenerPorId(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Juego.class, id); // Recuperamos el juego por su ID
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Actualizar los datos de un juego en la base de datos
    public void actualizarJuego(Juego juego) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.update(juego); // Actualizamos el juego en la base de datos
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Eliminar un juego de la base de datos por su ID
    public void eliminarJuego(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Juego juego = session.get(Juego.class, id); // Obtenemos el juego por ID
            if (juego != null) {
                session.delete(juego); // Eliminamos el juego
            }
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Eliminar un juego y sus relaciones en las tablas relacionadas
    public static void eliminarJuegoYRelaciones(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Eliminar relaciones asociadas al juego, usando HQL o Criteria
            String hqlRating = "DELETE FROM Rating WHERE juegoId = :id";
            session.createQuery(hqlRating).setParameter("id", id).executeUpdate();

            String hqlGeneros = "DELETE FROM JuegosGenerados WHERE juegoId = :id";
            session.createQuery(hqlGeneros).setParameter("id", id).executeUpdate();

            String hqlJuegoEquipo = "DELETE FROM JuegoEquipo WHERE juegoId = :id";
            session.createQuery(hqlJuegoEquipo).setParameter("id", id).executeUpdate();

            String hqlDetallesJuego = "DELETE FROM DetallesJuego WHERE juegoId = :id";
            session.createQuery(hqlDetallesJuego).setParameter("id", id).executeUpdate();

            // Ahora eliminar el juego principal
            Juego juego = session.get(Juego.class, id);
            if (juego != null) {
                session.delete(juego); // Eliminar el juego
            }

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Obtener el título de un juego por su ID
    public String obtenerTituloPorId(int juegoId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Juego juego = session.get(Juego.class, juegoId);
            if (juego != null) {
                return juego.getTitle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Título no encontrado"; // Retornar un mensaje si no se encuentra
    }
}

