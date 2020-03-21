package com.company;

import java.util.LinkedList;

//Un sommet dans un graphe represente un arrondissement
public class Sommet {

    //attributs genereaux: valeur (constitue d'un identifiant, le numero de l'arrondissemnt), aRecharge (bool qui indique s'il y a un station de recharge), arcs (liste des arcs entourant le sommet).
    private int valeur;
    private boolean aRecharge;
    private LinkedList<Arc> listeArcs;

    //attributs pour une étiquette, c'est-à-dire les attributs qui aident pour l'algorithme de Dijkstra.
    private int poidsAvecDepart;
    private LinkedList<Sommet> plusCourtChemin;

    //le constructeur
    public Sommet(int valeur, boolean recharge){
        this.valeur = valeur;
        this.aRecharge = recharge;
        this.listeArcs = new LinkedList<Arc>();
        this.plusCourtChemin = new LinkedList<Sommet>();
    }

    //les getters
    public int getValeur() {
        return valeur;
    }
    public int getPoidsAvecDepart() {
        return poidsAvecDepart;
    }
    public LinkedList<Arc> getArcs() {return listeArcs;}
    public LinkedList<Sommet> getPlusCourtChemin() { return plusCourtChemin; }

    //les setters
    public void setPoidsAvecDepart(int distance) { poidsAvecDepart = distance; }
    public void setPlusCourtChemin(LinkedList<Sommet> chemin) { plusCourtChemin = chemin; }

    //estRecharge() indique s'il y a une sation de recharge dans l'arrondissement, retourne un boolean.
    public boolean estRecharge(){ return aRecharge; }

    //ajouterArc(Arc arc) ajoute un arc autour de l'objet.
    public void ajouterArc(Arc arc){
        listeArcs.push(arc);
    }

}



