package com.example.be_java.Model.repository;

import com.example.be_java.Model.EsitoNotte;
import com.example.be_java.Model.Personaggio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import static com.example.be_java.Model.Constants.DBconnection.*;
import static com.example.be_java.Model.repository.UsaPotereRepository.getPersonaggiVivi;

public class EseguiNotteRepository {

    public static EsitoNotte esegui(){

        //richiamo il metodo personaggi per poter integrare il metodo di usapotere che avevamo scritto in debug2
        ArrayList<Personaggio> personaggi=  getPersonaggiVivi();
        //creo una variabile da utilizzare nel caso del veggente

        //istanzio l'oggetto esitoNotte vuoto (contenente morto="" e indagato="", per restituirlo a fine metodo una volta riempito
        EsitoNotte esitoNotte = new EsitoNotte();



                //faccio fare randomicamente la protezione alla guardia del corpo
                Collections.shuffle(personaggi);
                personaggi.get(0).setProtected(true);

                //faccio uccidere randomicamente 1 personaggio
                Collections.shuffle(personaggi);
                if (personaggi.get(0).isAlive() && personaggi.get(0).isProtected()==false){
                    personaggi.get(0).setAlive(false);
                }



        String morto="";
        for (int i = 0; i < personaggi.size() ; i++) {
            if(personaggi.get(i).isAlive()==false){
                morto= personaggi.get(i).getNome();
            }
        }

        //segno il morto nel database se c'è
        if(!morto.equalsIgnoreCase("")) {
            try {
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement pstmt = null;
                for (int i = 0; i < personaggi.size(); i++) {
                    String QUERY = "UPDATE personaggi isAlive=false where nome==? ";
                    pstmt = conn.prepareStatement(QUERY);
                    pstmt.setString(1, morto);//nome personaggio

                    pstmt.executeUpdate();
                }
                pstmt.close(); //chiudo lo statement
                conn.close(); //chiudo la connessione
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return esitoNotte;
    }
}

