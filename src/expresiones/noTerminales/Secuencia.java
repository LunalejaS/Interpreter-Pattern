package expresiones.noTerminales;

import contexto.Contexto;
import expresiones.Expresion;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Secuencia implements Expresion {
    private final Expresion[] hijas;

    public Secuencia(Expresion... hijas) { this.hijas = hijas; }

    @Override
    public void interpret(Contexto ctx) {
        for (Expresion e : hijas) e.interpret(ctx);
    }

    @Override
    public String toString() {
        return "(" + Arrays.stream(hijas).map(Object::toString).collect(Collectors.joining(" + ")) + ")";
    }
}
