package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Option extends StateMenu
{
	
	GlobalValues values_;		
	StateMEnuEnum selection_;
	Table layout_table;
	Stage stage;				
	TextButton retour_;

	
	public Option()
	{
		selection_ = StateMEnuEnum.OPTION;
		values_ = GlobalValues.getInstance();
		
		//creation zone affichage du menu
		stage = new Stage(new ScreenViewport()); 
		
		//boutons et label
		layout_table = new Table();
		layout_table.setSize(values_.get_width(),values_.get_width());
		retour_=new TextButton("Retour", values_.get_Skin());//init du bouton retour 
		
		
		retour_.addListener(new ClickListener()
		{
		       @Override
		       public void clicked(InputEvent event, float x, float y) {
		    	   selection_ = StateMEnuEnum.MENU;
		       }
		 });
		
		layout_table.add(retour_).width(values_.get_width()).pad(10);
		stage.addActor(layout_table);
		//Gdx.input.setInputProcessor(stage);
		

	}
	
	@Override
	public StateMEnuEnum changer_Etat() 
	{
		//affichage menu
		Gdx.input.setInputProcessor(stage);
		stage.draw();
		
		//Changer de menu
		if(selection_ != StateMEnuEnum.OPTION)
		{
			StateMEnuEnum tps = selection_;
			selection_ = StateMEnuEnum.OPTION;
			return  tps;
		}
		else
			return selection_;
				
	}
}
