package carrerasescobas.controller;

import carrerasescobas.model.Personaje;
import java.util.ArrayList;
import java.util.List;

//Controlador gestionar la lista de personajes disponibles (Singleton)
public class GPersonaje {
    private static GPersonaje instancia;
    private List<Personaje> personajes;
    //Contador autoincrementable para asignar códigos únicos
    private int nextCodigo = 1;

    //Cargar los personajes predeterminados al inicio
    private GPersonaje() {
        personajes = new ArrayList<>();
        personajes.add(new Personaje(nextCodigo++, "Harry Potter", "Gryffindor", "Nimbus 2001"));
        personajes.add(new Personaje(nextCodigo++, "Draco Malfoy", "Slytherin", "Nimbus 2000"));
        personajes.add(new Personaje(nextCodigo++, "Luna Lovegood", "Ravenclaw", "Nimbus 2000"));
        personajes.add(new Personaje(nextCodigo++, "Cedric Diggory", "Hufflepuff", "Saeta de Fuego"));
    }

    //Singleton
    public static GPersonaje getInstance() {
        if (instancia == null) instancia = new GPersonaje();
        return instancia;
    }

    //Agregar un personaje nuevo, retornar false si el nombre ya existe
    public boolean agregarPersonaje(String nombre, String casa, String modeloEscoba) {
        for (Personaje p : personajes) {
            if (p.getNomb().equalsIgnoreCase(nombre)) return false;
        }
        personajes.add(new Personaje(nextCodigo++, nombre, casa, modeloEscoba));
        return true;
    }

    //Getter retornar copia de la lista para evitar modificaciones
    public List<Personaje> getPersonajes() {
        return new ArrayList<>(personajes);
    }

    //Seleccionar un personaje aleatorio
    public Personaje getPersonajeAleat(Personaje excluir) {
        List<Personaje> disponibles = new ArrayList<>();
        for (Personaje p : personajes) {
            if (p.getCod() != excluir.getCod()) disponibles.add(p);
        }
        if (disponibles.isEmpty()) return excluir;
        int idx = (int)(Math.random() * disponibles.size()); // Índice aleatorio
        return disponibles.get(idx);
    }

    //Verificar si hay al menos un personaje registrado
    public boolean existePersonaje() {
        return !personajes.isEmpty();
    }
}