package condiciones;

import contexto.Contexto;

public class Triste implements Condicion {
    @Override public boolean evaluar(Contexto ctx) { return ctx.getAnimo() <= 40; }
    @Override public String toString() { return "TRISTE"; }
}