package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class UDITflixMonitor {

    public static void main(String[] args) {

        // Título del catálogo
        System.out.println("UDITFLIX - CATÁLOGO\n");

        // 1️ Matriz de dos dimensiones con nombre y dirección
        // Cada fila: [nombre del vídeo, dirección a comprobar]
        String[][] videos = {
                {"Animación 3D", "192.168.0.555"},   // dirección inexistente  CAÍDO
                {"Videojuegos", "10.0.0.123"},       // dirección inexistente  CAÍDO
                {"Kotlin", "55.55.55.55"},           // dirección inexistente  Caido
                {"Android", "127.0.0.1"},            // dirección válida  ACTIVO
                {"Flutter", "127.0.0.1"}             // dirección válida  ACTIVO
        };

        // 2 Bucle for para recorrer la matriz
        for (int i = 0; i < videos.length; i++) {

            String nombre = videos[i][0];     // nombre del vídeo
            String direccion = videos[i][1];  // dirección a comprobar

            System.out.println("[VÍDEO] " + nombre);

            try {
                // 3 Crear el proceso externo con ProcessBuilder
                // Usamos "ping" para simular la comprobación
                ProcessBuilder pb = new ProcessBuilder("ping", "-n", "1", direccion);

                // 4 Iniciar el proceso
                Process proceso = pb.start();

                // 5 Mostrar el PID del proceso
                long pid = proceso.pid();
                System.out.println("PID: " + pid);

                // 6 Leer la información que devuelve el proceso
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(proceso.getInputStream())
                );

                String linea;
                boolean activo = false;

                // 7 Analizamos la salida del ping
                while ((linea = br.readLine()) != null) {
                    if (linea.contains("TTL")) {   // TTL aparece cuando la dirección responde
                        activo = true;
                    }
                }

                // 8 Esperar a que termine el proceso
                proceso.waitFor();

                // 9 Determinar si está activo o caído
                if (activo) {
                    System.out.println("ESTADO: ACTIVO\n");
                } else {
                    System.out.println("ESTADO: CAÍDO\n");
                }

            } catch (Exception e) {
                System.out.println("ERROR EN LA COMPROBACIÓN\n");
            }
        }

        // 8️⃣ Mensaje final
        System.out.println("COMPROBACIÓN FINALIZADA");
    }
}
