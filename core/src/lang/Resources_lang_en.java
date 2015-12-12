package lang;

import java.util.ListResourceBundle;

public class Resources_lang_en extends ListResourceBundle {

	public Object[][] getContents() 
	{
		return contents;
	}
	
	
	static final Object[][] contents = {
			{"texte_suivant", "Next"},
			{"texte_precedent", "Previous"},
	};
	
}
