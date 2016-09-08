package workshops;

import processing.core.PApplet;
import static processing.core.PConstants.P2D;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.event.MouseEvent;

public class WS2 extends PApplet {

    float scale = 1.0f, angle = 0;
    int visorSize = 50, sectionSize = 300, miniMapSize;

    PGraphics minimap, map;
    PImage imgMap, minimg;

    @Override
    public void settings() {
        size(750, 750, P2D);
    }

    @Override
    public void setup() {
        miniMapSize = width / 3;
        map = createGraphics(width, height);
        minimap = createGraphics(miniMapSize, miniMapSize);
        imgMap = loadImage("resources/w2/map.jpg");
        minimg = imgMap.copy();
        minimg.resize(miniMapSize, miniMapSize);
    }

    @Override
    public void draw() {
        background(0);
        noCursor();
        int visorCurrentSize =(int) (scale * visorSize);
        int sectionCurrentSize = (int) (scale * sectionSize);
        
        mouseX = constrain(mouseX, 0, miniMapSize - visorCurrentSize);
        mouseY = constrain(mouseY, 0, miniMapSize - visorCurrentSize);
        
        int posX = mouseX * 6, posY = mouseY * 6;
        
        minimap.beginDraw();
        minimap.image(minimg, 0, 0);
        minimap.noFill();
        minimap.stroke(255);
        minimap.pushMatrix();
        minimap.translate(mouseX + visorCurrentSize/2, mouseY + visorCurrentSize/2);
        minimap.rotate(radians(angle));
        System.out.println("angle " + angle);
        minimap.rect(-visorCurrentSize/2, -visorCurrentSize/2, visorCurrentSize, visorCurrentSize);
        minimap.popMatrix();
        minimap.endDraw();

        PImage section = imgMap.get(posX, posY, sectionCurrentSize, sectionCurrentSize);
      
        map.beginDraw();
        section.resize(width, height);
        map.image(section, 0, 0);
        map.endDraw();

        image(map, 0, 0);
        image(minimap, 0, 0);
    }

    @Override
    public void mouseWheel(MouseEvent event) {
        scale += (event.getCount() / 2.0);
        if (scale < 0.5) {
            scale = 0.5f;
        } else if (scale > 5.0) {
            scale = 5.0f;
        }
    }

    @Override
    public void mousePressed() {
        if (mouseButton == LEFT) {
            angle -= 10;
        } else if (mouseButton == RIGHT) {
            angle += 10;
        }
    }

    public static void main(String[] args) {
        PApplet.main(new String[]{workshops.WS2.class.getName()});
    }
}