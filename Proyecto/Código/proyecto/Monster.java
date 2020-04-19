package proyecto;
import proyecto.Printable;
import java.util.Random;

class Monster implements Printable{
    int direction;
    int level;
    double attackHit;
    double attackDamage;
    double health;
    double currentHealth;
    double AC;

    Random rnd = new Random();
    int[] XP = {60, 180, 540, 1300, 2800, 4600, 6800, 9600, 12800, 17000, 20000, 24000, 28000, 33000, 39000, 45000, 53000, 61000, 71000, 80000};

    int carrying;
    int charactersKilled;
    int charactersNeeded;

    Monster(int direction, int worldLevel){
	this.direction = direction;
	this.carrying = 0;
	leveled(worldLevel);
    }

    void leveled(int l){
	this.health = 8 * l;
	this.currentHealth = 8 * l;
	this.attackDamage = 5 * l;
	this.charactersKilled = 0;
	this.charactersNeeded = 4 * l;
	this.level = l;
	if(l < 5){
	    this.attackHit = 6;
	    if(l < 4)
		this.AC = 13;
	    else
		this.AC = 14;
	}else if(l < 9){
	    this.attackHit = 8;
	    if(l < 8)
		this.AC = 15;
	    else
		this.AC = 16;
	}else if(l < 13){
	    this.attackHit = 10;
	    if(l == 9)
		this.AC = 16;
	    else
		this.AC = 17;
	}else if(l <17){
	    this.attackHit = 12;
	    this.AC = 18;
	}else{
	    this.attackHit = 14;
	    this.AC = 19;
	}
    }

    void levelUp(int characterLevels){
	charactersKilled = charactersKilled + characterLevels;
	if(charactersKilled >= charactersNeeded)
	    if(level < 20)
		leveled(level + 1);
    }

    @Override
    public String toString() {
        String s = "";
        s += direction + ", " + carrying + ", ";
        s += level + ", " + attackHit + ", ";
        s += attackDamage + ", " + health + ", " + AC + ", ";
        return s;
    }

    public String getHeader() {
        String s = "";
        s += "direction, treasure, level, attackHit, attackDamage, health, AC\n";
        return s;
    }
}
