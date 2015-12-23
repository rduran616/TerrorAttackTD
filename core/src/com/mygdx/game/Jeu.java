package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import Utilitaires.FingerTransformMap;
import Utilitaires.ReadXml;
import Utilitaires.TickHorloge;

public class Jeu extends StateMenu  implements InputProcessor
{ 
	//multiplexer de stage -> avoir plusieur stage en 1 ( interaction monde et hud)
	InputMultiplexer multiplexer = new InputMultiplexer();

	//var global
	GlobalValues values_;		
	StateMEnuEnum selection_;
	
	//gestion etat du jeu
	StateJeu jeu_[];
	StateJeuEnum etat_jeu_;
	boolean touche_activer_;
	TickHorloge tick_;			//gestion des ticks pour attente passive
	ReadXml xml_unit_file_;		//fichier xml avec toutes les unitées
	
	//enemies:
	int nb_mobs_; 	//nombre d'unité differente (typeenemi)
	int nb_towers_; //nombre de toxer differente (typetower)  utile pour le hud jeu
	
	//Zoom
	FingerTransformMap finger;
	
	//HUD
	
	//hud jeu

	int size_hud_ = 20;			//valeur en % de la taille du hud par rapport a l'ecran
	Stage stage_game_;				//stage du jeu
	Layout main_layout_game_;		//layout du hud du jeu
	Table main_table_game_;			//layout principale du hud du jeu
	Table layout_table_tower_;		//layout table pour le choix de l'unité
	Table Layout_table_upgrade_;	//layout table pour les boutons upgrade d'unité
	Table Layout_table_bonus_;		//layout table pour l'achat des bonus
	
	//hud pause
	
	//hud intro
	
	//hud fin
	
