package com.mygdx.game;

public class TdPause extends StateJeu
{

	GlobalValues values_;
	StateJeuEnum selection_;
	
	public TdPause()
	{
		//initialisation des variables
		selection_ = StateJeuEnum.PAUSE;
		values_ = GlobalValues.getInstance();
	}
	
	
	@Override
	public StateJeuEnum exectute() 
	{
		
		
		
		
		//Changer de menu
		if(selection_ != StateJeuEnum.PAUSE)
		{
			StateJeuEnum tps = selection_;
			selection_ = StateJeuEnum.PAUSE;
			return  tps;
		}
		else
			return selection_;
	}

}
