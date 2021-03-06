package proyecto;
import proyecto.Character;
import java.util.Random;
import java.util.ArrayList;
import proyecto.Printable;

class Party implements Printable{
    int posX;
    int posY;
    int direction;
    int partyMembers;
    int treasures;

    int fighters;
    int rangers;
    int mages;

    String characterClass;

    boolean found;
    int foundX;
    int foundY;
    ArrayList<Character> party = new ArrayList<Character>();

    boolean inCity;

    Party(int posX, int posY, int direction, Character c){
	this.posX = posX;
	this.posY = posY;
	this.fighters = 0;
	this.rangers = 0;
	this.mages = 0;
	this.partyMembers = 1;
	this.treasures = 0;
	this.inCity = true;

	this.found = false;
	this.foundX = 0;
	this.foundY = 0;

	if(c.type == "Fighter"){
	    characterClass = "Fighter";
	    fighters++;
	}else if(c.type == "Ranger"){
	    characterClass = "Ranger";
	    rangers++;
	}else{
	    characterClass = "Mage";
	    mages++;
	}
	party.add(c);
    }

    void addMember(Character c){
	if(c.type == "Fighter"){
	    party.add(fighters, c);
	    fighters++;
	}else if(c.type == "Ranger"){
	    party.add(fighters + rangers, c);
	    rangers++;
	}else{
	    party.add(c);
	    mages++;
	}
	partyMembers++;
    }


    void battleFinished(int treasure,int XP){
	for(Character c: party){
	    c.currentHealth = c.health;
	    c.currentXP = c.currentXP + XP;
	    c.leveledUp();
	}
	divideTreasure(treasure);
    }


    void divideTreasure(int treasure){
	int divided = (int) Math.floor(treasure/partyMembers);
	for(Character c: party)
	    c.carrying = c.carrying + divided;
    }

    @Override
    public String toString() {
        String s = "";
        s += posX + ", " + posY + ", ";
        s += treasures + ", " + partyMembers + ", ";
        s += String.valueOf(inCity) + ", " + direction + ", ";
        return s;
    }

    public String getHeader() {
        String s = "";
        s += "x, y, treasure, partyMembers, inCity, direction\n";
        return s;
    }
}
