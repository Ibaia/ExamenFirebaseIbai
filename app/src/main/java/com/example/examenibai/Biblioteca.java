package com.example.examenibai;

import com.google.firebase.firestore.QueryDocumentSnapshot;

public class Biblioteca {

    private String id;
    private String ISBN;
    private String autor;
    private String titulo;
    private String annoPublicaion;


    public Biblioteca(QueryDocumentSnapshot document) {
        this.id = document.getId();
        this.ISBN = document.getString("ISBN");
        this.autor = document.getString("Autor");
        this.titulo = document.getString("Titulo");
        this.annoPublicaion = document.getString("AñoPublicacion");
    }

    public String getId() {
        return id;
    }
    public String getISBN() {
        return ISBN;
    }
    public String getAutor() {
        return autor;
    }
    public String getTitulo() {
        return titulo;
    }
    public String getAnnoPublicacion() {
        return annoPublicaion;
    }


}
