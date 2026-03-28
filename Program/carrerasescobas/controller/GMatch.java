package carrerasescobas.controller;

import carrerasescobas.model.ResultadoMatch;
import java.util.ArrayList;
import java.util.List;

//Controlador gestionador del historial (Singleton)
public class GMatch {

    private static GMatch instancia;

    //Lista de resultados
    private List<ResultadoMatch> partidas;

    private GMatch() {
        partidas = new ArrayList<>();
    }

    //Metodo Singleton
    public static GMatch getInstance() {
        if (instancia == null) instancia = new GMatch();
        return instancia;
    }

    //Agregar partida a la lista partidas
    public void registrarPartida(ResultadoMatch resultado) {
        partidas.add(resultado);
    }

    //Getter retornar una copia de la lista de partidas
    public List<ResultadoMatch> getPartidas() {
        return new ArrayList<>(partidas);
    }

    //Retornar el mayor puntaje de cada jugador
    public List<ResultadoMatch> getTopPuntajes() {
        List<ResultadoMatch> top = new ArrayList<>();

        //Recorrer todas las partidas para identificar jugadores unicos
        for (ResultadoMatch r : partidas) {
            boolean encontrado = false;

            //Verificacion de agregacion al top
            for (ResultadoMatch t : top) {
                if (t.getNombJugador().equals(r.getNombJugador())) {
                    encontrado = true;
                    break;
                }
            }

            //No top, buscar mejor partida
            if (!encontrado) {
                int mejorPuntaje = 0;
                ResultadoMatch mejor = null;

                //Recorrer todas las partidas del jugador y guardar la de mayor puntaje
                for (ResultadoMatch p : partidas) {
                    if (p.getNombJugador().equals(r.getNombJugador())) {
                        if (p.getPtsJugador() > mejorPuntaje) {
                            mejorPuntaje = p.getPtsJugador();
                            mejor = p;
                        }
                    }
                }

                // Agrega el mejor resultado del jugador al top
                if (mejor != null) top.add(mejor);
            }
        }

        //Ordenar top de mayor a menor puntaje
        top.sort((a, b) -> b.getPtsJugador() - a.getPtsJugador());
        return top;
    }
}