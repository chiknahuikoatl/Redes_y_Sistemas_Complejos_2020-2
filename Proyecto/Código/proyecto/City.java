package proyecto;
import proyecto.Character;
import java.util.Random;
import java.util.ArrayList;

class City{
    int posX;
    int posY;
    int level;
    int currentWealth;
    int[] wealthLevels = {10000, 100000, 1000000, 10000000};
    ArrayList<Character> inhabitants = new ArrayList<Character>();
    int currentInhabitants;
    int[] cityBoundry = {5, 10, 15, 20, 25};

    City(int posX, int posY, int numberOfInhabitants){
	this.posX = posX;
	this.posY = posY;
	this.level = 1;
	this.currentWealth = 0;
	this.currentInhabitants = numberOfInhabitants;

	for(int i = 0; i < numberOfInhabitants; i++)
	    inhabitants.add(new Character(posX, posY, -1));
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
