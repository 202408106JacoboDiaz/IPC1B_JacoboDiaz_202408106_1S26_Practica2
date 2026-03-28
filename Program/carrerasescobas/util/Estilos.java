package carrerasescobas.util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Estilos {

    //Colores principales de la interfaz
    public static final Color C_FONDO       = new Color(15, 15, 15);
    public static final Color C_PANEL       = new Color(30, 30, 30);
    public static final Color C_ACENTO      = new Color(200, 30, 30);
    public static final Color C_TEXTO       = new Color(240, 240, 240);

    //Colores específicos del juego
    public static final Color C_JUGADOR     = new Color(220, 40, 40);
    public static final Color C_COMP        = new Color(180, 180, 180);
    public static final Color C_PISTA       = new Color(45, 45, 45);
    public static final Color C_META        = new Color(200, 30, 30);
    public static final Color C_BTN_HOVER   = new Color(230, 60, 60);

    //Métodos para obtener fuentes reutilizables
    public static Font fteTitulo()    { return new Font("Serif", Font.BOLD, 26); }
    public static Font fteSubtitulo() { return new Font("Serif", Font.BOLD, 16); }
    public static Font fteNormal()    { return new Font("SansSerif", Font.PLAIN, 14); }
    public static Font fteBoton()     { return new Font("SansSerif", Font.BOLD, 14); }

    //Crear un btn con estilo personalizado
    public static JButton crearBtn(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(fteBoton());
        btn.setBackground(C_ACENTO);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(10, 24, 10, 24));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);

        //Efecto hover -cambio de color al pasar el mouse-
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(C_BTN_HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(C_ACENTO);
            }
        });

        return btn;
    }

    //Crear un label con estilo configurable
    public static JLabel crearLabel(String texto, Font fuente, Color color) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(fuente);
        lbl.setForeground(color);
        return lbl;
    }

    //Aplicar fondo oscuro
    public static void aplicarOscuridad(JPanel panel) {
        panel.setBackground(C_FONDO);
    }
}