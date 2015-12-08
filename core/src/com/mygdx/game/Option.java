package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Option extends StateMenu
{
	public Option(){System.out.println("Création Option !");}
	
	@Override
	public StateMEnuEnum changer_Etat() 
	{
		System.out.println("etat Option");
		
		//affichage menu
		
		//si touche, on change de menu
		if(Gdx.input.isKeyPressed(Keys.UP))
		{
			return StateMEnuEnum.JEU;
		}

		return StateMEnuEnum.OPTION;
	}
}
