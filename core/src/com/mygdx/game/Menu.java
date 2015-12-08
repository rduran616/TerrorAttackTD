package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Menu extends StateMenu
{
	Stage menu_stage;		//zone de vue et de touch du menu
	TextButton jeu_bouton;  // 1 bouton
	Skin skin_bouton; 		// peau du bouton
     

	public Menu()
	{
		//creation zone affichage du menu
		menu_stage = new Stage(new ScreenViewport()); 
		
		//creation du bouton
		skin_bouton = new Skin( Gdx.files.internal( "ui/defaultskin.json" ));
		jeu_bouton=new TextButton("Mon boutton", skin_bouton);
		jeu_bouton.setPosition(350,200);
		menu_stage.addActor(jeu_bouton);
		
		//activation de la zone
		Gdx.input.setInputProcessor(menu_stage);
		
		//fin creation menu
		System.out.println("Création menu !");
	}
	
	@Override
	public StateMEnuEnum changer_Etat() 
	{
		System.out.println("etat Menu principal");
		
		//affichage menu
		menu_stage.act();
		menu_stage.draw();
		//menu_stage.setViewport(800,480,false);
		
		//si touche, on change de menu
		if(Gdx.input.isKeyPressed(Keys.UP))
		{
			return StateMEnuEnum.QUITTER;
		}
		
		return StateMEnuEnum.MENU;
	}

}
