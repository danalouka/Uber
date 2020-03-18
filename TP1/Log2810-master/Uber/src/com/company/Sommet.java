package com.company;

import java.util.LinkedList;

public class Sommet {

    // attributs genereaux
    private int valeur;
    private boolean recharge;
    private LinkedList<Arc> arcs;

    //attributs pour une étiquette
    private int poidsAvecDepart;
    private LinkedList<Sommet> plusCourtChemin;

    public Sommet(int valeur, boolean recharge){
        this.valeur = valeur;
        this.recharge = recharge;
        this.arcs = new LinkedList<Arc>();
        this.plusCourtChemin = new LinkedList<Sommet>();
    }

    public int getValeur() {
        return valeur;
    }
    public int getPoidsAvecDepart() {
        return poidsAvecDepart;
    }
    public LinkedList<Arc> getArcs() {return arcs;}
    public LinkedList<Sommet> getPlusCourtChemin() { return plusCourtChemin; }
    public void setPoidsAvecDepart(int distance) { poidsAvecDepart = distance; }
    public boolean estRecharge(){
        return recharge;
    }
    public void setPlusCourtChemin(LinkedList<Sommet> chemin) { plusCourtChemin = chemin; }


    public void ajouterArc(Arc arc){
        arcs.push(arc);
    }
}



