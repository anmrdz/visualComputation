package workshops;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    private int outCircleSize = 70;
    private int innerCircleSize = 35;

    @Override
    public void settings() {
        size(600, 600);
    }

    @Override
    public void setup() {
        linePoint0 = new PVector(0, height);
        linePoint1 = new PVector(0, height);
        circles = new ArrayList<>();
        circle2Ds = new ArrayList<>();
        circles.add(new Circle(new PVector(width / 2, height / 2), 250));
        circles.add(new Circle(new PVector((width / 2) - 130, (height / 2) - 50), outCircleSize));
        circles.add(new Circle(new PVector((width / 2) + 130, (height / 2) - 50), outCircleSize));
        circles.add(new Circle(new PVector(width / 2, height / 2), outCircleSize));
        circles.add(new Circle(new PVector(width / 2, (height / 2) - 130), outCircleSize));
        circles.add(new Circle(new PVector((width / 2) + 65, (height / 2) + 130), outCircleSize));
        circles.add(new Circle(new PVector((width / 2) - 65, (height / 2) + 130), outCircleSize));

        for (int i = 0; i < 2; i++) {
            circles.add(new Circle(new PVector(width / 2, (height / 2)
                    - outCircleSize * 2 + i * innerCircleSize), innerCircleSize));
            circles.add(new Circle(new PVector((width / 2) + i * innerCircleSize,
                    (height / 2)), innerCircleSize));
            circles.add(new Circle(new PVector(width / 2 - 130 - outCircleSize
                    + i * innerCircleSize, (height / 2) - 50), innerCircleSize));
            circles.add(new Circle(new PVector(width / 2 + 130 + outCircleSize
                    - i * innerCircleSize, (height / 2) - 50), innerCircleSize));
        }

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
        stroke(200);
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
        Line2D line = new Line2D(new Point2D(linePoint0.x, linePoint0.y),
                new Point2D(linePoint1.x, linePoint1.y));
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
        stroke(255, 0, 0);
        fill(255, 0, 0);
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

        Circle(PVector pos, int radius) {
            this.pos = pos;
            this.radius = radius;
        }

        void draw(PGraphics pg) {
            pg.strokeWeight(2);
            pg.stroke(255);
            pg.noFill();
            pg.ellipse(pos.x, pos.y, radius * 2, radius * 2);
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
    }

}
