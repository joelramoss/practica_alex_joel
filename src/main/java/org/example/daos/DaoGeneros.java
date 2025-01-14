package org.example.daos;

import org.example.entidades.Generos;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class DaoGeneros {

    private final Session session;

    // Constructor que acepta una sesión activa
    public DaoGeneros(Session session) {
        this.session = session;
    }

    // Insertar un género
    public int i(Generos genero) {
        try {
            session.persist(genero); // Usar la sesión activa para guardar el objeto
            return genero.getId(); // Obtener el ID generado
        } catch (Exception e) {
            System.err.println("Error al insertar género: " + e.getMessage());
            throw e; // Lanza la excepción para que la transacción principal la maneje
        }
    }

    // Actualizar un género
    public void u(Generos genero) {
        try {
            session.update(genero); // Usar la sesión activa para actualizar el objeto
        } catch (Exception e) {
            System.err.println("Error al actualizar género: " + e.getMessage());
            throw e; // Lanza la excepción para que la transacción principal la maneje
        }
    }

    // Eliminar un género por ID
    public void eliminarGenero(int id) {
        try {
            Generos genero = session.get(Generos.class, id);
            if (genero != null) {
                session.delete(genero); // Usar la sesión activa para eliminar el objeto
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar género: " + e.getMessage());
            throw e; // Lanza la excepción para que la transacción principal la maneje
        }
    }

    // Seleccionar un género por ID
    public Generos s(int id) {
        try {
            return session.get(Generos.class, id); // Usar la sesión activa para obtener el objeto
        } catch (Exception e) {
            System.err.println("Error al seleccionar género: " + e.getMessage());
            throw e; // Lanza la excepción para que la transacción principal la maneje
        }
    }

    // Seleccionar un género por nombre
    public Generos s2(String nombreGenero) {
        try {
            // Usa HQL para buscar el género con la propiedad correcta
            Query<Generos> query = session.createQuery("FROM Generos WHERE genero = :nombreGenero", Generos.class);
            query.setParameter("nombreGenero", nombreGenero);
            return query.uniqueResult(); // Devuelve el género si existe
        } catch (Exception e) {
            System.err.println("Error al seleccionar género por nombre: " + e.getMessage());
            throw e; // Lanza la excepción para que la transacción principal la maneje
        }
    }

    // Crear o obtener un género por nombre
    public Generos crearOObtenerGenero(String nombreGenero) {
        try {
            Generos genero = s2(nombreGenero); // Busca el género por nombre
            if (genero == null) {
                genero = new Generos();
                genero.setGenero(nombreGenero); // Asegúrate de usar el nombre correcto del setter
                session.persist(genero); // Inserta el nuevo género
            }
            return genero;
        } catch (Exception e) {
            System.err.println("Error al crear u obtener género: " + e.getMessage());
            throw e; // Lanza la excepción para que la transacción principal la maneje
        }
    }
}
