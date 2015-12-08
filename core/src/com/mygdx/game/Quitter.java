package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Quitter extends StateMenu
{
	public Quitter(){System.out.println("Création quitter !");}
	
	
	@Override
	public StateMEnuEnum changer_Etat() 
	{
		System.out.println("etat Quitter");
		
		//affichage menu
		
		//si touche, on change de menu
		if(Gdx.input.isKeyPressed(Keys.UP))
		{
			return StateMEnuEnum.STATISTIQUE;
		}
		
		
		return StateMEnuEnum.QUITTER;
	}

}
