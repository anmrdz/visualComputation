package workshops;

import processing.core.PApplet;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.P2D;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.event.MouseEvent;

public class WS2 extends PApplet {

	float ancho = 321, alto = 204, grosor = 5, escala = 1, ancho2 = 321, alto2 = 204, angle = 0;

	PGraphics detalle, completo;
	PImage ImgC, ImgD;

	public static void main(String[] args) {
		PApplet.main(new String[] { "workshops.WS2" });
	}

	@Override
	public void settings() {
		size((int) ancho * 3, (int) alto * 3, P2D);
	}

	@Override
	public void setup() {
		completo = createGraphics((int) ancho * 3, (int) alto * 3);
		detalle = createGraphics((int) ancho, (int) alto);
	}

	@Override
	public void draw() {
		if (ancho * (0.5f * escala) > 0) {
			ancho2 = ancho * (0.5f * escala);
		} else {
			ancho2 = ancho;
			escala = 1;
		}
		if (alto * (0.5f * escala) > 0) {
			alto2 = alto * (0.5f * escala);
		} else {
			alto2 = alto;
			escala = 1;
		}

		background(0);

		ImgD = loadImage("resources/w2/NYC.jpg");
		ImgD.resize((int) ancho, (int) alto);
		image(ImgD, 0, 0);

		ImgC = get((int) (mouseX - (ancho2 / 20) - (grosor / 2)), (int) (mouseY - (alto2 / 20) - (grosor / 2)),
				(int) ((ancho2 / 10) + grosor), (int) ((alto2 / 10) + grosor));
		ImgC.resize((int) ancho * 3, (int) alto * 3);

		pushMatrix();
		completo.beginDraw();
		completo.background(0);

		pushMatrix();
		completo.translate((ancho * 3) / 2, (alto * 3) / 2);
		completo.rotate(angle);
		completo.imageMode(CENTER);
		completo.image(ImgC, 0, 0);
		popMatrix();

		completo.endDraw();
		popMatrix();

		image(completo, 0, 0);

		pushMatrix();
		detalle.beginDraw();
		detalle.background(0);
		detalle.image(ImgD, 0, 0);
		detalle.noFill();
		detalle.stroke(255);
		detalle.strokeWeight(grosor);

		pushMatrix();
		detalle.translate(mouseX, mouseY);
		// detalle.rotate(angle);
		detalle.rectMode(CENTER);
		detalle.rect(0, 0, (ancho2 / 10) + grosor, (alto2 / 10) + grosor);
		popMatrix();

		detalle.endDraw();
		popMatrix();

		image(detalle, 0, 0);

		if (mousePressed) {
			if (mouseButton == LEFT) {
				angle -= 0.1f;
			} else {
				angle += 0.1f;
			}
		}
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		escala += event.getCount();
	}
}
