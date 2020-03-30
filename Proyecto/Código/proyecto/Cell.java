package proyecto;
import java.util.Random;
class Cell{
    int cellX, cellY;
    boolean treasure;
    int treasureAmount;
    boolean city;
    boolean party;
    boolean monster;
    boolean taken;
    Random rnd = new Random();

    Cell(int cellX, int cellY, boolean treasure, int amount){
	this.cellX = cellX;
	this.cellY = cellY;
	this.treasure = treasure;
    if(amount < 1) amount = 1;
	if(treasure)
	    treasureAmount = (rnd.nextInt(amount)*50) + 100;
	this.party = false;
	this.city = false;
	this.taken = false;
	this.monster = false;
    }
}
