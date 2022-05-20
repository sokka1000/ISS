package bibliotecamodel;

public class Abonat extends Persoana{
    public Abonat() {
    }

    public Abonat(String nume, String username, String parola, String email) {
        super(nume, username, parola, email);
    }

    @Override
    public Integer getId() {
        return super.getId();
    }

    @Override
    public void setId(Integer integer) {
        super.setId(integer);
    }
    @Override
    public String getNume() {
        return super.getNume();
    }
    @Override
    public void setNume(String nume) {
        super.setNume(nume);
    }
    @Override
    public String getUsername() {
        return super.getUsername();
    }
    @Override
    public void setUsername(String username) {
        super.setUsername(username);
    }
    @Override
    public String getParola() {
        return super.getParola();
    }
    @Override
    public void setParola(String parola) {
       super.setParola(parola);
    }
    @Override
    public String getAdresa() {
        return super.getAdresa();
    }
    @Override
    public void setAdresa(String adresa) {
        super.setAdresa(adresa);
    }
    @Override
    public String getCNP() {
        return super.getCNP();
    }
    @Override
    public void setCNP(String CNP) {
        super.setCNP(CNP);
    }
    @Override
    public String getTelefon() {
        return super.getTelefon();
    }
    @Override
    public void setTelefon(String telefon) {
        super.setTelefon(telefon);
    }

    @Override
    public String toString(){
        return "Abonat{" +
                "nume='" + getNume() + '\'' +
                ", username='" + getUsername() + '\'' +
                ", parola='" + getParola() + '\'' +
                ", adresa='" + getAdresa() + '\'' +
                ", CNP='" + getCNP() + '\'' +
                ", telefon='" + getTelefon() + '\'' +
                ", id=" + getId() +
                '}';

    }

}
