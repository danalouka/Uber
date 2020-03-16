package com.company;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graphe {

    private LinkedList<Sommet> listeSommets;

    public Graphe() {
        listeSommets = new LinkedList<Sommet>();
    }

//    public void lireArrondissements(String filePath){
//        try{
//            String ligneLue = null;
//            FileReader entree = new FileReader(filePath);
//            BufferedReader tampon = new BufferedReader(entree);
//            while ((ligneLue = tampon.readLine()) != null) {
//                String[] split = ligneLue.split(","); // ca lit juste les lignes avec 1 virgule
//                if (split.length == 2) {
//                    Boolean recharge = false;
//                    if (Integer.parseInt(split[1]) == 1) {
//                        recharge = true;
//                    }
//                    listeSommets.add(new Sommet(Integer.parseInt(split[0]), recharge));
//
//                } else if (ligneLue.equals("")) {
//                    break;
//                }
//            }
//            while ((ligneLue = tampon.readLine()) != null) {
//                String[] split = ligneLue.split(",");
//
//                if (split.length == 3){
//
//
//                    if(listeSommets.contains(Integer.parseInt(split[0])) && listeSommets.contains(Integer.parseInt(split[1]))){
//                        Sommet debutArc;
//                       for (Sommet sommet : listeSommets) {
//                            if (sommet.getValeur() == Integer.parseInt(split[0]))
//                                 debutArc = sommet;
//                        }
//                    }
//
//
//
//
//                    Sommet debutArc = new Sommet(Integer.parseInt(split[0]));         //that way we are not using les sommets that we already have
//                    Sommet finArc = new Sommet(Integer.parseInt(split[1]));
//                    int tempsParcours = Integer.parseInt(split[2]);
//                    listeArcs.add(new Arc(debutArc,finArc,tempsParcours));
//                }
//                else if (split.length <3) {
//                    break;
//                }
//            }
//
//
//        } catch (IOException e){
//            System.out.print("Erreur d'initialisation en lecture dans Graphe");
//        }
//    }


    public void creerGraphe(Scanner sc) {
            String ligneLue = sc.next();
            if(sc.hasNext() == false){
                return;
            }
            else {
                String[] valeurs = ligneLue.split(","); // ca lit juste les lignes avec 1 virgule
                if (valeurs.length == 2){
                    creerSommets(valeurs);
                } else if (valeurs.length == 3) {
                    creerSommetsAdjacents(valeurs);
                }
                creerGraphe(sc);
            }
    }

    private void creerSommetsAdjacents(String[] valeurs) {
        Sommet sfin = chercherSommet(Integer.parseInt(valeurs[1]));
        Arc a = new Arc(sfin, Integer.parseInt(valeurs[2]));
        Sommet sDebut = chercherSommet(Integer.parseInt(valeurs[0]));
        sDebut.ajouterArc(a);
    }

    public Sommet chercherSommet(int valeur) {
        for (Sommet s : listeSommets){
            if(s.getValeur() == valeur){
                return s;
            }
        }
        return null;
    }

    private void creerSommets(String[] valeurs) {
        Sommet s = new Sommet(Integer.parseInt(valeurs[0]), Integer.parseInt(valeurs[1]) == 1 ? true : false);
        listeSommets.push(s);
    }

    public void afficherGraphe(Scanner sc){
        creerGraphe(sc);
        for (Sommet sommet : listeSommets){
            System.out.print("(" + sommet.getValeur() + ", " + sommet.isRecharge() + ", (");
            for (Arc arc : sommet.getArcs()) {
               System.out.print("(" + arc.getSommet().getValeur() + ", " + arc.getPoids()+ "), ");
            }
            System.out.print("))\n");
        }
    }

    public void plusCourtChemin(Sommet origine, Sommet destination){
    // La fonction affiche le pourcentage final d’énergie dans les batteries de la voiture, le plus court chemin utilisé,
    // la longueur de ce dernier en minutes

        for (Sommet sommet : listeSommets) { sommet.setPoidsAvecDepart(1000000) ; }
        origine.setPoidsAvecDepart(0);
        Graphe sousGraphe = new Graphe();

        while (!(sousGraphe.listeSommets.contains(destination))) {
            //u un sommet non dans S et avec L(u) minimal
            Sommet u = trouverSommetAvecPoidsMinimal();
            if (!(sousGraphe.listeSommets.contains(u))) {
                sousGraphe.listeSommets.push(u);
                MiseAJourSommetsVoisins(u);
            }
        }

        //affichage des informations
        System.out.println("La longueur du chemin le plus court en minutes" + destination.getPoidsAvecDepart());
        System.out.println("Le plus court chemin est le suivant : ");
        LinkedList<Sommet> cheminFinal = destination.getPlusCourtChemin();
        for (Sommet sommet : cheminFinal){
            System.out.println(sommet.getValeur() + ", ");
        }

    }

    public Sommet trouverSommetAvecPoidsMinimal(){
        Sommet sommet =  Collections.min(listeSommets, Comparator.comparing(s -> s.getPoidsAvecDepart()));
        return sommet;
    }

    public void MiseAJourSommetsVoisins(Sommet u) {
        for (Arc arc : u.getArcs()) {
            arc.getSommet().setPoidsAvecDepart(arc.getPoids() + u.getPoidsAvecDepart());     //mise a jour des poids des sommets voisins
            LinkedList<Sommet> plusCourtChemin = arc.getSommet().getPlusCourtChemin();
            plusCourtChemin.push(arc.getSommet());
        }

    }

}
