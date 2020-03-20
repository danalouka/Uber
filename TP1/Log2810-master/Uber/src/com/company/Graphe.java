package com.company;

import org.w3c.dom.html.HTMLImageElement;

import java.util.*;

public class Graphe {

    private LinkedList<Sommet> listeSommets;
    private LinkedList<Client> listeRequetes;

    public Graphe() {
        listeSommets = new LinkedList<Sommet>();
        listeRequetes = new LinkedList<Client>();
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

    public Sommet plusCourtChemin(Sommet origine, Sommet destination) {

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
        System.out.println("Le pourcentage final d’énergie est " + (100-destination.getPoidsAvecDepart()));

        return destination;
    }

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
                MiseAJourSommetsVoisins(u, sousGraphe);
            }
        }
        return destination;
    }

        //retourne le sommet de depart
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

    public Client chercherRequete(int id) {
        for (Client c : listeRequetes) {
            if (c.getIdentifiant() == id) {
                return c;
            }
        }
        return null;
    }

    public void traiterRequetes(Scanner sc) {

        Sommet depart = lireRequetes(sc);
        Conducteur conducteur = new Conducteur(depart);

        LinkedList<Sommet> cheminDepartRequete = plusCourtCheminSansAffichage(depart, listeRequetes.getFirst().getSommetDepart()).getPlusCourtChemin();

        //on regarde si nous pouvons ramasser quelqu'un sur notre chemin.
        for (Sommet s : cheminDepartRequete) {
            for (Client c : listeRequetes) {
                if (!(c.equals(listeRequetes.getFirst())) && !(c.getSommetDepart().equals(cheminDepartRequete.getLast()))) {
                    if (c.getSommetDepart().equals(s)) {
                        if (conducteur.getCapacite() < conducteur.CAPACITE_MAX) {
                            //we gotta check if restriction here!!!!!!!!!!!!!
                            conducteur.ramasserClient(c);
                        }
                    }
                }
            }
        }

        int batterie = 100 - plusCourtCheminSansAffichage(depart, listeRequetes.getFirst().getSommetDepart()).getPoidsAvecDepart();

        //affichage
        for(Sommet s : cheminDepartRequete) {
            System.out.print(s.getValeur()+" -> ");
        }

        Client destinationSauvgarde = null;
        LinkedList<Sommet> prochainChemin = null;

        while (listeRequetes.size() != 0) {

            //on va aller deposer le client prioritaire
            listeRequetes.getFirst().getSommetDepart().getPlusCourtChemin().clear();
            prochainChemin = plusCourtCheminSansAffichage(listeRequetes.getFirst().getSommetDepart(), listeRequetes.getFirst().getSommetDestination()).getPlusCourtChemin();
            prochainChemin.clear();
            prochainChemin = plusCourtCheminSansAffichage(listeRequetes.getFirst().getSommetDepart(), listeRequetes.getFirst().getSommetDestination()).getPlusCourtChemin();
            prochainChemin.removeFirst();

            //going to our destinaton to pick who we're supposed to pick up
            if (conducteur.getCapacite()<conducteur.CAPACITE_MAX && !(conducteur.getVoiture().contains(listeRequetes.getFirst())))
                conducteur.ramasserClient(listeRequetes.getFirst());
            destinationSauvgarde = listeRequetes.getFirst();                                //save req
            listeRequetes.removeFirst();                                                    //pop the requete
            //on our way to deposer le prochain client is there a client we can pick up? lets check


            batterie -= plusCourtCheminSansAffichage(listeRequetes.getFirst().getSommetDepart(), listeRequetes.getFirst().getSommetDestination()).getPoidsAvecDepart();
            if(batterie < 15) {
                //rech
            }


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
            conducteur.deposerClient(destinationSauvgarde);

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
