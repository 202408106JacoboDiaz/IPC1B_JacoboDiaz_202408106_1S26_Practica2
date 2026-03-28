package carrerasescobas.controller;

import carrerasescobas.model.ElementoP;
import carrerasescobas.model.Personaje;
import carrerasescobas.model.ResultadoMatch;
import java.util.ArrayList;
import java.util.List;

public class MotorC {
    public static final int LONGITUD_PISTA = 30;
    private static final double PROB_SNITCH  = 0.03;
    private static final double PROB_BLUDGER = 0.08;
    private static final double PROB_QUAFFLE = 0.1;

    private final Personaje jugador;
    private final Personaje computadora;

    private int posJugador = 0;
    private int posComputadora = 0;
    private int ptsJugador = 0;
    private int ptsComputadora = 0;

    //Penalizaciones separadas para cada corredor
    private int penalJugador = 0;
    private int penalComputadora = 0;

    private volatile boolean carreraFinish = false;
    private String winner = "";

    //Listas de elementos SEPARADAS para cada corredor
    private List<ElementoP> elementosJugador;
    private List<ElementoP> elementosComputadora;

    private CarreraListener listener;

    public interface CarreraListener {
        void onActualizacion();
        void onFinCarrera(String ganador, int puntosJ, int puntosC);
    }

    public MotorC(Personaje jugador, Personaje computadora, CarreraListener listener) {
        this.jugador = jugador;
        this.computadora = computadora;
        this.listener = listener;
        //Cada corredor tiene su propia copia de elementos
        this.elementosJugador     = generarElementos();
        this.elementosComputadora = generarElementos();
    }

    private List<ElementoP> generarElementos() {
        List<ElementoP> lista = new ArrayList<>();

        for (int i = 1; i < LONGITUD_PISTA; i++) {
            double r = Math.random();
            if (r < PROB_SNITCH) {
                // Solo se genera una Snitch máximo
                lista.add(new ElementoP(ElementoP.Tipo.SNITCH, i));
                break; // Solo una Snitch por carrera
            } else if (r < PROB_SNITCH + PROB_BLUDGER) {
                lista.add(new ElementoP(ElementoP.Tipo.BLUDGER, i));
            } else if (r < PROB_SNITCH + PROB_BLUDGER + PROB_QUAFFLE) {
                lista.add(new ElementoP(ElementoP.Tipo.QUAFFLE, i));
            }
        }
        return lista;
    }

    public void iniciar() {
        Thread hiloJugador = new Thread(() -> correr(true));
        Thread hiloComputadora = new Thread(() -> correr(false));
        hiloJugador.setDaemon(true);
        hiloComputadora.setDaemon(true);
        hiloJugador.start();
        hiloComputadora.start();
    }

    private void correr(boolean esJugador) {
        Personaje corredor = esJugador ? jugador : computadora;

        while (!carreraFinish) {
            //Penalización propia de cada corredor
            int penal = esJugador ? penalJugador : penalComputadora;

            try {
                Thread.sleep(corredor.getVelocidadMs() + penal);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            //Resetear penalización después de dormir
            if (esJugador) penalJugador = 0;
            else penalComputadora = 0;

            if (carreraFinish) break;

            synchronized (this) {
                //Avanzar posición
                if (esJugador) posJugador = Math.min(posJugador + 1, LONGITUD_PISTA);
                else posComputadora = Math.min(posComputadora + 1, LONGITUD_PISTA);

                int posActual = esJugador ? posJugador : posComputadora;

                //Revisar elementos del corredor correspondiente
                List<ElementoP> misElementos = esJugador ? elementosJugador : elementosComputadora;

                for (ElementoP el : misElementos) {
                    if (el.isActivo() && el.getPosi() == posActual) {
                        el.desactivar();

                        //Sumar puntos al corredor
                        if (esJugador) ptsJugador += el.getPts();
                        else ptsComputadora += el.getPts();

                        //Aplicar penalización al corredor
                        if (esJugador) penalJugador = el.getPenalMs();
                        else penalComputadora = el.getPenalMs();

                        //Snitch terminar la carrera
                        if (el.getTipo() == ElementoP.Tipo.SNITCH) {
                            terminarCarrera(esJugador ? jugador.getNomb() : computadora.getNomb());
                            break;
                        }
                    }
                }

                //Verificar llegada a meta
                if (!carreraFinish && posActual >= LONGITUD_PISTA) {
                    terminarCarrera(esJugador ? jugador.getNomb() : computadora.getNomb());
                }
            }

            listener.onActualizacion();
        }
    }

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
    public List<ElementoP> getElementosJugador() { return elementosJugador; }
    public List<ElementoP> getElementosComputadora() { return elementosComputadora; }
    public int getPosJugador() { return posJugador; }
    public int getPosComputadora() { return posComputadora; }
    public int getPtsJugador() { return ptsJugador; }
    public int getPtsComputadora() { return ptsComputadora; }
    public Personaje getJugador() { return jugador; }
    public Personaje getComputadora() { return computadora; }
    public boolean isCarreraFinish() { return carreraFinish; }
}