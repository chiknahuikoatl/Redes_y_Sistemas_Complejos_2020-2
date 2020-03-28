package proyecto;
import java.util.Random;
class Cell{
    int cellX, cellY;
    boolean treasure;
    int treasureAmount;
    boolean city;
    boolean character;
    boolean taken;
    Random rnd = new Random();
    
    Cell(int cellX, int cellY, boolean treasure, int amount){
	this.cellX = cellX;
	this.cellY = cellY;
	this.treasure = treasure;
	if(treasure)
	    treasureAmount = (rnd.nextInt(amount)*50) + 100;
	this.character = false;
	this.city = false;
	this.taken = false;
    }
}
