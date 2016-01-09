package units;

import com.mygdx.game.GlobalValues;

/**
 * 
 * savoir quand creer mobs
 * @author Florian
 *
 */

public final class VagueRand 
{

	private static volatile VagueRand mob_ = null;
	
	public static VagueRand get_Instance()
	{
		if(VagueRand.mob_==null)
			VagueRand.mob_ = new VagueRand();
		
		return VagueRand.mob_;
	}
	
	
	private int num_vague_;
	private int power_;
	private int[] unit_power_;
	
	private int[] coef_; // tableau de mm taille que les "unit_power_" mais  avec le nombre d'apapraition lors de cette vague
	private int nb_ennemi_;

	
	private VagueRand()
	{
		super();
	}
	
	
	public void init(int power_min,int[] power_list)
	{
		try
		{
			setNum_vague_(0); 			//premiere vague
			power_ = power_min; 		//puissance de base.
			unit_power_ = power_list; 	//liste des puissances de chaque ennemi, ordonnée.
			nb_ennemi_ =0;
			
			//init tableau ou le nombre de mob par vague sera enregistré
			coef_ = new int[unit_power_.length];
			for(int i=0;i<unit_power_.length;i++)
				coef_[i]=0;	
		}
		catch(Exception e)
		{
			System.err.println("Erreur vague init :"+e);
		}
	}
	
	//Créé une vague
	public void new_Vague()
	{
		setNum_vague_(getNum_vague_() + 1); 
		int p=0;
		nb_ennemi_=0;
		
		for(int i=0;i<unit_power_.length;i++)
			coef_[i]=0;	
		
		while(p < power_)
		{
			int rand = (int)((Math.random())*unit_power_.length - 1 );
			p += unit_power_[rand];
			coef_[rand]++;
			nb_ennemi_++;
		}
	}
	
	public void new_Vague(int pow)
	{
		setNum_vague_(getNum_vague_() + 1); 
		power_ = pow;
		int p=0;
		
		for(int i=0;i<unit_power_.length;i++)
			coef_[i]=0;	
		
		while(p < power_)
		{
			int rand = (int)((Math.random())*unit_power_.length - 1 );
			if(rand < unit_power_.length)
			{
				p += unit_power_[rand];
				coef_[rand]++;
				nb_ennemi_++;
			}
		}
	}
	
	//tire le numéro du prochain ennemi
	public int get_Ennemi()
	{
		if(nb_ennemi_>0)
		{
			int rand =(int)((Math.random())*unit_power_.length - 1 );
			while(true)
			{
				if(rand < unit_power_.length)
					if(coef_[rand]>0)
					{
						coef_[rand]--;
						nb_ennemi_--;
						return rand;
					}
				rand =(int)((Math.random())*unit_power_.length - 1 );
			}
		}
		
		System.err.println("vague fini");
		return -1;
	}
	
	//calcul le nombre d'ennemi restant
	public int get_Ennemi_Restant()
	{
		int nb=0;
		
		for(int i=0;i< coef_.length ;i++)
		{
			nb+=coef_[i];
		}
		
		return nb;
	}	
	
	//retourne le nombre d'ennemi restant
	public int nb_Ennemis(){return nb_ennemi_;}
	
	public int get_Power(){return power_;}
	public void set_Power(int p){power_=p;}
	
	public int getNum_vague_() {return num_vague_;}
	public void setNum_vague_(int num_vague_) {this.num_vague_ = num_vague_;}
	
}

