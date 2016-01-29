package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Option extends StateMenu implements InputProcessor
{
	
	GlobalValues values_;		
	StateMEnuEnum selection_;
	Table layout_table;
	Stage stage;				
	TextButton retour_;
	TextButton valider_;
	ImageButton shader_button_;
	Label shader_switch_label_;
	Label chose_;
	Label lang_;
	TextButton gauche_;
	TextButton droite_ ;
	InputMultiplexer multiplexer = new InputMultiplexer();
	Image drapeau_;
	
	TextureAtlas atlas;
	Skin skin;
	
	private int num_lang_ = 0;

	public Option()
	{
		
		
		selection_ = StateMEnuEnum.OPTION;
		values_ = GlobalValues.getInstance();
		num_lang_ = Integer.parseInt( values_.localisation().get("value"));
		
		//creation zone affichage du menu
		stage = new Stage(new ScreenViewport()); 
		
		//boutons et label
		layout_table = new Table();
		layout_table.setSize(values_.get_width() -values_.get_width()*10/100,values_.get_height() -values_.get_height()*60/100 );
		layout_table.setPosition(values_.get_width()-values_.get_width()*95/100, values_.get_height()  -values_.get_height()*60/100 );
		gauche_ = new TextButton("<",values_.get_Skin());
		droite_ = new TextButton(">",values_.get_Skin());
		
		Texture txt  = new Texture(Gdx.files.internal("Lang/"+values_.localisation().get("flag")));
		Sprite sprite = new Sprite(txt);
		drapeau_ = new Image(sprite);
		
		gauche_.addListener(new ClickListener()
		{
		       @Override
		       public void clicked(InputEvent event, float x, float y) 
		       {
		    	   int val = Integer.parseInt(values_.localisation().get("value"));
		    	   val --;
		    	   
		    	   val = val%2;
		    	   changement_langue(val);
		       }
		 });
		
		droite_.addListener(new ClickListener()
		{
		       @Override
		       public void clicked(InputEvent event, float x, float y) 
		       {
		    	   int val = Integer.parseInt(values_.localisation().get("value"));
		    	   val ++;
		    	   
		    	   val = val%2;
		    	   changement_langue(val);
		       }
		 });

		
		layout_table.add(gauche_).pad(10);
		layout_table.add(drapeau_);
		layout_table.add(droite_).pad(10);
		
		
		retour_=new TextButton(values_.localisation().get("annuler")+" \\ "+values_.localisation().get("retour"), values_.get_Skin());//init du bouton retour 
		valider_ =new TextButton(values_.localisation().get("appliquer"), values_.get_Skin());//init du bouton retour
		chose_ = new Label(values_.localisation().get("choisir_langage"),values_.get_Skin());
		lang_ = new Label(values_.localisation().get("lang"),values_.get_Skin());
		
		retour_.addListener(new ClickListener()
		{
		       @Override
		       public void clicked(InputEvent event, float x, float y) 
		       {
		    	   selection_ = StateMEnuEnum.MENU;
		    	   changement_langue(num_lang_);
		       }
		 });
		
		valider_.addListener(new ClickListener()
		{
		       @Override
		       public void clicked(InputEvent event, float x, float y) 
		       {
		    	   selection_ = StateMEnuEnum.MENU;
		       }
		 });
		

		chose_.setPosition(values_.get_width()/2  - chose_.getWidth()/2, values_.get_height()-values_.get_height()*10/100);
		valider_.setWidth(values_.get_width()*40/100);
		retour_.setWidth(values_.get_width()*40/100);
		valider_.setPosition(values_.get_width()/2 - valider_.getWidth() - values_.get_width() * 1/100, values_.get_height()-values_.get_height()*90/100);
		retour_.setPosition(values_.get_width()/2 , values_.get_height()-values_.get_height()*90/100);
		lang_.setPosition(values_.get_width()/2 - lang_.getWidth()/2,values_.get_height()-values_.get_height()*70/100);
		
		
		atlas = new TextureAtlas(Gdx.files.internal("switch_skin.atlas"));
		skin = new Skin();
		skin.addRegions(atlas);

		shader_button_ = new ImageButton(skin.getDrawable("on"),skin.getDrawable("off"),skin.getDrawable("off"));
		shader_switch_label_ = new Label(values_.localisation().get("shader"),values_.get_Skin());	
		shader_button_.setWidth(shader_button_.getWidth() - shader_button_.getWidth() * 60/100);
		shader_button_.setHeight(shader_button_.getHeight() - shader_button_.getHeight() * 60/100);
		shader_button_.setPosition(values_.get_width()-values_.get_width()*25/100,values_.get_height() - values_.get_height()* 80/100);
		shader_switch_label_.setPosition(values_.get_width()-values_.get_width()*88/100,values_.get_height() - values_.get_height()* 80/100);
		
		shader_button_.addListener(new ClickListener()
		{
		       @Override
		       public void clicked(InputEvent event, float x, float y) 
		       {
		    	   
		    	   if(values_.isShader_enable())
		    	   {
		    		   values_.setShader_enable(false);
		    	   }
		    	   else
		    	   {
		    		   System.err.println("on");
		    	   }
		       }
		 });
		
		stage.addActor(chose_);
		stage.addActor(layout_table);
		stage.addActor(lang_);
		
		stage.addActor(shader_switch_label_);
		stage.addActor(shader_button_);
		
		stage.addActor(valider_);
		stage.addActor(retour_);
		
		multiplexer.addProcessor(this);
		multiplexer.addProcessor(stage);
	}
	
	private void changement_langue(int val)
	{
		switch(val)
		{
			case 0: //anglais
				values_.localisation().init_Langage("Lang/MyBundle", "", "", "");
			break;

			case 1: //francais
				values_.localisation().init_Langage("Lang/MyBundle","fr", "", "");
			break;
		}
		
		
		Texture txt  = new Texture(Gdx.files.internal("Lang/"+values_.localisation().get("flag")));
		Sprite sprite = new Sprite(txt);
		drapeau_.setDrawable(new SpriteDrawable(new Sprite(txt)));
		
		lang_.setText(values_.localisation().get("lang"));
		lang_.setPosition(values_.get_width()/2 - lang_.getWidth()/2,values_.get_height()-values_.get_height()*70/100);
		retour_.setText(values_.localisation().get("annuler")+" \\ "+values_.localisation().get("retour"));
		valider_.setText(values_.localisation().get("appliquer"));
		chose_.setText(values_.localisation().get("choisir_langage"));
		shader_switch_label_.setText(values_.localisation().get("shader"));
	}
	
	@Override
	public StateMEnuEnum changer_Etat() 
	{
		Gdx.input.setCatchBackKey(true);
		//Gdx.input.setInputProcessor(menu_stage);
		Gdx.input.setInputProcessor(multiplexer);
		
		//affichage menu
		//Gdx.input.setInputProcessor(stage);
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

	@Override
	public boolean keyDown(int keycode) 
	{
		if(keycode == Keys.BACK)
		{
			System.err.println("option");
			//Gdx.input.setCatchBackKey(false);
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
		if(values_.localisation() != null)
		{
			num_lang_ = Integer.parseInt( values_.localisation().get("value"));
			changement_langue(num_lang_);
		}
	}

	
	public int getNum_lang() {
		return num_lang_;
	}

	public void setNum_lang(int num_lang_) {
		this.num_lang_ = num_lang_;
	}
}
