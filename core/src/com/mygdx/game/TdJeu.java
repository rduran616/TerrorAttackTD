package com.mygdx.game;


public class TdJeu extends StateJeu
{

	GlobalValues values_;
	StateJeuEnum selection_;

	
	public TdJeu()
	{
		//initialisation des variables
		selection_ = StateJeuEnum.JEU;
		values_ = GlobalValues.getInstance();
	}
	
	
	@Override
	public StateJeuEnum exectute() 
	{

		//mise � jour des ia
		
		
		//mise � jour des tour
		
		
		//mise � jour des projectiles
		
		
		//Changer de menu
		if(selection_ != StateJeuEnum.JEU)
		{
			StateJeuEnum tps = selection_;
			selection_ = StateJeuEnum.JEU;
			return  tps;
		}
		else
			return selection_;
	}

}
