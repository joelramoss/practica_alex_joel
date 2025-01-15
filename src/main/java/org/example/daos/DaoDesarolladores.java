package org.example.daos;

import org.example.entidades.Desarrolladores;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class DaoDesarolladores {

    private Session session;

    // Constructor que acepta una sesión activa
    public DaoDesarolladores(Session session) {
        this.session = session;
    }

    //insertar o consultar un desarrollador
    public Desarrolladores crearOObtenerDesarrollador(String nombreDesarrollador) {
        Desarrolladores desarrollador = obtenerPorNombre(nombreDesarrollador);
        if (desarrollador == null) {
            desarrollador = new Desarrolladores();
            desarrollador.setNombre(nombreDesarrollador);
            session.persist(desarrollador);
        }
        return desarrollador;
    }

    // Actualizar un desarrollador
    public void u(Desarrolladores desarrollador) {
        try {
            session.update(desarrollador); // Usar la sesión activa para actualizar el objeto
        } catch (Exception e) {
            System.err.println("Error al actualizar desarrollador: " + e.getMessage());
            throw e; // Lanza la excepción para que la transacción principal la maneje
        }
    }

    // Eliminar un desarrollador por ID
    public void d(int id) {
        try {
            Desarrolladores desarrollador = session.get(Desarrolladores.class, id);
            if (desarrollador != null) {
                session.remove(desarrollador); // Usar la sesión activa para eliminar el objeto
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar desarrollador: " + e.getMessage());
            throw e; // Lanza la excepción para que la transacción principal la maneje
        }
    }
    public List<Desarrolladores> obtenerTodos() {
        Query<Desarrolladores> query = session.createQuery("FROM Desarrolladores", Desarrolladores.class);
        return query.list();
    }

    // Seleccionar un desarrollador por ID
    public Desarrolladores s(int id) {
        try {
            return session.get(Desarrolladores.class, id); // Usar la sesión activa para obtener el objeto
        } catch (Exception e) {
            System.err.println("Error al seleccionar desarrollador: " + e.getMessage());
            throw e; // Lanza la excepción para que la transacción principal la maneje
        }
    }

    public Desarrolladores obtenerPorNombre(String nombreDesarrollador) {
        Query<Desarrolladores> query = session.createQuery("FROM Desarrolladores WHERE nombre = :nombre", Desarrolladores.class);
        query.setParameter("nombre", nombreDesarrollador);
        return query.uniqueResult();
    }

}
