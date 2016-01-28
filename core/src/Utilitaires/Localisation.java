package Utilitaires;

import java.util.Locale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;

public class Localisation 
{
	//localisation
	private FileHandle baseFileHandle =null;
	private Locale locale = null;  
	private I18NBundle myBundle = null; 
	
	public Localisation(String file, String langage, String country, String variante)
	{
		baseFileHandle = Gdx.files.internal(file);
		
		if(langage==null && country==null && variante == null)
			locale = new Locale("", "", "");
		else 
			locale = new Locale(langage, country, variante);
		
		if(baseFileHandle==null)
			System.err.println("null file handle");
		if(locale==null)
			System.err.println("null locale");
		
		myBundle = I18NBundle.createBundle(baseFileHandle, locale);
	}
	
	public I18NBundle langage(){return myBundle;}
	
	public String get(String key){if(myBundle!=null)return myBundle.get(key);else return null;}
	
	public void init_Langage(String file, String langage, String country, String variante)
	{
		if(langage=="" && country=="" && variante == "")
			locale = null;
		else 
			locale = new Locale(langage, country, variante);
			
		baseFileHandle = Gdx.files.internal(file);
		myBundle = I18NBundle.createBundle(baseFileHandle, locale);
	}
}
