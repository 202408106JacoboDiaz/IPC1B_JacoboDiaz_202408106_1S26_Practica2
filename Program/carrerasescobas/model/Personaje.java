package carrerasescobas.model;

public class Personaje {
    private int cod;
    private String nomb;
    private String cas;
    private String modeloEsc;

    public Personaje(int codigo, String nombre, String casa, String modeloEscoba) {
        this.cod = codigo;
        this.nomb = nombre;
        this.cas = casa;
        this.modeloEsc = modeloEscoba;
    }

    public int getCodigo() { return cod; }
    public String getNombre() { return nomb; }
    public String getCasa() { return cas; }
    public String getModeloEscoba() { return modeloEsc; }

    public int getVelocidadMs() {
        return switch (modeloEsc) {
            case "Nimbus 2001" -> 2000;
            case "Saeta de Fuego" -> 1000;
            default -> 3000;
        };
    }

    @Override
    public String toString() {
        return cod + " - " + nomb + " (" + cas + ") [" + modeloEsc + "]";
    }
}