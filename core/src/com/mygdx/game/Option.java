package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Option extends StateMenu
{
	
	GlobalValues values_;		
	StateMEnuEnum selection_;
	
	public Option()
	{
		selection_ = StateMEnuEnum.OPTION;
		values_ = GlobalValues.getInstance();
	}
	
	@Override
	public StateMEnuEnum changer_Etat() 
	{
		System.out.println("etat Option");
		
		//affichage menu
		
		//Changer de menu
		if(selection_ != StateMEnuEnum.OPTION)
		{
			StateMEnuEnum tps = selection_;
			selection_ = StateMEnuEnum.OPTION;
			return  tps;
		}
		else
			return selection_;
				
	}
}
