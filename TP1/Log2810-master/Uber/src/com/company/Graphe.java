package com.company;

import java.util.*;

public class Graphe {

    //les attributs, un garphe est constitué d'une liste de sommets et d'une liste de requetes de clients qui sont actives sur un graphe.
    private LinkedList<Sommet> listeSommets;
    private LinkedList<Client> listeRequetes;

    //le constructeur
    public Graphe() {
        listeSommets = new LinkedList<Sommet>();
        listeRequetes = new LinkedList<Client>();
    }

    //getter de la liste des sommets.
    public LinkedList<Sommet> getListeSommets() { return listeSommets; }

    //creerGraphe(Scanner sc) (pas besoin de passer le nom du fichier!) remplie la liste des sommets. Pour chaque sommets, les arcs seront ajoutés.
    public void creerGraphe(Scanner sc) {

        String ligneLue = sc.next();
        String[] valeurs = ligneLue.split(",");

        //lire la premiere partie du fichier.
        if (valeurs.length == 2) {
            creerSommets(valeurs);
        }
        //lire la deuxieme partie du fichier.
        else if (valeurs.length == 3) {
            creerSommetsAdjacents(valeurs);
        }
        //continuer à lire recursivement tant que le fichier nest pas finit
        if (sc.hasNext()) {
            creerGraphe(sc);
        }
    }

    //creerSommetsAdjacents(String[] valeurs) : pour chaque sommet, ajoute les sommets qui lentourent.
    private void creerSommetsAdjacents(String[] valeurs) {

        //creer les deux arcs pour un ligne dans le fichier.
        Sommet sfin = chercherSommet(Integer.parseInt(valeurs[1]));
        Arc a = new Arc(sfin, Integer.parseInt(valeurs[2]));
        Sommet sDebut = chercherSommet(Integer.parseInt(valeurs[0]));
        Arc a1 = new Arc(sDebut, Integer.parseInt(valeurs[2]));

        //on ajoute l'arc dans la liste des arcs pour les deux sommets dun ligne du fichier.
        sDebut.ajouterArc(a);
        sfin.ajouterArc(a1);
    }

    //chercherSommet(int valeur) : par lidentifiant (ou valeur) du sommet, retourne le sommet
    public Sommet chercherSommet(int valeur) {
        for (Sommet s : listeSommets) {
            if (s.getValeur() == valeur) {
                return s;
            }
        }
        return null;
    }

    //creerSommets(String[] valeurs) : a laide de la premiere partie du fichier, cree les sommets et les ajoute a la liste des sommets.
    private void creerSommets(String[] valeurs) {
        Sommet s = new Sommet(Integer.parseInt(valeurs[0]), Integer.parseInt(valeurs[1]) == 1 ? true : false);
        listeSommets.push(s);
    }

    //void afficherGraphe(Scanner sc) : appelle la fonction creer Graphe et affiche celui-la.
    public void afficherGraphe(Scanner sc) {

        creerGraphe(sc);
        String output = "";
        for (Sommet sommet : listeSommets) {
            output =("(" + sommet.getValeur() + ", " + sommet.estRecharge() + ", (");
            System.out.print(output);
            for (Arc arc : sommet.getArcs()) {
                output = ("(" + arc.getSommet().getValeur() + ", " + arc.getPoids() + "), ");
                if(arc.equals(sommet.getArcs().getLast())){
                    System.out.print(output.substring(0, output.length()-2));
                }
                else {
                    System.out.print(output);
                }
            }
            System.out.print("))\n");
        }
    }

