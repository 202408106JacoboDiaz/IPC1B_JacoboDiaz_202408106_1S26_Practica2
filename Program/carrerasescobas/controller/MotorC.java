package carrerasescobas.controller;

import carrerasescobas.model.ElementoP;
import carrerasescobas.model.Personaje;
import carrerasescobas.model.ResultadoMatch;
import java.util.ArrayList;
import java.util.List;

//Controlador principal de la carrera (manejar la lógica y hilos de movimiento)
public class MotorC {
    public static final int LONGITUD_PISTA = 30;

    //Probabilidades de aparición de cada elemento en la pista
    private static final double PROB_SNITCH  = 0.04;
    private static final double PROB_BLUDGER = 0.12;
    private static final double PROB_QUAFFLE = 0.15;

    private final Personaje jugador;
    private final Personaje computadora;

    private int posJugador = 0;
    private int posComputadora = 0;
    private int ptsJugador = 0;
    private int ptsComputadora = 0;

    //Garantizar que ambos hilos reciban el valor actualizado
    private volatile boolean carreraFinish = false;
    private String winner = "";

    private List<ElementoP> elementos;
    private CarreraListener listener;

    //Interfaz para notificar a la vista cuando existen actualizaciones
    public interface CarreraListener {
        void onActualizacion();
        void onFinCarrera(String ganador, int puntosJ, int puntosC);
    }

    public MotorC(Personaje jugador, Personaje computadora, CarreraListener listener) {
        this.jugador = jugador;
        this.computadora = computadora;
        this.listener = listener;
        this.elementos = generarElementos();
    }

    //Generar aleatoriamente los elementos en la pista
    private List<ElementoP> generarElementos() {
        List<ElementoP> lista = new ArrayList<>();
        int posSnitch = 5 + (int)(Math.random() * (LONGITUD_PISTA - 10));
        lista.add(new ElementoP(ElementoP.Tipo.SNITCH, posSnitch));

        for (int i = 1; i < LONGITUD_PISTA; i++) {
            if (i == posSnitch) continue;
            double r = Math.random();
            if (r < PROB_BLUDGER) {
                lista.add(new ElementoP(ElementoP.Tipo.BLUDGER, i));
            } else if (r < PROB_BLUDGER + PROB_QUAFFLE) {
                lista.add(new ElementoP(ElementoP.Tipo.QUAFFLE, i));
            }
        }
        return lista;
    }

    //Crear e iniciar un hilo independiente para cada jugador
    public void iniciar() {
        Thread hiloJugador = new Thread(() -> correr(true));
        Thread hiloComputadora = new Thread(() -> correr(false));
        hiloJugador.setDaemon(true);
        hiloComputadora.setDaemon(true);
        hiloJugador.start();
        hiloComputadora.start();
    }

    //Lógica de movimiento de cada corredor
    private void correr(boolean esJugador) {
        Personaje corredor = esJugador ? jugador : computadora;
        int penalizacion = 0;

        while (!carreraFinish) {
            try {
                // Pausa según velocidad de la escoba + penalización acumulada
                Thread.sleep(corredor.getVelocidadMs() + penalizacion);
                penalizacion = 0;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            if (carreraFinish) break;

            //Bloque sincronizado para evitar condiciones de carrera entre hilos
            synchronized (this) {
                if (esJugador) posJugador = Math.min(posJugador + 1, LONGITUD_PISTA);
                else posComputadora = Math.min(posComputadora + 1, LONGITUD_PISTA);

                int posActual = esJugador ? posJugador : posComputadora;

                for (ElementoP el : elementos) {
                    if (el.isActivo() && el.getPosi() == posActual) {
                        el.desactivar();
                        if (esJugador) ptsJugador += el.getPts();
                        else ptsComputadora += el.getPts();
                        penalizacion = el.getPenalMs();

                        // La Snitch termina la carrera inmediatamente
                        if (el.getTipo() == ElementoP.Tipo.SNITCH) {
                            terminarCarrera(esJugador ? jugador.getNomb() : computadora.getNomb());
                            break;
                        }
                    }
                }

                if (!carreraFinish && posActual >= LONGITUD_PISTA) {
                    terminarCarrera(esJugador ? jugador.getNomb() : computadora.getNomb());
                }
            }

            listener.onActualizacion();
        }
    }

    //Finalizar la carrera, registrar el resultado y notificar a la vista
    private void terminarCarrera(String nombGanador) {
        if (!carreraFinish) {
            carreraFinish = true;
            winner = nombGanador;
            ResultadoMatch resultado = new ResultadoMatch(
                    jugador.getNomb(),
                    computadora.getNomb(),
                    jugador.getModeloEsc(),
                    ptsJugador,
                    ptsComputadora,
                    winner
            );
            GMatch.getInstance().registrarPartida(resultado);
            listener.onFinCarrera(winner, ptsJugador, ptsComputadora);
        }
    }

    //Getters
    public int getPosJugador() { return posJugador; }
    public int getPosComputadora() { return posComputadora; }
    public int getPtsJugador() { return ptsJugador; }
    public int getPtsComputadora() { return ptsComputadora; }
    public List<ElementoP> getElementos() { return elementos; }
    public Personaje getJugador() { return jugador; }
    public Personaje getComputadora() { return computadora; }
    public boolean isCarreraFinish() { return carreraFinish; }
}