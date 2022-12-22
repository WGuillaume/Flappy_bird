import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Oiseau extends Carre implements Deplacable{

    protected float vitesseVertical;

    protected BufferedImage image;

    protected final static int HAUTEUR_OISEAU = 40;

    public Oiseau(int x, int y) {
        super(x, y, HAUTEUR_OISEAU);
        this.vitesseVertical = 0;
    }

    public Oiseau(int hauteurEcran) {
        super(50, 0, HAUTEUR_OISEAU);
        reinitialiser(0, hauteurEcran);
        this.vitesseVertical = 0;
        try {
            image = ImageIO.read(new File("src/main/resources/flappy.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public void reinitialiser(int largeurEcran, int hauteurEcran) {
        y = hauteurEcran / 2 - HAUTEUR_OISEAU / 2;
        vitesseVertical = 0;
    }

    @Override
    public void dessiner(Graphics2D dessin) {
//        dessin.setColor(couleur);
//        dessin.fillRect(x,y,largeur,largeur);
            dessin.setColor(couleur);
            dessin.drawImage(image, x, y,null);
    }

    @Override
    public void deplacer(int largeurEcran, int hauteurEcran) {

        //petite correction de la gravité, pour eviter un temps de flotement
        //si la vitesse est comprise entre -O,1 et -0,9
        // on augmente legerement la gravité
        if(vitesseVertical % 10 != 0 && vitesseVertical < 0) {
            y -= vitesseVertical - 0.5f ;
        } else {
            y -= vitesseVertical;
        }

        vitesseVertical -= 0.05f;

        if(y < 0) {
            vitesseVertical = Math.abs(vitesseVertical) * - 1;
        }
    }

    public void sauter() {
        vitesseVertical=2;

    }

    public float getVitesseVertical() {
        return vitesseVertical;
    }

    public void setVitesseVertical(float vitesseVertical) {
        this.vitesseVertical = vitesseVertical;
    }
}