    //plusCourtChemin(Sommet origine, Sommet destination) : trouve le plus court chemin (en minutes) entre origine et destination.
    //retourne le sommet destination. Cette sommet aura le chemin entre origine et destination et aura la longueur du ce chemin.
    public Sommet plusCourtChemin(Sommet origine, Sommet destination) {

        //partie initialisation de lalgorithme Dijkstra
        for (Sommet sommet : listeSommets) { sommet.setPoidsAvecDepart(1000000);}
        origine.setPoidsAvecDepart(0);
        origine.getPlusCourtChemin().add(origine);
        Graphe sousGraphe = new Graphe();
        LinkedList<Sommet> complementSousGraphe = (LinkedList<Sommet>) listeSommets.clone();

        //partie mise a jour de lalgorithme
        while (!(sousGraphe.listeSommets.contains(destination))) {
            //u un sommet non dans S et avec L(u) minimal.
            Sommet u = trouverSommetAvecPoidsMinimal(complementSousGraphe);
            if (!(sousGraphe.listeSommets.contains(u))) {
                complementSousGraphe.remove(u);
                sousGraphe.listeSommets.add(u);
                miseAJourSommetsVoisins(u, sousGraphe);
            }
        }

        //affichage des informations
        System.out.println("La longueur du chemin le plus court en minutes est " + destination.getPoidsAvecDepart());
        System.out.println("Le plus court chemin est le suivant : ");
        String output = "";
        LinkedList<Sommet> cheminFinal = destination.getPlusCourtChemin();
        for (Sommet sommet : cheminFinal) {
            output = (sommet.getValeur() + ", ");
            if(sommet.equals(cheminFinal.getLast())){
                System.out.print(output.substring(0, output.length()-2));
            }
            else {
                System.out.print(output);
            }
        }
        System.out.print("\n");
        System.out.println("Le pourcentage final d’énergie est " + (100-destination.getPoidsAvecDepart()));

        return destination;
    }

