package org.example;
import java.util.ArrayList; 
public class Main {
    public static void main(String[] args) {
    
      System.out.println("---INNICIANDO UDITVERSO---");

      long inicio = System.currentTimeMillis();

        ArrayList<Episodio> catalogo = new ArrayList();


        Plataforma plataforma = new Plataforma();
        plataforma.agregarEpisodio(new Episodio("Diseño 3D - Intro", 45));
        plataforma.agregarEpisodio(new Episodio("Animacion", 60));  
        plataforma.agregarEpisodio(new Episodio("Texturas", 50));
        plataforma.agregarEpisodio(new Episodio("Casero", 60));
        plataforma.agregarEpisodio(new Episodio("Videojuegos", 50));

        plataforma.procesarCatalogo();

        for(Episodio ep : catalogo){
            ep.procesar();
        }
        long fin = System.currentTimeMillis();
        long totalSegundos=(fin - inicio)/1000;
        System.out.print("Tiempo total"+ totalSegundos);
    }
}
