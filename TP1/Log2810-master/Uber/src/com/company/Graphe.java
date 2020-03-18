package com.company;

import org.w3c.dom.html.HTMLImageElement;

import java.util.*;

public class Graphe {

    private LinkedList<Sommet> listeSommets;

    public Graphe() {
        listeSommets = new LinkedList<Sommet>();
    }

    public LinkedList<Sommet> getListeSommets() { return listeSommets; }

    public void creerGraphe(Scanner sc) {
        String ligneLue = sc.next();
        String[] valeurs = ligneLue.split(",");
        if (valeurs.length == 2) {
            creerSommets(valeurs);
        } else if (valeurs.length == 3) {
            creerSommetsAdjacents(valeurs);
        }
        if (sc.hasNext()) {
            creerGraphe(sc);
        }
        return;
    }

    private void creerSommetsAdjacents(String[] valeurs) {
        Sommet sfin = chercherSommet(Integer.parseInt(valeurs[1]));
        Arc a = new Arc(sfin, Integer.parseInt(valeurs[2]));
        Sommet sDebut = chercherSommet(Integer.parseInt(valeurs[0]));
        Arc a1 = new Arc(sDebut, Integer.parseInt(valeurs[2]));
        sDebut.ajouterArc(a);
        sfin.ajouterArc(a1);
    }

    public Sommet chercherSommet(int valeur) {
        for (Sommet s : listeSommets) {
            if (s.getValeur() == valeur) {
                return s;
            }
        }
        return null;
    }

    private void creerSommets(String[] valeurs) {
        Sommet s = new Sommet(Integer.parseInt(valeurs[0]), Integer.parseInt(valeurs[1]) == 1 ? true : false);
        listeSommets.push(s);
    }

    public void afficherGraphe(Scanner sc) {
        creerGraphe(sc);
        for (Sommet sommet : listeSommets) {
            System.out.print("(" + sommet.getValeur() + ", " + sommet.estRecharge() + ", (");
            for (Arc arc : sommet.getArcs()) {
                System.out.print("(" + arc.getSommet().getValeur() + ", " + arc.getPoids() + "), ");
            }
            System.out.print("))\n");
        }
    }

    public void plusCourtChemin(Sommet origine, Sommet destination) {

        for (Sommet sommet : listeSommets) {
            sommet.setPoidsAvecDepart(1000000);
        }
        origine.setPoidsAvecDepart(0);
        Graphe sousGraphe = new Graphe();
        origine.getPlusCourtChemin().add(origine);
        LinkedList<Sommet> complementSousGraphe = (LinkedList<Sommet>) listeSommets.clone();
        while (!(sousGraphe.listeSommets.contains(destination))) {
            //u un sommet non dans S et avec L(u) minimal
            Sommet u = trouverSommetAvecPoidsMinimal(complementSousGraphe);
            if (!(sousGraphe.listeSommets.contains(u))) {
                complementSousGraphe.remove(u);
                sousGraphe.listeSommets.add(u);
                MiseAJourSommetsVoisins(u, sousGraphe);
            }
        }

        //affichage des informations
        System.out.println("La longueur du chemin le plus court en minutes est " + destination.getPoidsAvecDepart());
        System.out.println("Le plus court chemin est le suivant : ");
        LinkedList<Sommet> cheminFinal = destination.getPlusCourtChemin();
        for (Sommet sommet : cheminFinal) {
            System.out.print(sommet.getValeur() + ", ");
        }
        System.out.print("\n");
        System.out.print("Le pourcentage final d’énergie est " + (100-destination.getPoidsAvecDepart()));

    }

//    public void recharger(Sommet u) {
//        if (tempsParcouruDesDepart > 85)
//        {
//            if (u.estRecharge()){
//                tempsParcouruDesDepart += 10;
//                System.out.print("recharge");
//            }
//            else {
//                //idk
//            }
//        }
//
//    }

    public void traiterRequetes(Scanner sc) {

        //lire requete (classe avec depart, et liste sommets depart et liste sommets dest)
        Sommet depart = chercherSommet(Integer.parseInt(sc.next()));
        int tempsDepartConducteur = 0;




    }

    public Sommet trouverSommetAvecPoidsMinimal(LinkedList<Sommet> complementSousGraphe) {
        Sommet sommet = Collections.min(complementSousGraphe, Comparator.comparing(s -> s.getPoidsAvecDepart()));
        return sommet;
    }

    public void MiseAJourSommetsVoisins(Sommet u, Graphe sousGraphe) {
        for (Arc arc : u.getArcs()) {
            if (!(sousGraphe.listeSommets.contains(arc.getSommet()))){
                if(u.getPoidsAvecDepart() + (arc.getPoids()) < arc.getSommet().getPoidsAvecDepart()){
                    arc.getSommet().setPoidsAvecDepart(arc.getPoids() + u.getPoidsAvecDepart());
                    // mise a jour des poids des sommets voisins
                    LinkedList<Sommet> plusCourtCheminDuU = (LinkedList<Sommet>) u.getPlusCourtChemin().clone();       // mise a jour du chemin le plus court
                    plusCourtCheminDuU.add(arc.getSommet());
                    arc.getSommet().setPlusCourtChemin(plusCourtCheminDuU);
                }
            }
        }
    }
}
