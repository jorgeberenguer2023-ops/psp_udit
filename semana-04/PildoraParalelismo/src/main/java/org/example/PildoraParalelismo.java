package org.example;

import javax.crypto.spec.PSource;

public class PildoraParalelismo {

    public static void main(String[] args){
        System.out.println("PILDORA TECNICA: SECUENCIAL VS PARALELISMO");

        try {
            System.out.println("INICIANDO EJECUCION SECUENCIAL");
            long inicioSecuencial = System.currentTimeMillis();

            System.out.println("LANZANDO PROCESO UNO");
            Process p1 = new ProcessBuilder("ping","-n","2", "127.0.0.1").start();
            p1.waitFor();
            System.out.println("LANZANDO PROCESO DOS");
            Process p2 = new ProcessBuilder("ping","-n","2", "127.0.0.2").start();
            p2.waitFor();
            long finSecuencial =  System.currentTimeMillis();
            System.out.println("TOTAL SECUENCIAL   "+ (finSecuencial - inicioSecuencial) + "ms\n");

            System.out.println("INICIANDO EJECUCION PARALELA");
            long inicioParalelo = System.currentTimeMillis();

            System.out.println("PROCESO 3");

            Process p3 = new ProcessBuilder("ping","-n","1", "127.0.0.1").start();
            System.out.println("PROCESO 4");

            Process p4 = new ProcessBuilder("ping","-n","1", "127.0.0.2").start();

            System.out.println("BLOQUEANDO");
            p3.waitFor();
            p4.waitFor();
            long finParalelo = System.currentTimeMillis();
            System.out.println("TOTAL PARALELO   "+ (finParalelo - inicioParalelo) + "ms\n");


        }catch (Exception E){}
    }
}



