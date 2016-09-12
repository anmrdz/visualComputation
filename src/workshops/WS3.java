/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workshops;

import java.util.ArrayList;
import remixlab.proscene.*;
import processing.core.PApplet;
import processing.core.PVector;

/**
 *
 * @author Rockero
 */
public class WS3 extends PApplet {    

Flock flock;

public static void main(String[] args) {
		PApplet.main(new String[] { "workshops.WS3" });
	}

@Override
public void settings(){
    size(600, 600, P3D);
}

@Override
public void setup() {
  
  flock = new Flock();
  Scene scene;
  
  // Add an initial set of boids into the system
  for (int i = 0; i < 150; i++) {
    flock.addBoid(new Boid(0,0,0));
  }
  
  scene = new Scene(this);
  scene.setRadius(400);
  scene.showAll();
  
}

@Override
public void draw() {
  background(50);
  
      stroke(255);

    line(-300, -300, -300, -300, height / 2, -300);
    line(-300, -300, 300, -300, height / 2, 300);
    line(-300, -300, -300, width / 2, -300, -300);
    line(-300, -300, 300, width / 2, -300, 300);

    line(width / 2, -300, -300, width / 2, height / 2, -300);
    line(width / 2, -300, 300, width / 2, height / 2, 300);
    line(-300, height / 2, -300, width / 2, height / 2, -300);
    line(-300, height / 2, 300, width / 2, height / 2, 300);

    line(-300, -300, -300, -300, -300, 300);
    line(-300, height / 2, -300, -300, height / 2, 300);
    line(width / 2, -300, -300, width / 2, -300, 300);
    line(width / 2, height / 2, -300, width / 2, height / 2, 300);
    
    
   
 
  
  flock.run();
}

// Add a new boid into the System
@Override
public void mousePressed() {
  flock.addBoid(new Boid(0,0, 0));
}



// The Flock (a list of Boid objects)

public class Flock {
  ArrayList<Boid> boids; // An ArrayList for all the boids

  Flock() {
    boids = new ArrayList<Boid>(); // Initialize the ArrayList
  }

  void run() {
    for (Boid b : boids) {
      b.run(boids);  // Passing the entire list of boids to each boid individually
    }
  }

  void addBoid(Boid b) {
    boids.add(b);
  }

}




// The Boid class

public class Boid {

  PVector location;
  PVector velocity;
  PVector acceleration;
  public float r; 
  float maxforce;    // Maximum steering force
  float maxspeed;    // Maximum speed

    Boid(float x, float y, float z) {
    acceleration = new PVector(0, 0, 0);

    // This is a new PVector method not yet implemented in JS
    // velocity = PVector.random2D();

    // Leaving the code temporarily this way so that this example runs in JS
    float angle = random(TWO_PI);
    velocity = new PVector(cos(angle), sin(angle), random(TWO_PI));

    location = new PVector(x, y, z);
    r = 2;
    maxspeed =(float) 2.0;
    maxforce = (float) 0.03;   
  }
  


  void run(ArrayList<Boid> boids) {
    
    flock(boids);
    update();
    borders();
    render();
  }

  void applyForce(PVector force) {
    // We could add mass here if we want A = F / M
    acceleration.add(force);
  }

  // We accumulate a new acceleration each time based on three rules
  void flock(ArrayList<Boid> boids) {
    PVector sep = separate(boids);   // Separation
    PVector ali = align(boids);      // Alignment
    PVector coh = cohesion(boids);   // Cohesion
    // Arbitrarily weight these forces
    sep.mult((float) 1.5);
    ali.mult((float) 1.0);
    coh.mult((float) 1.0);
    // Add the force vectors to acceleration
    applyForce(sep);
    applyForce(ali);
    applyForce(coh);
  }

  // Method to update location
  void update() {
    // Update velocity
    velocity.add(acceleration);
    // Limit speed
    velocity.limit(maxspeed);
    location.add(velocity);
    // Reset accelertion to 0 each cycle
    acceleration.mult(0);
  }

  // A method that calculates and applies a steering force towards a target
  // STEER = DESIRED MINUS VELOCITY
  PVector seek(PVector target) {
    PVector desired = PVector.sub(target, location);  // A vector pointing from the location to the target
    // Scale to maximum speed
    desired.normalize();
    desired.mult(maxspeed);

    // Above two lines of code below could be condensed with new PVector setMag() method
    // Not using this method until Processing.js catches up
    // desired.setMag(maxspeed);

    // Steering = Desired minus Velocity
    PVector steer = PVector.sub(desired, velocity);
    steer.limit(maxforce);  // Limit to maximum steering force
    return steer;
  }
  
