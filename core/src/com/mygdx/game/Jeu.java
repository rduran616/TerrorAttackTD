package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Jeu extends StateMenu
{
	
	public Jeu(){System.out.println("Création jeu !");}

	@Override
	public StateMEnuEnum changer_Etat() 
	{
		System.out.println("etat Jeu");
		
		//affichage menu
		
		//si touche, on change de menu
		if(Gdx.input.isKeyPressed(Keys.UP))
		{
			return StateMEnuEnum.MENU;
		}
		
		
		return StateMEnuEnum.JEU;
	}

}
