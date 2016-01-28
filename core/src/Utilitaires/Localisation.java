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
	
	private String lang_use_; 
	private String country_use_;
	private String variante_use_; 
	
	public Localisation(String file, String langage, String country, String variante)
	{
		baseFileHandle = Gdx.files.internal(file);
		
		lang_use_ = langage;
		country_use(country);
		variante_use(variante);
		
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
		locale = new Locale(langage, country, variante);
			
		baseFileHandle = Gdx.files.internal(file);
		myBundle = I18NBundle.createBundle(baseFileHandle, locale);
		
		lang_use_ = langage;
		country_use(country);
		variante_use(variante);
		
	}

	public String lang() {
		return lang_use_;
	}

	public void lang(String lang_use_) {
		this.lang_use_ = lang_use_;
	}

	public String country_use() {
		return country_use_;
	}

	public void country_use(String country_use_) {
		this.country_use_ = country_use_;
	}

	public String variante_use() {
		return variante_use_;
	}

	public void variante_use(String variante_use_) {
		this.variante_use_ = variante_use_;
	}
}
