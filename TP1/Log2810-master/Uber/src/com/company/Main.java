package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void choisirMenu(Graphe graphe) throws FileNotFoundException {

        boolean repeter = true;

        while (repeter) {
            System.out.println("\n\nMenu :");
            System.out.println("\n (a) Mettre à jour la carte. \n (b) Déterminer le plus court chemin sécuritaire.\n (c) Traiter les requêtes.\n (d) Quitter. ");
            System.out.println("\nSVP choisir a, b, c ou d.");

            Scanner scanner = new Scanner(System.in);

            //lit le char donné par l'utilisateur
            char option = scanner.next().charAt(0);

            //choisit l'option.
            switch (option) {

                case 'a': // permet de lire une nouvelle carte afin de créer le graphe correspondant.
                {

                    graphe.getListeSommets().clear();
                    Scanner sc = new Scanner(new File("./arrondissements.txt"));
                    graphe.afficherGraphe(sc);
                    repeter = true;
                    break;
                }

                case 'b': // permet de déterminer le plus court chemin sécuritaire d’après les points de départ et d’arrivée. Pour ce faire, les paramètres doivent être demandés par la console
                {

                    if (graphe.getListeSommets().size() == 0) {
                        System.out.println(" \nLe graphe est vide. Choisir (a) pour mettre à jour la carte.");
                    }
                    else {
                        //lit les inputs de lutilisateur
                        int depart = Integer.parseInt(scanner.next());
                        int destination = Integer.parseInt(scanner.next());
                        Sommet sDepart = graphe.chercherSommet(depart);
                        Sommet sDestination = graphe.chercherSommet(destination);

                        //reinisialisation
                        sDepart.getPlusCourtChemin().clear();
                        sDestination.getPlusCourtChemin().clear();

                        //trouver plus court chemin
                        graphe.plusCourtChemin(sDepart, sDestination);
                    }

                    repeter = true;
                    break;
                }

                case 'c': //permet de traiter les requêtes reçues
                {

                    //reinitialisation
                    for (Sommet s : graphe.getListeSommets()){
                        s.getPlusCourtChemin().clear();
                    }

                    if (graphe.getListeSommets().size() == 0) {
                        System.out.println(" \nLe graphe est vide.");
                    }
                    else {
                        Scanner sc = new Scanner(new File("./requetes.txt"));
                        graphe.traiterRequetes(sc);
                    }

                    repeter = true;
                    break;
                }

                case 'd': //quitter
                {

                    repeter = false;
                    break;
                }

                default:
                {

                    System.out.println(" \nMauvais lettre.");
                }
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {

        Graphe graphe = new Graphe();
        choisirMenu(graphe);

    }

}