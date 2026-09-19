package expresiones.Terminales;

import contexto.Contexto;
import expresiones.Expresion;

public class Jugar implements Expresion {
    private static final int COSTO_ENERGIA = 30;

    @Override
    public void interpret(Contexto ctx) {
        if (ctx.getEnergia() < COSTO_ENERGIA) {
            ctx.registrar("JUGAR (falló: sin energía)");
            return;
        }
        ctx.cambiarAnimo(+25);
        ctx.cambiarEnergia(-COSTO_ENERGIA);
        ctx.cambiarHambre(+15);
        ctx.registrar("JUGAR");
    }

    @Override
    public String toString() { return "JUGAR"; }
}