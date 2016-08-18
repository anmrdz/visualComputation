package workshops;

import processing.core.*;
import processing.core.PApplet;
import processing.core.PImage;

public class WS2 extends PApplet {

    PImage img, img2;
    PGraphics pg, secondLy;

    int x0 = 0, y0 = 0;

    public void setup() {
        img = loadImage("resources/w1/map_copia.png");
        img2 = loadImage("resources/w1/map.png");
        pg = createGraphics(440, 300);
        secondLy = createGraphics(1000, 600);
       
        secondLy.beginDraw();
        secondLy.image(img2, 0, 0);
        secondLy.endDraw();
        pg.beginDraw();
        pg.image(img, 0, 0);
        pg.endDraw();
        
        
    }

    public void settings() {
        size(1200, 800);
    }

    public void draw() {

        background(0);
        
        image(secondLy, 0, 0);
        image(pg, 0, 0);

    }

    public void mousePressed() {
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PApplet.main(new String[]{workshops.WS2.class.getName()});
    }

}
