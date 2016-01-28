package Utilitaires;

import com.badlogic.gdx.Gdx;

/*
 * CLASSE QUI PERMET DE GERER LES TICKS D HORLOGE
 */

public class TickHorloge 
{
	private double temps_depart_;	//temps au depart du chrono
	private double temps_actuel_;  	//temps courant en ms
	private double temps_passer_;  	//temps avant le derneir rafraichissement en ms
	private double range_;			//interval de temps en ms
	
	public TickHorloge()
	{
		range_ = 0;
		temps_passer_ = System.currentTimeMillis();;
		temps_actuel_ = temps_passer_;
	}
	
	public TickHorloge(double temps)
	{
		if(temps >= 0)
		{
			range_ = temps;
			temps_passer_ = System.currentTimeMillis();
			temps_actuel_ = temps_passer_;
			temps_depart_ = temps_passer_;
			//System.out.println(System.currentTimeMillis());
		}
		else
		{
			temps =0;
			range_ = temps;
			temps_passer_ = System.currentTimeMillis();;
			temps_actuel_ = temps_passer_;
			temps_depart_ = temps_passer_;
			System.err.println("Valeur par defaut choisi");
		}
			
	}
	
	//renvoi le temps ecoulé entre le depart et maintenant
	public double temp_Passe_Total()
	{ 
		temps_actuel_ = System.currentTimeMillis();
		return temps_actuel_ - temps_depart_;
	}
	
	//renvoi le temps ecoulé entre les dernier appels et maintenant
	public double temp_Passe()
	{ 
		temps_actuel_ = System.currentTimeMillis();
		return temps_actuel_ - temps_passer_;
	}
	
	//retourne vrai si le le temps ecoulé est supérieur au range
	public boolean tick()
	{
		temps_actuel_ = System.currentTimeMillis();
		if(temps_actuel_ - temps_passer_ >= range_)
		{
			temps_passer_ = temps_actuel_;
			return true;
		}
		else
			return false;
	}
	
	public void range(double tps)
	{
		if(tps >=0)
			range_ = tps;
		else
			range_ = 0;
	}
	
	public double range(){return range_;}
	
	//test de la classe
	public static void main(String[] args) {
		
		TickHorloge A = new TickHorloge(30); 
		int i=0;
		boolean bool = true;
		while(bool)
		{
			if(A.tick())
			{
				System.out.println(i);	
				i++;
			}
			
			if(A.temp_Passe_Total() > 1000)
				bool = false;
		}
	}
}
