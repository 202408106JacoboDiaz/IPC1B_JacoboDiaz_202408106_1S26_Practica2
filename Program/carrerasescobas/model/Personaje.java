package carrerasescobas.model;

//Representar a un personaje/corredor dentro del juego
public class Personaje {
    private int cod;
    private String nomb;
    private String cas;
    private String modeloEsc;

    //Constructor que inicializa todos los datos del personaje
    public Personaje(int codigo, String nombre, String casa, String modeloEscoba) {
        this.cod = codigo;
        this.nomb = nombre;
        this.cas = casa;
        this.modeloEsc = modeloEscoba;
    }

    //Getters
    public int getCod() { return cod; }
    public String getNomb() { return nomb; }
    public String getCas() { return cas; }
    public String getModeloEsc() { return modeloEsc; }

    //Retornar el tiempo de espera en ms según el modelo de escoba
    public int getVelocidadMs() {
        return switch (modeloEsc) {
            case "Nimbus 2001" -> 2000;
            case "Saeta de Fuego" -> 1000;
            //Nimbus 2000
            default -> 3000;
        };
    }

    //Formatear la info del personaje para mostrarlo en la lista
    @Override
    public String toString() {
        return cod + " - " + nomb + " (" + cas + ") [" + modeloEsc + "]";
    }
}