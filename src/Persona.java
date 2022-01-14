import java.io.Serializable;

public class Persona implements Serializable {

        private String Nome;
        private String Cognome;
        private int Dose;//(0,1,2,3)
        private String Cf;
        private String Sesso;
        private String Numero_cell;
        private int Età;
        private String Tipovacc;



    public Persona(String nome, String cognome, String codiceFiscale, String sesso, String n_cell, int età, int dose) {
        Nome = nome;
        Cognome = cognome;
        Cf=codiceFiscale;
        this.Età=età;
        Sesso = sesso;
        Dose = dose;
        this.Numero_cell=n_cell;
    }

    public Persona(String nome, String cognome, String sesso, int età, String codiceFiscale, int dose, String t_vac) {
        Nome = nome;
        Cognome = cognome;
        Cf=codiceFiscale;
        this.Età=età;
        Sesso = sesso;
        Dose = dose;
        this.Tipovacc=t_vac;

    }


    public String getCf() {
        return Cf;
    }

    public void setCf(String cf) {
        Cf = cf;
    }

    public String getNome() {
        return Nome;
    }

    public String getCognome() {
        return Cognome;
    }

    public int getEtà() {
        return Età;
    }

    public String getSesso() {
        return Sesso;
    }

    public int getDose() {
        return Dose;
    }

    public String getNumero_cell() {
        return Numero_cell;
    }

    public String getTipovacc() {
        return Tipovacc;
    }

    public void setTipovacc(String tipovacc) {
        Tipovacc = tipovacc;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public void setCognome(String cognome) {
        Cognome = cognome;
    }

    public void setSesso(String sesso) {
        Sesso = sesso;
    }

    public void setEtà(int età) {
        Età = età;
    }

    public void setNumero_cell(String numero_cell) {
        Numero_cell = numero_cell;
    }

    public void setDose(int dose) {
        Dose = dose;
    }



    @Override
    public String toString() {
        return "Persona (" +
                "Nome='" + Nome + '\'' +
                ", Cognome='" + Cognome + '\'' +
                ", Età=" + Età +
                ", Sesso='" + Sesso + '\'' +
                ", Cf='" + Cf + '\'' +
                ", Numero di cellulare='" + Numero_cell + '\'' +
                ", Dose=" + Dose + '\'' +
                ", Tipo di vaccino='" + Tipovacc + '\'' +
                ')'+'\n';
    }

}


