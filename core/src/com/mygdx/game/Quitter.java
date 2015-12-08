package com.mygdx.game;

public class Quitter extends StateMenu
{

	@Override
	public StateMenu changer_Etat() 
	{
		System.out.println("etat Quitter");
		return null;
	}

}
