package contexto;

import java.util.ArrayList;
import java.util.List;

public class Contexto {
    private int hambre;   
    private int energia;  
    private int animo;   
    private final List<String> log = new ArrayList<>();

    public Contexto(int hambre, int energia, int animo) {
        this.hambre = limitar(hambre);
        this.energia = limitar(energia);
        this.animo = limitar(animo);
    }

    public int getHambre()  { return hambre; }
    public int getEnergia() { return energia; }
    public int getAnimo()   { return animo; }

    public void cambiarHambre(int delta)  { hambre = limitar(hambre + delta); }
    public void cambiarEnergia(int delta) { energia = limitar(energia + delta); }
    public void cambiarAnimo(int delta)   { animo = limitar(animo + delta); }

    public void registrar(String accion) {
        log.add(accion);
        System.out.println("   " + accion + "  ->  " + this);
    }

    public List<String> getLog() { return log; }

    private int limitar(int valor) { return Math.max(0, Math.min(100, valor)); }

    @Override
    public String toString() {
        return "[hambre=" + hambre + ", energía=" + energia + ", ánimo=" + animo + "]";
    }
}