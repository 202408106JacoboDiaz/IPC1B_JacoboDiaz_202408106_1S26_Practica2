package carrerasescobas.model;

//Representar un elemento que aparece en la pista
public class ElementoP {

    //Tipos de elementos
    public enum Tipo { SNITCH, BLUDGER, QUAFFLE }

    private Tipo tipo;
    private int posi;
    private boolean activo;
    //false cuando el corredor ya pasó por el mismo

    public ElementoP(Tipo tipo, int posicion) {
        this.tipo = tipo;
        this.posi = posicion;
        this.activo = true;
    }

    //Getters
    public Tipo getTipo() { return tipo; }
    public int getPosi() { return posi; }
    public boolean isActivo() { return activo; }

    //Desactivar el elemento para que no vuelva a interactuar con ningún jugador
    public void desactivar() { this.activo = false; }

    //Retornar la letra que representa visualmente el elemento en la pista
    public char getLetra() {
        return switch (tipo) {
            case SNITCH -> 'S';
            case BLUDGER -> 'B';
            case QUAFFLE -> 'Q';
        };
    }

    //Retornar los puntos que otorga el elemento al ser alcanzado
    public int getPts() {
        return switch (tipo) {
            case SNITCH -> 150;
            case QUAFFLE -> 10;
            //Bludger no da puntos
            default -> 0;
        };
    }

    //Retornar la penalización en ms que se suma al sleep del jugador (Bludger)
    public int getPenalMs() {
        return (tipo == Tipo.BLUDGER) ? 2000 : 0;
    }
}