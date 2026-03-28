package carrerasescobas.view;

import carrerasescobas.controller.GPersonaje;
import carrerasescobas.util.Estilos;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CreacionPer extends JDialog {

    public CreacionPer(JFrame parent) {
        //Diálogo modal
        super(parent, "Crear Personaje", true);
        setSize(420, 400);
        //Centrar ventana
        setLocationRelativeTo(parent);
        setResizable(false);
        construirUI();
    }

    private void construirUI() {
        //Layout flexible
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Estilos.C_FONDO);
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));
        //Control de posiciones
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Título
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel titulo = Estilos.crearLabel("Nuevo Personaje", Estilos.fteSubtitulo(), Estilos.C_ACENTO);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titulo, gbc);

        gbc.gridwidth = 1;

        //Campo nombre
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.35;
        panel.add(Estilos.crearLabel("Nombre:", Estilos.fteNormal(), Estilos.C_TEXTO), gbc);
        gbc.gridx = 1; gbc.weightx = 0.65;
        JTextField txtNombre = crearTextField();
        panel.add(txtNombre, gbc);

        //Selección de casa
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(Estilos.crearLabel("Casa:", Estilos.fteNormal(), Estilos.C_TEXTO), gbc);
        gbc.gridx = 1;
        String[] casas = {"Gryffindor", "Slytherin", "Ravenclaw", "Hufflepuff"};
        JComboBox<String> cbCasa = crearCombo(casas);
        panel.add(cbCasa, gbc);

        //Selección de escoba
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(Estilos.crearLabel("Escoba:", Estilos.fteNormal(), Estilos.C_TEXTO), gbc);
        gbc.gridx = 1;
        String[] escobas = {"Nimbus 2000", "Nimbus 2001", "Saeta de Fuego"};
        JComboBox<String> cbEscoba = crearCombo(escobas);
        panel.add(cbEscoba, gbc);

        //Información adicional de escobas
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JLabel info = Estilos.crearLabel(
                "<html><i>Nimbus 2000: lenta · Nimbus 2001: rápida · Saeta de Fuego: muy rápida</i></html>",
                new Font("SansSerif", Font.ITALIC, 11), new Color(180, 180, 180));
        info.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(info, gbc);

        //Btns de acción
        gbc.gridy = 5; gbc.gridwidth = 2;
        JPanel panelBtns = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panelBtns.setOpaque(false);

        JButton btnGuardar  = Estilos.crearBtn("Guardar");
        JButton btnCancelar = Estilos.crearBtn("Cancelar");

        //Estilo especial para botón cancelar
        btnCancelar.setBackground(new Color(60, 10, 10));
        btnCancelar.setForeground(Color.WHITE);

        panelBtns.add(btnGuardar);
        panelBtns.add(btnCancelar);
        panel.add(panelBtns, gbc);

        setContentPane(panel);

        //Acción guardar (validación + registro)
        btnGuardar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            if (nombre.isEmpty()) {
                //Validación básica
                mostrarError("El nombre no puede estar vacío.");
                return;
            }
            String casa   = (String) cbCasa.getSelectedItem();
            String escoba = (String) cbEscoba.getSelectedItem();

            boolean ok = GPersonaje.getInstance().agregarPersonaje(nombre, casa, escoba);

            if (ok) {
                JOptionPane.showMessageDialog(this,
                        "Personaje \"" + nombre + "\" registrado exitosamente.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                //Cerrar ventana
                dispose();
            } else {
                mostrarError("Ya existe un personaje con ese nombre.");
            }
        });

        //Acción cancelar
        btnCancelar.addActionListener(e -> dispose());
    }

    //Crear campo de texto con estilo
    private JTextField crearTextField() {
        JTextField tf = new JTextField();
        tf.setFont(Estilos.fteNormal());
        tf.setBackground(Estilos.C_PANEL);
        tf.setForeground(Estilos.C_TEXTO);
        tf.setCaretColor(Estilos.C_ACENTO);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Estilos.C_ACENTO, 1),
                new EmptyBorder(5, 8, 5, 8)));
        return tf;
    }

    //Crea combo-box con estilo
    private JComboBox<String> crearCombo(String[] items) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setFont(Estilos.fteNormal());
        cb.setBackground(Estilos.C_PANEL);
        cb.setForeground(Estilos.C_TEXTO);
        return cb;
    }

    //Mostrar mensaje de error
    private void mostrarError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}