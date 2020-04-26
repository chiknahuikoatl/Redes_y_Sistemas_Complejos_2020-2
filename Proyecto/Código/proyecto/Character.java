package proyecto;
import proyecto.Printable;
import java.util.Random;

class Character implements Printable{
    int level;
    double attackHit;
    double attackDamage;
    double health;
    double currentHealth;
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

    int carrying;
    int amountSpent;
    int itemLevel;

    Character(){
	this.carrying = 0;
	this.level = 1;
	this.currentXP = 0;
	this.amountSpent = 0;
	this.itemLevel = 0;

	switch(rnd.nextInt(2)){
	case 0:
	    fighter();
	    break;
	case 1:
	    ranger();
	    break;
	default:
	    mage();
	    break;
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
	this.level = level + 1;
	this.attackHit = attackHit + attackHitGrowth;
	this.attackDamage = attackDamage + attackDamageGrowth;
	this.health = health + healthGrowth;
	this.currentHealth = health;
	this.AC = AC + ACGrowth;
    }

    public void leveledUp(){
	int xp = this.currentXP;
	if(!(this.level == 20))
	    for(int i = this.level; i < 20; i++){
		if(xp > levelXP[i-1]){
		    levelUp();
		}else
		    break;
	    }
    }

    public void buyItem(){
	switch(itemLevel){
	case 0:
	    if(amountSpent > 500)
		      boughtItem();
	    break;
	case 1:
	    if(amountSpent > 2000)
		    boughtItem();
	    break;
	case 2:
	    if(amountSpent > 20000)
		    boughtItem();
	    break;
	}
    }

    public void boughtItem(){
	this.attackHit = attackHit + 1;
	this.AC = AC + 1;
	this.attackDamage = this.attackDamage + 1;
	this.itemLevel = this.itemLevel + 1;
    }

    @Override
    public String toString() {
        String s = "";
        s += level + ", ";
        s += attackHit + ", " + attackDamage + ", ";
        s += health + ", " + AC + ", ";
        s += type + ", " + carrying + ", " + itemLevel + ", ";
        return s;
    }

    public String getHeader() {
        String s = "";
        s += "level, attackHit, attackDamage, health, AC, type, carrying, itemLevel\n";
        return s;
    }
}
