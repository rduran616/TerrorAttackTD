package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

import Utilitaires.ReadXml;
import Utilitaires.TypeFlag;


/**
 * 
 * Possède toutes les textures
 * 
 * @author Florian
 *
 */

public class SpriteConteneur 
{
	private Array<Sprite> sprite_array_;
	
	public SpriteConteneur(String file,String mainNode,String value,TypeFlag flag)
	{
		sprite_array_ 	= new Array<Sprite>();
		if(file != null && mainNode != null)
		{
			if(TypeFlag.FILEHANDLE_INTERNAL == flag)
			{
				ReadInternalXML xml = new ReadInternalXML(file);
				if(xml!=null)
				{
					int nb_ = xml.get_Number_Child(mainNode); //recup nombre de sous noeud
					System.err.println(nb_);
					for(int i=0;i<nb_;i++)//pour chaque noeud recuperer le sprite et le stocker
					{
						String path = xml.get_Child_Attribute(mainNode, value, i);
						System.err.println(path);
						try
						{
							System.err.println(path);
							Texture tps_txture = new Texture(Gdx.files.internal(path));
							Sprite tps =new Sprite(tps_txture);
							sprite_array_.add(tps);
						}
						catch(Exception e){System.err.println("erreur  "+e);}
						
					}
				}
			}
			else
			{
				//si noeud principal et sous noeud faire
				ReadXml xml_tower = new ReadXml(file);
				if(xml_tower!=null)
				{
					int nb_ = xml_tower.node_Item_Child_Number(mainNode); //recup nombre de sous noeud
					for(int i=0;i<nb_;i++)//pour chaque noeud recuperer le sprite et le stocker
					{
						System.err.println(i);
						String path = xml_tower.get_Sub_Node_Item(i,mainNode,value);
						if(path!=null && path != "")
						{
							try
							{
								if(Gdx.app.getType() == ApplicationType.Android)
								{
									System.err.println(path);
									Texture tps_txture = new Texture(Gdx.files.internal(path));
									Sprite tps =new Sprite(tps_txture);
									sprite_array_.add(tps);
								}
								else
								{
									System.err.println(path);
									Texture tps_txture = new Texture(path);
									Sprite tps =new Sprite(tps_txture);
									sprite_array_.add(tps);
								}
							}
							catch(Exception e){System.err.println("erreur "+e);}
						}
					}
				}
			}
			
		}
		else
			System.err.println("Erreur creation de sprite conteneur");
	}
	
	
	public Array<Sprite> sprites(){return sprite_array_;}
	
	public Sprite sprite(int index)
	{
		try
		{
			if(sprite_array_ != null)
				if(sprite_array_.size >= index)
					return sprite_array_.get(index);
			
			return null;
			
		}catch(Exception e){}
		
		return null;
	}

	
	
	
	
	
	
	public static void main(String [ ] args)
	{
		SpriteConteneur mobs_sprite_1;
		SpriteConteneur tower_sprite_1;
		SpriteConteneur mobs_sprite_2;
		SpriteConteneur tower_sprite_2;
		
		mobs_sprite_1  = new SpriteConteneur("Config/units.xml", "mobs", "src_andro", TypeFlag.FILEHANDLE_INTERNAL);
		tower_sprite_1 = new SpriteConteneur("Config/units.xml", "tower", "src_andro",TypeFlag.FILEHANDLE_INTERNAL);
		

		 FileHandle fichier = Gdx.files.internal("Config/units.xml");

	//	mobs_sprite_2  = new SpriteConteneur("../android/assets/Config/units.xml", "mobs", "src_desk",TypeFlag.PATH);
	//	tower_sprite_2 = new SpriteConteneur("../android/assets/Config/units.xml", "tower","src_desk",TypeFlag.PATH);
	}

}
