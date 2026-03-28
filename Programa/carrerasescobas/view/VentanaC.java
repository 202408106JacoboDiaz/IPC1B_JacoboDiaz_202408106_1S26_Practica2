package carrerasescobas.view;

import carrerasescobas.controller.MotorC;
import carrerasescobas.model.Personaje;
import carrerasescobas.util.Estilos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VentanaC extends JFrame {
    private final JFrame parent;
    private final Personaje jugador;
    private final Personaje computadora;

    private PanelP panelPista;
    private JLabel lblPuntosJ;
    private JLabel lblPuntosC;
    private JLabel lblPosJ;
    private JLabel lblPosC;
    private JLabel lblEstado;
    private JButton btnVolver;
    private MotorC motor;

    public VentanaC(JFrame parent, Personaje jugador, Personaje computadora) {
        this.parent = parent;
        this.jugador = jugador;
        this.computadora = computadora;
        setTitle("Carrera en Progreso");
        setSize(900, 620);
        setMinimumSize(new Dimension(900, 620));
        setLocationRelativeTo(parent);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        construirUI();
        iniciarCarrera();
    }

    private void construirUI() {
        JPanel main = new JPanel(new BorderLayout(0, 8));
        main.setBackground(Estilos.C_FONDO);
        main.setBorder(new EmptyBorder(15, 15, 15, 15));

        //Tarjetas personajes
        JPanel panelInfo = new JPanel(new GridLayout(1, 2, 20, 0));
        panelInfo.setOpaque(false);
        panelInfo.setPreferredSize(new Dimension(0, 130));
        panelInfo.add(crearTarjetaPersonaje(jugador, Estilos.C_JUGADOR, "TU"));
        panelInfo.add(crearTarjetaPersonaje(computadora, Estilos.C_COMP, "CPU"));

        //Panel pista
        panelPista = new PanelP();

        //Panel Sur
        //Marcador
        JPanel panelMarcador = new JPanel(new GridLayout(1, 2, 20, 0));
        panelMarcador.setOpaque(false);
        panelMarcador.setPreferredSize(new Dimension(0, 70));

        JPanel cardJ = new JPanel(new GridLayout(2, 1));
        cardJ.setBackground(new Color(40, 10, 10));
        cardJ.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Estilos.C_JUGADOR, 1),
                new EmptyBorder(6, 12, 6, 12)));
        lblPuntosJ = Estilos.crearLabel("Puntos: 0", Estilos.fteSubtitulo(), Estilos.C_JUGADOR);
        lblPosJ    = Estilos.crearLabel("Posicion: 0 / " + MotorC.LONGITUD_PISTA,
                Estilos.fteNormal(), Estilos.C_TEXTO);
        cardJ.add(lblPuntosJ);
        cardJ.add(lblPosJ);

        JPanel cardC = new JPanel(new GridLayout(2, 1));
        cardC.setBackground(new Color(10, 20, 40));
        cardC.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Estilos.C_COMP, 1),
                new EmptyBorder(6, 12, 6, 12)));
        lblPuntosC = Estilos.crearLabel("Puntos: 0", Estilos.fteSubtitulo(), Estilos.C_COMP);
        lblPosC    = Estilos.crearLabel("Posicion: 0 / " + MotorC.LONGITUD_PISTA,
                Estilos.fteNormal(), Estilos.C_TEXTO);
        cardC.add(lblPuntosC);
        cardC.add(lblPosC);

        panelMarcador.add(cardJ);
        panelMarcador.add(cardC);

        //Estado
        lblEstado = Estilos.crearLabel("Carrera en progreso...",
                Estilos.fteSubtitulo(), Estilos.C_ACENTO);
        lblEstado.setHorizontalAlignment(SwingConstants.CENTER);

        //Btn volver
        btnVolver = Estilos.crearBtn("Volver al Menu");
        btnVolver.setVisible(false);
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtn.setOpaque(false);
        panelBtn.add(btnVolver);

        JPanel sur = new JPanel();
        sur.setOpaque(false);
        sur.setLayout(new BoxLayout(sur, BoxLayout.Y_AXIS));
        sur.add(panelMarcador);
        sur.add(Box.createVerticalStrut(8));
        sur.add(lblEstado);
        sur.add(panelBtn);

        main.add(panelInfo, BorderLayout.NORTH);
        main.add(panelPista, BorderLayout.CENTER);
        main.add(sur, BorderLayout.SOUTH);

        setContentPane(main);

        btnVolver.addActionListener(e -> {
            parent.setVisible(true);
            dispose();
        });
    }

    private JPanel crearTarjetaPersonaje(Personaje p, Color color, String rol) {
        JPanel card = new JPanel(new GridLayout(4, 1));
        card.setBackground(Estilos.C_PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 2),
                new EmptyBorder(8, 12, 8, 12)));
        card.add(Estilos.crearLabel("[" + rol + "]", new Font("SansSerif", Font.BOLD, 11), color));
        card.add(Estilos.crearLabel(p.getNomb(), Estilos.fteSubtitulo(), Estilos.C_TEXTO));
        card.add(Estilos.crearLabel("Casa: " + p.getCas(), Estilos.fteNormal(), new Color(180, 180, 180)));
        card.add(Estilos.crearLabel("Escoba: " + p.getModeloEsc(), Estilos.fteNormal(), Estilos.C_ACENTO));
        return card;
    }

    private void iniciarCarrera() {
        motor = new MotorC(jugador, computadora, new MotorC.CarreraListener() {
            @Override
            public void onActualizacion() {
                SwingUtilities.invokeLater(() -> {
                    lblPuntosJ.setText("Puntos: " + motor.getPtsJugador());
                    lblPuntosC.setText("Puntos: " + motor.getPtsComputadora());
                    lblPosJ.setText("Posicion: " + motor.getPosJugador() + " / " + MotorC.LONGITUD_PISTA);
                    lblPosC.setText("Posicion: " + motor.getPosComputadora() + " / " + MotorC.LONGITUD_PISTA);
                    panelPista.repaint();
                });
            }

            @Override
            public void onFinCarrera(String ganador, int puntosJ, int puntosC) {
                SwingUtilities.invokeLater(() -> {
                    panelPista.repaint();
                    String msg = "Gano: " + ganador + "  |  Tu: " + puntosJ + " pts  |  CPU: " + puntosC + " pts";
                    lblEstado.setText(msg);
                    lblEstado.setForeground(ganador.equals(jugador.getNomb())
                            ? Estilos.C_JUGADOR : Estilos.C_COMP);
                    btnVolver.setVisible(true);
                });
            }
        });

        panelPista.setMotor(motor);
        motor.iniciar();
    }
}