import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Nuage extends Rectangle implements Deplacable {
    protected BufferedImage imageNuage;
    public Nuage(int largeurEcran, int hauteurEcran) {
        super(0, 0, 0, 0, new Color(0,0,0,0.05f));
        reinitialiser(largeurEcran, hauteurEcran);
        x -= (int)(Math.random() * largeurEcran);

    try {
      imageNuage = ImageIO.read(new File("src/main/resources/nuage.png"));
    } catch (
    IOException ex) {
        ex.printStackTrace();
    }
    }

    @Override
    public void dessiner(Graphics2D dessin) {
//        dessin.setColor(couleur);
//        dessin.fillRect(x,y,largeur,hauteur);
        dessin.drawImage(imageNuage, x, y,null);
    }

    @Override
    public void deplacer(int largeurEcran, int hauteurEcran) {
        x--;
        if(x < -largeur) {
            reinitialiser(largeurEcran, hauteurEcran);
            x+= largeurEcran;
        }
    }

    @Override
    public void reinitialiser(int largeurEcran, int hauteurEcran) {
        x = (int)(Math.random() * largeurEcran ) ;
        y = (int)(Math.random() * (hauteurEcran / 2));
        largeur = (int)(Math.random() * 40 + 80);
        hauteur = (int)(Math.random() * 20 + 20);
    }
}