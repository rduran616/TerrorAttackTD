package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class TdFin extends StateJeu
{
	GlobalValues values_;
	StateJeuEnum selection_;	
	Table layout_table;
	Label label_victoire;
	Stage stage;				
	TextButton retour_;
	InputMultiplexer multiplexer = new InputMultiplexer();
	
	
	public TdFin()
	{
		//initialisation des variables
		selection_ = StateJeuEnum.FIN;
		values_ = GlobalValues.getInstance();
		
		//creation zone affichage du menu
		stage = new Stage(new ScreenViewport()); 
		
		//boutons et label
		layout_table = new Table();
		layout_table.setSize(values_.get_width(),values_.get_width());
		retour_=new TextButton(values_.localisation().get("retour"), values_.get_Skin());//init du bouton retour 
		label_victoire = new Label(values_.localisation().get("victoire")+"\n"+values_.localisation().get("gagner"),values_.get_Skin());
		
		retour_.addListener(new ClickListener()
		{
		       @Override
		       public void clicked(InputEvent event, float x, float y) {
		    	   selection_ = StateJeuEnum.RETOUR;
		       }
		 });
		layout_table.add(label_victoire).width(values_.get_width()).pad(10).row();
		layout_table.add(retour_).width(values_.get_width()).pad(10);
		stage.addActor(layout_table);
		
		multiplexer.addProcessor(stage);
	}
	
	
	@Override
	public StateJeuEnum exectute() 
	{
		stage.draw();
		Gdx.input.setInputProcessor(multiplexer);
		
		//Changer de menu
		if(selection_ != StateJeuEnum.FIN)
		{
			StateJeuEnum tps = selection_;
			selection_ = StateJeuEnum.FIN;
			return  tps;
		}
		else
			return selection_;
	}


	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
}
