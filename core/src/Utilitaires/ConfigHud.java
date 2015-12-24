package Utilitaires;

import com.badlogic.gdx.scenes.scene2d.ui.Table;


/**
 * 
 * Structure de donn�e pour la configuration des boutons du hud
 * 
 * @author Florian
 * 
 *
 */

public class ConfigHud<T> 
{
	Table table_;
	ReadXml file_;
	String node_;
	int nb_button_;
	int column_;
	int pad_;
	float width_;
	float height_;
	int other_;		//variable poubelle (on met ce qu'on veux , sa valeur depend du contexte)
	
	
	public ConfigHud()
	{
		nb_button_=0;
		column_=0;
		pad_=0;
		width_=0;
		height_=0;
	}
	
	public void table(Table t){table_=t;}
	public Table table(){return table_;}
	
	public int nb_button(){return nb_button_;} 
	public int column(){return column_;} 
	public int pad(){return pad_;}
	public int other(){return other_;} 
	public float width(){return width_;} 
	public float height(){return height_;}
	public String node(){return node_;}
	public ReadXml xml(){return file_;} 
	
	public void nb_button(int nb ){nb_button_ =nb;} 
	public void column(int c){ column_=c;} 
	public void pad(int p){pad_=p;}
	public void other(int o){other_=o;} 
	public void width(float w){width_=w;} 
	public void height(float h){height_=h;} 
	public void node(String n){node_ =n;}
	public void xml(ReadXml xml){file_ =xml;} 
	
}