    //méthode plusCourtChemin() sans la partie affichage.
    public Sommet plusCourtCheminSansAffichage(Sommet origine, Sommet destination) {

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
                miseAJourSommetsVoisins(u, sousGraphe);
            }
        }
        return destination;
    }

    //lireRequetes(Scanner sc) : lit le fichier contenant les requetes, ajoute les requetes a la liste des requetes. et
    //retourne la sommet de depart dun conducteur utlisant ce graphe.
    public Sommet lireRequetes(Scanner sc) {

        Sommet depart = chercherSommet(Integer.parseInt(sc.next()));

        while (sc.hasNext()) {
            String ligneLue = sc.next();
            String[] valeurs = ligneLue.split(",");
            Sommet dep = chercherSommet(Integer.parseInt(valeurs[1]));
            Sommet dest = chercherSommet(Integer.parseInt(valeurs[2]));
            listeRequetes.add(new Client(Integer.parseInt(valeurs[0]), dep, dest, Integer.parseInt(valeurs[3])));
        }

        return depart;
    }

    //recharger(Conducteur conducteur, int longueurChemin, LinkedList<Sommet> chemin) recharge la voiture du conducteur
    //s'il la logueur du prochain chemin mettera le pourcentage denergie de la voiture en dessous de 15%.
    //retourne un boulean aRechergeait qui indique si le conducteur a pu recharger la batterie sur son prochain chemin ou non.
    public boolean recharger(Conducteur conducteur, int longueurChemin, LinkedList<Sommet> chemin) {
        boolean aRechergeait = true;
        if (conducteur.getPourcentageEnergie() - longueurChemin < 15) {
            aRechergeait = false;
            for (Sommet s : chemin) {
                if (s.estRecharge()) {
                    // 10 minutes pour recharger la voiture.
                    conducteur.modifierTempsPasse(10);
                    System.out.print("Recharge -> ");
                    aRechergeait = true;
                    break;
                }
            }
        }
        return aRechergeait;
    }

    //traite les requetes des clients
    public void traiterRequetes(Scanner sc) {

        //construction du conducteur et assignement du premier chemin à partir du sommet de depart
        Sommet depart = lireRequetes(sc);
        Conducteur conducteur = new Conducteur(depart);
        LinkedList<Sommet> cheminDepartRequete = plusCourtCheminSansAffichage(depart, listeRequetes.getFirst().getSommetDepart()).getPlusCourtChemin();

        // regarde si le conducteur pout ramasser quelqu'un sur notre premier chemin.
        for (Sommet s : cheminDepartRequete) {
            for (Client c : listeRequetes) {
                if (!(c.equals(listeRequetes.getFirst())) && !(c.getSommetDepart().equals(cheminDepartRequete.getLast()))) {
                    if (c.getSommetDepart().equals(s)) {
                        if (conducteur.getCapacite() < conducteur.CAPACITE_MAX) {
                            conducteur.ramasserClient(c);
                        }
                    }
                }
            }
        }

        //la batterie devient plus faible.
        conducteur.modifierPourcentageEnergie(plusCourtCheminSansAffichage(depart, listeRequetes.getFirst().getSommetDepart()).getPoidsAvecDepart()); //100-longeur

        //affichage du premier chemin
        for(Sommet s : cheminDepartRequete) {
            System.out.print(s.getValeur()+" -> ");
        }

        //initialisation avant un while.
        Client destinationSauvgarde = null;
        LinkedList<Sommet> prochainChemin = null;
        int longeurChemin = 0;
        boolean aRechergeait = true;

        while (listeRequetes.size() != 0) {

            //determine le chemin pour aller deposer le client prioritaire (premier dans la liste des requetes).
            listeRequetes.getFirst().getSommetDepart().getPlusCourtChemin().clear();
            prochainChemin = plusCourtCheminSansAffichage(listeRequetes.getFirst().getSommetDepart(), listeRequetes.getFirst().getSommetDestination()).getPlusCourtChemin();
            prochainChemin.clear();
            prochainChemin = plusCourtCheminSansAffichage(listeRequetes.getFirst().getSommetDepart(), listeRequetes.getFirst().getSommetDestination()).getPlusCourtChemin();
            prochainChemin.removeFirst();

            //ramasser le client de son point de depart sil ne lest pas deja dans la voiture et sil la capacite le laisse.
            if (conducteur.getCapacite()<conducteur.CAPACITE_MAX && !(conducteur.getVoiture().contains(listeRequetes.getFirst())))
                conducteur.ramasserClient(listeRequetes.getFirst());

            //sauveguarder la requete et l'enlever de la liste des requetes
            destinationSauvgarde = listeRequetes.getFirst();
            listeRequetes.removeFirst();

            //recharge si c'est requis et modifier temps passe du conducteur.
            if (!listeRequetes.isEmpty())
                longeurChemin = plusCourtCheminSansAffichage(listeRequetes.getFirst().getSommetDepart(), listeRequetes.getFirst().getSommetDestination()).getPoidsAvecDepart();
            conducteur.modifierPourcentageEnergie(longeurChemin);
            conducteur.modifierTempsPasse(longeurChemin);
            aRechergeait = recharger(conducteur, longeurChemin, prochainChemin);  //moo hon ma7ala! la2no affichage!!!!!
            if (!aRechergeait) {    //sil na pas pu recharger on his way
                System.out.print("Pas de station de recharge -> ");
                aRechergeait = true;
            }

            //sur le chemin pour deposer quelqu'un, voir si on peut ramasser quelqu'un sur notre chemin.
            for (Sommet s : prochainChemin) {
                for (Client c : listeRequetes) {
                    if (c.getSommetDepart().equals(s)) {
                        if (conducteur.getCapacite() < conducteur.CAPACITE_MAX && !(conducteur.getVoiture().contains(c)))  {
                            //we gotta check if restriction here!!!!!!!!!!!!!
                            //we stopped to pick up someone on our way
                            conducteur.ramasserClient(c);
                        }
                    }
                }
                System.out.print(s.getValeur()+" -> ");
            }

            //deposer le client
            conducteur.deposerClient(destinationSauvgarde);

            //determine le chemin pour aller rammaser le client prioritaire (premier dans la liste des requetes).
            destinationSauvgarde.getSommetDestination().getPlusCourtChemin().clear();
            if (!(listeRequetes.isEmpty()) && !(conducteur.getVoiture().contains(listeRequetes.getFirst()))){
                prochainChemin = plusCourtCheminSansAffichage(destinationSauvgarde.getSommetDestination(), listeRequetes.getFirst().getSommetDepart()).getPlusCourtChemin();
            }
            else {
                if(!(listeRequetes.isEmpty()))
                    prochainChemin = plusCourtCheminSansAffichage(destinationSauvgarde.getSommetDestination(), listeRequetes.getFirst().getSommetDestination()).getPlusCourtChemin();
            }
            prochainChemin.clear();
            if (!(listeRequetes.isEmpty()) && !(conducteur.getVoiture().contains(listeRequetes.getFirst()))) {
                prochainChemin = plusCourtCheminSansAffichage(destinationSauvgarde.getSommetDestination(), listeRequetes.getFirst().getSommetDepart()).getPlusCourtChemin();
            }
            else {
                if(!(listeRequetes.isEmpty()))
                    prochainChemin = plusCourtCheminSansAffichage(destinationSauvgarde.getSommetDestination(), listeRequetes.getFirst().getSommetDestination()).getPlusCourtChemin();
            }
            if(!prochainChemin.isEmpty())
                prochainChemin.removeFirst();

            //recharge si c'est requis et modifier le temps passe du conducteur.
            if (!listeRequetes.isEmpty())
                longeurChemin = plusCourtCheminSansAffichage(destinationSauvgarde.getSommetDestination(), listeRequetes.getFirst().getSommetDestination()).getPoidsAvecDepart();
            conducteur.modifierPourcentageEnergie(longeurChemin);
            conducteur.modifierTempsPasse(longeurChemin);
            aRechergeait = recharger(conducteur, longeurChemin, prochainChemin);
            if (!aRechergeait) {    //sil na pas pu recharger on his way

                System.out.print("Pas de station de recharge -> ");
            }

            //sur le chemin pour ramasser quelqu'un, voir si on peut ramasser quelqu'un sur notre chemin.
            for (Sommet s : prochainChemin) {
                for (Client c : listeRequetes) {
                    if (!(listeRequetes.isEmpty()) && !(c.equals(listeRequetes.getFirst())) && !(c.getSommetDepart().equals(cheminDepartRequete.getLast()))) {
                        if (c.getSommetDepart().equals(s)) {
                            if (conducteur.getCapacite() < conducteur.CAPACITE_MAX) {
                                //we gotta check if restriction here!!!!!!!!!!!!!
                                conducteur.ramasserClient(c);
                            }
                        }
                    }
                }
                System.out.print(s.getValeur()+" -> ");
            }

        }

        //affichage des informations
        System.out.println("\nLa longueur du chemin en minute est " + conducteur.getTempsPasse());
        System.out.print("Le pourcentage final d'énérgie de la voiture est " + conducteur.getPourcentageEnergie());

    }

    //trouverSommetAvecPoidsMinimal(LinkedList<Sommet> complementSousGraphe) trouve le sommet avec le PoidsAvecDepart minimal
    //du LinkedList<Sommet> complementSousGraphe.
    public Sommet trouverSommetAvecPoidsMinimal(LinkedList<Sommet> complementSousGraphe) {
        Sommet sommet = Collections.min(complementSousGraphe, Comparator.comparing(s -> s.getPoidsAvecDepart()));
        return sommet;
    }

    //miseAJourSommetsVoisins(Sommet u, Graphe sousGraphe) : met a jour les des poids (temps) des sommets voisins et le chemin le plus court des le depart.
    public void miseAJourSommetsVoisins(Sommet u, Graphe sousGraphe) {
        for (Arc arc : u.getArcs()) {
            if (!(sousGraphe.listeSommets.contains(arc.getSommet()))){
                if(u.getPoidsAvecDepart() + (arc.getPoids()) < arc.getSommet().getPoidsAvecDepart()){

                    // mise a jour des poids des sommets voisins
                    arc.getSommet().setPoidsAvecDepart(arc.getPoids() + u.getPoidsAvecDepart());

                    // mise a jour du chemin le plus court
                    LinkedList<Sommet> plusCourtCheminDuU = (LinkedList<Sommet>) u.getPlusCourtChemin().clone();
                    plusCourtCheminDuU.add(arc.getSommet());
                    arc.getSommet().setPlusCourtChemin(plusCourtCheminDuU);
                }
            }
        }
    }

}
