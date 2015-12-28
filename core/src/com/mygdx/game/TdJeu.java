package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TdJeu extends StateJeu
{

	GlobalValues values_;
	StateJeuEnum selection_;
	SpriteBatch sb_;
	
	public TdJeu()
	{
		//initialisation des variables
		selection_ = StateJeuEnum.JEU;
		values_ = GlobalValues.getInstance();
		
		sb_ = new SpriteBatch();
	}
	
	
	@Override
	public StateJeuEnum exectute() 
	{

		//mise à jour des ia
		
		
		//mise à jour des tour
		//afficher image
		for(int i=0;i < values_.tower().size();i++)
		{
			String n = values_.tower().get(i)._nom;
			if(n.equals("air"))
			{
				if(sb_!=null)
				{ 
					sb_.begin();
					values_.tower_sprite(0).setPosition(values_.get_width()/2, values_.get_height()/2);
					values_.tower_sprite(0).draw(sb_);
					sb_.end();
				}
			}
		}
		
		
		//mise à jour des projectiles
		
		
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
