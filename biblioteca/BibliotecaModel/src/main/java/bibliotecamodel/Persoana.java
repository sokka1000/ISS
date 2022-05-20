package bibliotecamodel;

import java.io.Serializable;

public class Persoana implements Entity<Integer>, Serializable {


    private String nume;
    private String username;
    private String parola;
    private String adresa;
    private String CNP;
    private String telefon;
    private Integer id;

    public Persoana()
    {

    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer integer) {
        this.id = integer;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Persoana(String nume, String username, String parola, String adresa) {
        this.nume = nume;
        this.username = username;
        this.parola = parola;
        this.adresa = adresa;
    }

    @Override
    public String toString() {
        return "Persoana{" +
                "nume='" + nume + '\'' +
                ", username='" + username + '\'' +
                ", parola='" + parola + '\'' +
                ", adresa='" + adresa + '\'' +
                ", CNP='" + CNP + '\'' +
                ", telefon='" + telefon + '\'' +
                ", id=" + id +
                '}';
    }
}
