package condiciones;

import contexto.Contexto;

public class No implements Condicion {
    private final Condicion c;
    public No(Condicion c) { this.c = c; }
    @Override public boolean evaluar(Contexto ctx) { return !c.evaluar(ctx); }
    @Override public String toString() { return "NO " + c; }
}