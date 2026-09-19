package condiciones;

import contexto.Contexto;

public class Cansada implements Condicion {
    @Override public boolean evaluar(Contexto ctx) { return ctx.getEnergia() <= 30; }
    @Override public String toString() { return "CANSADA"; }
}