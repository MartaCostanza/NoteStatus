import java.io.Serializable;

public class GuaritoCovPers implements Serializable {
    private String Nome;
    private String Cognome;
    private String Sesso;
    private int Età;
    private String Cf;
    private String Numero_cell;
    private String Sintomi;





    public GuaritoCovPers(String gNome, String gCognome, String gSesso,int gEtà, String cf,String gN_cell, String sintomi) {
        Nome=gNome;
        Cognome=gCognome;
        Numero_cell=gN_cell;
        this.Età=gEtà;
        Sintomi=sintomi;
        Sesso=gSesso;
        Cf=cf;
    }

    public String getSesso() {return Sesso;}

    public void setSesso(String sesso) {Sesso = sesso;}

    public String getCf() {
        return Cf;
    }

    public void setCf(String cf) {
        Cf = cf;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getCognome() {
        return Cognome;
    }

    public void setCognome(String cognome) {
        Cognome = cognome;
    }

    public String getNumero_cell() {
        return Numero_cell;
    }

    public void setNumero_cell(String numero_cell) {
        Numero_cell = numero_cell;
    }

    public int getEtà() {
        return Età;
    }

    public void setEtà(int età) {
        Età = età;
    }

    public String getSintomi() {
        return Sintomi;
    }

    public void setSintomi(String sintomi) {
        Sintomi = sintomi;
    }


    @Override
    public String toString() {
        return "PERSONA GUARITA DA COV19 (" +
                "Nome='" + Nome + '\'' +
                ", Cognome='" + Cognome + '\'' +
                ", Sesso='" + Sesso + '\'' +
                ", Età=" + Età +
                ", Cf='" + Cf + '\'' +
                ", Numero_cell='" + Numero_cell + '\'' +
                ", Sintomi='" + Sintomi + '\'' +
                ')'+ '\n';
    }
}

