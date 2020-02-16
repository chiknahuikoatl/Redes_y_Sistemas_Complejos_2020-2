package proyecto;
import java.util.Random;

class Character{
    int posX, posY;
    int direction;
    boolean carrying;
    int level;
    double attackHit;
    double attackDamage;
    int health;
    int currentHealth;
    double AC;
    String type;

    double attackHitGrowth;
    double attackDamageGrowth;
    int healthGrowth;
    double ACGrowth;

    Random rnd = new Random();
    int currentXP;
    int[] levelXP = {300, 900, 2700, 6500, 14000,
                     23000, 34000, 48000, 64000, 85000,
                     100000, 120000, 140000, 165000, 195000,
                     225000, 265000, 305000, 355000};

    Character(int posX, int posY, int direction){
	this.posX = posX;
	this.posY = posY;
	this.direction = direction;
	this.carrying = false;
	this.level = 1;
	this.currentXP = 0;

	switch(rnd.nextInt(2)){
	case 0:
	    fighter();
	case 1:
	    ranger();
	default:
	    mage();
	}
    }

    private void fighter(){
	this.type = "Fighter";
	this.attackHit = 7;
	this.attackDamage = 10;
	this.health = 10;
	this.currentHealth = 10;
	this.AC = 16;
	this.attackHitGrowth = .2;
	this.attackDamageGrowth = 6;
	this.healthGrowth = 5;
	this.ACGrowth = .25;
    }

    private void mage(){
	this.type = "Mage";
	this.attackHit = 4;
	this.attackDamage = 6;
	this.health = 6;
	this.currentHealth = 6;
	this.AC = 12;
	this.attackHitGrowth = .4;
	this.attackDamageGrowth = 10;
	this.healthGrowth = 3;
	this.ACGrowth = .2;
    }

    private void ranger(){
	this.type = "Ranger";
	this.attackHit = 5;
	this.attackDamage = 8;
	this.health = 8;
	this.currentHealth = 8;
	this.AC = 14;
	this.attackHitGrowth = .3;
	this.attackDamageGrowth = 8;
	this.healthGrowth = 4;
	this.ACGrowth = .1;
    }

    public void levelUp(){
	this.attackHit = attackHit + attackHitGrowth;
	this.attackDamage = attackDamage + attackDamageGrowth;
	this.health = health + healthGrowth;
	this.currentHealth = health;
	this.AC = AC + ACGrowth;
    }

    public void leveledUP(){
	int xp = this.currentXP;

	for(int i = this.level; i <= 18; i++){
	    if(xp < levelXP[i]){
		this.level++;
		levelUp();
	    }else
		break;
	}
    }

}
