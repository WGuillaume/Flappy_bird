import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Flappy extends Canvas implements KeyListener {

    protected static int largeurEcran = 800;
    protected static int hauteurEcran = 500;

    protected boolean pause = false;
    protected boolean perdu = false;

    protected Oiseau oiseau;

    protected BufferedImage image;
    protected BufferedImage imagebas = ImageIO.read(new File("src/main/resources/tuyaubas.png"));
    protected BufferedImage imagehaut = ImageIO.read(new File("src/main/resources/tuyauhaut.png"));

    protected ArrayList<Deplacable> listeDeplacable = new ArrayList<>();
    protected ArrayList<Sprite> listeSprite = new ArrayList<>();

    protected ArrayList<Tuyau> listeTuyau = new ArrayList<>();

    public Flappy() throws InterruptedException, IOException {

        JFrame fenetre = new JFrame("Flappy");
        //On récupère le panneau de la fenetre principale
        JPanel panneau = (JPanel) fenetre.getContentPane();
        //On définie la hauteur / largeur de l'écran


        panneau.setPreferredSize(new Dimension(largeurEcran, this.hauteurEcran));
        setBounds(0, 0, this.largeurEcran, this.hauteurEcran);
        //On ajoute cette classe (qui hérite de Canvas) comme composant du panneau principal
        panneau.add(this);

        fenetre.pack();
        fenetre.setResizable(false);
        fenetre.setLocationRelativeTo(null);
        fenetre.setVisible(true);
        fenetre.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fenetre.requestFocus();
        fenetre.addKeyListener(this);

        //On indique que le raffraichissement de l'ecran doit être fait manuellement.
        this.createBufferStrategy(2);
        this.setIgnoreRepaint(true);
        this.setFocusable(false);

        this.demarrer();
    }

    public void initialiser() {

        pause = false;

        //si c'est la première initialisation
        if (oiseau == null) {
            oiseau = new Oiseau(hauteurEcran);
//            Nuage nuage = new Nuage(largeurEcran,  hauteurEcran);
            listeDeplacable.add(oiseau);
            listeSprite.add(oiseau);


            //ajout nuages
            for (int i = 0; i < 15; i++) {
                Nuage nuage = new Nuage(largeurEcran, hauteurEcran);
                listeDeplacable.add(nuage);
                listeSprite.add(nuage);
            }

            int nombreTuyau = 3;
            int decalage = (largeurEcran + 100) / nombreTuyau;
            for (int i = 0; i < nombreTuyau; i++) {
                ;

                Tuyau tuyauHaut = new Tuyau((int) (Math.random() * 180), hauteurEcran, largeurEcran, i * decalage, true, imagehaut,true);
//                Tuyau tuyauBas = new Tuyau(180, hauteurEcran, largeurEcran, i * decalage, true,imagebas);
                Tuyau tuyauBas = new Tuyau((int) (Math.random() * 180), hauteurEcran, largeurEcran, i * decalage, true, imagebas,false);

                listeTuyau.add(tuyauHaut);
                listeDeplacable.add(tuyauHaut);
                listeSprite.add(tuyauHaut);
                listeTuyau.add(tuyauBas);
                listeDeplacable.add(tuyauBas);
                listeSprite.add(tuyauBas);
            }
        } else {
            for (Deplacable deplacable : listeDeplacable) {
                deplacable.reinitialiser(largeurEcran, hauteurEcran);
            }
        }
    }

    public void demarrer() throws InterruptedException {

        long indexFrame = 0;
        Font police = new Font("Calibri", Font.BOLD, 30);
        initialiser();

        try {
            image = ImageIO.read(new File("src/main/resources/fond.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        while (true) {

            indexFrame++;
            Graphics2D dessin = (Graphics2D) getBufferStrategy().getDrawGraphics();

            //-----------------------------
            //reset dessin
            dessin.setColor(Color.WHITE);


            dessin.drawImage(image, 0, 0, null);
//            dessin.fillRect(0, 0, largeurEcran, hauteurEcran);


            for (Sprite sprite : listeSprite) {
                sprite.dessiner(dessin);
            }
            if (!perdu)
                if (!pause) {
                    //-----si jamais l'oiseau est tombé par terre ---
                    if (oiseau.getY() > hauteurEcran - oiseau.getLargeur()) {
                        System.out.println("perdu");
                        perdu = true;
                    } else {
                        //----sinon si le jeu continu ----
//                    oiseau.deplacer();
//                    tuyau.deplacer();

                        for (Deplacable deplacable : listeDeplacable) {
                            deplacable.deplacer(largeurEcran, hauteurEcran);
                        }
                        for (Tuyau tuyau : listeTuyau) {
                            if (tuyau.getIsActiveTuyau())
                                if (Sprite.testcollision(oiseau, tuyau)) {
                                    System.out.println("perdu");
                                    perdu = true;
                                }
                        }

                    }
                } else {
                    dessin.setColor(new Color(0, 0, 0, 0.3f));
                    dessin.fillRect(0, 0, largeurEcran, hauteurEcran);
                    //pour l'affichage du mot PAUSE
                    dessin.setColor(Color.BLACK);
                    dessin.setFont(police);
                    String String = "PAUSE";
                    dessin.drawString(
                            String.valueOf("---> PAUSE <---"),
                            300,
                            300);
                }
            else {

                dessin.setColor(new Color(1f, 0, 0, 0.7f));
                dessin.fillRect(0, 0, largeurEcran, largeurEcran);
                //pour l'affichage du mot Perdu
                dessin.setColor(Color.BLACK);
                dessin.setFont(police);
                String String = "LOSER";
                dessin.drawString(
                        String.valueOf("`LOSER´ !! Try again...   "),
                        largeurEcran - 700,
                        150);
            }



        //-----------------------------
        dessin.dispose();
        getBufferStrategy().show();
        Thread.sleep(1000 / 60);
    }

}

    public static void main(String[] args) throws InterruptedException, IOException {
        new Flappy();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            oiseau.sauter();
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            initialiser();
        }

        if (e.getKeyCode() == KeyEvent.VK_P) {
            //inverser un booléen
            pause = !pause;
        }
    }
}