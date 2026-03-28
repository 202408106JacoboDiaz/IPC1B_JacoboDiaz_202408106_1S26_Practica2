package carrerasescobas.view;

import carrerasescobas.controller.MotorC;
import carrerasescobas.model.Personaje;
import carrerasescobas.util.Estilos;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VentanaC extends JFrame {
    private final JFrame parent;
    private final Personaje player;
    private final Personaje computadora;

    private PanelP panelPista; private JLabel lblPuntosJ;
    private JLabel lblPuntosC; private JLabel lblPosJ;
    private JLabel lblPosC; private JLabel lblEstado;
    private JButton btnVolver; private MotorC motor;

    public VentanaC(JFrame parent, Personaje jugador, Personaje computadora) {
        this.parent = parent;
        this.player = jugador;
        this.computadora = computadora;
        setTitle("Carrera en Progreso");
        setSize(750, 480);
        //Centrar ventana
        setLocationRelativeTo(parent);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        construirUI();
        //Iniciar lógica del juego
        iniciarCarrera();
    }

    private void construirUI() {
        //Layout principal
        JPanel main = new JPanel(new BorderLayout(0, 10));
        main.setBackground(Estilos.C_FONDO);
        main.setBorder(new EmptyBorder(20, 20, 20, 20));

        //Tarjetas con info de los personajes disponibles
        JPanel panelInfo = new JPanel(new GridLayout(1, 2, 20, 0));
        panelInfo.setOpaque(false);
        panelInfo.add(crearTarjetaPersonaje(player, Estilos.C_JUGADOR, "TU"));
        panelInfo.add(crearTarjetaPersonaje(computadora, Estilos.C_COMP, "CPU"));

        //Panel de pista (dibujo del juego)
        panelPista = new PanelP();

        //Marcador (puntos/posiciones)
        JPanel panelMarcador = new JPanel(new GridLayout(1, 2, 20, 0));
        panelMarcador.setOpaque(false);

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

        //Estado de la carrera
        lblEstado = Estilos.crearLabel("Carrera en progreso...",
                Estilos.fteSubtitulo(), Estilos.C_ACENTO);
        lblEstado.setHorizontalAlignment(SwingConstants.CENTER);

        //Btn volver (al final)
        btnVolver = Estilos.crearBtn("Regresar");
        btnVolver.setVisible(false);

        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtn.setOpaque(false);
        panelBtn.add(btnVolver);

        JPanel sur = new JPanel(new GridLayout(3, 1, 0, 5));
        sur.setOpaque(false);
        sur.add(panelMarcador);
        sur.add(lblEstado);
        sur.add(panelBtn);

        main.add(panelInfo, BorderLayout.NORTH);
        main.add(panelPista, BorderLayout.CENTER);
        main.add(sur, BorderLayout.SOUTH);

        setContentPane(main);

        //Acción volver al menú anterior
        btnVolver.addActionListener(e -> {
            parent.setVisible(true);
            dispose();
        });
    }

    //Crear tarjeta visual de personaje
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

    //Inicializar la lógica de la carrera
    private void iniciarCarrera() {
        motor = new MotorC(player, computadora, new MotorC.CarreraListener() {

            @Override
            public void onActualizacion() {
                //Actualizar UI en cada 'tick' del juego
                SwingUtilities.invokeLater(() -> {
                    lblPuntosJ.setText("Puntos: " + motor.getPtsJugador());
                    lblPuntosC.setText("Puntos: " + motor.getPtsComputadora());
                    lblPosJ.setText("Posicion: " + motor.getPosJugador() + " / " + MotorC.LONGITUD_PISTA);
                    lblPosC.setText("Posicion: " + motor.getPosComputadora() + " / " + MotorC.LONGITUD_PISTA);
                    panelPista.repaint(); // Redibuja pista
                });
            }

            @Override
            public void onFinCarrera(String ganador, int puntosJ, int puntosC) {
                //Mostrar resultado final
                SwingUtilities.invokeLater(() -> {
                    panelPista.repaint();

                    String msg = "Gano: " + ganador + "  |  Tu: " + puntosJ + " pts  |  CPU: " + puntosC + " pts";
                    lblEstado.setText(msg);

                    //Color según ganador
                    lblEstado.setForeground(ganador.equals(player.getNomb())
                            ? Estilos.C_JUGADOR : Estilos.C_COMP);

                    //Habilitar regreso
                    btnVolver.setVisible(true);
                });
            }
        });

        panelPista.setMotor(motor);
        //Arrancar la carrera
        motor.iniciar();
    }
}