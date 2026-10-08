package org.example;

import java.io.IOException;

public class PildoraParalelismo {

    public static void main(String[] args) {

        System.out.println("RETO 2 · PIPELINE DE AUDITORÍA");
        System.out.println("FASE 1: PARALELISMO + CODIGO DE SALIDA\n");

        try {
            // -------------------------------
            // LANZAMIENTO EN PARALELO
            // -------------------------------
            System.out.println("Lanzando procesos en paralelo...");

            Process p1 = new ProcessBuilder("ping", "-n", "1", "127.0.0.1").start();
            Process p2 = new ProcessBuilder("ping", "-n", "1", "error.invalid").start();

            System.out.println("PID P1: " + p1.pid());
            System.out.println("PID P2: " + p2.pid());

            // -------------------------------
            // BLOQUEO FINAL
            // -------------------------------
            int salida1 = p1.waitFor();
            int salida2 = p2.waitFor();

            System.out.println("Código salida P1: " + salida1);
            System.out.println("Código salida P2: " + salida2);

            // -------------------------------
            // LÓGICA DEL RETO
            // -------------------------------
            if (salida1 == 0 && salida2 == 0) {
                System.out.println("Ambos activos → Abriendo Bloc de Notas");
                new ProcessBuilder("notepad.exe").start();
            } else {
                System.out.println("Alguno caído → Abriendo Calculadora");
                new ProcessBuilder("calc.exe").start();
            }

        } catch (IOException e) {
            System.out.println("ERROR: No se pudo lanzar un proceso externo.");
        } catch (InterruptedException e) {
            System.out.println("ERROR: El hilo fue interrumpido mientras esperaba.");
        }

        System.out.println("\nFIN DEL PIPELINE");
    }
}
