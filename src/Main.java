import condiciones.*;
import contexto.Contexto;
import expresiones.Expresion;
import expresiones.noTerminales.*;
import expresiones.Terminales.*;
import java.util.Arrays;
import java.util.List;

public class Main {

    static int total = 0, ok = 0;

    static void probar(String nombre, Expresion expr, Contexto ctx, String... esperado) {
       System.out.println("->  " + nombre);
       System.out.println("Expresión: " + expr);
       System.out.println("Contexto inicial: " + ctx);
       expr.interpret(ctx);
       List<String> esperadoList = Arrays.asList(esperado);
       boolean paso = ctx.getLog().equals(esperadoList);
       total++;
       if (paso) ok++;
       System.out.println("Esperado: " + esperadoList);
       System.out.println("Obtenido: " + ctx.getLog() + (paso ? "  ✔ OK" : "  ✘ FALLÓ"));
       System.out.println();
    }

    public static void main(String[] args) {
       Expresion comer = new Comer(), jugar = new Jugar(), dormir = new Dormir();
       probar("Caso 1: COMER", comer, new Contexto(60, 80, 50), "COMER");
       probar("Caso 2: JUGAR + DORMIR", new Secuencia(jugar, dormir), new Contexto(20, 80, 50), "JUGAR", "DORMIR");

       Expresion condicion  = new Si(new Hambrienta(), comer, jugar);
       probar("Caso 3: SI HAMBRIENTA ... (hambre alta)", condicion, new Contexto(80, 80, 50), "COMER");
       probar("Caso 4: SI HAMBRIENTA ... (hambre baja)", condicion, new Contexto(10, 80, 50), "JUGAR");

       Expresion cambioEstado = new Secuencia(jugar, new Si(new Cansada(), dormir, jugar));
       probar("Caso 5: JUGAR + SI CANSADA... (energía 60 -> JUGAR la deja en 30)", cambioEstado, new Contexto(20, 60, 50), "JUGAR", "DORMIR");
       probar("Caso 5: mismo árbol, energía 100 (JUGAR la deja en 70)", cambioEstado, new Contexto(20, 100, 50), "JUGAR", "JUGAR");

       Expresion estado = new Si(new Hambrienta(), new Si(new Cansada(), dormir, new Secuencia(comer, jugar)), new Secuencia(jugar, jugar));
       probar("Hambrienta y cansada", estado, new Contexto(80, 20, 50), "DORMIR");
       probar("Hambrienta y con energía", estado, new Contexto(80, 90, 50), "COMER", "JUGAR");
       probar("Sin hambre", estado, new Contexto(10, 100, 50), "JUGAR", "JUGAR");
    }
}