package Vue;

import BaseDeDonnees.Session;
import Controle.AL2000;
import Controle.Mediateur;
import Metier.GestionClient.CarteAbo;

import javax.swing.*;
import java.awt.*;

public class InterfaceUtilisateur {

    JFrame ecran;
    boolean utilisateurConnecte;
    NavigationBar navBar;
    BackgroundPanel fondDEcran;
    Dimension tailleEcran;
    Bienvenue ecranDeBienvenue;
    Inscription inscription;
    InscriptionReussie inscriptionReussie;
    Connexion connexion;
    PreConnexion preConnexion;
    RendreBluray rendrebluray;
    VoirFilms voir_films;
    AjouterAuPanier ajouterAuPanier;
    AttenteDVD attenteDVD;
    ETAT_IU etatCourant;
    JPanel panneauCourant;

    Mediateur mediateur;
    AL2000 logiciel;
    int numeroDeCarte;
    CarteAbo carteAbonne;


    public InterfaceUtilisateur(){
        OurTools.setFont();
        mediateur = new Mediateur();
        logiciel = new AL2000(new Session());
        mediateur.setLogiciel(logiciel);
        // Initialisations
        ecran = new JFrame();
        navBar = new NavigationBar(this);
        fondDEcran = new BackgroundPanel();
        tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
        etatCourant = ETAT_IU.AUCUN;

        ecranDeBienvenue = new Bienvenue(this);
        inscription = new Inscription(this);
        inscriptionReussie = new InscriptionReussie();
        connexion = new Connexion(this);
        preConnexion = new PreConnexion(this);
        rendrebluray = new RendreBluray(this);
        voir_films = new VoirFilms(this);
        attenteDVD = new AttenteDVD();
        ajouterAuPanier = new AjouterAuPanier();

        inscription.setVisible(true);
        inscriptionReussie.setVisible(true);

        panneauCourant = ecranDeBienvenue;
        navBar.ajouterEtat(ETAT_IU.BIENVENUE);
        navBar.cacher();

        // Taille de l'ecran de l'utilisateur :
        double LARGEUR = tailleEcran.getWidth()/3;
        double HAUTEUR = tailleEcran.getHeight()/3;

        // Parametrage
        ecran.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ecran.setMinimumSize(new Dimension(1300, 1000));
        ecran.setPreferredSize(new Dimension((int)LARGEUR,(int)HAUTEUR));
        ecran.setContentPane(fondDEcran);
        ecran.setResizable(true);

        ecran.setLayout(new BorderLayout());
        ecran.add(navBar, BorderLayout.NORTH);

        ecran.add(panneauCourant);

        //changerEtat(ETAT_IU.AJOUTER_AU_PANIER);

        ecran.pack();
        ecran.setVisible(true);
        ecran.setLocationRelativeTo(null);
    }

    public void changerEtat(ETAT_IU nouvelEtat) {
        ecran.remove(panneauCourant);
        navBar.reset();
        if (nouvelEtat != etatCourant) {
            switch (nouvelEtat) {
                case BIENVENUE -> {
                    navBar.cacher();
                    panneauCourant = ecranDeBienvenue;
                }
                case INSCRIPTION -> {
                    navBar.retourSeulement(true);
                    panneauCourant = inscription;
                }
                case INSCRIPTION_REUSSIE -> {
                    navBar.retourSeulement(true);
                    panneauCourant = inscriptionReussie;
                }
                case CONNEXION -> {
                    navBar.retourSeulement(true);
                    panneauCourant = connexion;
                }
                case PRE_CONNEXION -> {
                    navBar.retourSeulement(true);
                    panneauCourant = preConnexion;
                }
                case RENDRE_DVD -> {
                    navBar.retourSeulement(true);
                    panneauCourant = rendrebluray;
                }
                case VOIR_FILMS -> panneauCourant = voir_films;
                case ATTENTE_DVD -> panneauCourant = attenteDVD;
                case AJOUTER_AU_PANIER -> panneauCourant = ajouterAuPanier;
                default -> {
                    System.out.println("ERROR -- Unknown new state !");
                }
            }
            ecran.add(panneauCourant);
            etatCourant = nouvelEtat;
            navBar.ajouterEtat(nouvelEtat);
            ecran.pack();
            ecran.repaint();
        }
    }

    public void connexion(){
        this.utilisateurConnecte = true;
    }


    public void deconnexion(){
        this.utilisateurConnecte = false;
    }

    public boolean estConnecte(){
        return this.utilisateurConnecte;
    }

    public NavigationBar getNavBar(){
        return this.navBar;
    }

    public Mediateur getMediateur(){
        return this.mediateur;
    }

    public AL2000 getLogiciel(){
        return logiciel;
    }

    public CarteAbo getCarteAbonne() {
        return carteAbonne;
    }

    public void setCarteAbonne(CarteAbo ca){
        this.carteAbonne = ca;
    }

    public VoirFilms getVoir_films(){
        return this.voir_films;
    }

    public static void main(String[] args) {
        InterfaceUtilisateur UI = new InterfaceUtilisateur();
    }
}
