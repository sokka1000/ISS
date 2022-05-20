package bibliotecamodel;

import java.io.Serializable;

public class Carte implements Entity<Integer>, Serializable {

    private String titlu;
    private String autor;
    private Boolean disponibila;
    private int id;

    public Carte(){

    }

    public Carte(String titlu, String autor, Boolean disponibila) {
        this.titlu = titlu;
        this.autor = autor;
        this.disponibila = disponibila;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Boolean getDisponibila() {
        return disponibila;
    }

    public void setDisponibila(Boolean disponibila) {
        this.disponibila = disponibila;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id=id;
    }

    @Override
    public String toString() {
        return  "cod: " + id + ", " +
                "titlu: " + titlu + ", " +
                "autor: " + autor;
    }
}
