package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
	/****** Attributs *******/
	
	Stage menu_stage;			// zone de vue et de touch du menu
	TextButton jeu_bouton;  	// 1 bouton pour lancer le jeu
	TextButton option_bouton;  	// 1 bouton pour aller a l'ecran d'option
	TextButton stat_bouton;  	// 1 bouton pour voir les stat du joueur
	TextButton quitter_bouton;  // 1 bouton pour quitter le jeu
	Skin skin_bouton; 			// peau du bouton
    Table layout_menu;			// Layout en forme de tableau pour possitionnement des bouttons
    int width_		= 0 ;		// taille de l'ecran
    int height_ 	= 0;		// taille de l'ecran
    GlobalValues values_;		// valeurs global
    StateMEnuEnum selection_;	// Etats

    /****** Constructeur *******/
    
	public Menu()
	{
		//recupération du singleton en vue d'obtenir des informations cruciales
		values_ = GlobalValues.getInstance();
		if(values_ != null)
		{
			width_ = values_.get_width();
			height_ = values_.get_height();
			skin_bouton = values_.get_Skin();
		}
		
		//init attributs
		selection_ = StateMEnuEnum.MENU;
		
		//creation d'un layout table pour le positionnement des boutons
		layout_menu = new Table();
		layout_menu.setSize(width_,height_);
		
		//creation zone affichage du menu
		menu_stage = new Stage(new ScreenViewport()); 
	
		//creation des boutons
		jeu_bouton=new TextButton("Jouer", skin_bouton);//init du bouton Jeu 
		option_bouton=new TextButton("Option", skin_bouton);//init du bouton Option 
		stat_bouton=new TextButton("Statistique", skin_bouton);	//init du bouton Stat 
		quitter_bouton=new TextButton("Quitter", skin_bouton);//init du bouton Quitter

		//création des listenners
		jeu_bouton.addListener(new ClickListener()
		{
		       @Override
		       public void clicked(InputEvent event, float x, float y) {
		    	   selection_ = StateMEnuEnum.JEU;
		       }
		 });
		
		option_bouton.addListener(new ClickListener()
		{
		       @Override
		       public void clicked(InputEvent event, float x, float y) {
		    	   selection_ = StateMEnuEnum.OPTION;
		       }
		 });
		
		stat_bouton.addListener(new ClickListener()
		{
		       @Override
		       public void clicked(InputEvent event, float x, float y) {
		    	   selection_ = StateMEnuEnum.STATISTIQUE;
		       }
		 });
		
		quitter_bouton.addListener(new ClickListener()
		{
		       @Override
		       public void clicked(InputEvent event, float x, float y) {
		    	   selection_ = StateMEnuEnum.QUITTER;
		       }
		 });
		
		
		//ajout des boutons  et placement dans une grille 
		layout_menu.add(jeu_bouton).width(width_).pad(10);
		layout_menu.row();
		layout_menu.add(option_bouton).width(width_).pad(10);
		layout_menu.row();
		layout_menu.add(stat_bouton).width(width_).pad(10);
		layout_menu.row();
		layout_menu.add(quitter_bouton).width(width_).pad(10);
		layout_menu.row();
		menu_stage.addActor(layout_menu);

		//activation de la zone
		Gdx.input.setInputProcessor(menu_stage);
	}
	
	
	/****** Méthodes *******/
	
	//changement d'etat 
	@Override
	public StateMEnuEnum changer_Etat() 
	{
		
		if(Gdx.input.getInputProcessor() != menu_stage)
		{
			Gdx.input.setInputProcessor(menu_stage);
		}

		//affichage menu
		menu_stage.act();
		menu_stage.draw();

		if(selection_ != StateMEnuEnum.MENU)
		{
			StateMEnuEnum tps = selection_;
			selection_ = StateMEnuEnum.MENU;
			return  tps;
		}
		else
			return selection_;
	}

}
