package com.company;

import java.util.LinkedList;

public class Conducteur {

    private final int CAPACITE = 4;
    private int pourcentageEnergie;
    private int tempsPasse;
    private LinkedList<Client> requetes;


    //constructeur
    public Conducteur(){

        pourcentageEnergie = 100;
        tempsPasse = 0;
        requetes = new LinkedList<Client>();

    }

    //les getters
    public int getTempsPasse(){
        return tempsPasse;
    }
    public int getPourcentageEnergie(){
        return pourcentageEnergie;
    }
    public LinkedList<Client> getRequetes(){
        return requetes;
    }

    //les setters
    public void setTempsPasse(int temps){
        tempsPasse = temps;
    }
    public void setPourcentageEnergie(int pourcentage){
        pourcentageEnergie = pourcentage;
    }

    public void ajouterRequete(Client requete){
        requetes.add(requete);
    }

}
