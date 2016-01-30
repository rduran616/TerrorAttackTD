package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.GL20;

import Utilitaires.Localisation;

public class MyGdxGame extends ApplicationAdapter 
{
	//SpriteBatch batch;
	//Texture img;
	
	ArrayList<StateMenu> main_menu; //creation du menu 
	StateMEnuEnum menu_en_cours; //gestion numéro etat du menu
	
	@Override
	public void create () 
	{
		System.err.println("create game");
		
		main_menu  = null;
		
		main_menu 	 = new ArrayList<StateMenu>();
		main_menu.add( new Menu());
		main_menu.add( new Jeu());
		main_menu.add( new Statistique());
		main_menu.add( new Option());
		main_menu.add( new Quitter());
		
		menu_en_cours = StateMEnuEnum.MENU;
		
		GlobalValues values = GlobalValues.getInstance();
		values.load();
		
		for(int i=0; i < main_menu.size();i++)
			main_menu.get(i).load();
	}

	@Override
	public void render () 
	{
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//rendu du menu
		menu_en_cours = main_menu.get(menu_en_cours.ordinal()).changer_Etat();
		if(menu_en_cours == StateMEnuEnum.MENU)
		{
			for(int i=0; i < main_menu.size();i++)
				main_menu.get(i).init();
		}
		
	}
	
	@Override
	public void resize (int width, int height)
	{
		System.err.println("resize");
	}

	@Override
	public void pause()
	{
		System.err.println("pause");
	}

	@Override
	public void resume()
	{
		System.err.println("resume");

		GlobalValues.getInstance().load();
		
		for(int i=0; i < main_menu.size();i++)
			main_menu.get(i).load();
	}

	@Override
	public void dispose()
	{
		System.err.println("dispose main_menu");
		for(int i=0; i < main_menu.size();i++)
			main_menu.get(i).dispose();
		
		for(int i=0; i < main_menu.size();i++)
			main_menu.remove(i);
		
		System.err.println(" ");
	}
}
