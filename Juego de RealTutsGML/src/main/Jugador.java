package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Jugador extends ObjetoDeJuego{
	
	private int ancho = 32;
	private int alto = 32;
	private Handler manejador;
	
	public Jugador(int x, int y, ID id, Handler manejador) {
		super(x, y, id);
		this.manejador = manejador;
	}

	public Rectangle obtenerArea(){//-----------------------------------METODO OBTENERAREA (hitBox):
		return new Rectangle((int)x,(int)y,ancho,alto);
	}//--------------------------------- ---------------------------------------------------------
	
	
	//en este metodo tenemos que poner todo lo que se tenga que actualizar por ticks del juego(60 ticks/s)
	public void tick() {
		x+=velX;
		y+=velY;
		
		//ESTE ES MI METODO PARA NO DEJAR QUE SE SALGA EL JUGADOR DE LA PANTALLA
		//sin embargo decidi usar el metodo del turorial
		/*
		if(x < 0) x-=velX;
		else if(x>Juego.ANCHO-38) x-=velX;
		if(y < 0) y-=velY;
		else if(y>Juego.ALTO-60) y-=velY;
		*/
		
		x = Juego.barrera(x, 0, Juego.ANCHO-38);
		y = Juego.barrera(y, 0, Juego.ALTO-60);
		
		colision();
		
		manejador.addObjeto(new Rastro((int)x,(int)y,ID.Rastro,manejador,ancho,alto,Color.blue,0.05f));
	}
	
	public void colision(){//--------------------------------------------------------METODO COLISION:
		//recorremos todos los objetos de juego
		for (int i = 0; i < manejador.objetos.size(); i++) {
			ObjetoDeJuego temp = manejador.objetos.get(i);
			//si el objeto temporal actual es de tipo enemigo
			if(temp.getId() == ID.EnemigoBasico || temp.getId() == ID.EnemigoRapido || temp.getId() == ID.EnemigoInteligente || temp.getId() == ID.Jefe1 || temp.getId() == ID.Bala){
				//si las cajas de colisi�n de los dos se est�n chocando
				if(obtenerArea().intersects(temp.obtenerArea())){
					//restar 2 de vida
					HUD.VIDA--;
				}
			}
			
		}
	}//---------------------------------------------------------------------------------------------
	
	//renderizacion
	public void render(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect((int)x,(int)y, ancho, alto);
	}
	//---------------------------------------------------------getters setters
	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}
}