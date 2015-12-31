package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import Utilitaires.FingerTransformMap;
import Utilitaires.TickHorloge;
import units.Status;
import units.TowerType;

public class Jeu extends StateMenu  implements InputProcessor
{ 
	//multiplexer de stage -> avoir plusieur stage en 1 ( interaction monde et hud)
	InputMultiplexer multiplexer = new InputMultiplexer();

	//var global
	GlobalValues values_;		
	StateMEnuEnum selection_;
	
	//gestion etat du jeu
	StateJeu jeu_[];
	StateJeuEnum etat_jeu_;
	boolean touche_activer_;
	TickHorloge tick_;			//gestion des ticks pour attente passive

	//Zoom
	FingerTransformMap finger;
	
	//HUD
	
	//hud jeu
	HudGame hud_game_;
	
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
		
		hud_game_ = new HudGame(10);
		multiplexer.addProcessor(hud_game_.stage());
	//	multiplexer.addProcessor(hud_game_.stage2());
	//	multiplexer.addProcessor(hud_game_.stage3());
		multiplexer.addProcessor(this);
		
		//boutton retour
		Gdx.input.setCatchBackKey(true);
		
		tick_ = new TickHorloge(30); //30fps max
		finger = new FingerTransformMap(0.01);
	}

	@Override
	public StateMEnuEnum changer_Etat() 
	{
		Gdx.input.setCatchBackKey(true);

		//dessin du jeu
		if(etat_jeu_ == StateJeuEnum.JEU || etat_jeu_ == StateJeuEnum.PAUSE)
		{
			//viewport de la map
			//Gdx.gl.glViewport( values_.get_width()*size_hud_/100,0,values_.get_width(),values_.get_height());

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
				// Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth()*size_hud_/100,Gdx.graphics.getHeight());//viewport du hud interactif			 
				 hud_game_.argent();
				 hud_game_.vie();
				 hud_game_.stage().draw();

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
				 Gdx.input.setInputProcessor(multiplexer);
				 touche_activer_ = true;
			 }
			 
		}
		else if(etat_jeu_ == StateJeuEnum.RETOUR)
		{
			etat_jeu_  = StateJeuEnum.CHOIX;
			selection_ = StateMEnuEnum.MENU;
		}

		// choix de l'etat et action en fonction de l'état du jeu
		etat_jeu_ = jeu_[etat_jeu_.ordinal()].exectute(); //execute fais les mise à jour des ia
		
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
			System.err.println("test");
		//	Gdx.input.setCatchBackKey(false);
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
        	values_.camera_Translate(0,32);
        if(keycode == Input.Keys.DOWN)
        	values_.camera_Translate(0,-32);
        
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

		if(values_.status() == Status.POSITIONNE)
		{
			Vector3 pos = new Vector3(screenX,screenY,0);
			values_.camera().unproject(pos);
			int x = (int) (pos.x / values_.size_Px());
			int y= (int) (pos.y / values_.size_Px());
			int cellule = ((x -1) * values_.size_n()) +  y - 1 ; 
			
			if(values_.tower()!=null)
			if(cellule<values_.size_m()*values_.size_n() && cellule>=0)
			if(values_.carte()[cellule].getUnits_size_() >0)
			{
				int  max = values_.carte()[cellule].getUnits_size_();
				for(int i=0; i< max;i++)
				{
					int index = values_.carte()[cellule].getUnits_().get(i); // index dans le tableau tower 0... n tour
					
					if(values_.tower(index).collision((int)pos.x,(int)pos.y) == true)
					{
						values_.status(Status.INFO);
						values_.setIndex_unit_selection_(index);
						
						break;
					}
				}
			}	
		}
		
		return false;
	}

	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) 
	{
		finger.finger_Up(screenX, screenY, pointer);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) 
	{
		if(values_.status()== Status.NON_POSITIONNE)
		{
			//repasse dans le  repere monde	
			Vector3 pos = new Vector3(screenX,screenY,0);
			values_.camera().unproject(pos);
			
			if(values_.last_tower().collision((int)pos.x,(int)pos.y) == true)
			{
				int Tx = Gdx.input.getDeltaX(0);
				int Ty = Gdx.input.getDeltaY(0);

				values_.last_tower().position_add(Tx, -Ty);
			}
			else
			{
				finger.finger_Zoom(screenX, screenY, pointer);
				finger.finger_Move(screenX, screenY, pointer);
			}
		}
		else if(values_.status()== Status.POSITIONNE)
		{
			finger.finger_Zoom(screenX, screenY, pointer);
			finger.finger_Move(screenX, screenY, pointer);
		}
		
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
	public boolean scrolled(int amount)
	{
		values_.zoom(amount*0.1);
		return false;
	}

}
