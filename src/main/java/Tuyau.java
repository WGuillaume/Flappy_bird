import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tuyau extends Rectangle implements Deplacable{

    protected int decalage ;

    protected BufferedImage imageTuyau;
    protected boolean activeTuyau=false;
    protected boolean haut ;

    public Tuyau(int hauteur,int hauteurEcran, int largeurEcran,int decalage,boolean activeTuyau,BufferedImage imageTuyau,boolean haut) {
        super(largeurEcran - 100, hauteurEcran - hauteur, 100, hauteur);
        this.decalage=decalage;
        this.activeTuyau=activeTuyau;
        this.imageTuyau=imageTuyau;
        this.haut=haut;
        reinitialiser(largeurEcran, hauteurEcran);

    }


    @Override
    public void dessiner(Graphics2D dessin) {
        if (activeTuyau==true) {
            dessin.setColor(couleur);
            if(haut) {
                dessin.drawImage(imageTuyau, x, - 100 + hauteur, null);
            }else {
                dessin.drawImage(imageTuyau, x,  y, null);
            }
           // dessin.fillRect(x, y, largeur, hauteur);

        }


    }

    @Override
    public void deplacer(int largeurEcran, int hauteurEcran) {
        x -= 5;
        if(x < -largeur) {
            x=largeurEcran;

        }
    }

    public void reinitialiser(int largeurEcran, int hauteurEcran) {

        x = largeurEcran + decalage;
        if(haut){
            y=0;
        }else {
            y=hauteurEcran - hauteur;
        }
    }

    public boolean getIsActiveTuyau() {
        return activeTuyau;
    }

    public void setActiveTuyau(boolean activeTuyau) {
        this.activeTuyau = activeTuyau;
    }
}

