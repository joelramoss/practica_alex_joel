package org.example.daos;

import org.example.entidades.DetallesJuego;
import org.hibernate.Session;

public class DaoDetallesJuego {

    private Session session;

    // Constructor que acepta una sesión activa
    public DaoDetallesJuego(Session session) {
        this.session = session;
    }

    // Insertar un objeto DetallesJuego
    public void i(DetallesJuego detallesJuego) {
        try {
            session.persist(detallesJuego); // Usar la sesión activa para guardar el objeto
        } catch (Exception e) {
            System.err.println("Error al insertar DetallesJuego: " + e.getMessage());
            throw e; // Lanza la excepción para que la transacción principal la maneje
        }
    }

    // Actualizar un objeto DetallesJuego
    public void u(DetallesJuego detallesJuego) {
        try {
            session.update(detallesJuego); // Usar la sesión activa para actualizar el objeto
        } catch (Exception e) {
            System.err.println("Error al actualizar DetallesJuego: " + e.getMessage());
            throw e; // Lanza la excepción para que la transacción principal la maneje
        }
    }

    // Eliminar un objeto DetallesJuego por ID
    public void d(int juegoId) {
        try {
            DetallesJuego detallesJuego = session.get(DetallesJuego.class, juegoId);
            if (detallesJuego != null) {
                session.remove(detallesJuego); // Usar la sesión activa para eliminar el objeto
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar DetallesJuego: " + e.getMessage());
            throw e; // Lanza la excepción para que la transacción principal la maneje
        }
    }

    // Seleccionar un objeto DetallesJuego por ID
    public DetallesJuego s(int juegoId) {
        try {
            return session.get(DetallesJuego.class, juegoId); // Usar la sesión activa para obtener el objeto
        } catch (Exception e) {
            System.err.println("Error al seleccionar DetallesJuego: " + e.getMessage());
            throw e; // Lanza la excepción para que la transacción principal la maneje
        }
    }
}
