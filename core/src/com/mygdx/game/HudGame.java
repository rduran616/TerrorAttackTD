package com.mygdx.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import Utilitaires.ConfigHud;
import Utilitaires.ReadXml;
import units.TowerAir;
import units.TowerBase;
import units.TowerSlow;
import units.TowerZone;


/**
 * 
 * @author Florian
 *
 *	Class spécialmenet prevu pour la creation du hud jeux, via un fichier xml
 */

public class HudGame 
{
	private GlobalValues values_;
	private ReadXml xml_unit_file_;		//fichier xml avec toutes les unitées
	
	//enemies:
	private int nb_mobs_; 	//nombre d'unité differente (typeenemi)
	private int nb_towers_; //nombre de tower differente (typetower)  utile pour le hud jeu
	private int nb_bonus_;	//nombre de bonus different
	
	
	private int size_hud_ = 20;			//valeur en % de la taille du hud par rapport a l'ecran
	private Stage stage_game_;				//stage du jeu
	private Layout main_layout_game_;		//layout du hud du jeu
	private Table main_table_game_;			//layout principale du hud du jeu
	private Label label_tour_;				//label pour placement tour
	private Label label_bonus_;				//label pour placement tour
	private Label label_amelioration_;		//label pour amelioratin générale des tours
	private Label vie_;
	private Label argent_;
	
	
	public HudGame()
	{
		values_ = GlobalValues.getInstance();
		
		//recup nombre de type de tour existant pour creation des boutons
		if(Gdx.app.getType() == ApplicationType.Android) //test plateforme
			xml_unit_file_ = new ReadXml("Config/units.xml"); //erreur chemin  
		else
			xml_unit_file_ = new ReadXml("../android/assets/Config/units.xml");
			
		nb_towers_ 	= xml_unit_file_.node_Item_Child_Number("tower");
		nb_bonus_	= xml_unit_file_.node_Item_Child_Number("bonus");
		
		//couleur jaune pour le background du hud
		Pixmap pm1 = new Pixmap(1, 1, Format.RGB565);
		pm1.setColor(Color.YELLOW);
		pm1.fill();
				
		main_table_game_ 	= new Table();
		main_table_game_.setSize(values_.get_width()*size_hud_/100,values_.get_height());
		//layout_table_tower_.setPosition(0,0);
		
		label_tour_				= new Label("Tours :", values_.get_Skin());
		label_bonus_			= new Label("Bonus :", values_.get_Skin());
		label_amelioration_		= new Label("Upgra :", values_.get_Skin());
		stage_game_				= new Stage(new ScreenViewport());
				
		//placement des boutons
		@SuppressWarnings("rawtypes")
		ConfigHud hud = new ConfigHud();
		hud.column(2);
		hud.height(20);
		hud.width((values_.get_width()*size_hud_/100/2) -2);
		hud.nb_button(nb_towers_);
		hud.pad(2);
		hud.xml(xml_unit_file_);
		hud.table(main_table_game_);
		
		main_table_game_.add(label_tour_);
		main_table_game_.row();
		hud.node("tower");
		creation_Hud_Objects(hud);
		main_table_game_.add(label_amelioration_);
		main_table_game_.row();
		hud.node("upgrade");
		creation_Hud_Objects(hud);
		main_table_game_.add(label_bonus_);
		main_table_game_.row();
		hud.node("bonus");
		hud.nb_button(nb_bonus_);
		creation_Hud_Objects(hud);
		
		//background jaune
		main_table_game_.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pm1))));
		stage_game_.addActor(main_table_game_);
		
		//autre informations
		vie_ = new Label("Vie :", values_.get_Skin());
		vie_.setPosition(0, values_.get_height()-20);
		argent_ = new Label("Argent :", values_.get_Skin());
		argent_.setPosition(0, values_.get_height()-40);
		
		stage_game_.addActor(argent_);
		stage_game_.addActor(vie_);
	}
	
	
	public void argent(){argent_.setText("Argent :"+Integer.toString(values_.argent()));}
	public void vie(){vie_.setText("Vie :"+Integer.toString(values_.vie()));}
	
	
	public Stage stage(){return stage_game_;} 
	
	//création des boutons de l'ui 
	private void creation_Hud_Objects(@SuppressWarnings("rawtypes") ConfigHud hud)
	{
		int cpt=0;
		hud.other(0);
		for(int i=0;i<hud.nb_button();i++)
		{
			//recuperation des chemins (ou autre, à voir) d'image pour icone bouton
			TextButton button = new TextButton(Integer.toString(i),values_.get_Skin());
			if(hud.xml().isEmpty() == false)
	    	{
				String name = hud.xml().get_Sub_Node_Item(hud.other(), hud.node(),"name");
				if(name!=null)
				{
					if(name.equals("air"))
					{
						button.addListener(new ClickListener()
						{
						       @Override
						       public void clicked(InputEvent event, float x, float y) 
						       {
						    	   System.err.println("TowerAir");
						    	   
						    	   //creation de la tour et ajout dans le tableau "list_tower" en mode non positionner
						    	   values_.tower().add(new TowerAir(values_.t_air_modele_()));
						    	   
						       }
						 });
					}
					else if(name.equals("zone"))
					{
						button.addListener(new ClickListener()
						{
						       @Override
						       public void clicked(InputEvent event, float x, float y) 
						       {
						    	   System.err.println("TowerZone");
						    	  // values_.tower().add(new TowerAir(values_.t_zone_modele_()));
						       }
						 });
					}
					else if(name.equals("slow"))
					{
						button.addListener(new ClickListener()
						{
						       @Override
						       public void clicked(InputEvent event, float x, float y) 
						       {
						    	   System.err.println("TowerSlow");
						    	   //values_.tower().add(new TowerAir(values_.t_slow_modele_());
						       }
						 });
					}
					else if(name.equals("base"))
					{
						button.addListener(new ClickListener()
						{
						       @Override
						       public void clicked(InputEvent event, float x, float y) 
						       {
						    	   System.err.println("TowerBase");
						    	   //values_.tower().add(new TowerAir(values_.t_base_modele_()));
						       }
						 });
					}
				}
			}

			hud.table().add(button).pad(hud.pad()).height(hud.height()).width(hud.width());
			cpt++;
			hud.other(cpt);
			
			if(cpt%hud.column()==0)
				hud.table().row();
		}
	}	
	

}
