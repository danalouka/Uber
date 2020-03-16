package com.company;

public class Arc {

    private Sommet sommet;
    private int temps;

    public Arc(Sommet sommet,int poids){
        this.sommet = sommet;
        this.temps = poids;
    }

    public Sommet getSommet() {
        return sommet;
    }
    public int getPoids() {
        return temps;
    }

    public void setPoids(int poids) { this.temps = poids; }
    public void setSommet(Sommet sommet) {
        this.sommet = sommet;
    }

}