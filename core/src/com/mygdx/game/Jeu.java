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
		etat_jeu_ = StateJeuEnum.INTRO;
		jeu_ = new StateJeu[4];
		if(jeu_ != null)
		{
			jeu_[0] = new TdIntro();
			jeu_[1] = new TdPause();
			jeu_[2] = new TdJeu();
			jeu_[3] = new TdFin();
		}

	}

	@Override
	public StateMEnuEnum changer_Etat() 
	{

		// choix de l'etat et action en fonction de l'état du jeu
		
		
		
		//dessin du jeu
		
		

		
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
