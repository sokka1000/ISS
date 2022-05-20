package bibliotecamodel;

import java.util.Date;

public class CarteDTO extends Carte{
    private Date dataImprumut;

    public CarteDTO(String titlu, String autor, Boolean disponibila, Date dataImprumut) {
        super(titlu, autor, disponibila);
        this.dataImprumut = dataImprumut;
    }

    public Date getDataImprumut() {
        return dataImprumut;
    }

    public void setDataImprumut(Date dataImprumut) {
        this.dataImprumut = dataImprumut;
    }

    @Override
    public String getTitlu() {
        return super.getTitlu();
    }

    @Override
    public void setTitlu(String titlu) {
        super.setTitlu(titlu);
    }

    @Override
    public String getAutor() {
        return super.getAutor();
    }

    @Override
    public void setAutor(String autor) {
        super.setAutor(autor);
    }

    @Override
    public Boolean getDisponibila() {
        return super.getDisponibila();
    }

    @Override
    public void setDisponibila(Boolean disponibila) {
        super.setDisponibila(disponibila);
    }

    @Override
    public void setId(int id) {
        super.setId(id);
    }

    @Override
    public Integer getId() {
        return super.getId();
    }

    @Override
    public void setId(Integer id) {
        super.setId(id);
    }
}
