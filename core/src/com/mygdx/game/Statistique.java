package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Statistique extends StateMenu
{
	GlobalValues values_;		
	StateMEnuEnum selection_;
	
	public Statistique()
	{
		selection_ = StateMEnuEnum.STATISTIQUE;
		values_ = GlobalValues.getInstance();
	}
	
	@Override
	public StateMEnuEnum changer_Etat() 
	{

		//Changer de menu
		if(selection_ != StateMEnuEnum.STATISTIQUE)
		{
			StateMEnuEnum tps = selection_;
			selection_ = StateMEnuEnum.STATISTIQUE;
			return  tps;
		}
		else
			return selection_;
		
	}

}
