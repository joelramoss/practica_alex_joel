package org.example.daos;

import org.example.entidades.Juego;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class DaoJuego {

    private static Session session;

    // Constructor que acepta una sesión activa
    public DaoJuego(Session session) {
        this.session = session;
    }

    // Crear un nuevo juego en la base de datos
    public void crearJuego(Juego juego) {
        try {
            session.merge(juego); // Usa merge para manejar entidades existentes o detached
        } catch (Exception e) {
            System.err.println("Error al crear juego: " + e.getMessage());
            throw e; // Propaga la excepción
        }
    }


    // Obtener todos los juegos de la base de datos
    public List<Juego> obtenerTodos() {
        try {
            Query<Juego> query = session.createQuery("FROM Juego", Juego.class);
            return query.list(); // Retorna la lista de juegos
        } catch (Exception e) {
            System.err.println("Error al obtener todos los juegos: " + e.getMessage());
            throw e; // Lanza la excepción para que la transacción principal la maneje
        }
    }

    // Obtener un juego por su ID
    public Juego obtenerPorId(int id) {
        try {
            return session.get(Juego.class, id); // Recuperar el juego por su ID
        } catch (Exception e) {
            System.err.println("Error al obtener juego por ID: " + e.getMessage());
            throw e; // Lanza la excepción para que la transacción principal la maneje
        }
    }

    // Actualizar los datos de un juego en la base de datos
    public void actualizarJuego(Juego juego) {
        try {
            session.update(juego); // Actualizar el juego usando la sesión activa
        } catch (Exception e) {
            System.err.println("Error al actualizar juego: " + e.getMessage());
            throw e; // Lanza la excepción para que la transacción principal la maneje
        }
    }

    // Eliminar un juego de la base de datos por su ID
    public void eliminarJuego(int id) {
        try {
            Juego juego = session.get(Juego.class, id);
            if (juego != null) {
                session.delete(juego); // Eliminar el juego usando la sesión activa
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar juego: " + e.getMessage());
            throw e; // Lanza la excepción para que la transacción principal la maneje
        }
    }

    // Eliminar un juego y sus relaciones en las tablas relacionadas
    public static void eliminarJuegoYRelaciones(int id) {
        try {
            // Eliminar relaciones asociadas al juego usando HQL
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
                session.delete(juego); // Eliminar el juego principal
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar juego y sus relaciones: " + e.getMessage());
            throw e; // Lanza la excepción para que la transacción principal la maneje
        }
    }


    public int obtenerUltimoId() {
        try {
            String hql = "SELECT MAX(j.id) FROM Juego j"; // Usamos MAX para obtener el ID más alto
            Query<Integer> query = session.createQuery(hql, Integer.class);
            Integer ultimoId = query.uniqueResult(); // Obtener el único resultado
            return (ultimoId != null) ? ultimoId : 0; // Si no hay registros, devolver 0
        } catch (Exception e) {
            System.err.println("Error al obtener el último ID: " + e.getMessage());
            throw e; // Lanza la excepción para que la transacción principal la maneje
        }
    }


    // Obtener el título de un juego por su ID
    public String obtenerTituloPorId(int juegoId) {
        try {
            Juego juego = session.get(Juego.class, juegoId);
            if (juego != null) {
                return juego.getTitle();
            }
        } catch (Exception e) {
            System.err.println("Error al obtener título del juego: " + e.getMessage());
            throw e; // Lanza la excepción para que la transacción principal la maneje
        }
        return "Título no encontrado"; // Retornar un mensaje si no se encuentra
    }
}
