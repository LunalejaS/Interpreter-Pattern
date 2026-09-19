package condiciones;

import contexto.Contexto;

public class Hambrienta implements Condicion {
    @Override public boolean evaluar(Contexto ctx) { return ctx.getHambre() >= 50; }
    @Override public String toString() { return "HAMBRIENTA"; }
}