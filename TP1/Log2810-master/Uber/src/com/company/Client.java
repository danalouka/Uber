package com.company;

//un Client (peut etre aussi nommé Requete).
public class Client {

    //les attributs
    private int restrictionTemporelle;     //la restriction temporelle du client
    private int identifiant;               //l'identifiant du client
    private Sommet sommetDepart;           //d'ou il faut ramasser le client
    private Sommet sommetDestination;      //ou il faut deposer le client
    private int tempsPasse;                //le temps que le client reste dans une voiture d'un conducteur

    //le constructeur
    public Client(int id, Sommet dep, Sommet arriv, int rest) {

        identifiant = id;
        sommetDepart = dep;
        sommetDestination = arriv;
        restrictionTemporelle = rest;
        tempsPasse = 0;
    }

    //les getters
    public int getRestrictionTemporelle(){
        return restrictionTemporelle;
    }
    public int getIdentifiant(){
        return identifiant;
    }
    public Sommet getSommetDepart(){
        return sommetDepart;
    }
    public Sommet getSommetDestination(){
        return sommetDestination;
    }
    public int getTempsPasse(){
        return tempsPasse;
    }

    //les setters
    public void setRestrictionTemporelle(int rest){
        restrictionTemporelle = rest;
    }
    public void setTempsPasse(int temps){
        tempsPasse = temps;
    }

}
