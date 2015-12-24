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
import units.TowerZone;

public class HudGame 
{
	GlobalValues values_;
	ReadXml xml_unit_file_;		//fichier xml avec toutes les unitées
	
	//enemies:
	int nb_mobs_; 	//nombre d'unité differente (typeenemi)
	int nb_towers_; //nombre de tower differente (typetower)  utile pour le hud jeu
	int nb_bonus_;	//nombre de bonus different
	
	
	int size_hud_ = 20;			//valeur en % de la taille du hud par rapport a l'ecran
	Stage stage_game_;				//stage du jeu
	Layout main_layout_game_;		//layout du hud du jeu
	Table main_table_game_;			//layout principale du hud du jeu
	Label label_tour_;				//label pour placement tour
	Label label_bonus_;				//label pour placement tour
	Label label_amelioration_;		//label pour amelioratin générale des tours
	
	
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
		
		//hud jeu:
		
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
		
		
		main_table_game_.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pm1))));
		stage_game_.addActor(main_table_game_);
	}
	
	
	public Stage stage(){return stage_game_;} 
	
	//placement des boutons pour les objets (methode temporaire)
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
						    	   System.out.println("air");
						    	   values_.tower().add(new TowerAir());
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
						    	   System.out.println("TowerZone");
						    	   values_.tower().add(new TowerZone());
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
