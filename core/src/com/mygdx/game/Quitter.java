package com.mygdx.game;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Quitter extends StateMenu
{

	GlobalValues values_;		//Variables globales
	Skin skin_bouton; 			// peau du bouton
	Table layout_table;
	Stage stage;				// zone de vue et de touch du menu
	TextButton oui_bouton;
	TextButton non_bouton;
	StateMEnuEnum selection_;
	Label label_quitter ;
	
	public Quitter()
	{
		selection_ = StateMEnuEnum.QUITTER;
		values_ = GlobalValues.getInstance();
		skin_bouton = values_.get_Skin();
		
		layout_table = new Table();
		layout_table.setSize(values_.get_width(),values_.get_width());
		
		//creation zone affichage du menu
		stage = new Stage(new ScreenViewport()); 
		
		//boutons et label
		oui_bouton=new TextButton("Oui", skin_bouton);//init du bouton Jeu 
		non_bouton=new TextButton("Non", skin_bouton);//init du bouton Option 
		label_quitter = new Label("Voulez vous quitter?", skin_bouton);
		
		//création des listenners
		oui_bouton.addListener(new ClickListener()
		{
		       @Override
		       public void clicked(InputEvent event, float x, float y) 
		       {
		    	   Gdx.app.exit();
		       }
		 });
		
		non_bouton.addListener(new ClickListener()
		{
		       @Override
		       public void clicked(InputEvent event, float x, float y) {
		    	   selection_ = StateMEnuEnum.MENU;
		       }
		 });
		
		//placement des boutons	
		layout_table.add(label_quitter).width(values_.get_width()).pad(10);
		layout_table.row();
		layout_table.add(oui_bouton).width(values_.get_width()).pad(10);
		layout_table.row();
		layout_table.add(non_bouton).width(values_.get_width()).pad(10);
		stage.addActor(layout_table);
		
		//activation de la zone
		//Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public StateMEnuEnum changer_Etat() 
	{

		Gdx.input.setInputProcessor(stage);
		//stage.act();
		stage.draw();
		
		
		//Changer de menu
		if(selection_ != StateMEnuEnum.QUITTER)
		{
			StateMEnuEnum tps = selection_;
			selection_ = StateMEnuEnum.QUITTER;
			return  tps;
		}
		else
			return selection_;
	}

}
