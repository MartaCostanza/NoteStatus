import java.io.Serializable;
import java.util.ArrayList;


public class Registro implements Serializable {


    private ArrayList<Persona> VaxRegister;
    private ArrayList<Persona> NoVaxRegister;

     public Registro(){
      VaxRegister=new ArrayList<>();
      NoVaxRegister=new ArrayList<>();
   }

    public synchronized void addVax (Persona p){
       VaxRegister.add(p);
    }

    public synchronized void addNoVaX (Persona p){
        NoVaxRegister.add(p);
    }

   /* public synchronized  String removeVAX(String cod_f) {
        String canc = null;
        for (Persona p : VaxRegister) {

            if ((p.getCf().equals(cod_f))) {
                VaxRegister.remove(p);
                canc = "REMOVE_V_OK";
                return canc;
            }
        }
        System.out.println("Utente non presente in lista!");
        canc = "REMOVE_ERROR";
        return canc;
    }
*/


        public synchronized  String removeNOVAX(String cod_f1 ) {
            String canc1 = null;
            for (Persona pe : NoVaxRegister) {
                if ((pe.getCf().equals(cod_f1))) {
                    NoVaxRegister.remove(pe);
                    canc1 = "REMOVE_NV_OK";
                    return canc1;
                }
            }
            System.out.println("Utente non presente in lista");
            canc1 = "REMOVE_ERROR";
            return canc1;
        }



    public ArrayList<Persona> getVaxCopy(){
        ArrayList<Persona> v_reg = new ArrayList<>();
        v_reg.addAll(VaxRegister);
        return v_reg;
    }

    public ArrayList<Persona> getNVaxCopy(){
        ArrayList<Persona> nv_reg = new ArrayList<>();
        nv_reg.addAll(NoVaxRegister);
        return nv_reg;
    }



    @Override
    public String toString() {
        return "Registro{" +
                ", VACCINATI=" + VaxRegister +
                ", NON VACCINATI=" + NoVaxRegister +
                '}';
    }
}
