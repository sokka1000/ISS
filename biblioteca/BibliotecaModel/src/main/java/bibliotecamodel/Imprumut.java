package bibliotecamodel;

import java.io.Serializable;
import java.util.Date;

public class Imprumut implements Entity<Integer>, Serializable {

    private int id;
    private int idCarte;
    private int idAbonat;
    private Date dataImprumut;
    private Boolean returnat;

    public Imprumut(){}

    public Boolean getReturnat() {
        return returnat;
    }

    public void setReturnat(Boolean returnat) {
        this.returnat = returnat;
    }

    public Imprumut(int idCarte, int idAbonat, Date dataImprumut, Boolean returnat) {
        this.idCarte = idCarte;
        this.idAbonat = idAbonat;
        this.dataImprumut = dataImprumut;
        this.returnat = returnat;
    }

    public int getIdCarte() {
        return idCarte;
    }

    public void setIdCarte(int idCarte) {
        this.idCarte = idCarte;
    }

    public int getIdAbonat() {
        return idAbonat;
    }

    public void setIdAbonat(int idAbonat) {
        this.idAbonat = idAbonat;
    }

    public Date getDataImprumut() {
        return dataImprumut;
    }

    public void setDataImprumut(Date dataImprumut) {
        this.dataImprumut = dataImprumut;
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
        return "Imprumut{" +
                "id=" + id +
                ", idCarte=" + idCarte +
                ", idAbonat=" + idAbonat +
                ", dataImprumut=" + dataImprumut +
                ", returnat=" + returnat +
                '}';
    }
}