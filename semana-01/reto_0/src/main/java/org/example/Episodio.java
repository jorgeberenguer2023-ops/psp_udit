package org.example;

public class Episodio {

    private String titulo;
    private int duracion;

    //CONSTRUCTOR

    public Episodio(String titulo, int duracion){
        this.titulo = titulo;
        this.duracion = duracion;
    }


    //METODO

    public voi procesar(){
        System.out.println("iniciando procesamiento de"+ this.titulo +".....");
        try {
            Thread.sleep(2000);
        }catch(InterruptedException e){
            System.out.println("Error al procesar episodio");
        }
        System.out.println("Completado: " + this.titulo);
    }
}