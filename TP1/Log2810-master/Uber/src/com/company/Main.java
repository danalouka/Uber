package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {

//    FileReader entree = new FileReader(nomFichier);
//    BufferedReader tampon = new BufferedReader(entree);

    public static void AfficherOptions() throws FileNotFoundException {
        Graphe graphe = new Graphe();
        boolean repeter = true;;

        while (repeter) {
            System.out.println("\n\nMenu :");
            System.out.println("\n (a) Mettre à jour la carte. \n (b) Déterminer le plus court chemin sécuritaire.\n (c) Traiter les requêtes.\n (d) Quitter. ");
            Scanner scanner = new Scanner(System.in);
            System.out.println(" \nSVP choisir a, b, c ou d.");
            char option = scanner.next().charAt(0);
            switch (option) {
                case 'a':              // permet de lire une nouvelle carte afin de créer le graphe correspondant
                {
                    graphe.getListeSommets().clear();
                    Scanner sc = new Scanner(new File("/Users/User/Desktop/Uber/TP1/Log2810-master/Uber/arrondissements.txt"));
                    graphe.afficherGraphe(sc);
                    repeter = true;
                    break;
                }
                case 'b':
                    // permet de déterminer le plus court chemin sécuritaire d’après les points de départ et d’arrivée. Pour ce faire, les paramètres doivent être demandés par la console
                {
                    //il faut s'assurer que le graphe a ete creee!!!!!!!!!!!!!
                    // Si une nouvelle carte est lue, les options (b) et (c) doivent être réinitialisées!!!!!!!!!!!!!
                    if (graphe.getListeSommets().size()==0){
                        System.out.println(" \nLe graphe est vide.");
                    }
                    else{
                    int depart = Integer.parseInt(scanner.next());
                    int destination = Integer.parseInt(scanner.next());
                    Sommet sDepart = graphe.chercherSommet(depart);
                    Sommet sDestination = graphe.chercherSommet(destination);
                    sDepart.getPlusCourtChemin().clear();
                    sDestination.getPlusCourtChemin().clear();
                    graphe.plusCourtChemin(sDepart, sDestination);
                    }
                    repeter = true;

                    break;
                }

                case 'c': {
                    if (graphe.getListeSommets().size()==0){
                        System.out.println(" \nLe graphe est vide.");
                    }

                   // graphe.getListeSommets().clear();
                    Scanner sc = new Scanner(new File("/Users/User/Desktop/Uber/TP1/Log2810-master/Uber/requetes.txt"));
                    graphe.traiterRequetes(sc);
                    repeter = true;
                    break;
                }

                case 'd': {
                    repeter = false;
                    break;
                }
                default: {
                    System.out.println(" \nMauvais lettre.");
                }

            }
        }

    }

    public static void main(String[] args) throws FileNotFoundException {

        AfficherOptions();

    }
}