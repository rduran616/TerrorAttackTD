package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;



/* A faire:
 * 
 * - pas de prise en charge de la langue
 * - placement des menu manuel en clair
 * - pas de prise en charge des bouton 
 * 
 */


public class Menu extends StateMenu
{
	Stage menu_stage;			// zone de vue et de touch du menu
	TextButton jeu_bouton;  	// 1 bouton pour lancer le jeu
	TextButton option_bouton;  	// 1 bouton pour aller a l'ecran d'option
	TextButton stat_bouton;  	// 1 bouton pour voir les stat du joueur
	TextButton quitter_bouton;  // 1 bouton pour quitter le jeu
	Skin skin_bouton; 			// peau du bouton
     

	public Menu()
	{
		//creation zone affichage du menu
		menu_stage = new Stage(new ScreenViewport()); 
		
		//creation des boutons
		
		//creation d'un skin ( fond du boutton )
		skin_bouton = new Skin( Gdx.files.internal( "uiskin.json" ));

		//init du bouton Jeu 
		jeu_bouton=new TextButton("Jouer", skin_bouton);
		jeu_bouton.setPosition(100,200); //(x,y)
		menu_stage.addActor(jeu_bouton);
		
		//init du bouton Option 
		option_bouton=new TextButton("Option", skin_bouton);
		option_bouton.setPosition(200,200);
		menu_stage.addActor(option_bouton);
		
		//init du bouton Stat 
		stat_bouton=new TextButton("Statistique", skin_bouton);
		stat_bouton.setPosition(300,200);
		menu_stage.addActor(stat_bouton);
		
		//init du bouton Quitter
		quitter_bouton=new TextButton("Quitter", skin_bouton);
		quitter_bouton.setPosition(400,200);
		menu_stage.addActor(quitter_bouton);
		
		
		
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
