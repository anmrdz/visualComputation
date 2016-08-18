package workshops;

import processing.core.*;
import processing.core.PApplet;
import processing.core.PImage;

public class WS1 extends PApplet {

    PImage img;
    PGraphics pg;

    int x0 = 0, y0 = 0, color, color2;

    public void setup() {
        img = loadImage("resources/w1/map.png");
        pg = createGraphics(640, 400);
        pg.beginDraw();
        pg.image(img, 0, 0);
        pg.endDraw();
    }

    public void settings() {
        size(640, 400);
    }

    public void draw() {

        background(0);
        image(pg, 0, 0);

        color = get(mouseX, mouseY);
        fill(color);
        rect(mouseX + 10, mouseY, 20, 20);
        fill(red(color), 0, 0);
        rect(mouseX + 10, mouseY + 20, 10, 10);
        fill(0, green(color), 0);
        rect(mouseX + 20, mouseY + 20, 10, 10);
        fill(0, 0, blue(color));
        rect(mouseX + 30, mouseY + 20, 10, 10);

        if (mousePressed && (mouseButton == LEFT)) {
            pg.beginDraw();
            pg.fill(color2);
            pg.stroke(color2);
            pg.rect(mouseX, mouseY, 5, 5);
            pg.endDraw();
        }
    }

    public void mousePressed() {
        color2 = color;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PApplet.main(new String[]{workshops.WS1.class.getName()});
    }

}
