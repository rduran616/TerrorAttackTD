package Utilitaires;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GlobalValues;




public class FingerTransformMap 
{
	 //parcours de la carte
	 private int x_min_ =0;
	 private int x_max_ =1000;
	 private int y_min_ =0;
	 private int y_max_ =1000;
	 
	 //limite zoom
	 private double zoom_max_ = 0;
	 private double zoom_min_ =0;
	 
	 //gestion des doigts
	 private int numberOfFingers = 0;	//nombre de doight sur l'ecran
	 private int fingerOnePointer;		//doigt numero 0
	 private int fingerTwoPointer;		//doigt numero 1
	 private float lastDistance = 1;	//distance entre les doigts
	 private Vector3 fingerOne = new Vector3();		// coordon�e du doigt 0
	 private Vector3 fingerTwo = new Vector3();		//coordonn�e du doigt 1
	 GlobalValues values_;					//valeur globale + camera
	 MapProperties map_prop_;				//propri�t� de la carte
	 
	 double factor_ = 0.1;					//Facteur de zoom
	 
	 public FingerTransformMap()
	 {
		 values_ = GlobalValues.getInstance();
		 factor_=1;
		 map_prop_ = values_.map_Properties();
	 }
	 
	 public FingerTransformMap(double f)
	 {
		 values_ = GlobalValues.getInstance();
		 factor(f);
	 }
	 
	 
	 //enregistrement des coordon�es des doigts sur l'ecran
	 public void finger_Touch(int x, int y,int pointer)
	 {
		 numberOfFingers++;
		 if(numberOfFingers == 1)
		 {
		        fingerOnePointer = pointer;
		        fingerOne.set(x, y, 0);
		 }
		 else if(numberOfFingers == 2)
		 {
		        fingerTwoPointer = pointer;
		        fingerTwo.set(x, y, 0);
		 

		       float distance = fingerOne.dst(fingerTwo);
		       if(distance == 0)
		    	   distance = 1;
		       
		       lastDistance = distance;
		 }
	 }
	 
	 //zoom si deux doigts sur lecran qui se d�place
	 public void finger_Zoom(int x, int y,int pointer)
	 {
		 //System.err.println("finger = "+numberOfFingers);
		 if(numberOfFingers>=2)
		 {
		 
			if (pointer == fingerOnePointer)
				 fingerOne.set(x, y, 0);
	
			if (pointer == fingerTwoPointer)
				 fingerTwo.set(x, y, 0);
			 
			if(lastDistance==0)
				lastDistance=1;
			 
			float distance = fingerOne.dst(fingerTwo);
			float factor = distance / lastDistance;
			 
			if (lastDistance > distance) 
			{
				if(values_!=null)
					values_.zoom(0.1f,zoom_max_,zoom_min_);
			}
			else if (lastDistance < distance) 
			{
				if(values_!=null)
					values_.zoom(-0.1f,zoom_max_,zoom_min_);
			}
	
			lastDistance = distance;
		 }
 
	 }
	 
	 //qd on leve les doigts on le sauvegarde
	 public void finger_Up(int x, int y,int pointer)
	 {     
		 numberOfFingers--;
		 if(numberOfFingers<0)
			 numberOfFingers = 0;
		lastDistance = 0;
	 }
	 
	 public void finger_Move(int x, int y,int pointer)
	 {
		 if(numberOfFingers==1)
		 {
			 
			 fingerOnePointer = pointer;
			 Vector3 positon_actuel = new Vector3(fingerOne.x,fingerOne.y,0);
			 fingerOne.set(x, y, 0);
			 Vector3 translation = new Vector3(positon_actuel.x-fingerOne.x,positon_actuel.y-fingerOne.y,0); 
			 
			 if((values_.camera().position.x + translation.x) >= x_min_ && (values_.camera().position.x + translation.x) < x_max_)
				 	values_.camera_Translate(translation.x, 0);
			 if((values_.camera().position.y - translation.y) >= y_min_ && (values_.camera().position.y - translation.y) < y_max_ )
					values_.camera_Translate(0,-translation.y);
			 
		 }
	 }
	 
	 public void factor(double f){if(f != 0 ){factor_=f;}else{factor_=1;}}

	public void set_Range(int x_min, int x_max, int y_min, int y_max, int zoom_min, int zoom_max) 
	{
		
		 x_min_ =x_min;
		 x_max_ =x_max;
		 y_min_ =y_min;
		 y_max_ =y_max;
		 
		 zoom_max_ =zoom_max;
		 zoom_min_ =zoom_min;
	}
	 
}
