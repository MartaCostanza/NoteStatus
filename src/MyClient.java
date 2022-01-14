import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class MyClient {
    //Il programma sfrutta l'inserimento di due parametri (indirizzo e porta) per aprire il socket destinato al client
    //che ne fa richiesta. Lato server questa azione comporta anche la creazione e l'assegnazione di un client manager (CM).

    Socket socket;
    private String address;
    private int port;

    public static void main(String args[]) {

        if (args.length != 2) {
            System.out.println("Usage: java MyClient <address> <port>");
            return;
        }

        MyClient client = new MyClient(args[0], Integer.parseInt(args[1]));
        client.start();

    }

    public MyClient(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void start() {
        System.out.println("Starting client connection to " + address + ": " + port);
        /*Inizio la creazione del socket e parto creando gli scanner ed i printwriter
          per la comunicazione tra client e client manager*/
        try {
            socket = new Socket(address, port);
            System.out.println("Started client connection to " + address + ":" + port);

            // // Il seguente printwriter consentirà la comunicazione protocollare indirizzata al server e inoltrata al CM.
            PrintWriter pw = new PrintWriter(socket.getOutputStream());

            //  Il seguente scanner consentirà la comunicazione protocollare, leggendo i messaggi inviati dal CM.
            Scanner server_scanner = new Scanner(socket.getInputStream());

            // Il seguente scanner consentirà di leggere gli input inseriti dall'utente
            Scanner user_scanner = new Scanner(System.in);

            //Stringhe usate nella comunicazione protocollare
            String msg_to_send;
            String msg_received;

            boolean go = true; //Per la stampa del menù
            int choice;

            while (go) {
                System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
                System.out.println("____________________***Welcome to NoteStatus***_______________________");
                System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
                System.out.println("");
                System.out.println("____________________MENU'___________________");
                System.out.println("1. Inserisci persona nel registro VACCINATI");
                System.out.println("2. Inserisci persona nel registro NON VACCINATI");
                System.out.println("3. Rimuovi  persona NON VACCINATA");
                System.out.println("4. Inserisci persona GUARITA dal covid ");
                System.out.println("5. Trova persona NON VACCINATA con eta' superiore ai 70 anni");
                System.out.println("6. Stampa il registro VACCINATI");
                System.out.println("7. Stampa il registro  NON VACCINATI");
                System.out.println("8. Stampa il registro GUARITI DAL COVID");
                System.out.println("9. Save");
                System.out.println("0. Quit");
                System.out.println("");
                System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
                System.out.print("INSERISCI LA TUA SCELTA -> ");

                choice = user_scanner.nextInt();
                switch (choice) {
                    case 1:
                        //Inserisci un  vaccinato
                        System.out.print("inserisci nome:");
                        String Nome = user_scanner.next();
                        System.out.print("Inserisci cognome:");
                        String Cognome = user_scanner.next();
                        System.out.print("Inserisci cf:");
                        String CodiceFiscale = user_scanner.next();
                        System.out.print("Inserisci sesso(F/M):");
                        String Sesso = user_scanner.next();
                        System.out.print("Inserisci età:");
                        int Età = user_scanner.nextInt();
                        System.out.print("Inserisci dose (1/2/3):");
                        int Dose = user_scanner.nextInt();
                        System.out.print("Inserisci tipo di vaccino effettuato:");
                        String t_vac = user_scanner.next();


                        msg_to_send = "ADD_NEW_VAX" + " " + Nome + " " + Cognome + " "
                                + CodiceFiscale + " " + Sesso + " " + Età +" "+ Dose+" "+ t_vac +  " " ;
                        System.out.println("DEBUG: Sending " + msg_to_send);
                        pw.println(msg_to_send);
                        pw.flush();
                        msg_received = server_scanner.nextLine();

                        if (msg_received.equals("ADD_NEW_OK")) {
                            System.out.println("La persona vaccinata è stata aggiunta con successo!");
                        } else if (msg_received.equals("ADD_NEW_ERROR")) {
                            System.out.println("Errore inserimento!!!");
                        } else {
                            System.out.println("ERROR: unkown message-> " + msg_received);
                        }
                        break;

                    case 2:
                        //Inserisci un non vaccinato
                        System.out.print("inserisci nome:");
                        String nNome = user_scanner.next();
                        System.out.print("Inserisci cognome:");
                        String nCognome = user_scanner.next();
                        System.out.print("Inserisci cf:");
                        String nCodiceFiscale = user_scanner.next();
                        System.out.print("Inserisci sesso(F/M):");
                        String nSesso = user_scanner.next();
                        System.out.print("Inserisci numero cellulare:");
                        String nN_cell = user_scanner.next();
                        System.out.print("Inserisci età:");
                        int nEtà = user_scanner.nextInt();
                        int nDose = 0;


                        msg_to_send = "ADD_NEW_NOVAX" + " " + nNome + " " + nCognome + " "
                                + nCodiceFiscale + " " + nSesso + " " + nN_cell + " " + nEtà + " " + nDose + " "  ;
                        System.out.println("DEBUG: Sending " + msg_to_send);
                        pw.println(msg_to_send);
                        pw.flush();
                        msg_received = server_scanner.nextLine();

                        if (msg_received.equals("ADD_NEW_OK")) {
                            System.out.println("La persona non vaccinata è stata aggiunta con successo!");
                        } else if (msg_received.equals("ADD_NEW_ERROR")) {
                            System.out.println("Errore inserimento!!!");
                        } else {
                            System.out.println("ERROR: unkown message-> " + msg_received);
                        }
                        break;

                    case 3:
                        System.out.println("Inserisci il CF della persona NON VACCINATA da eliminare dall'elenco NoVax:");
                        String cf = user_scanner.next();

                        msg_to_send = "REMOVE_NOVAX" + " " + cf;
                        System.out.println("Debug: Sto inviando " + msg_to_send);
                        pw.println(msg_to_send);
                        pw.flush();

                        msg_received = server_scanner.nextLine();
                        if(msg_received.equals("REMOVE_NV_OK")){
                            System.out.println("Utente novax eliminato con successo");
                        }else if (msg_received.equals("REMOVE_ERROR")){
                            System.out.println("Errore, impossibile eliminare utente");
                        }else {
                            System.out.println("Errore: messaggio sconosciuto-> " + msg_received);
                        }

                        break;

                    case 4:
                        System.out.println("La persona guarita da COV19 ha avuto sintomi? (SI o NO)");
                        String risposta = user_scanner.next();
                        msg_to_send = "RISPOSTA:" + " " + risposta;
                        System.out.println("DEBUG: Sending " + msg_to_send);
                        pw.println(msg_to_send);
                        pw.flush();

                        msg_received = server_scanner.nextLine();
                        if (msg_received.equals("SI")) {
                            System.out.println("Inserisci la persona guarita e i sintomi avuti-> ");
                            System.out.print("inserisci nome: ");
                            String gNome = user_scanner.next();
                            System.out.print("Inserisci cognome:");
                            String gCognome = user_scanner.next();
                            System.out.print("Inserisci età:");
                            int gEtà = user_scanner.nextInt();
                            System.out.println("inserisci sesso(F/M):");
                            String gSesso= user_scanner.next();
                            System.out.print("Inserisci numero cellulare:");
                            String gN_cell = user_scanner.next();
                            System.out.print("Inserisci sintomi avuti:");
                            String gSintomi = user_scanner.next();
                            System.out.println("Inserisci codice fiscale:");
                            String gCf= user_scanner.next();

                            msg_to_send = "ADD_NEW_S_GUARITO" + " " + gNome + " " + gCognome + " "+ gEtà + " "+gSesso+" "+gCf+ " "
                                    + gN_cell + " " + gSintomi+ " " ;
                            System.out.println("DEBUG: Sending " + msg_to_send);
                            pw.println(msg_to_send);
                            pw.flush();

                            msg_received = server_scanner.nextLine();
                            if (msg_received.equals("ADD_NEW_OK")) {
                                System.out.println("La persona guarita da COV19 è stata aggiunta con successo!");
                            } else if (msg_received.equals("ADD_NEW_ERROR")) {
                                System.out.println("Errore inserimento!!!");
                            } else {
                                System.out.println("ERROR: unkown message-> " + msg_received);
                            }

                        } else if (msg_received.equals("NO")){
                            System.out.println("Inserisci la persona guarita precedentemente ASINTOMATICA-> ");
                            System.out.print("inserisci nome: ");
                            String gNome = user_scanner.next();
                            System.out.print("Inserisci cognome:");
                            String gCognome = user_scanner.next();
                            System.out.print("Inserisci età:");
                            int gEtà = user_scanner.nextInt();
                            System.out.print("Inserisci sesso:");
                            String gSesso = user_scanner.next() ;
                            System.out.println("Inserisci codice fiscale:");
                            String gCf= user_scanner.next();
                            System.out.print("Inserisci numero cellulare:");
                            String gN_cell = user_scanner.next();
                            String gSintomi = "NESSUNO";

                            msg_to_send = "ADD_NEW_AS_GUARITO" + " " + gNome + " " + gCognome + " "+ gEtà + " "+gSesso+" "+gCf+ " "
                                    + gN_cell + " " + gSintomi+ " " ;
                            System.out.println("DEBUG: Sending " + msg_to_send);
                            pw.println(msg_to_send);
                            pw.flush();

                            msg_received = server_scanner.nextLine();
                            if (msg_received.equals("ADD_NEW_OK")) {
                                System.out.println("La persona guarita da COV19 è stata aggiunta con successo!");
                            } else if (msg_received.equals("ADD_NEW_ERROR")) {
                                System.out.println("Errore inserimento!!!");
                            } else {
                                System.out.println("ERROR: unkown message-> " + msg_received);
                            }

                        }else {
                            System.out.println("ERRORE INSERIMENTO");
                        }

                        break;

                    case 5://Trova persona no Vax con età superiore ai 70"
                        msg_to_send = "STAMP_NOVAX_70";
                        System.out.println("DEBUG: Sending " + msg_to_send);
                        pw.println(msg_to_send);
                        pw.flush();

                        msg_received = server_scanner.nextLine();
                        boolean n = true;
                        if(msg_received.equals("BEGIN")){
                            System.out.println("Sto ricevendo NoVaxRegister...");
                            System.out.println("LE PERSONE CON ETÀ  > 70 ANNI SONO: ");
                            while(n){
                                msg_received = server_scanner.nextLine();
                                if(msg_received.equals("END")){
                                    n = false;
                                    System.out.println("NoVaxRegister finito!");
                                }else{
                                    System.out.println(msg_received);
                                }
                            }
                        }else{
                            System.out.println("Unknown response: " + msg_received);
                        }

                        break;

                    case 6:
                        msg_to_send = "STAMP_VAX";
                        System.out.println("DEBUG: Sending " + msg_to_send);
                        pw.println(msg_to_send);
                        pw.flush();

                        msg_received = server_scanner.nextLine();
                        boolean s = true;
                        if(msg_received.equals("BEGIN")){
                            System.out.println("Sto ricevendo VaxRegister...");
                            while(s){
                                msg_received = server_scanner.nextLine();
                                if(msg_received.equals("END")){
                                    s = false;
                                    System.out.println("VaxRegister finito!");
                                }else{
                                    System.out.println(msg_received);
                                }
                            }
                        }else{
                            System.out.println("Unknown response: " + msg_received);
                        }
                        break;
                    case 7:
                        msg_to_send = "STAMP_NOVAX";
                        System.out.println("DEBUG: Sending " + msg_to_send);
                        pw.println(msg_to_send);
                        pw.flush();

                        msg_received = server_scanner.nextLine();
                        boolean sn = true;
                        if(msg_received.equals("BEGIN")){
                            System.out.println("Sto ricevendo NoVaxRegister...");
                            while(sn){
                                msg_received = server_scanner.nextLine();
                                if(msg_received.equals("END")){
                                    sn = false;
                                    System.out.println("NoVaxRegister finito!");
                                }else{
                                    System.out.println(msg_received);
                                }
                            }
                        }else{
                            System.out.println("Unknown response: " + msg_received);
                        }
                        break;
                    case 8:
                        msg_to_send = "STAMP_GUARITI";
                        System.out.println("DEBUG: Sending " + msg_to_send);
                        pw.println(msg_to_send);
                        pw.flush();

                        msg_received = server_scanner.nextLine();
                        boolean g = true;
                        if(msg_received.equals("BEGIN")){
                            System.out.println("Sto ricevendo RegGuariti...");
                            while(g){
                                msg_received = server_scanner.nextLine();
                                if(msg_received.equals("END")){
                                    g = false;
                                    System.out.println("RegGuariti finito!");
                                }else{
                                    System.out.println(msg_received);
                                }
                            }
                        }else{
                            System.out.println("Unknown response: " + msg_received);
                        }
                        break;
                    case 9:
                        msg_to_send = "SAVE_V";
                        System.out.println("DEBUG: Sending " + msg_to_send);
                        pw.println(msg_to_send);
                        pw.flush();

                        msg_received = server_scanner.nextLine();
                        if (msg_received.equals("SAVE_V_OK")) {
                            System.out.println("VaxRegister salvato correttamente!");
                        }else if(msg_received.equals("SAVE_ERROR")){
                            System.out.println("Error saving file");
                        }else {
                            System.out.println("Unknown message: " + msg_received);
                        }

                        msg_to_send = "SAVE_N";
                        System.out.println("DEBUG: Sending " + msg_to_send);
                        pw.println(msg_to_send);
                        pw.flush();

                        msg_received = server_scanner.nextLine();
                        if (msg_received.equals("SAVE_N_OK")) {
                            System.out.println("NoVaxRegister salvato correttamente!");
                        }else if(msg_received.equals("SAVE_ERROR")){
                            System.out.println("Error saving file");
                        }else {
                            System.out.println("Unknown message: " + msg_received);
                        }

                        msg_to_send = "SAVE_G";
                        System.out.println("DEBUG: Sending " + msg_to_send);
                        pw.println(msg_to_send);
                        pw.flush();

                        msg_received = server_scanner.nextLine();
                        if (msg_received.equals("SAVE_GS_OK")) {
                            System.out.println("RegSGuariti salvato correttamente!");
                        }else if (msg_received.equals("SAVE_GA_OK")) {
                            System.out.println("RegASGuariti salvato correttamente!");

                        }else if(msg_received.equals("SAVE_ERROR")){
                            System.out.println("Error saving file");
                        }else {
                            System.out.println("Unknown message: " + msg_received);
                        }

                        break;
                    case 0:
                        //Chiudo
                        go = false;
                        System.out.println("Quitting Client...");
                        msg_to_send = "QUIT";
                        pw.println(msg_to_send);
                        pw.flush();
                        break;

                    default:
                        System.out.println("Choise " + choice + "not valid!");
                        break;

                }
            }
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
