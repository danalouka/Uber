package com.company;

import java.util.LinkedList;
import java.util.Scanner;


public class Conducteur {

    public final int CAPACITE_MAX = 4;
    private int pourcentageEnergie;
    private int tempsPasse;
    private LinkedList<Client> voiture;
    private int capacite;
    Sommet sommetDepart;

    //constructeur
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
    public void setSommetDepart(Sommet sommet){
        sommetDepart = sommet;
    }

    public void ajouterRequete(Client requete){
        voiture.add(requete);
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

    public Client chercherVoiture(int id) {
        for (Client c : voiture) {
            if (c.getIdentifiant() == id) {
                return c;
            }
        }
        return null;
    }

}
