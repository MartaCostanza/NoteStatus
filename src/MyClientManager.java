import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

   /*Il Client Manager funge da intermediario tra il client ed il server,è responsabile dello svolgimento di tutte le funzioni eseguibili dal client,
   che richiedano un'interazione con il server.
   Comunica con il client tramite i messaggii
   protocollari che precedono ciascun messaggio inviato dall'utente (ed inviandone dei propri in risposta)*/



public class MyClientManager implements Runnable {
    /*Essendo runnable, il Client Manager è implementato come se fosse un thread infatti ritroveremo il metono run().*/


    private Socket client_socket;
    private Registro VaxRegister;
    private Registro NoVaxRegister;
    private RegGuariti GuaritiSRegister;
    private RegGuariti GuaritiAsRegister;


    public MyClientManager(Socket client_socket, Registro VaxRegister, Registro NoVaxRegister, RegGuariti GuaritiSRegister,RegGuariti GuaritiAsRegister) {
        this.client_socket = client_socket;
        this.VaxRegister = VaxRegister;
        this.NoVaxRegister = NoVaxRegister;
        this.GuaritiSRegister = GuaritiSRegister;
        this.GuaritiAsRegister=GuaritiAsRegister;
    }


    @Override
    public void run() {

        String tid = Thread.currentThread().getName();
        System.out.println(tid + "-> Accepted connection from " + client_socket.getRemoteSocketAddress());
        //Inizializzo lo scanner e prinwriter usati nella comunicazione protocollare
        Scanner client_scanner = null;
        PrintWriter pw = null;

        try {
            // Vengono associati getInputStream e getOutputStream per la lettura e la scrittura dei messaggi protocollari.
            client_scanner = new Scanner(client_socket.getInputStream());
            pw = new PrintWriter(client_socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectInputStream vr;
        try {
            vr = new ObjectInputStream(new FileInputStream("VaxRegister.ser"));
            VaxRegister = (Registro) vr.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ObjectInputStream nvr;
        try {
            nvr = new ObjectInputStream(new FileInputStream("NoVaxRegister.ser"));
            NoVaxRegister = (Registro) nvr.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ObjectInputStream gc;
        try {
            gc = new ObjectInputStream(new FileInputStream("GuaritiSRegister.ser"));
            GuaritiSRegister = (RegGuariti) gc.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ObjectInputStream gca;
        try {
            gca = new ObjectInputStream(new FileInputStream("GuaritiAsRegister.ser"));
            GuaritiAsRegister = (RegGuariti) gca.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        boolean go = true;
        while (go) {
            String message = client_scanner.nextLine();
            System.out.println("Server: Received " + message);
            Scanner msg_scanner = new Scanner(message);

            String cmd = msg_scanner.next();
            System.out.println("Received Command: " + cmd);

            if (cmd.equals("ADD_NEW_VAX")) {
                String Nome = msg_scanner.next();
                String Cognome = msg_scanner.next();
                String CodiceFiscale = msg_scanner.next();
                String Sesso = msg_scanner.next();
                int Età = msg_scanner.nextInt();
                int Dose = msg_scanner.nextInt();
                String t_vac = msg_scanner.next();

                Persona p = new Persona(Nome,Cognome,Sesso,Età,CodiceFiscale,Dose,t_vac);
                VaxRegister.addVax(p);
                System.out.println("SERVER LOG: Added " + p);
                pw.println("ADD_NEW_OK");
                pw.flush();
            } else if (cmd.equals("ADD_NEW_NOVAX")) {
                String nNome = msg_scanner.next();
                String nCognome = msg_scanner.next();
                String nCodiceFiscale = msg_scanner.next();
                String nSesso = msg_scanner.next();
                String nN_cell = msg_scanner.next();
                int nEtà = msg_scanner.nextInt();
                int nDose = msg_scanner.nextInt();

                Persona pp = new Persona(nNome, nCognome, nCodiceFiscale, nSesso, nN_cell, nEtà, nDose);
                NoVaxRegister.addNoVaX(pp);
                System.out.println("SERVER LOG: Added " + pp);
                pw.println("ADD_NEW_OK");
                pw.flush();
            }

            else if (cmd.equals("REMOVE_NOVAX")) {
                String cf = msg_scanner.next();
                String responso = NoVaxRegister.removeNOVAX(cf);
                pw.println(responso);
                pw.flush();
            }else if (cmd.equals("RISPOSTA:")) {
                String risposta = msg_scanner.next();
                risposta=risposta.toUpperCase();
                System.out.println("SERVER LOG: Ho ricevuto la risposta: " + risposta);
                pw.println(risposta);
                pw.flush();

            } else if (cmd.equals("ADD_NEW_S_GUARITO")) {
                String gNome = msg_scanner.next();
                String gCognome = msg_scanner.next();
                int gEtà = msg_scanner.nextInt();
                String gN_cell = msg_scanner.next();

                String gSesso = msg_scanner.next();
                String cf = msg_scanner.next();
                String sintomi = msg_scanner.next();
                GuaritoCovPers pg = new GuaritoCovPers(gNome, gCognome,gSesso,gEtà,cf,gN_cell,sintomi);
                GuaritiSRegister.addGuaritiS(pg);
                System.out.println("SERVER LOG: Added " + pg);
                pw.println("ADD_NEW_OK");
                pw.flush();

            }else if (cmd.equals("ADD_NEW_AS_GUARITO")) {
                String gNome = msg_scanner.next();
                String gCognome = msg_scanner.next();
                int gEtà = msg_scanner.nextInt();
                String gN_cell = msg_scanner.next();
                String gSesso= msg_scanner.next();
                String cf = msg_scanner.next();
                String sintomi = msg_scanner.next();
                GuaritoCovPers pa = new GuaritoCovPers(gNome, gCognome,gSesso,gEtà,cf,gN_cell,sintomi);
                GuaritiAsRegister.addGuaritiA(pa);
                System.out.println("SERVER LOG: Added" + pa);
                pw.println("ADD_NEW_OK");
                pw.flush();


            } else if (cmd.equals("STAMP_VAX")) {
                pw.println("BEGIN");
                pw.flush();

                ArrayList<Persona> vax;
                vax = VaxRegister.getVaxCopy();
                for (Persona p : vax) {
                    pw.println(p);
                    pw.flush();
                }
                pw.println("END");
                pw.flush();

            } else if (cmd.equals("STAMP_NOVAX")) {
                pw.println("BEGIN");
                pw.flush();

                ArrayList<Persona> novax;
                novax = NoVaxRegister.getNVaxCopy();
                for (Persona p : novax) {
                    pw.println(p);
                    pw.flush();
                }
                pw.println("END");
                pw.flush();
            } else if (cmd.equals("STAMP_GUARITI")) {
                pw.println("BEGIN");
                pw.flush();

                ArrayList<GuaritoCovPers> pers_gs;
                ArrayList<GuaritoCovPers> pers_ga;
                pers_gs = GuaritiSRegister.getGSCopy();
                pers_ga= GuaritiAsRegister.getGACopy();

                for (GuaritoCovPers p_g:pers_ga) {
                  pw.println(p_g);
                  pw.flush();
                }

                for (GuaritoCovPers pg : pers_gs ) {
                    pw.println(pg);
                    pw.flush();
                }
                pw.println("END");
                pw.flush();

            } else if (cmd.equals("STAMP_NOVAX_70")) {
                pw.println("BEGIN");
                pw.flush();

                ArrayList<Persona> novax;
                novax = NoVaxRegister.getNVaxCopy();
                for (Persona p : novax) {
                    if (p.getEtà() > 70) {
                        System.out.println("PERSONA CON ETÀ >70 ANNI: "+p);
                        pw.println(p);
                        pw.flush();
                    }
                }
                pw.println("END");
                pw.flush();


            } else if (cmd.equals("SAVE_V")) {
                try {
                    var nos = new ObjectOutputStream(new FileOutputStream("VaxRegister.ser"));
                    nos.writeObject(VaxRegister);
                    nos.close();
                    pw.println("SAVE_V_OK");
                    pw.flush();
                    System.out.println("SERVER LOG: lista salvata correttamente");
                } catch (IOException e) {
                    pw.println("SAVE_ERROR");
                    pw.flush();
                    e.printStackTrace();
                }
            }

            else if (cmd.equals("SAVE_N")){
                try{
                    var los = new ObjectOutputStream(new FileOutputStream("NoVaxRegister.ser"));
                    los.writeObject(NoVaxRegister);
                    los.close();
                    pw.println("SAVE_N_OK");
                    pw.flush();
                    System.out.println("SERVER LOG: lista salvata correttamente");
                }
                catch (IOException e){
                    pw.println("SAVE_ERROR");
                    pw.flush();
                    e.printStackTrace();
                }
            }

            else if (cmd.equals("SAVE_G")){
                try{
                    var gos=new ObjectOutputStream(new FileOutputStream("GuaritiAsRegister.ser"));
                    var sos = new ObjectOutputStream(new FileOutputStream("GuaritiSRegister.ser"));
                    sos.writeObject(GuaritiSRegister);
                    gos.writeObject(GuaritiAsRegister);
                    sos.close();
                    gos.close();
                    pw.println("SAVE_GS_OK");
                    pw.flush();
                    pw.println("SAVE_GA_OK");
                    pw.flush();
                    System.out.println("SERVER LOG: lista salvata correttamente");
                }
                catch (IOException e){
                    pw.println("SAVE_ERROR");
                    pw.flush();
                    e.printStackTrace();
                }

            }
            else if (cmd.equals("QUIT")){
                System.out.println("Server: Closing connection to "+client_socket.getRemoteSocketAddress());
                try {
                    client_socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                go = false;
            }


            else {
                System.out.println("Unknown command" + message);
                pw.println("ERROR_CMD");
                pw.flush();
            }

        }
    }
}

