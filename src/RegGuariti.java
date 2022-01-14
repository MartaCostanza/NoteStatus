import java.io.Serializable;
import java.util.ArrayList;


public class RegGuariti implements Serializable {
    private ArrayList<GuaritoCovPers> GuaritiSintomaticiRegister;
    private ArrayList<GuaritoCovPers> GuaritiAsintomaticiRegister;

    public RegGuariti(){
        GuaritiSintomaticiRegister=new ArrayList<>();
        GuaritiAsintomaticiRegister=new ArrayList<>();
    }

    public synchronized void addGuaritiS (GuaritoCovPers p){
        GuaritiSintomaticiRegister.add(p);
    }
    public synchronized void addGuaritiA (GuaritoCovPers p){
        GuaritiAsintomaticiRegister.add(p);
    }

/*
    public synchronized  String removeGuaritiS(String cod_f) {
        String canc = null;
        for (GuaritoCovPers p : GuaritiSintomaticiRegister) {

            if ((p.getCf().equals(cod_f))) {
                GuaritiSintomaticiRegister.remove(p);
                canc = "REMOVE_GS_OK";
                return canc;
            }
        }
        System.out.println("Utente non presente in lista");
        canc = "REMOVE_ERROR";
        return canc;
    }

    public synchronized  String removeGuaritiA(String cod_f) {
        String canc = null;
        for (GuaritoCovPers p : GuaritiAsintomaticiRegister) {

            if ((p.getCf().equals(cod_f))) {
                GuaritiAsintomaticiRegister.remove(p);
                canc = "REMOVE_GA_OK";
                return canc;
            }
        }
        System.out.println("Utente non presente in lista");
        canc = "REMOVE_ERROR";
        return canc;
    }
*/

    public ArrayList<GuaritoCovPers> getGSCopy(){
        ArrayList<GuaritoCovPers> v_g = new ArrayList<>();
        v_g.addAll(GuaritiSintomaticiRegister);
        return v_g;
    }

    public ArrayList<GuaritoCovPers> getGACopy(){
        ArrayList<GuaritoCovPers> v_g = new ArrayList<>();
        v_g.addAll(GuaritiAsintomaticiRegister);
        return v_g;
    }

    @Override
    public String toString() {
        return "Registro Guariti:{" +
                "Guariti con sintomi avuti=" + GuaritiSintomaticiRegister +
                ", Guariti senza aver avuto sintomi=" + GuaritiAsintomaticiRegister +
                '}';
    }
}
