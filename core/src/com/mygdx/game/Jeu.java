package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Jeu extends StateMenu
{
	GlobalValues values_;		
	StateMEnuEnum selection_;
	
	public Jeu()
	{
		selection_ = StateMEnuEnum.JEU;
		values_ = GlobalValues.getInstance();
	}

	@Override
	public StateMEnuEnum changer_Etat() 
	{

		//affichage menu
		
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
