package org.example;

import java.io.IOException;

public class LanzadorProcesos {
    public static void main(String[] args) {
        try{
        System.out.println("Solicitando al SO la creacion de un proceso nativo ...");
        // 1. ProcessBuilder
        //
        ProcessBuilder pb = new ProcessBuilder ("notepad.exe");
        // 2. start ()
        Process proceso = pb.start();
        System.out.println("Proceso en ejecucion. PIB"+ proceso.pid());
        // 3. waitFor()
        int salida = proceso.waitFor();
        // 0 significa cierre forzoso
        System.out.println("El proceso externo finalizo con codigo" + salida);
        } catch (IOException e) {

            System.out.println("Error: el sistema operativo no encuentra el programa");

        } catch (InterruptedException e){
            System.out.println("Error: el proceso fue interrumpido bruscamente");
        }


    }
}
