package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {

//    FileReader entree = new FileReader(nomFichier);
//    BufferedReader tampon = new BufferedReader(entree);

    public static void AfficherOptions () throws FileNotFoundException {

        System.out.println("\nMenu :");
        System.out.println("\n (a) Mettre à jour la carte. \n (b) Déterminer le plus court chemin sécuritaire.\n (c) Traiter les requêtes.\n (d) Quitter. ");
        Scanner scanner = new Scanner(System.in);
        System.out.println(" \nSVP choisir a, b, c ou d.");
        char option = scanner.next().charAt(0);
        Graphe graphe = new Graphe();

        switch (option){
            case 'a':              // permet de lire une nouvelle carte afin de créer le graphe correspondant
            {
                Scanner sc = new Scanner(new File("/Users/User/Desktop/TP1/Log2810-master/Uber/arrondissements.txt"));
                graphe.afficherGraphe(sc);
                AfficherOptions();
                break;
            }
            case 'b':              // permet de déterminer le plus court chemin sécuritaire d’après les points de départ et d’arrivée. Pour ce faire, les paramètres doivent être demandés par la console
            {
                //il faut s'assurer que le graphe a ete creee!!!!!!!!!!!!!
                // Si une nouvelle carte est lue, les options (b) et (c) doivent être réinitialisées!!!!!!!!!!!!!
                int depart = Integer.parseInt(scanner.next());
                int destination = Integer.parseInt(scanner.next());
                Sommet sDepart = graphe.chercherSommet(depart);
                Sommet sDestination = graphe.chercherSommet(destination);
                graphe.plusCourtChemin(sDepart, sDestination);
                AfficherOptions();
                break;
            }

            case 'c':
            {

                AfficherOptions();
                break;
            }

            case 'd':
                return;

            default : {
                System.out.println(" \nMauvais lettre.");
                AfficherOptions();
            }

        }

    }

    public static void main(String[] args) throws FileNotFoundException {

        AfficherOptions();

    }
}