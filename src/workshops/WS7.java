package workshops;

import java.util.ArrayList;
import processing.core.PApplet;
import static processing.core.PApplet.asin;
import static processing.core.PApplet.atan2;
import static processing.core.PApplet.cos;
import static processing.core.PApplet.min;
import static processing.core.PApplet.pow;
import static processing.core.PApplet.sin;
import static processing.core.PApplet.sqrt;
import static processing.core.PConstants.P3D;
import static processing.core.PConstants.TRIANGLES;
import static processing.core.PConstants.TWO_PI;
import processing.core.PVector;
import remixlab.proscene.Scene;

import java.util.ArrayList;
import remixlab.proscene.*;
import processing.core.PApplet;
import processing.core.PVector;
import static processing.core.PApplet.min;
import static processing.core.PApplet.min;
import static processing.core.PApplet.min;

public class WS7 extends PApplet {
    
    public static void main(String[] args) {
        PApplet.main(new String[]{"workshops.WS7"});
    }

    @Override
    public void settings() {
        size(600, 600, P3D);
    }

    @Override
    public void setup() {

        Scene scene;
        scene = new Scene(this);
        scene.setRadius(400);
        scene.showAll();

    }

    @Override
    public void draw() {
        background(255);
        Point pInit = new Point(0, 0);
        Point pc1 = new Point(10, 150);
        Point pc2 = new Point(290, 150);
        Point pEnd = new Point(300, 0);
        
        
        for (int i = 0, z = 0; i < 100; i++, z += 10) {
        beginShape();
        vertex(pInit.x, pInit.x, z);
        bezierVertex(pc1.x, pc1.y, z, pc2.x, pc2.y, z, pEnd.x, pEnd.y, z);
        //int y = (1 - x)*(1 - x)*point + 2*x*(1 - x)*p1 + x*x*p2;
        endShape();
        }
    }
       
    @Override
    public void mousePressed() {

    }

}

class Point {
    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
