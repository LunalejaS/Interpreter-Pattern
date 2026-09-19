package condiciones;

import contexto.Contexto;

public class Y implements Condicion {
    private final Condicion a, b;
    public Y(Condicion a, Condicion b) { this.a = a; this.b = b; }
    @Override public boolean evaluar(Contexto ctx) { return a.evaluar(ctx) && b.evaluar(ctx); }
    @Override public String toString() { return "(" + a + " Y " + b + ")"; }
}