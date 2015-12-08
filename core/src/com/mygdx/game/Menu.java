package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Menu extends StateMenu
{

	@Override
	public StateMenu changer_Etat() 
	{
		System.out.println("etat Menu principal");
		
		//affichage menu
		
		//si touche, on change de menu
		if(Gdx.input.isKeyPressed(Keys.UP))
		{
			
		}
		
		return ;
	}

}
