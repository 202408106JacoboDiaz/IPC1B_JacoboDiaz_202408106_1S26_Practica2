package carrerasescobas.view;

import carrerasescobas.util.Estilos;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Carrera de Escobas - Harry Potter");
        //Cierrar toda la app
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        //Centrar ventana
        setLocationRelativeTo(null);
        setResizable(false);
        construirUI();
    }

    private void construirUI() {
        //Layout principal
        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(Estilos.C_FONDO);
        fondo.setBorder(new EmptyBorder(40, 60, 40, 60));

        //Panel de título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setOpaque(false);
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));

        JLabel titulo = Estilos.crearLabel("CARRERA DE ESCOBAS",
                Estilos.fteTitulo(), Estilos.C_ACENTO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = Estilos.crearLabel("— Una Experiencia de Harry Potter —",
                Estilos.fteNormal(), new Color(180, 180, 180));
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelTitulo.add(titulo);
        panelTitulo.add(Box.createVerticalStrut(8));
        panelTitulo.add(subtitulo);

        //Panel de btns principales
        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setLayout(new GridLayout(4, 1, 0, 16));

        JButton btnJugar   = Estilos.crearBtn("Play");
        JButton btnCrear   = Estilos.crearBtn("Crear Personaje");
        JButton btnTop     = Estilos.crearBtn("Top Puntajes");
        JButton btnSalir   = Estilos.crearBtn("Exit");

        //Estilo especial btn salir
        btnSalir.setBackground(new Color(60, 10, 10));
        btnSalir.setForeground(Color.WHITE);

        panelBotones.add(btnJugar);
        panelBotones.add(btnCrear);
        panelBotones.add(btnTop);
        panelBotones.add(btnSalir);

        fondo.add(panelTitulo, BorderLayout.NORTH);
        fondo.add(Box.createVerticalStrut(30));
        fondo.add(panelBotones, BorderLayout.CENTER);

        setContentPane(fondo);

        //Acciones de navegación
        btnJugar.addActionListener(e -> abrirEleccionPersonajes());
        btnCrear.addActionListener(e -> abrirCrearPersonaje());
        btnTop.addActionListener(e -> abrirTopPuntajes());
        btnSalir.addActionListener(e -> System.exit(0)); // Salir del programa
    }

    //Abrir selección de personajes
    private void abrirEleccionPersonajes() {
        new EleccionPer(this).setVisible(true);
        setVisible(false);
    }

    //Abrir ventana de creación de personaje
    private void abrirCrearPersonaje() {
        new CreacionPer(this).setVisible(true);
    }

    //Abrir ventana de puntajes
    private void abrirTopPuntajes() {
        new PuntajesTOP(this).setVisible(true);
    }
}