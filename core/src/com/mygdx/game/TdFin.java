package com.mygdx.game;

public class TdFin extends StateJeu
{
	GlobalValues values_;
	StateJeuEnum selection_;
	
	public TdFin()
	{
		//initialisation des variables
		selection_ = StateJeuEnum.FIN;
		values_ = GlobalValues.getInstance();
	}
	
	
	@Override
	public StateJeuEnum exectute() 
	{
		
		
		
		
		//Changer de menu
		if(selection_ != StateJeuEnum.FIN)
		{
			StateJeuEnum tps = selection_;
			selection_ = StateJeuEnum.FIN;
			return  tps;
		}
		else
			return selection_;
	}
}
