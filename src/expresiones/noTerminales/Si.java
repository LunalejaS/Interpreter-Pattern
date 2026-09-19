package expresiones.noTerminales;

import condiciones.Condicion;
import contexto.Contexto;
import expresiones.Expresion;

public class Si implements Expresion {
    private final Condicion condicion;
    private final Expresion entonces;
    private final Expresion sino;

    public Si(Condicion condicion, Expresion entonces, Expresion sino) {
        this.condicion = condicion;
        this.entonces = entonces;
        this.sino = sino;
    }

    @Override
    public void interpret(Contexto ctx) {
        if (condicion.evaluar(ctx)) entonces.interpret(ctx);
        else sino.interpret(ctx);
    }

    @Override
    public String toString() {
        return "SI " + condicion + " ENTONCES " + entonces + " SINO " + sino;
    }
}