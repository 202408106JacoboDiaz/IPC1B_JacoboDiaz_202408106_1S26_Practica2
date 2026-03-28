package carrerasescobas.view;

import carrerasescobas.controller.GPersonaje;
import carrerasescobas.model.Personaje;
import carrerasescobas.util.Estilos;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class EleccionPer extends JFrame {
    private final JFrame parent;
    private JList<String> listaPersonajes;
    private List<Personaje> personajes;

    public EleccionPer(JFrame parent) {
        this.parent = parent;
        setTitle("Elige tu Personaje");
        setSize(500, 480);
        //Centrar ventana
        setLocationRelativeTo(parent);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //Volver a mostrar la ventana padre, cuando se cierra la actual
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent e) {
                parent.setVisible(true);
            }
        });

        construirUI();
    }

    private void construirUI() {
        //Layout principal
        JPanel panel = new JPanel(new BorderLayout(0, 16));
        panel.setBackground(Estilos.C_FONDO);
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));

        //Título
        JLabel titulo = Estilos.crearLabel("Elige tu Personaje",
                Estilos.fteSubtitulo(), Estilos.C_ACENTO);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titulo, BorderLayout.NORTH);

        //Obtener personajes del controlador
        personajes = GPersonaje.getInstance().getPersonajes();
        String[] nombres = personajes.stream().map(Personaje::toString).toArray(String[]::new);

        //Lista de selección
        listaPersonajes = new JList<>(nombres);
        listaPersonajes.setFont(Estilos.fteNormal());
        listaPersonajes.setBackground(Estilos.C_PANEL);
        listaPersonajes.setForeground(Estilos.C_TEXTO);
        listaPersonajes.setSelectionBackground(Estilos.C_ACENTO);
        listaPersonajes.setSelectionForeground(Color.WHITE);
        listaPersonajes.setFixedCellHeight(34);
        //Solo uno
        listaPersonajes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(listaPersonajes);
        scroll.setBorder(BorderFactory.createLineBorder(Estilos.C_ACENTO));
        panel.add(scroll, BorderLayout.CENTER);

        //Info del oponente
        JLabel infoOp = Estilos.crearLabel(
                "<html><center>La computadora elegirá un oponente<br>aleatorio de la lista.</center></html>",
                new Font("SansSerif", Font.ITALIC, 12), new Color(180, 180, 180));
        infoOp.setHorizontalAlignment(SwingConstants.CENTER);

        //Btns
        JPanel panelBtns = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panelBtns.setOpaque(false);

        JButton btnJugar  = Estilos.crearBtn("Iniciar Carrera");
        JButton btnVolver = Estilos.crearBtn("Volver");

        //Estilo especial btn volver
        btnVolver.setBackground(new Color(60, 10, 10));
        btnVolver.setForeground(Color.WHITE);

        panelBtns.add(btnJugar);
        panelBtns.add(btnVolver);

        JPanel sur = new JPanel(new GridLayout(2, 1, 0, 10));
        sur.setOpaque(false);
        sur.add(infoOp);
        sur.add(panelBtns);
        panel.add(sur, BorderLayout.SOUTH);

        setContentPane(panel);

        //Acción iniciar carrera
        btnJugar.addActionListener(e -> {
            int idx = listaPersonajes.getSelectedIndex();

            //Validar selección
            if (idx < 0) {
                JOptionPane.showMessageDialog(this,
                        "Selecciona un personaje para continuar.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            //Obtener jugador y oponente aleatorio
            Personaje jugador = personajes.get(idx);
            Personaje comp = GPersonaje.getInstance().getPersonajeAleat(jugador);

            //Abrir ventana de carrera
            new VentanaC(this, jugador, comp).setVisible(true);
            setVisible(false);
        });

        //Acción volver
        btnVolver.addActionListener(e -> {
            parent.setVisible(true);
            dispose();
        });
    }
}