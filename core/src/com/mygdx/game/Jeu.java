package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Jeu extends StateMenu
{
	GlobalValues values_;		
	StateMEnuEnum selection_;
	StateJeu jeu_[];
	StateJeuEnum etat_jeu_;
	
	
	public Jeu()
	{
		//initialisation des variables
		selection_ = StateMEnuEnum.JEU;
		values_ = GlobalValues.getInstance();
		
		//initialisation du jeu
		etat_jeu_ = StateJeuEnum.CHOIX;
		jeu_ = new StateJeu[5];
		
		if(jeu_ != null)
		{
			jeu_[0] = new TdChoixNiveau();
			jeu_[1] = new TdIntro();
			jeu_[2] = new TdPause();
			jeu_[3] = new TdJeu();
			jeu_[4] = new TdFin();
		}
	}

	@Override
	public StateMEnuEnum changer_Etat() 
	{

		// choix de l'etat et action en fonction de l'état du jeu
		etat_jeu_ = jeu_[etat_jeu_.ordinal()].exectute();

		//dessin du jeu
		
		if(etat_jeu_ == StateJeuEnum.JEU || etat_jeu_ == StateJeuEnum.PAUSE)
		{
			//dessin de la carte
			values_.camera_Update();
			values_.tiled_Map_View();
	        values_.tiled_Map_Render();
	        
	        //dessins du reste
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
			return  tps;
		}
		else
			return selection_;
	}

}
