package com.mygdx.game;

import java.io.IOException;

import org.w3c.dom.NodeList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class ReadInternalXML 
{
	XmlReader xml_;
	FileHandle file_;
	Element xml_element_;
	
	
	public ReadInternalXML(String path)
	{
		try
		{
			file_ = Gdx.files.internal(path);
			if(file_.exists())
			{
				xml_  = new XmlReader();
				xml_element_ = xml_.parse(file_.readString());
			}
			else
			{
				System.err.println("Erreur");
			}
		}
		catch(Exception e)
		{
			System.err.println("Erreur readinternalxml = "+e);
		}
	}
	
	public String get_Child_Attribute(String node, String attribut, int index)
	{
		try
		{
			Array<Element> items = xml_element_.getChildrenByName(node); //tower
			for (Element child : items)// == 1
			{
				//pour chaque enfant de node faire 
				for ( int i =0; i < child.getChildCount();i++)
				{	
					if(index < child.getChildCount() && index >=0)
					{
						return child.getChild(index).getAttribute(attribut);
					}
					else
						return null;
				}
			}
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
		
		//return xml_element_.get(node);
		return null;
	}
	
	
	public String get_Child_Attribute(String node, String attribut, String nameChild)
	{
		try
		{
			Array<Element> items = xml_element_.getChildrenByName(node); //tower
			for (Element child : items)// == 1
			{
				//pour chaque enfant de node faire 
				for ( int i =0; i < child.getChildCount();i++)
				{	
					if(nameChild.equals(child.getChild(i).getName()))
					{
						return child.getChild(i).getAttribute(attribut);
					}
				}
			}
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
		
		//return xml_element_.get(node);
		return null;
	}
	
	
	
	
	public String get_Attribute(String node, String attribut)
	{
		try
		{
			Array<Element> items = xml_element_.getChildrenByName(node); //tower
			return items.get(0).getAttribute(attribut);

		}
		catch(Exception e)
		{
			System.err.println(e);
		}
		
		//return xml_element_.get(node);
		return null;
	}
	
	
	public int get_Number_Child(String node)
	{
		try
		{
			Array<Element> items = xml_element_.getChildrenByName(node); //tower
			return items.size;

		}
		catch(Exception e)
		{
			System.err.println(e);
		}
		
		return -1;
	}
	
	
	
	//Retourne tous les elements enfant du noeud
	public Array<Element> read_Node(String node)
	{
		if(xml_element_ != null)
		{
			Array<Element> node_list = xml_element_.getChildrenByName(node);
			return node_list;
		}
		
		return null;
	}
	
	
	//retourne le nombre d'item avec le nom node
	public int node_Item_Number(String node)
	{
		if(xml_element_ != null)
		{
			Array<Element> node_list = xml_element_.getChildrenByName(node);
			if(node_list != null)
			{
				return node_list.size;
			}
		}
		
		return -1;
	}
	
	
	//methode qui retourne la valeur de la propriété value
	public String value(String id, String value )
	{
		if(xml_element_ != null)
		{
			Element element = xml_element_.getChildByName(id);
			return element.getAttribute(value);
		}
		
		return null;
	}

	public boolean isEmpty() {if(xml_element_ != null)return false; else return true;}
	
	
	
	
	

}
