package carrerasescobas.view;

import carrerasescobas.controller.MotorC;
import carrerasescobas.model.ElementoP;
import carrerasescobas.util.Estilos;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelP extends JPanel {
    private MotorC motor;

    public PanelP() {
        setBackground(Estilos.C_FONDO);
        setPreferredSize(new Dimension(700, 320));
        setMinimumSize(new Dimension(600, 320));
    }

    public void setMotor(MotorC motor) {
        this.motor = motor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (motor == null) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int longitud   = MotorC.LONGITUD_PISTA;
        int margenIzq  = 30;
        int margenDer  = 30;
        int anchoPista = getWidth() - margenIzq - margenDer;
        int celda      = anchoPista / (longitud + 1);
        int alturaPista = 36;

        int yJugador = 80;
        int yComp    = 210;

        //Pista jugador con sus propios elementos
        dibujarPista(g2, margenIzq, yJugador, anchoPista, alturaPista,
                motor.getPosJugador(), longitud, celda,
                Estilos.C_JUGADOR,
                motor.getJugador().getNomb() + " (TU)");

        dibujarElementosDePista(g2, margenIzq, yJugador, celda,
                motor.getElementosJugador());

        //Pista CPU con sus propios elementos
        dibujarPista(g2, margenIzq, yComp, anchoPista, alturaPista,
                motor.getPosComputadora(), longitud, celda,
                Estilos.C_COMP,
                "CPU: " + motor.getComputadora().getNomb());

        dibujarElementosDePista(g2, margenIzq, yComp, celda,
                motor.getElementosComputadora());

        //Leyenda
        dibujarLeyenda(g2, margenIzq, getHeight() - 6);
    }

    private void dibujarElementosDePista(Graphics2D g2, int margenIzq,
                                         int yPista, int celda,
                                         List<ElementoP> elementos) {
        for (ElementoP el : elementos) {
            if (!el.isActivo()) continue;
            int xElem = margenIzq + el.getPosi() * celda + celda / 2;
            dibujarElemento(g2, xElem, yPista - 12, el);
        }
    }

    private void dibujarPista(Graphics2D g2, int x, int y, int ancho, int alto,
                              int posActual, int longitud, int celda,
                              Color colorCorredor, String nombre) {
        //Fondo pista
        g2.setColor(Estilos.C_PISTA);
        g2.fillRoundRect(x, y, ancho, alto, 10, 10);
        g2.setColor(colorCorredor.darker());
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(x, y, ancho, alto, 10, 10);

        //META
        int xMeta = x + longitud * celda;
        g2.setColor(Estilos.C_META);
        g2.fillRect(xMeta, y, ancho - longitud * celda, alto);
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, 10));
        g2.drawString("META", xMeta + 3, y + alto / 2 + 4);

        //Nombre sobre la pista
        g2.setColor(colorCorredor.brighter());
        g2.setFont(new Font("SansSerif", Font.BOLD, 13));
        g2.drawString(nombre, x, y - 6);

        //Corredor
        int posLimitada = Math.min(posActual, longitud);
        int cx = x + posLimitada * celda + celda / 2;
        int cy = y + alto / 2;
        g2.setColor(colorCorredor);
        g2.fillOval(cx - 13, cy - 13, 26, 26);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 12));
        String init = String.valueOf(nombre.charAt(0));
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(init, cx - fm.stringWidth(init) / 2, cy + fm.getAscent() / 2 - 1);
    }

    private void dibujarElemento(Graphics2D g2, int x, int y, ElementoP el) {
        Color c = switch (el.getTipo()) {
            case SNITCH  -> Color.WHITE;
            case BLUDGER -> new Color(200, 30, 30);
            case QUAFFLE -> new Color(150, 150, 150);
        };
        g2.setColor(c);
        g2.fillOval(x - 9, y - 9, 18, 18);
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, 10));
        FontMetrics fm = g2.getFontMetrics();
        String letra = String.valueOf(el.getLetra());
        g2.drawString(letra, x - fm.stringWidth(letra) / 2, y + fm.getAscent() / 2 - 1);
    }

    private void dibujarLeyenda(Graphics2D g2, int x, int y) {
        g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
        Color[] colores = {Color.WHITE, new Color(200, 30, 30), new Color(150, 150, 150)};
        String[] labels = {"S = Snitch (+150 pts, gana)", "B = Bludger (-2s)", "Q = Quaffle (+10 pts)"};
        int cx = x;
        for (int i = 0; i < 3; i++) {
            g2.setColor(colores[i]);
            g2.fillOval(cx, y - 10, 12, 12);
            g2.setColor(Estilos.C_TEXTO);
            g2.drawString(labels[i], cx + 16, y);
            cx += 210;
        }
    }
}