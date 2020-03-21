package com.company;

import java.util.LinkedList;

public class Conducteur {

    //capacité maximale de clients qu'un conducteur peut ramasser
    public final int CAPACITE_MAX = 4;

    //les atributs
    private int pourcentageEnergie;       //le pourcentage d'energie de la voiture du conducteur
    private int tempsPasse;               //le temps depuis sont depart pour ramasser les clients
    private LinkedList<Client> voiture;   //la liste des clients dans la voiture
    private int capacite;                 //nombre de personnes dans la voiture
    Sommet sommetDepart;                  //le sommet de depart de depart du conducteur

    //le constructeur
    public Conducteur(Sommet depart){

        pourcentageEnergie = 100;
        tempsPasse = 0;
        voiture = new LinkedList<Client>();
        capacite = 0;
        sommetDepart = depart;
    }

    //les getters
    public int getTempsPasse(){
        return tempsPasse;
    }
    public int getCapacite(){
        return capacite;
    }
    public int getPourcentageEnergie(){
        return pourcentageEnergie;
    }
    public LinkedList<Client> getVoiture(){
        return voiture;
    }

    //les setters
    public void modifierTempsPasse(int temps){
        tempsPasse += temps;
    }
    public void modifierPourcentageEnergie(int pourcentage){
        pourcentageEnergie -= pourcentage;
    }

    // fonction qui ramasse un client
    public void ramasserClient(Client requete) {
        System.out.print("Client #" + requete.getIdentifiant() + " monte -> ");
        voiture.add(requete);
        capacite++;
    }

    // fonction qui depose un client
    public void deposerClient(Client requete) {
        System.out.print("Client #" + requete.getIdentifiant() + " descend -> ");
        voiture.remove(requete);
        capacite--;
    }

}
