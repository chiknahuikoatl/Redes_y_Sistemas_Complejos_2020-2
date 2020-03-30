package proyecto;
import proyecto.Character;
import proyecto.Party;
import java.util.Random;
import java.util.ArrayList;

class City{
    int posX;
    int posY;
    int level;
    int currentWealth;
    int[] wealthLevels = {10000, 100000, 1000000, 10000000};

    ArrayList<Party> parties = new ArrayList<Party>();

    int currentInhabitants;
    int[] cityBoundry = {5, 10, 15, 20, 25};

    City(int posX, int posY, int numberOfInhabitants){
	this.posX = posX;
	this.posY = posY;
	this.level = 1;
	this.currentWealth = 0;
	this.currentInhabitants = numberOfInhabitants;

	if(currentInhabitants < 0)
	    parties.add(new Party(posX, posY, -1, new Character()));

	for(int i = 0; i < numberOfInhabitants - 1; i++){
	    boolean b = true;
	    for(Party p: parties){
		if(p.partyMembers < 6){
		    p.addMember(new Character());
		    b = false;
		    break;
		}
	    }
	    if(b)
		parties.add(new Party(posX, posY, -1, new Character()));
	}
    }

    public void levelUp(){
	this.level = level + 1;
    }

    public void leveledUp(){
	int wealth = this.currentWealth;
	if(!(level == 5)){
	    for(int i = level; i < 5; i++){
		if(wealth > wealthLevels[i -1]){
		    levelUp();
		}else
		    break;
	    }
	}
    }

    public void addWealth(int gold){
	this.currentWealth = currentWealth + gold;
    }
}
