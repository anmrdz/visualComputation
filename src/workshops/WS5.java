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
import fisica.*;
import processing.core.*;

public class WS5 extends PApplet {

    public static void main(String[] args) {
        PApplet.main(new String[]{"workshops.WS5"});
    }

    @Override
    public void settings() {

        size(600, 400, P2D);
        smooth();
    }

    FWorld world;

    public void setup() {

        Fisica.init(this);

        world = new FWorld();
        //world.setGravity(0, 400);
        world.setEdges();

        int posX = width - 105;
        int posY = height - 20;
        FBox box;

        for (int i = 0; i < 10; i++) {
            box = new FBox(200 - (i * 15), 30);
            box.setStrokeWeight(1);
            box.setStroke(0, 255, 0, 50);
            box.setPosition(posX + (i * 15), posY - (i * 30));
            box.setFill(0, 255, 0, 50);
            box.setStatic(true);
            world.add(box);

            box = new FBox(200 - (i * 15), 30);
            box.setStrokeWeight(1);
            box.setStroke(0, 255, 0, 50);
            box.setPosition(600 - posX - (i * 15), posY - (i * 30));
            box.setFill(0, 255, 0, 50);
            box.setStatic(true);
            world.add(box);
        }

        box = new FBox(80, 100);
        box.setStrokeWeight(1);
        box.setStroke(0, 255, 0, 50);
        box.setPosition(height - 100, width - 255);
        box.setFill(0, 255, 0, 50);
        box.setStatic(true);
        box.setRotatable(false);
        world.add(box);

        Body ragdoll1 = new Body(width / 4, height / 2);
        Body ragdoll2 = new Body((width / 4) * 3, height / 2);

        frameRate(45);

    }

    @Override
    public void draw() {

        background(80);
        world.step();
        world.draw(this);
    }

    public class Body {

        Body(int posX, int posY) {

            FBox torse = new FBox(40, 80);
            torse.setPosition(posX, posY);
            world.add(torse);

            FBox brasG = new FBox(10, 80);
            brasG.setPosition(posX - 25, posY);
            world.add(brasG);

            FBox brasD = new FBox(10, 80);
            brasD.setPosition(posX + 25, posY);
            world.add(brasD);

            FCircle tete = new FCircle(40);
            tete.setPosition(posX, posY - 60);
            world.add(tete);

            FBox jambeG = new FBox(10, 80);
            jambeG.setPosition(posX - 15, posY + 80);
            world.add(jambeG);

            FBox jambeD = new FBox(10, 80);
            jambeD.setPosition(posX + 15, posY + 80);
            world.add(jambeD);

            FDistanceJoint jointTorseBrasG = new FDistanceJoint(torse, brasG);
            jointTorseBrasG.setLength(0);
            jointTorseBrasG.setAnchor1(-20, -40);
            jointTorseBrasG.setAnchor2(5, -40);

            FDistanceJoint jointTorseBrasD = new FDistanceJoint(torse, brasD);
            jointTorseBrasD.setLength(0);
            jointTorseBrasD.setAnchor1(20, -40);
            jointTorseBrasD.setAnchor2(-5, -40);

            FDistanceJoint jointTorseTete = new FDistanceJoint(torse, tete);
            jointTorseTete.setLength(0);
            jointTorseTete.setAnchor1(0, -40);
            jointTorseTete.setAnchor2(0, 20);

            FDistanceJoint jointTorsejambeG = new FDistanceJoint(torse, jambeG);
            jointTorsejambeG.setLength(0);
            jointTorsejambeG.setAnchor1(-20, 40);
            jointTorsejambeG.setAnchor2(-5, -40);

            FDistanceJoint jointTorsejambeD = new FDistanceJoint(torse, jambeD);
            jointTorsejambeD.setLength(0);
            jointTorsejambeD.setAnchor1(20, 40);
            jointTorsejambeD.setAnchor2(5, -40);

            //Construct a revolute joint between two bodies given an anchor position.
            world.add(jointTorseBrasG);
            world.add(jointTorseBrasD);
            world.add(jointTorseTete);
            world.add(jointTorsejambeG);
            world.add(jointTorsejambeD);

        }

    }

}
