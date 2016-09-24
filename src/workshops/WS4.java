/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workshops;

/**
 *
 * @author Rockero
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import math.geom2d.Point2D;
import math.geom2d.conic.Circle2D;
import math.geom2d.line.Line2D;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class WS4 extends PApplet {

    private PVector linePoint0;             // For drawing
    private PVector linePoint1;             // For drawing
    private List<Circle> circles;           // For drawing
    private List<Circle2D> circle2Ds;       // For computing the intersect
    private List<PVector> intersectPoints;

    @Override
    public void settings() {
        size(640, 360);
    }

    @Override
    public void setup() {
        linePoint0 = new PVector(0, height);
        linePoint1 = new PVector(0, height);
        circles = new ArrayList<>();
        circle2Ds = new ArrayList<>();
        circles.add(new Circle(new PVector(width / 2, height / 2), 100));
        circles.add(circles.get(0).spawnChild());
        circles.add(circles.get(0).spawnChild());
        circles.add(circles.get(1).spawnChild());
        circles.add(circles.get(2).spawnChild());
        for (Circle circle : circles) {
            circle2Ds.add(new Circle2D(circle.getPos().x, circle.getPos().y, circle.getRadius()));
        }
    }

    @Override
    public void draw() {
        background(0);
        blendMode(ADD);

        drawLine();
        drawCircles();
        getIntersections();
        drawIntersections();
    }

    private void drawLine() {
        stroke(128);
        strokeWeight(5);
        line(0, height, mouseX, mouseY);
        linePoint1.x = mouseX;
        linePoint1.y = mouseY;
    }

    private void drawCircles() {
        for (Circle circle : circles) {
            circle.draw(getGraphics());
        }
    }

    private void getIntersections() {
        Line2D line
                = new Line2D(new Point2D(linePoint0.x, linePoint0.y), new Point2D(linePoint1.x, linePoint1.y));
        Collection<Point2D> intersections;
        intersectPoints = new ArrayList<>();
        int x, y;
        for (Circle2D circle : circle2Ds) {
            intersections = Circle2D.lineCircleIntersections(line, circle);
            for (Point2D point : intersections) {
                x = (int) point.getX();
                y = (int) point.getY();
                intersectPoints.add(new PVector(x, y));
            }
        }
    }

    private void drawIntersections() {
        strokeWeight(10);
        stroke(250);
        for (PVector point : intersectPoints) {
            point(point.x, point.y);
        }
    }

    public static void main(String[] args) {
        PApplet.main(WS4.class.getName());

    }

    public class Circle {

        private PVector pos;
        private int radius;
        private int[] colours;

        Circle(PVector pos, int radius) {
            this.pos = pos;
            this.radius = radius;
            Random random = new Random();
            colours = new int[]{random.nextInt(256), random.nextInt(256), random.nextInt(256)};
        }

        void draw(PGraphics pg) {
            pg.strokeWeight(2);
            pg.stroke(colours[0], colours[1], colours[2]);
            pg.fill(colours[0], colours[1], colours[2], 50);
            pg.ellipse(pos.x, pos.y, radius * 2, radius * 2);
        }

        Circle spawnChild() {
            Random random = new Random();
            int childRadius = radius / 2;
            int xMin = (int) pos.x - radius / 2;
            int xMax = (int) pos.x + radius / 2;
            int x = random.nextInt(xMax - xMin) + xMin;
            int yMin = (int) pos.y - radius / 2;
            int yMax = (int) pos.y + radius / 2;
            int y = random.nextInt(yMax - yMin) + yMin;
            return new Circle(new PVector(x, y), childRadius);
        }

        public PVector getPos() {
            return pos;
        }

        public void setPos(PVector pos) {
            this.pos = pos;
        }

        public int getRadius() {
            return radius;
        }

        public void setRadius(int radius) {
            this.radius = radius;
        }

        public int[] getColours() {
            return colours;
        }

        public void setColours(int[] colours) {
            this.colours = colours;
        }
    }

}
