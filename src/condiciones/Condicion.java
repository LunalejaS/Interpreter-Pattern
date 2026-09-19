package condiciones;

import contexto.Contexto;

public interface Condicion {
    boolean evaluar(Contexto context);
}