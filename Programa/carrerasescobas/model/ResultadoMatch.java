package carrerasescobas.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//Almacenar el resultado de una partida jugada
public class ResultadoMatch {
    private String nombJugador;
    private String nombComputadora;
    private String escoba;
    private int ptsJugador;
    private int ptsComputadora;
    private String winner;
    private String fecha;

    //Constructor que registra los datos de la partida y genera la fecha en auto
    public ResultadoMatch(String nombreJugador, String nombreComputadora,
                          String escoba, int puntosJugador, int puntosComputadora,
                          String ganador) {
        this.nombJugador = nombreJugador;
        this.nombComputadora = nombreComputadora;
        this.escoba = escoba;
        this.ptsJugador = puntosJugador;
        this.ptsComputadora = puntosComputadora;
        this.winner = ganador;
        //Capturar fecha y hora exacta en que termino la partida
        this.fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    //Getters
    public String getNombJugador() { return nombJugador; }
    public String getNombComputadora() { return nombComputadora; }
    public String getEscoba() { return escoba; }
    public int getPtsJugador() { return ptsJugador; }
    public int getPtsComputadora() { return ptsComputadora; }
    public String getWinner() { return winner; }
    public String getFecha() { return fecha; }
}