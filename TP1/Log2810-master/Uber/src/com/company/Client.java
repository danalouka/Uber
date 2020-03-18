package com.company;

public class Client {
    private int restrictionTemporelle;
    private int identifiant;
    private Sommet sommetDepart;
    private Sommet sommetDestination;
    private int tempsPasse;

    //constructeur
    public Client(int id, Sommet dep, Sommet arriv, int rest){

        identifiant = id;
        sommetDepart = dep;
        sommetDestination = arriv;
        restrictionTemporelle = rest;
        tempsPasse = 0;
    }

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

    public void setRestrictionTemporelle(int rest){
        restrictionTemporelle = rest;
    }
    public void setIdentifiant(int id){
        identifiant = id;
    }
    public void setSommetDepart(Sommet s){
        sommetDepart = s;
    }
    public void setSommetDestination(Sommet s){
        sommetDestination = s;
    }
    public void setTempsPasse(int temps){
        tempsPasse = temps;
    }
}
