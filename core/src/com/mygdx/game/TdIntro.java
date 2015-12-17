package com.mygdx.game;

public class TdIntro extends StateJeu
{
	GlobalValues values_;
	StateJeuEnum selection_;
	
	public TdIntro()
	{
		//initialisation des variables
		selection_ = StateJeuEnum.INTRO;
		values_ = GlobalValues.getInstance();
	}
	
	
	@Override
	public StateJeuEnum exectute() 
	{
		
		selection_ = StateJeuEnum.JEU;

		//Changer de menu
		if(selection_ != StateJeuEnum.INTRO)
		{
			StateJeuEnum tps = selection_;
			selection_ = StateJeuEnum.INTRO;
			return  tps;
		}
		else
			return selection_;
	}

}
