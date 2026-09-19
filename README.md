# Interpreter-Pattern
> Tamagotchi Command Language
> Introduction to Interpreter Pattern

## Description
The context of this project is a virtual pet (a Tamagotchi) that the user controls with a small command language of its own, instead of writing Java code for every single action. The program builds expressions of that language as trees of Java objects and interprets them against the pet.

The main goal is to design a mini-language with 3 terminal expressions (`COMER`, `JUGAR`, `DORMIR`), 1 compound expression (`Secuencia`, written `A + B`) and 1 conditional expression (`Si`, written `SI ... ENTONCES ... SINO ...`), and to interpret it with the Interpreter pattern: every class knows how to interpret itself in its own `interpret()` method, so there is no `if/else` or `switch` asking what type of instruction it is. Conditions (`HAMBRIENTA`, `CANSADA`, `TRISTE`, `Y`, `NO`) live in their own hierarchy so `Si` does not need to know what it is evaluating.

The `Contexto` is shared by all the expressions and changes during the interpretation: an action modifies the pet's state, and a later condition decides based on that new state, not on the initial one. The final challenge is a nested expression (a `SI` inside another `SI`, with sequences inside the branches), executed with three different contexts to show that the correct branch is followed each time:

```
SI HAMBRIENTA ENTONCES
    SI CANSADA ENTONCES DORMIR
    SINO (COMER + JUGAR)
SINO
    JUGAR + JUGAR
```

## Project Structure

Package: `(default)`

* class Main (Client, builds the expression trees and runs the test cases)

Package: `contexto`

* class Contexto (Context)

Package: `expresiones`

* interface Expresion (AbstractExpression)

Package: `expresiones.Terminales`

* class Comer (Terminal expression)
* class Jugar (Terminal expression)
* class Dormir (Terminal expression)

Package: `expresiones.noTerminales`

* class Secuencia (Non-terminal expression, compound)
* class Si (Non-terminal expression, conditional)

Package: `condiciones`

* interface Condicion
* class Hambrienta, Cansada, Triste (terminal conditions)
* class Y, No (non-terminal conditions)

## Terminal and Non-terminal Expressions, and the Context
The terminal expressions are `Comer`, `Jugar` and `Dormir`: each one is a single action that changes the pet's state directly and does not contain any other expression. The non-terminal expressions are `Secuencia`, which runs its child expressions in order, and `Si`, which chooses between two child expressions depending on a condition; both hold other expressions, which is what allows nested trees. Conditions are a separate hierarchy: `Hambrienta`, `Cansada` and `Triste` are terminal conditions, while `Y` and `No` are non-terminal because they wrap other conditions. The `Contexto` carries the pet's hunger, energy and mood (from 0 to 100) plus a log of the actions executed so far. It travels through the whole tree because every action transforms it and every condition reads it, so a `Si` that runs after `JUGAR` decides with the already reduced energy and not with the initial value, and the log makes each test case verifiable in the console.

## How to Run

1. Clone or download the repository.
2. Open the project in a Java-compatible IDE such as VS Code, IntelliJ IDEA, or Eclipse.
3. Make sure Java is correctly installed and configured.
4. Run `Main` (located in the `src` folder).
5. Check the console output: every test case prints the expression, the initial context, each action executed with the resulting state, and whether the obtained result matches the expected one.

From the terminal, inside the project folder:

```
javac -encoding UTF-8 -d bin $(find src -name "*.java")
java -Dstdout.encoding=UTF-8 -cp bin Main
```

## Console Output

```
->  Caso 1: COMER
Expresión: COMER
Contexto inicial: [hambre=60, energía=80, ánimo=50]
   COMER  ->  [hambre=20, energía=80, ánimo=55]
Esperado: [COMER]
Obtenido: [COMER]  ✔ OK

->  Caso 2: JUGAR + DORMIR
Expresión: (JUGAR + DORMIR)
Contexto inicial: [hambre=20, energía=80, ánimo=50]
   JUGAR  ->  [hambre=35, energía=50, ánimo=75]
   DORMIR  ->  [hambre=45, energía=100, ánimo=75]
Esperado: [JUGAR, DORMIR]
Obtenido: [JUGAR, DORMIR]  ✔ OK

->  Caso 3: SI HAMBRIENTA ... (hambre alta)
Expresión: SI HAMBRIENTA ENTONCES COMER SINO JUGAR
Contexto inicial: [hambre=80, energía=80, ánimo=50]
   COMER  ->  [hambre=40, energía=80, ánimo=55]
Esperado: [COMER]
Obtenido: [COMER]  ✔ OK

->  Caso 4: SI HAMBRIENTA ... (hambre baja)
Expresión: SI HAMBRIENTA ENTONCES COMER SINO JUGAR
Contexto inicial: [hambre=10, energía=80, ánimo=50]
   JUGAR  ->  [hambre=25, energía=50, ánimo=75]
Esperado: [JUGAR]
Obtenido: [JUGAR]  ✔ OK

->  Caso 5: JUGAR + SI CANSADA... (energía 60 -> JUGAR la deja en 30)
Expresión: (JUGAR + SI CANSADA ENTONCES DORMIR SINO JUGAR)
Contexto inicial: [hambre=20, energía=60, ánimo=50]
   JUGAR  ->  [hambre=35, energía=30, ánimo=75]
   DORMIR  ->  [hambre=45, energía=80, ánimo=75]
Esperado: [JUGAR, DORMIR]
Obtenido: [JUGAR, DORMIR]  ✔ OK

->  Caso 5: mismo árbol, energía 100 (JUGAR la deja en 70)
Expresión: (JUGAR + SI CANSADA ENTONCES DORMIR SINO JUGAR)
Contexto inicial: [hambre=20, energía=100, ánimo=50]
   JUGAR  ->  [hambre=35, energía=70, ánimo=75]
   JUGAR  ->  [hambre=50, energía=40, ánimo=100]
Esperado: [JUGAR, JUGAR]
Obtenido: [JUGAR, JUGAR]  ✔ OK

->  Hambrienta y cansada
Expresión: SI HAMBRIENTA ENTONCES SI CANSADA ENTONCES DORMIR SINO (COMER + JUGAR) SINO (JUGAR + JUGAR)
Contexto inicial: [hambre=80, energía=20, ánimo=50]
   DORMIR  ->  [hambre=90, energía=70, ánimo=50]
Esperado: [DORMIR]
Obtenido: [DORMIR]  ✔ OK

->  Hambrienta y con energía
Expresión: SI HAMBRIENTA ENTONCES SI CANSADA ENTONCES DORMIR SINO (COMER + JUGAR) SINO (JUGAR + JUGAR)
Contexto inicial: [hambre=80, energía=90, ánimo=50]
   COMER  ->  [hambre=40, energía=90, ánimo=55]
   JUGAR  ->  [hambre=55, energía=60, ánimo=80]
Esperado: [COMER, JUGAR]
Obtenido: [COMER, JUGAR]  ✔ OK

->  Sin hambre
Expresión: SI HAMBRIENTA ENTONCES SI CANSADA ENTONCES DORMIR SINO (COMER + JUGAR) SINO (JUGAR + JUGAR)
Contexto inicial: [hambre=10, energía=100, ánimo=50]
   JUGAR  ->  [hambre=25, energía=70, ánimo=75]
   JUGAR  ->  [hambre=40, energía=40, ánimo=100]
Esperado: [JUGAR, JUGAR]
Obtenido: [JUGAR, JUGAR]  ✔ OK
```

Last Modification: 18/09/2026