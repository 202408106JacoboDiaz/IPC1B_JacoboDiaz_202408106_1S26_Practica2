package carrerasescobas.view;

import carrerasescobas.controller.MotorC;
import carrerasescobas.model.ElementoP;
import carrerasescobas.util.Estilos;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelP extends JPanel {
    //Motor del juego (estado de la carrera)
    private MotorC motor;

    public PanelP() {
        setBackground(Estilos.C_FONDO);
        //Tamaño del panel
        setPreferredSize(new Dimension(700, 220));
    }

    public void setMotor(MotorC motor) {
        this.motor = motor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Evitar dibujar si no hay datos
        if (motor == null) return;

        Graphics2D g2 = (Graphics2D) g;
        //Suavizado
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int longitud   = MotorC.LONGITUD_PISTA;
        int margenIzq  = 30;
        int anchoPista = getWidth() - margenIzq - 30;
        //Tamaño de cada posición
        int celda      = anchoPista / (longitud + 1);

        int yJugador = 55;
        int yComp    = 140;
        int alturaPista = 40;

        //Dibujar pista del jugador
        dibujarPista(g2, margenIzq, yJugador, anchoPista, alturaPista,
                motor.getPosJugador(), longitud, Estilos.C_JUGADOR,
                motor.getJugador().getNomb() + " (TU)");

        //Dibujar pista de la computadora
        dibujarPista(g2, margenIzq, yComp, anchoPista, alturaPista,
                motor.getPosComputadora(), longitud, Estilos.C_COMP,
                "CPU: " + motor.getComputadora().getNomb());

        //Dibujar elementos (Snitch, Bludger, Quaffle)
        List<ElementoP> elems = motor.getElementos();
        for (ElementoP el : elems) {
            //Solo activos
            if (!el.isActivo()) continue;
            int x = margenIzq + el.getPosi() * celda + celda / 2;
            dibujarElemento(g2, x, yJugador - 18, el);
            dibujarElemento(g2, x, yComp - 18, el);
        }
        //Leyenda inferior
        dibujarLegend(g2, 10, getHeight() - 20);
    }

    private void dibujarPista(Graphics2D g2, int x, int y, int ancho, int alto,
                              int posActual, int longitud, Color colorCorredor, String nombre) {

        //Fondo de pista
        g2.setColor(Estilos.C_PISTA);
        g2.fillRoundRect(x, y, ancho, alto, 10, 10);

        //Borde de pista
        g2.setColor(colorCorredor.darker());
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(x, y, ancho, alto, 10, 10);

        //Meta
        int celda = ancho / (longitud + 1);
        int xMeta = x + longitud * celda;
        g2.setColor(Estilos.C_META);
        g2.fillRect(xMeta, y, celda, alto);
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, 11));
        g2.drawString("META", xMeta + 2, y + alto / 2 + 5);

        //Posición del corredor
        int cx = x + Math.min(posActual, longitud) * celda + celda / 2;
        int cy = y + alto / 2;
        g2.setColor(colorCorredor);
        g2.fillOval(cx - 14, cy - 14, 28, 28);

        //Inicial del nombre dentro del círculo
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 10));
        String init = String.valueOf(nombre.charAt(0));
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(init, cx - fm.stringWidth(init) / 2, cy + fm.getAscent() / 2 - 1);

        //Nombre del jugador
        g2.setColor(colorCorredor.brighter());
        g2.setFont(Estilos.fteNormal());
        g2.drawString(nombre, x, y - 5);
    }

    private void dibujarElemento(Graphics2D g2, int x, int y, ElementoP el) {
        //Color según tipo de elemento
        Color c = switch (el.getTipo()) {
            case SNITCH  -> new Color(255, 255, 255);
            case BLUDGER -> new Color(200, 30, 30);
            case QUAFFLE -> new Color(150, 150, 150);
        };

        //Dibujo del elemento especifico
        g2.setColor(c);
        g2.fillOval(x - 9, y - 9, 18, 18);

        //Letra representativa del elemento
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, 10));
        g2.drawString(String.valueOf(el.getLetra()), x - 4, y + 4);
    }

    private void dibujarLegend(Graphics2D g2, int x, int y) {
        g2.setFont(new Font("SansSerif", Font.PLAIN, 11));

        //Colores/descripciones de elementos
        int[] colores  = {0xFFFFFF, 0xC81E1E, 0x969696};
        String[] labels = {"S = Snitch (+150 pts, gana)", "B = Bludger (-2s)", "Q = Quaffle (+10 pts)"};

        int cx = x;
        for (int i = 0; i < 3; i++) {
            g2.setColor(new Color(colores[i]));
            g2.fillOval(cx, y - 10, 12, 12);
            g2.setColor(Estilos.C_TEXTO);
            g2.drawString(labels[i], cx + 15, y);
            cx += 200;
        }
    }
}