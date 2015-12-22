package Utilitaires;

import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GlobalValues;




public class FingerTransformMap 
{
	 //parcours de la carte
	 int x_min_ =0;
	 int x_max_ =1000;
	 int y_min_ =0;
	 int y_max_ =1000;
	 
	 //limite zoom
	 int zoom_max = 0;
	 int zoom_min =0;
	 
	 //gestion des doigts
	 int numberOfFingers = 0;	//nombre de doight sur l'ecran
	 int fingerOnePointer;		//doigt numero 0
	 int fingerTwoPointer;		//doigt numero 1
	 float lastDistance = 1;	//distance entre les doigts
	 Vector3 fingerOne = new Vector3();		// coordonée du doigt 0
	 Vector3 fingerTwo = new Vector3();		//coordonnée du doigt 1
	 GlobalValues values_;					//valeur globale + camera
	 
	 double factor_ = 0.1;					//Facteur de zoom
	 
	 public FingerTransformMap()
	 {
		 values_ = GlobalValues.getInstance();
		 factor_=1;
	 }
	 
	 public FingerTransformMap(double f)
	 {
		 values_ = GlobalValues.getInstance();
		 factor(f);
	 }
	 
	 //enregistrement des coordonées des doigts sur l'ecran
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
	 
	 //zoom si deux doigts sur lecran qui se déplace
	 public void finger_Zoom(int x, int y,int pointer)
	 {
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
					values_.zoom(factor*factor_);
			}
			else if (lastDistance < distance) 
			{
				if(values_!=null)
					values_.zoom(-factor*factor_);
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
	 //sdfsdf
	 
	 public void finger_Move(int x, int y,int pointer)
	 {
		 if(numberOfFingers==1)
		 {
			 fingerOnePointer = pointer;
			 Vector3 positon_actuel = new Vector3(fingerOne.x,fingerOne.y,0);
			 fingerOne.set(x, y, 0);
			 
			 Vector3 translation = new Vector3(positon_actuel.x-fingerOne.x,positon_actuel.y-fingerOne.y,0); 
			 values_.camera_Translate(translation.x, -translation.y);
			 
		 }
	 }
	 
	 public void factor(double f){if(f != 0 ){factor_=f;}else{factor_=1;}}
	 
}
