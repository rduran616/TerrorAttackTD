package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Quitter extends StateMenu implements InputProcessor
{

	GlobalValues values_;		//Variables globales
	Skin skin_bouton; 			// peau du bouton
	Table layout_table;
	Stage stage;				// zone de vue et de touch du menu
	TextButton oui_bouton;
	TextButton non_bouton;
	StateMEnuEnum selection_;
	Label label_quitter ;
	InputMultiplexer multiplexer = new InputMultiplexer();
	
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
		oui_bouton=new TextButton(values_.localisation().get("oui"), skin_bouton);//init du bouton Jeu 
		non_bouton=new TextButton(values_.localisation().get("non"), skin_bouton);//init du bouton Option 
		label_quitter = new Label(values_.localisation().get("fin_app"), skin_bouton);
		
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
		
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(this);
	}
	
	@Override
	public StateMEnuEnum changer_Etat() 
	{
		//Gdx.input.setInputProcessor(stage);
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setInputProcessor(multiplexer);
		
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

	@Override
	public boolean keyDown(int keycode) 
	{
		if(keycode == Keys.BACK)
		{
			System.err.println("option return key");
			Gdx.input.setCatchBackKey(false);
			selection_ = StateMEnuEnum.MENU;
	    }
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void init() 
	{
		oui_bouton.setText(values_.localisation().get("oui"));
		non_bouton.setText(values_.localisation().get("non"));
		label_quitter.setText(values_.localisation().get("fin_app"));
		
		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void load() 
	{
	}

	@Override
	public void dispose() 
	{
		Gdx.input.setCatchBackKey(false);
	}

}