	public Jeu()
	{
		//initialisation des variables
		selection_ = StateMEnuEnum.JEU;
		values_ = GlobalValues.getInstance();
		
		//initialisation du jeu
		etat_jeu_ = StateJeuEnum.CHOIX;
		jeu_ = new StateJeu[5];
		touche_activer_ = false;
		
		if(jeu_ != null)
		{
			jeu_[0] = new TdChoixNiveau();
			jeu_[1] = new TdIntro();
			jeu_[2] = new TdPause();
			jeu_[3] = new TdJeu();
			jeu_[4] = new TdFin();
		}
		
		//initialisation des huds
		
		//recup nombre de type de tour existant pour creation des boutons
		if(Gdx.app.getType() == ApplicationType.Android) //test plateforme
			xml_unit_file_ = new ReadXml("/units.xml");
		else
			xml_unit_file_ = new ReadXml("../android/assets/units.xml");
			
		nb_towers_ 	= xml_unit_file_.node_Item_Child_Number("tower");
		
		
		//hud jeu:
		
		//couleur jaune pour le background
		Pixmap pm1 = new Pixmap(1, 1, Format.RGB565);
		pm1.setColor(Color.YELLOW);
		pm1.fill();
		
		
		layout_table_tower_ 	= new Table();
		layout_table_tower_.setSize(values_.get_width()*size_hud_/100,values_.get_height());
		//layout_table_tower_.setPosition(0,0);
		
		Layout_table_upgrade_	= new Table();
		Layout_table_bonus_		= new Table();
		main_table_game_		= new Table();
		stage_game_				= new Stage(new ScreenViewport());
		
		creation_Hud_Tower(layout_table_tower_,2,1,(values_.get_width()*size_hud_/100/2) -2,20);
		layout_table_tower_.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pm1))));
		Layout_table_bonus_.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pm1))));
		Layout_table_upgrade_.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pm1))));
		main_table_game_.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pm1))));
		
		main_table_game_.setSize(values_.get_width(), values_.get_height());
		main_table_game_.add(layout_table_tower_);
		main_table_game_.add(Layout_table_upgrade_);
		main_table_game_.add(Layout_table_bonus_);
		stage_game_.addActor(layout_table_tower_);

		multiplexer.addProcessor(stage_game_);
		multiplexer.addProcessor(this);
		
		//boutton retour
		Gdx.input.setCatchBackKey(true);
		
		
		tick_ = new TickHorloge(30); //30fps max
		finger = new FingerTransformMap(0.01);
	}

	@Override
	public StateMEnuEnum changer_Etat() 
	{
		// choix de l'etat et action en fonction de l'état du jeu
		etat_jeu_ = jeu_[etat_jeu_.ordinal()].exectute(); //execute fais les mise à jour des ia

		//dessin du jeu
		if(etat_jeu_ == StateJeuEnum.JEU || etat_jeu_ == StateJeuEnum.PAUSE)
		{
			//viewport de la map
			//Gdx.gl.glViewport( values_.get_width()*size_hud_/100,0,values_.get_width(),values_.get_height());

			//dessin de la carte
			if(values_.camera_Update() != ErrorEnum.OK)
			{	
				etat_jeu_ = StateJeuEnum.CHOIX;
				selection_ = StateMEnuEnum.JEU;
				System.err.println("Erreur camera update");
			}
			
			if(values_.tiled_Map_View()!= ErrorEnum.OK)
			{	
				etat_jeu_ = StateJeuEnum.CHOIX;
				selection_ = StateMEnuEnum.JEU;
				System.err.println("Erreur rendu carte");
			}
			
			if(values_.tiled_Map_Render()!= ErrorEnum.OK)
			{	
				etat_jeu_ = StateJeuEnum.CHOIX;
				selection_ = StateMEnuEnum.JEU;
				System.err.println("Erreur rendu carte");
			}
			
	        //dessins du reste
			 if(etat_jeu_ == StateJeuEnum.JEU )
			 {
				//dessin uid
				// Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth()*size_hud_/100,Gdx.graphics.getHeight());//viewport du hud interactif
				 stage_game_.draw();
			 }
			 else if(etat_jeu_ == StateJeuEnum.PAUSE)
			 {
				//dessin uid
			 }
			 else if(etat_jeu_ == StateJeuEnum.INTRO)
			 {
				//dessin uid
			 }
			 else if(etat_jeu_ == StateJeuEnum.FIN)
			 {
				//dessin uid
			 }
			 
			 //Activation touche si pas deja fait
			 if(touche_activer_ == false)
			 {
				 Gdx.input.setInputProcessor(multiplexer);
				 touche_activer_ = true;
			 }
		}
		else if(etat_jeu_ == StateJeuEnum.RETOUR)
		{
			etat_jeu_  = StateJeuEnum.CHOIX;
			selection_ = StateMEnuEnum.MENU;
		}

		
		//Changer de menu
		if(selection_ != StateMEnuEnum.JEU)
		{
			StateMEnuEnum tps = selection_;
			selection_ = StateMEnuEnum.JEU;
			touche_activer_ = false;
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
			etat_jeu_ = StateJeuEnum.CHOIX;
			selection_ = StateMEnuEnum.MENU;
	    }
		return false;
	}

	
	//only desktop
	@Override
	public boolean keyUp(int keycode) 
	{
		if(keycode == Input.Keys.LEFT)
            values_.camera_Translate(-32,0);
        if(keycode == Input.Keys.RIGHT)
        	values_.camera_Translate(32,0);
        if(keycode == Input.Keys.UP)
        	values_.camera_Translate(0,-32);
        if(keycode == Input.Keys.DOWN)
        	values_.camera_Translate(0,32);
        
        return false;
	}

	//only desktop
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) 
	{
		finger.finger_Touch(screenX, screenY, pointer);
		
		return false;
	}

	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) 
	{
		finger.finger_Up(screenX, screenY, pointer);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {

		finger.finger_Zoom(screenX, screenY, pointer);
		finger.finger_Move(screenX, screenY, pointer);
		return false;
	}

	//only desktop
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	//only desktop
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	
	private void creation_Hud_Tower(Table table, int column, int pad, float w,float h )
	{
		int cpt=0;
		for(int i=0;i<nb_towers_;i++)
		{
			//recuperation des chemins (ou autre, à voir) d'image pour icone bouttons
			//System.err.println(xml_unit_file_.get_Sub_Node_Item(i,"tower","src_android"));
			TextButton button = new TextButton(Integer.toString(i),values_.get_Skin());
			table.add(button).pad(pad).height(h).width(w);
			cpt++;
			
			if(cpt%column==0)
				table.row();
			
		}
	}
	

}