  PVector steer(PVector target, boolean arrival) {
      PVector steer = new PVector(); // creates vector for steering
      if (!arrival) {
        steer.set(PVector.sub(target, location)); // steering vector points
                            // towards target
                            // (switch target and
                            // pos for avoiding)
        steer.limit(maxforce); // limits the steering force to
                      // maxSteerForce
      } else {
        PVector targetOffset = PVector.sub(target, location);
        float distance = targetOffset.mag();
        float rampedSpeed = maxspeed * (distance / 100);
        float clippedSpeed = min(rampedSpeed, maxspeed);
        PVector desiredVelocity = PVector.mult(targetOffset, (clippedSpeed / distance));
        steer.set(PVector.sub(desiredVelocity, velocity));
      }
      return steer;
    }

  void render() {
    // Draw a triangle rotated in the direction of velocity
    //float theta = velocity.heading() + radians(90);
    // heading2D() above is now heading() but leaving old syntax until Processing.js catches up
    
    
    pushMatrix();
    fill(2, 2);
    stroke(255);
    translate(location.x, location.y, location.z);
    rotateY(atan2(-velocity.z, velocity.x));
    rotateZ(asin(velocity.y / velocity.mag()));
      
    //rotate(theta);
    beginShape(TRIANGLES);
    vertex(0, -r*2);
    vertex(-r, r*2);
    vertex(r, r*2);
    
    
    endShape();
    popMatrix();
  }

  // Wraparound
  void borders() { 
    /*
    if (location.x > 300)  location.x = +300;
    if (location.x < -300) location.x = -300;
    if (location.y > 300)  location.y = +300;
    if (location.y < -300)  location.y = -300;
    if (location.z > 300)  location.z = +300;
    if (location.z < -300)  location.z = -300;
            */
    
    float magnitude = (300-sqrt(pow(location.x,2)+pow(location.y,2)+pow(location.z,2)))*1.8f;
    acceleration.sub(new PVector(location.x/(pow(magnitude,2)),location.y/(pow(magnitude,2)),location.z/(pow(magnitude,2))));
    

  }

  // Separation
  // Method checks for nearby boids and steers away
  PVector separate (ArrayList<Boid> boids) {
    float desiredseparation = 25.0f;
    PVector steer = new PVector(0, 0, 0);
    int count = 0;
    // For every boid in the system, check if it's too close
    for (Boid other : boids) {
      float d = PVector.dist(location, other.location);
      // If the distance is greater than 0 and less than an arbitrary amount (0 when you are yourself)
      if ((d > 0) && (d < desiredseparation)) {
        // Calculate vector pointing away from neighbor
        PVector diff = PVector.sub(location, other.location);
        diff.normalize();
        diff.div(d);        // Weight by distance
        steer.add(diff);
        count++;            // Keep track of how many
      }
    }
    // Average -- divide by how many
    if (count > 0) {
      steer.div((float)count);
    }

    // As long as the vector is greater than 0
    if (steer.mag() > 0) {
      // First two lines of code below could be condensed with new PVector setMag() method
      // Not using this method until Processing.js catches up
      // steer.setMag(maxspeed);

      // Implement Reynolds: Steering = Desired - Velocity
      steer.normalize();
      steer.mult(maxspeed);
      steer.sub(velocity);
      steer.limit(maxforce);
    }
    return steer;
  }

  // Alignment
  // For every nearby boid in the system, calculate the average velocity
  PVector align (ArrayList<Boid> boids) {
    float neighbordist = 50;
    PVector sum = new PVector(0, 0, 0);
    int count = 0;
    for (Boid other : boids) {
      float d = PVector.dist(location, other.location);
      if ((d > 0) && (d < neighbordist)) {
        sum.add(other.velocity);
        count++;
      }
    }
    if (count > 0) {
      sum.div((float)count);
      // First two lines of code below could be condensed with new PVector setMag() method
      // Not using this method until Processing.js catches up
      // sum.setMag(maxspeed);

      // Implement Reynolds: Steering = Desired - Velocity
      sum.normalize();
      sum.mult(maxspeed);
      PVector steer = PVector.sub(sum, velocity);
      steer.limit(maxforce);
      return steer;
    } 
    else {
      return new PVector(0, 0, 0);
    }
  }

  // Cohesion
  // For the average location (i.e. center) of all nearby boids, calculate steering vector towards that location
  PVector cohesion (ArrayList<Boid> boids) {
    float neighbordist = 50;
    PVector sum = new PVector(0, 0, 0);   // Start with empty vector to accumulate all locations
    int count = 0;
    for (Boid other : boids) {
      float d = PVector.dist(location, other.location);
      if ((d > 0) && (d < neighbordist)) {
        sum.add(other.location); // Add location
        count++;
      }
    }
    if (count > 0) {
      sum.div(count);
      return seek(sum);  // Steer towards the location
    } 
    else {
      return new PVector(0, 0, 0);
    }
  }
}
    
    
    
    
}
