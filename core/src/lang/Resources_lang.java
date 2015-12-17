package lang;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;

public class Resources_lang {

	private JFrame frame;	
	Locale locale;
	ResourceBundle res;
	
	
	//locale = new Locale("fr","");	
		
	//locale = new Locale("en","");
		
	public Resources_lang()
	{
		initialize();
	}
	
	private void initialize()
	{
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
}
