package workshops;

import static processing.core.PConstants.P3D;
import remixlab.proscene.Scene;

import processing.core.PApplet;

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

        rectMode(CORNERS);

        float t = 0.01f;
        Point tmp = new Point(0, 0), next;
        for (int i = 0, z = 0; i < 100; i++, z += 10) {
            //System.out.println("d " + tmp.dist(bezier(pInit, pc1, pc2, pEnd, t*i)));
            next = bezier(pInit, pc1, pc2, pEnd, t * i);
            rect(tmp.x, tmp.y, next.x + 4, next.y + 4);
            tmp = next;
        }
    }

    public Point bezier(Point ini, Point pc1, Point pc2, Point end, float t) {

        int x = (int) (ini.x * Math.pow(1 - t, 3) + 3 * pc1.x * t * Math.pow(1 - t, 2)
                + 3 * pc2.x * t * t * (1 - t) + end.x * t * t * t);
        int y = (int) (ini.y * Math.pow(1 - t, 3) + 3 * pc1.y * t * Math.pow(1 - t, 2)
                + 3 * pc2.y * t * t * (1 - t) + end.y * t * t * t);

        return new Point(x, y);
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

    public double dist(Point o) {
        return Math.sqrt(this.x * o.x + this.y * y);
    }
}
