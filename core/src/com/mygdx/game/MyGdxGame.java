package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter 
{
	//SpriteBatch batch;
	//Texture img;
	
	StateMenu main_menu[]; //creation du menu 
	StateMEnuEnum menu_en_cours; //gestion numéro etat du menu
	
	@Override
	public void create () 
	{
		//batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");
		
		main_menu = new StateMenu[5];
		main_menu[0] = new Menu();
		main_menu[1] = new Jeu();
		main_menu[2] = new Statistique();
		main_menu[3] = new Option();
		main_menu[4] = new Quitter();
		
		menu_en_cours = StateMEnuEnum.MENU;
		
		
	}

	@Override
	public void render () 
	{
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//rendu du menu
		menu_en_cours = main_menu[menu_en_cours.ordinal()].changer_Etat();

		/*batch.begin();
		batch.draw(img, 0, 0);
		batch.end();*/
	}
}
