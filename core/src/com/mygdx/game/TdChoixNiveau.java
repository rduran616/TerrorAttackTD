package com.mygdx.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


/*
 * 
 * attention, lien absolu
 * 
 * 
 */


public class TdChoixNiveau extends StateJeu
{
	GlobalValues values_;
	StateJeuEnum selection_;
	String carte_name_;
	Array<String> lst;
	
	//widgets
	
	List<String> lvl_list_;
	Stage menu_stage;
	Table layout_menu;
	TextButton retour_;
	TextButton jouer_;
	Label erreur_nb_map_;
	
	
	
	public TdChoixNiveau()
	{
		//initialisation des variables
		selection_ = StateJeuEnum.CHOIX;
		values_ = GlobalValues.getInstance();
		
		//initialisation des widgets
		
		retour_=new TextButton("Retour", values_.get_Skin());//init du bouton Jeu 
		jouer_=new TextButton("Jouer", values_.get_Skin());//init du bouton Option 
		
		layout_menu = new Table();
		layout_menu.setSize(values_.get_width(),values_.get_height());
		
		
		//creation zone affichage du menu
		menu_stage = new Stage(new ScreenViewport()); 
		
		lvl_list_ = new List<String>(values_.get_Skin());
		lst = new Array<String>();
		FileHandle dirHandle = Gdx.files.internal("C:/Users/Florian/Desktop/YP-M4A-EQG3/TerrorAttackTD-master/android/assets/map/");
		for (FileHandle entry: dirHandle.list()) 
		{
			lst.add(entry.path());
		}
		
			
		if(lst.size == 0)
		{
			erreur_nb_map_ = new Label("Erreur: aucune carte trouvee",values_.get_Skin());
			layout_menu.add(erreur_nb_map_).pad(10);
		}
		else
		{
			lvl_list_.setItems(lst);
			layout_menu.add(lvl_list_);
			//layout_menu.add(jouer_).pad(10);
			//layout_menu.add(retour_).pad(10);
		}
		
		layout_menu.row();
		
		if(lst.size > 0)
			layout_menu.add(jouer_).pad(10);
		
		layout_menu.row();
		layout_menu.add(retour_).pad(10);
		menu_stage.addActor(layout_menu);
		
		
		
		
		retour_.addListener(new ClickListener()
		{
		       @Override
		       public void clicked(InputEvent event, float x, float y) {
		    	   selection_ = StateJeuEnum.RETOUR;
		       }
		 });
	}
	
	public String carte_name(){return carte_name_;}

	@Override
	public StateJeuEnum exectute() 
	{
		
		//afficher menu de selection
		if(lst.size == 1 )
		{
			selection_ = StateJeuEnum.INTRO;
			values_.map_name(lst.get(0));
			//values_.carte_init();		
			values_.init_tile_map();
		}
		
			
		menu_stage.draw();
		Gdx.input.setInputProcessor(menu_stage);

		
		//Changer de menu
		if(selection_ != StateJeuEnum.CHOIX)
		{
			StateJeuEnum tps = selection_;
			selection_ = StateJeuEnum.CHOIX;
			return  tps;
		}
		else
			return selection_;
	}

}
