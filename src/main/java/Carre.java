import java.awt.*;

public abstract class Carre extends Sprite {

    protected int largeur;

    public Carre(int x, int y, int largeur, Color couleur) {
        super(x, y, couleur);
        this.largeur = largeur;
    }

    public Carre(int x, int y, int largeur) {
        super(x, y);
        this.largeur = largeur;
    }

    @Override
    public boolean collision(int x, int y) {
       return x>=this.x
                &&x<= this.x+largeur
                &&y>= this.y
                &&y<= this.y+largeur;

    }
    @Override
    public boolean collision (Sprite sprite) {

        if(sprite instanceof Rectangle) {
            Rectangle rectangle=(Rectangle)sprite;

          return this.collision(rectangle.getX(), rectangle.getY())
                  || this.collision(rectangle.getX() + rectangle.getLargeur(), rectangle.getY())
                  || this.collision(rectangle.getX(), rectangle.getY() + rectangle.getHauteur())
                  || this.collision(
                  rectangle.getX() + rectangle.getLargeur(),
                  rectangle.getY() + rectangle.getHauteur());

        } else if (sprite instanceof Carre) {
            Carre carre=(Carre)sprite;

            return this.collision(carre.getX(), carre.getY())
                    || this.collision(carre.getX() + carre.getLargeur(), carre.getY())
                    || this.collision(carre.getX(), carre.getY() + carre.getLargeur())
                    || this.collision(
                    carre.getX() + carre.getLargeur(),
                    carre.getY() + carre.getLargeur());

        }

        return false;
    }

    @Override
    public int getCentreX() {
        return largeur / 2;
    }

    @Override
    public int getCentreY() {
        return largeur / 2;
    }

    public int getLargeur() {
        return largeur;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }
}