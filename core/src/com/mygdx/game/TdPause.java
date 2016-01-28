package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TdPause extends StateJeu
{

	GlobalValues values_;
	StateJeuEnum selection_;
	
	private TextButton retour_;
	private TextButton menu_;
	private Label label_;
	private Stage stage_;
	private Table main_table_;
	private InputMultiplexer multiplexer;
	
	public TdPause()
	{
		//initialisation des variables
		selection_ = StateJeuEnum.PAUSE;
		values_ = GlobalValues.getInstance();
		
		retour_ = new TextButton(values_.localisation().get("retour"),values_.get_Skin());
		menu_ = new TextButton(values_.localisation().get("retour")+" "+values_.localisation().get("main_menu")+"?",values_.get_Skin());
		
		
		retour_.addListener(new ClickListener()
		{
		       @Override
		       public void clicked(InputEvent event, float x, float y) 
		       { 
		    	   selection_ = StateJeuEnum.JEU;
		       }
		 });	
		menu_.addListener(new ClickListener()
		{
		       @Override
		       public void clicked(InputEvent event, float x, float y) 
		       { 
		    	   selection_ = StateJeuEnum.RETOUR;
		    	   values_.reinit_modele();
		       }
		 });
		

		label_ = new Label(values_.localisation().get("pause"),values_.get_Skin());
		stage_ = new Stage();
		main_table_ = new Table();
		main_table_.setSize(values_.get_width(),values_.get_height());
		
		main_table_.add(label_).pad(10).row();
		main_table_.add(retour_).pad(10).row();
		main_table_.add(menu_).pad(10).row();
		
		stage_.addActor(main_table_);
		
		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage_);
	}
	
	
	@Override
	public StateJeuEnum exectute() 
	{
		
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setInputProcessor(multiplexer);
		stage_.draw();
		
		//Changer de menu
		if(selection_ != StateJeuEnum.PAUSE)
		{
			StateJeuEnum tps = selection_;
			selection_ = StateJeuEnum.PAUSE;
			return  tps;
		}
		else
			return selection_;
	}


	@Override
	public void init() 
	{

		retour_.setText(values_.localisation().get("retour"));
		menu_.setText(values_.localisation().get("retour")+" "+values_.localisation().get("main_menu")+"?");
		label_.setText(values_.localisation().get("pause"));
		
	}

}
