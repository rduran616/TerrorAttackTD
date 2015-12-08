package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Statistique extends StateMenu
{

	public Statistique(){System.out.println("Création stat !");}
	
	@Override
	public StateMEnuEnum changer_Etat() 
	{
		System.out.println("etat Statistique");
		
		//affichage menu
		
		//si touche, on change de menu
		if(Gdx.input.isKeyPressed(Keys.UP))
		{
			return StateMEnuEnum.OPTION;
		}
		
		
		return StateMEnuEnum.STATISTIQUE;
		
	}

}
