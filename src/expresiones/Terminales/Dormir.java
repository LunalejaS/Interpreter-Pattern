package expresiones.Terminales;

import contexto.Contexto;
import expresiones.Expresion;

public class Dormir implements Expresion {
    @Override
    public void interpret(Contexto ctx) {
        ctx.cambiarEnergia(+50);
        ctx.cambiarHambre(+10);
        ctx.registrar("DORMIR");
    }

    @Override
    public String toString() { return "DORMIR"; }
}