package carrerasescobas.view;

import carrerasescobas.controller.GMatch;
import carrerasescobas.model.ResultadoMatch;
import carrerasescobas.util.Estilos;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class PuntajesTOP extends JDialog {

    public PuntajesTOP(JFrame parent) {
        //Diálogo modal
        super(parent, "Puntajes TOP", true);
        setSize(680, 520);
        //Centrar
        setLocationRelativeTo(parent);
        setResizable(true);
        construirUI();
    }

    private void construirUI() {
        //Layout principal
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBackground(Estilos.C_FONDO);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        //Título
        JLabel titulo = Estilos.crearLabel("Puntajes TOP",
                Estilos.fteSubtitulo(), Estilos.C_ACENTO);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titulo, BorderLayout.NORTH);

        //Obtener datos del controlador
        List<ResultadoMatch> top = GMatch.getInstance().getTopPuntajes();

        if (top.isEmpty()) {
            //Mensaje si no hay datos
            JLabel sinDatos = Estilos.crearLabel(
                    "Sin Partidas",
                    Estilos.fteNormal(), Estilos.C_TEXTO);
            sinDatos.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(sinDatos, BorderLayout.CENTER);
        } else {
            //Crear dataset para gráfica
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (ResultadoMatch r : top) {
                dataset.addValue(r.getPtsJugador(), "Puntos", r.getNombJugador());
            }

            //Crear gráfica de barras
            JFreeChart chart = ChartFactory.createBarChart(
                    "Mejores Puntajes por Jugador",
                    "Jugador", "Puntos",
                    dataset, PlotOrientation.VERTICAL,
                    false, true, false);

            //Personalización de la gráfica
            chart.setBackgroundPaint(Estilos.C_FONDO);
            chart.getTitle().setPaint(Estilos.C_ACENTO);
            chart.getTitle().setFont(Estilos.fteSubtitulo());

            CategoryPlot plot = chart.getCategoryPlot();
            plot.setBackgroundPaint(Estilos.C_PANEL);
            plot.setOutlinePaint(Estilos.C_ACENTO);
            plot.setRangeGridlinePaint(new Color(80, 80, 80));

            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            renderer.setSeriesPaint(0, Estilos.C_ACENTO);
            renderer.setDrawBarOutline(false);
            renderer.setMaximumBarWidth(0.1);

            //Configuración de ejes
            CategoryAxis xAxis = plot.getDomainAxis();
            xAxis.setTickLabelPaint(Estilos.C_TEXTO);
            xAxis.setLabelPaint(Estilos.C_TEXTO);

            NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
            yAxis.setTickLabelPaint(Estilos.C_TEXTO);
            yAxis.setLabelPaint(Estilos.C_TEXTO);

            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setBackground(Estilos.C_FONDO);
            panel.add(chartPanel, BorderLayout.CENTER);
        }

        //Btns
        JPanel panelBtns = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panelBtns.setOpaque(false);

        JButton btnExportar = Estilos.crearBtn("Exportar en PDF");
        JButton btnCerrar   = Estilos.crearBtn("Salir");

        //Estilo btn cerrar
        btnCerrar.setBackground(new Color(60, 10, 10));
        btnCerrar.setForeground(Color.WHITE);

        //Deshabilitar exportar, si no hay datos
        if (top.isEmpty()) btnExportar.setEnabled(false);

        panelBtns.add(btnExportar);
        panelBtns.add(btnCerrar);
        panel.add(panelBtns, BorderLayout.SOUTH);

        setContentPane(panel);

        //Acciones
        btnCerrar.addActionListener(e -> dispose());
        btnExportar.addActionListener(e -> crearPDF(top));
    }

    //Generar PDF con puntajes
    private void crearPDF(List<ResultadoMatch> top) {
        JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new java.io.File("top_puntajes.pdf"));
        int res = fc.showSaveDialog(this);
        if (res != JFileChooser.APPROVE_OPTION) return;

        String ruta = fc.getSelectedFile().getAbsolutePath();
        if (!ruta.endsWith(".pdf")) ruta += ".pdf";

        try {
            //Crear archivo PDF
            com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
            com.itextpdf.text.pdf.PdfWriter.getInstance(doc, new java.io.FileOutputStream(ruta));
            doc.open();

            //Fts
            com.itextpdf.text.Font fontTitulo = new com.itextpdf.text.Font(
                    com.itextpdf.text.Font.FontFamily.HELVETICA, 18,
                    com.itextpdf.text.Font.BOLD,
                    new com.itextpdf.text.BaseColor(200, 30, 30));

            com.itextpdf.text.Font fontNormal = new com.itextpdf.text.Font(
                    com.itextpdf.text.Font.FontFamily.HELVETICA, 12);

            //Título del PDF
            doc.add(new com.itextpdf.text.Paragraph(
                    "TOP PUNTAJES - CARRERA DE ESCOBAS (HP)\n\n", fontTitulo));

            //Crear tabla
            com.itextpdf.text.pdf.PdfPTable tabla = new com.itextpdf.text.pdf.PdfPTable(4);
            tabla.setWidthPercentage(100);

            String[] headers = {"Jugador", "Escoba", "Pts.", "Ganador"};
            for (String h : headers) {
                com.itextpdf.text.pdf.PdfPCell cell = new com.itextpdf.text.pdf.PdfPCell(
                        new com.itextpdf.text.Phrase(h, fontTitulo));
                cell.setBackgroundColor(new com.itextpdf.text.BaseColor(30, 30, 30));
                tabla.addCell(cell);
            }

            //Llenar tabla con datos
            for (ResultadoMatch r : top) {
                tabla.addCell(new com.itextpdf.text.Phrase(r.getNombJugador(), fontNormal));
                tabla.addCell(new com.itextpdf.text.Phrase(r.getEscoba(), fontNormal));
                tabla.addCell(new com.itextpdf.text.Phrase(String.valueOf(r.getPtsJugador()), fontNormal));
                tabla.addCell(new com.itextpdf.text.Phrase(r.getWinner(), fontNormal));
            }

            doc.add(tabla);
            doc.close();

            JOptionPane.showMessageDialog(this,
                    "PDF creado exitosamente:\n" + ruta,
                    "Exportado", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            //Manejo de errores con sus mensajes
            JOptionPane.showMessageDialog(this,
                    "Error durante la exportacion: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}