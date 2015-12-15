package units;

public class BaseTower extends Tower {
	
	String nom = "Base Tower";
	int cout = 10;
	float attspeed = 1;
	int damage = 4;
	float dps;
	float dot = 0;
	int slow = 0;
	float zone = 0;
	boolean air = true;
	
	public BaseTower(int posX, int posY) {
		super(posX, posY);
		// TODO Auto-generated constructor stub
	}
	
}
