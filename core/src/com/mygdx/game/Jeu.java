package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import Utilitaires.FingerTransformMap;
import Utilitaires.TickHorloge;

public class Jeu extends StateMenu  implements ApplicationListener,InputProcessor
{ 
	//var global
	GlobalValues values_;		
	StateMEnuEnum selection_;
	
	//gestion etat du jeu
	StateJeu jeu_[];
	StateJeuEnum etat_jeu_;
	boolean touche_activer_;
	TickHorloge tick_;		//gestion des ticks pour attente passive
	
	//Zoom
	FingerTransformMap finger;
	
	//HUD
	
	//hud jeu
	Stage stage_game_;				//stage du jeu
	Layout main_layout_game_;		//layout du hud du jeu
	Table main_table_game_;			//layout principale du hud du jeu
	Table layout_table_tower_;		//layout table pour le choix de l'unité
	Table Layout_table_upgrade_;	//layout table pour les boutons upgrade d'unité
	Table Layout_table_bonus_;		//layout table pour l'achat des bonus
	
	//hud pause
	
	//hud intro
	
	//hud fin
	
	public Jeu()
	{
		//initialisation des variables
		selection_ = StateMEnuEnum.JEU;
		values_ = GlobalValues.getInstance();
		
		//initialisation du jeu
		etat_jeu_ = StateJeuEnum.CHOIX;
		jeu_ = new StateJeu[5];
		touche_activer_ = false;
		
		if(jeu_ != null)
		{
			jeu_[0] = new TdChoixNiveau();
			jeu_[1] = new TdIntro();
			jeu_[2] = new TdPause();
			jeu_[3] = new TdJeu();
			jeu_[4] = new TdFin();
		}
		
		//initialisation des huds
		
		//hud jeu:
		layout_table_tower_ 	= new Table();
		Layout_table_upgrade_	= new Table();
		Layout_table_bonus_		= new Table();
		main_table_game_		= new Table();
		stage_game_				= new Stage(new ScreenViewport());
		

		main_table_game_.add(layout_table_tower_);
		main_table_game_.add(Layout_table_upgrade_);
		main_table_game_.add(Layout_table_bonus_);
		
		stage_game_.addActor(main_table_game_);

		Gdx.input.setCatchBackKey(true);
		
		tick_ = new TickHorloge(30); //30fps max
		finger = new FingerTransformMap(0.01);
	}

	@Override
	public StateMEnuEnum changer_Etat() 
	{

		// choix de l'etat et action en fonction de l'état du jeu
		etat_jeu_ = jeu_[etat_jeu_.ordinal()].exectute(); //execute fais les mise à jour des ia

		//dessin du jeu
		if(etat_jeu_ == StateJeuEnum.JEU || etat_jeu_ == StateJeuEnum.PAUSE)
		{
			//dessin de la carte
			if(values_.camera_Update() != ErrorEnum.OK)
			{	
				etat_jeu_ = StateJeuEnum.CHOIX;
				selection_ = StateMEnuEnum.JEU;
				System.err.println("Erreur camera update");
			}
			
			if(values_.tiled_Map_View()!= ErrorEnum.OK)
			{	
				etat_jeu_ = StateJeuEnum.CHOIX;
				selection_ = StateMEnuEnum.JEU;
				System.err.println("Erreur rendu carte");
			}
			
			if(values_.tiled_Map_Render()!= ErrorEnum.OK)
			{	
				etat_jeu_ = StateJeuEnum.CHOIX;
				selection_ = StateMEnuEnum.JEU;
				System.err.println("Erreur rendu carte");
			}
			
	        //dessins du reste
			 if(etat_jeu_ == StateJeuEnum.JEU )
			 {
				//dessin uid
				 stage_game_.draw();
			 }
			 else if(etat_jeu_ == StateJeuEnum.PAUSE)
			 {
				//dessin uid
			 }
			 else if(etat_jeu_ == StateJeuEnum.INTRO)
			 {
				//dessin uid
			 }
			 else if(etat_jeu_ == StateJeuEnum.FIN)
			 {
				//dessin uid
			 }
			 
			 //Activation touche si pas deja fait
			 if(touche_activer_ == false)
			 {
				 Gdx.input.setInputProcessor(this);
				 touche_activer_ = true;
			 }
		}
		else if(etat_jeu_ == StateJeuEnum.RETOUR)
		{
			etat_jeu_  = StateJeuEnum.CHOIX;
			selection_ = StateMEnuEnum.MENU;
		}

		
		//Changer de menu
		if(selection_ != StateMEnuEnum.JEU)
		{
			StateMEnuEnum tps = selection_;
			selection_ = StateMEnuEnum.JEU;
			touche_activer_ = false;
			return  tps;
		}
		else
			return selection_;
	}

	@Override
	public boolean keyDown(int keycode) 
	{
		if(keycode == Keys.BACK)
		{
			etat_jeu_ = StateJeuEnum.CHOIX;
			selection_ = StateMEnuEnum.MENU;
	    }
		return false;
	}

	
	//only desktop
	@Override
	public boolean keyUp(int keycode) 
	{
		if(keycode == Input.Keys.LEFT)
            values_.camera_Translate(-32,0);
        if(keycode == Input.Keys.RIGHT)
        	values_.camera_Translate(32,0);
        if(keycode == Input.Keys.UP)
        	values_.camera_Translate(0,-32);
        if(keycode == Input.Keys.DOWN)
        	values_.camera_Translate(0,32);
        
        return false;
	}

	//only desktop
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) 
	{
		finger.finger_Touch(screenX, screenY, pointer);
		
		return false;
	}

	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) 
	{
		finger.finger_Up(screenX, screenY, pointer);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {

		finger.finger_Zoom(screenX, screenY, pointer);
		finger.finger_Move(screenX, screenY, pointer);
		return false;
	}

	//only desktop
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	//only desktop
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}

	
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
