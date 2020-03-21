package com.company;

public class Arc {

    //les attributs, un arc se constitue d'un sommet et d'un 'ligne' qui lie le sommet avec l'objet, c'est en effet la valeur pondéré (le temps).
    private Sommet sommet;
    private int temps;

    //le constructeur
    public Arc(Sommet sommet,int poids){
        this.sommet = sommet;
        this.temps = poids;
    }

    //les getters, retournent le sommet et le la valeur du temps.
    public Sommet getSommet() { return sommet; }
    public int getPoids() {
        return temps;
    }

}