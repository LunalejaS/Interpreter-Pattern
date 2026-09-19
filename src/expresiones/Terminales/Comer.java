package expresiones.Terminales;

import contexto.Contexto;
import expresiones.Expresion;

public class Comer implements Expresion {
    @Override
    public void interpret(Contexto ctx) {
        ctx.cambiarHambre(-40);
        ctx.cambiarAnimo(+5);
        ctx.registrar("COMER");
    }

    @Override
    public String toString() { return "COMER"; }
}