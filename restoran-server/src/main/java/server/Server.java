/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import domen.Konobar;
import forme.ServerskaForma;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import niti.ObradaKlijentskiihZahteva;

/**
 *
 * @author Cofara
 */
public class Server extends Thread{
    
    boolean kraj = false;
    ServerSocket serverSoket;
    List<ObradaKlijentskiihZahteva> klijenti;
    private final List<Konobar> ulogovaniKonobari = new ArrayList<>();
    private final ServerskaForma sf;

    public Server(ServerskaForma sf) {
        klijenti = new ArrayList<ObradaKlijentskiihZahteva>();
        this.sf = sf;
    }

    @Override
    public void run() {
        try {
            int port = Integer.parseInt(konfiguracija.Konfiguracija.getInstanca().getProperty("port"));
            serverSoket = new ServerSocket(port);
             
            while(!kraj){
                Socket s = serverSoket.accept();
                System.out.println("Klijent je povezan");
            
                ObradaKlijentskiihZahteva okz = new ObradaKlijentskiihZahteva(s,this);
            
                klijenti.add(okz);
            
             okz.start();
            
            
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    
    public void zaustaviServer(){
        
        kraj = true;
        try {
            
            for(ObradaKlijentskiihZahteva k:klijenti){
                k.prekini();
            }
            
            serverSoket.close();
        } catch (IOException ex) {
            
            ex.printStackTrace();

        }
    }
    
    public void osveziFormu() {
        StringBuilder sb = new StringBuilder();

        boolean imaUlogovanih = false;
            for (ObradaKlijentskiihZahteva okz : klijenti) {
                Konobar k = okz.getK();
                if (k != null) {
                    imaUlogovanih = true;
                    sb.append("Konobar: ")
                      .append(k.getIme())
                      .append(" ")
                      .append(k.getPrezime())
                      .append(" (")
                      .append(k.getKorisnickoIme())
                      .append(")")
                      .append("<br>");
                }
            }

            if (!imaUlogovanih) {
                sb.append("Nema trenutno ulogovanih konobara.");
            }

            if (sf != null && sf.getjLabelKonobari() != null) {
                sf.getjLabelKonobari().setText("<html>" + sb.toString() + "</html>");
            }
    }


    
}
