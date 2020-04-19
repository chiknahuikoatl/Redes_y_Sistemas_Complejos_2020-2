package proyecto;
import java.util.Random;

public class Cell implements Printable{
    int cellX, cellY;
    boolean treasure;
    int treasureAmount;
    boolean city;
    boolean party;
    boolean monster;
    boolean taken;
    Random rnd = new Random();

    Monster m = null;

    Cell(int cellX, int cellY, boolean treasure, int amount){
    	this.cellX = cellX;
    	this.cellY = cellY;
    	this.treasure = treasure;
    	if(treasure)
    	    treasureAmount = (rnd.nextInt(amount)*50) + 100;
    	this.party = false;
    	this.city = false;
    	this.taken = false;
    	this.monster = false;
    }

    @Override
    public String toString() {
        String s = "";
        s += cellX + ", " + cellY + ", ";
        s += String.valueOf(treasure) + ", " + treasureAmount + ", ";
        s += String.valueOf(city) + ", " + String.valueOf(party) + ", ";
        s += String.valueOf(monster) + ", " + String.valueOf(taken) + ", ";
        return s;
    }

    public String getHeader() {
        String s = "";
        s += "x, y, treasure, treasureAmount, city, party, monster, taken\n";
        return s;
    }
}
