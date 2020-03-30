package proyecto;
import proyecto.Cell;
import proyecto.Character;
import proyecto.Party;
import proyecto.Monster;
import proyecto.City;
import processing.core.PApplet;
import processing.core.PFont;

import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

public class Proyecto extends PApplet{
    PFont font;

    int height = 300;
    int width = 400;
    int cell = 2;
    int characters = 251;
    int monsters = 300;
    int cities = 37;
    int levelAverage = 1;
    float density = 0.01f;
    World world;

    @Override
    public void setup(){
        font = createFont("Arial",12,true);
        System.out.println("Creating world...");
        world = new World(width, height, cell, characters, monsters, density);
        System.out.println("Setup finished");
    }

    @Override
    public void settings(){
        size(width * cell, (height * cell) + 32);
    }

    @Override
    public void draw(){
        background(0, 102, 0);
        //Draw Treasures
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                if(world.cell[i][j].treasure){
                    fill(255,255,0);
                    rect(i * world.size,j * world.size, world.size, world.size);
                }
            }
        }

        //Draw Characters
        for(City city: world.cityList){
            for(Party party : city.parties){
                fill(0,0,255);
                rect(party.posX * world.size, party.posY * world.size,
                world.size, world.size);
            }
        }

        //Draw Cities
        for(City c : world.cityList){
            fill(255,255,255);
            rect((c.posX * world.size) -c.level/2 , (c.posY * world.size) - c.level/2,
            world.size * 2 * c.level, world.size * 2 * c.level);
        }

	/*Draw monsters
	for(Monster m: world.monsterList){
	    fill(255,0,0);
	    rect(m.posX * world.size, m.posY * world.size, world.size,
		 world.size);
		 }*/


        //Information
        fill(0,102,102);
        rect(0, height * cell, (width * height), 32);
        fill(255);
        textFont(font,10);
        text("Size: "+ world.width + " x " + world.height, 5,
        (height * cell) + 12);
        text("Year: " + world.year + ", Month: " + world.month + ", Day: "
        + world.day, 128, (height * cell) + 12);
        text("Characters: " + world.characters, 5, (height * cell) + 24);
        text("Treasures: " + world.treasures, 128, (height * cell) + 24);
        text("Average Level: " + levelAverage, 251, (height * cell) + 24);

        world.run();
    }

    class World{
        int width, height;
        int size;
        int characters;
	int monsters;
        int treasures;
        Cell[][] cell;
        ArrayList<City> cityList = new ArrayList<City>();
	ArrayList<Monster> monsterList = new ArrayList<Monster>();
        Random rnd = new Random();

        int day = 0;
        int month = 0;
        int year = 0;

        World(int width, int height, int size, int characters,
	      int monsters, float density){
            this.width = width;
            this.height = height;
            this.size = size;
            cell = new Cell[width][height];
            int x, y;
	    System.out.println("Populating with treasures...");
            treasures = 0;
            boolean create;
            for(int i = 0; i < width; i++){
                for(int j = 0; j < height; j++){
                    create = rnd.nextFloat() < density ? true : false;
                    cell[i][j] = new Cell(i, j, create, 1);
                    if(create){
                        treasures++;
                    }
                }
            }

            System.out.println("Populating with cities...");
            ArrayList<Integer> divided = divideCharacters(characters, cities);
            System.out.println(divided.toString());
            for(Integer i: divided){
                do{
                    x = rnd.nextInt(width);
                    y = rnd.nextInt(height);
                    System.out.println(x+", "+y);
                }while(cell[x][y].treasure == true);
                cityList.add(new City(x, y, i));
                cell[x][y].city = true;
            }

	    /*System.out.println("Populating with monsters...");
	    while(monsters > 0){

		do{
		    x = rnd.nextInt(width);
		    y = rnd.nextInt(height);

		}while(cell[x][y].city == true ||
		       cell[x][y].city == true ||
		       checkNear(cell[x][y], "City", 5));
		monsters--;
	    }*/

            if(characters >= ((width * height) - treasures)){
                throw new IllegalArgumentException("The number of characters"+
                " won't fit");
            }
            this.characters = characters;
        }


        void moveParty(Party p, int direction){
            if(!p.inCity)
		cell[p.posX][p.posY].party = false;
            switch (direction) {
                case -1:
                break;
                case 0:
                p.posX = (p.posX - 1) % width;
                if (p.posX < 0) {
                    p.posX += width;
                }
                p.posY = (p.posY - 1) % height;
                if (p.posY < 0) {
                    p.posY += height;
                }
                p.direction = direction;
                break;
                case 1:
                p.posY = (p.posY - 1) % height;
                if (p.posY < 0) {
                    p.posY += height;
                }
                p.direction = direction;
                break;
                case 2:
                p.posX = (p.posX + 1) % width;
                if (p.posX < 0) {
                    p.posX += width;
                }
                p.posY = (p.posY - 1) % height;
                if (p.posY < 0) {
                    p.posY += height;
                }
                p.direction = direction;
                break;
                case 3:
                p.posX = (p.posX + 1) % width;
                if (p.posX < 0) {
                    p.posX += width;
                }
                p.direction = direction;
                break;
                case 4:
                p.posX = (p.posX + 1) % width;
                if (p.posX < 0) {
                    p.posX += width;
                }
                p.posY = (p.posY + 1) % height;
                if (p.posY < 0) {
                    p.posY += height;
                }
                p.direction = direction;
                break;
                case 5:
                p.posY = (p.posY + 1) % height;
                if (p.posY < 0) {
                    p.posY += height;
                }
                p.direction = direction;
                break;
                case 6:
                p.posX = (p.posX - 1) % width;
                if (p.posX < 0) {
                    p.posX += width;
                }
                p.posY = (p.posY + 1) % height;
                if (p.posY < 0) {
                    p.posY += height;
                }
                p.direction = direction;
                break;
                case 7:
                p.posX = (p.posX - 1) % width;
                if (p.posX < 0) {
                    p.posX += width;
                }
                p.direction = direction;
                break;
            }
            cell[p.posX][p.posY].party = true;
        }
	/*
	 void moveMonster(Monster m, int direction){
            switch (direction) {
                case -1:
                break;
                case 0:
                m.posX = (m.posX - 1) % width;
                if (m.posX < 0) {
                    m.posX += width;
                }
                m.posY = (m.posY - 1) % height;
                if (m.posY < 0) {
                    m.posY += height;
                }
                m.direction = direction;
                break;
                case 1:
                m.posY = (m.posY - 1) % height;
                if (m.posY < 0) {
                    m.posY += height;
                }
                m.direction = direction;
                break;
                case 2:
                m.posX = (m.posX + 1) % width;
                if (m.posX < 0) {
                    m.posX += width;
                }
                m.posY = (m.posY - 1) % height;
                if (m.posY < 0) {
                    m.posY += height;
                }
                m.direction = direction;
                break;
                case 3:
                m.posX = (m.posX + 1) % width;
                if (m.posX < 0) {
                    m.posX += width;
                }
                m.direction = direction;
                break;
                case 4:
                m.posX = (m.posX + 1) % width;
                if (m.posX < 0) {
                    m.posX += width;
                }
                m.posY = (m.posY + 1) % height;
                if (m.posY < 0) {
                    m.posY += height;
                }
                m.direction = direction;
                break;
                case 5:
                m.posY = (m.posY + 1) % height;
                if (m.posY < 0) {
                    m.posY += height;
                }
                m.direction = direction;
                break;
                case 6:
                m.posX = (m.posX - 1) % width;
                if (m.posX < 0) {
                    m.posX += width;
                }
                m.posY = (m.posY + 1) % height;
                if (m.posY < 0) {
                    m.posY += height;
                }
                m.direction = direction;
                break;
                case 7:
                m.posX = (m.posX - 1) % width;
                if (m.posX < 0) {
                    m.posX += width;
                }
                m.direction = direction;
                break;
            }
            cell[m.posX][m.posY].monster = true;
	    }*/

        int directionRandomFront(int direction){
            int n = direction;
            if(direction == 0){
                n = rnd.nextInt(3) - 1;
                if(n == -1)
                return 7;
                else
                return n;
            }else if (direction == 7){
                n = rnd.nextInt(3) + 6;
                if(n == 8)
                return 0;
                else
                return n;
            }else
            return rnd.nextInt(3) + n - 1;
        }

        //boolean is(Party p, int d, String target){
        //    int x = p.posX;
        //    int y = p.posY;
	boolean is(int x, int y, int d, String target){
            switch(d){
                case 0:
                x = (x - 1) % width;
                if (x < 0) {
                    x += width;
                }
                y = (y - 1) % height;
                if (y < 0) {
                    y += height;
                }
                break;
                case 1:
                y = (y - 1) % height;
                if (y < 0) {
                    y += height;
                }
                break;
                case 2:
                x = (x + 1) % width;
                if (x < 0) {
                    x += width;
                }
                y = (y - 1) % height;
                if (y < 0) {
                    y += height;
                }
                break;
                case 3:
                x = (x + 1) % width;
                if (x < 0) {
                    x += width;
                }
                break;
                case 4:
                x = (x + 1) % width;
                if (x < 0) {
                    x += width;
                }
                y = (y + 1) % height;
                if (y < 0) {
                    y += height;
                }
                break;
                case 5:
                y = (y + 1) % height;
                if (y < 0) {
                    y += height;
                }
                break;
                case 6:
                x = (x - 1) % width;
                if (x < 0) {
                    x += width;
                }
                y = (y + 1) % height;
                if (y < 0) {
                    y += height;
                }
                break;
                default:
                x = (x - 1) % width;
                if (x < 0) {
                    x += width;
                }
                break;
            }
	    if(target.equals("Party"))
		return cell[x][y].party;
	    if(target.equals("City"))
		return cell[x][y].city;
	    if(target.equals("Monster"))
		return cell[x][y].monster;
	    if(target.equals("Treasure"))
		return cell[x][y].treasure;
	    return false;
        }

        ArrayList<Integer> divideCharacters(int characters, int cities){
            ArrayList<Integer> divided = new ArrayList<Integer>();
            int divisor = (int) Math.ceil(characters/cities);
            int sum = 0;
            int i = 0;
            int x;
            System.out.println("Dividing characters amongst each city...");
            for(int m = 1; m <= cities; m++)
		divided.add(0);
            while(characters > sum){
                System.out.println(divided.toString());
                x = rnd.nextInt(divisor+1) + 1;
                if((sum + x) > characters)
		    x = characters - sum;
                if(i > (cities-1))
		    i = 0;
                divided.set(i, x + divided.get(i));
                sum = sum + x;
                i++;
            }
            return divided;
        }

        void takeTreasure(Party p, int d){
            moveParty(p,d);
	    p.divideTreasure(cell[p.posX][p.posY].treasureAmount);
	    p.treasures = p.treasures + cell[p.posX][p.posY].treasureAmount;
            world.treasures--;
            cell[p.posX][p.posY].treasure = false;
            cell[p.posX][p.posY].treasureAmount = 0;
	    cell[p.posX][p.posY].taken = false;
	    p.found = false;
	    System.out.println("Picked up treasure");
        }

        void enterCity(City city, Party p, int d){
            moveParty(p,d);
            p.inCity = true;
	    p.treasures = 0;
	    for(Character c: p.party){
		if(c.carrying > 0)
		    city.currentWealth = city.currentWealth +
			c.carrying;
		c.amountSpent = c.amountSpent +
		    c.carrying;
		c.carrying = 0;
		c.buyItem();
		System.out.println("Entered the city.");
		System.out.println("Dropped off, my current wealth is: "+city.currentWealth + ", My level is now: " + city.level);
	    }
        }

        boolean checkNear(Cell c, String target, int distance){
            for (int d = 1; d<distance; d++) {
                for (int i = 0; i < d + 1; i++) {
                    int x1 = (c.cellX - d + i) % width;
		    if(x1 < 0)
			x1 += width;
                    int y1 = (c.cellY - i) % height;
		    if(y1 < 0)
			y1 += height;

                    if(isEntity(x1, y1, target))
			return true;

                    int x2 = (c.cellX + d - i) % width;
		    if(x2 < 0)
			x2 += width;
                    int y2 = (c.cellY + i) % height;
		    if(y2 < 0)
			y2 += height;

                    if(isEntity(x2, y2, target))
			return true;
                }


                for (int i = 1; i < d; i++) {
                    int x1 = (c.cellX - i) % width;
		    if(x1 < 0)
			x1 += width;
                    int y1 = (c.cellY + d - i) % height;
		    if(y1 < 0)
			y1 += height;

                    if(isEntity(x1, y1, target))
			return true;

                    int x2 = (c.cellX + i) % width;
		    if(x2 < 0)
			x2 += width;
                    int y2 = (c.cellY - d + i) % height;
		    if(y2 < 0)
			y2 += height;

                    if(isEntity(x2, y2, target))
			return true;
                }
            }
            return false;
        }

        boolean isEntity(int x, int y, String target){
            if(cell[x][y] != null){
                if(target.equals("Party") && cell[x][y].party)
		    return true;
		if(target.equals("City") && cell[x][y].city)
		    return true;
		if(target.equals("Monster") && cell[x][y].monster)
		    return true;
		if(target.equals("Treasure") && cell[x][y].treasure)
		    if(!cell[x][y].taken)
			return true;
		    else
			return false;
            }
            return false;
        }

        ArrayList<Integer> findNearest(Cell c, String target){
            ArrayList<Integer> result = new ArrayList<Integer>();
	    result.add(-1);
	    result.add(-1);

	    for (int d = 1; d<max(height,width); d++) {
                for (int i = 0; i < d + 1; i++) {
                    int x1 = (c.cellX - d + i) % width;
		    if(x1 < 0)
			x1 += width;
                    int y1 = (c.cellY - i) % height;
		    if(y1 < 0)
			y1 += height;

                    if(isEntity(x1, y1, target)){
			result.set(0, x1);
			result.set(1, y1);
			return result;
		    }

                    int x2 = (c.cellX + d - i) % width;
		    if(x2 < 0)
			x2 += width;
                    int y2 = (c.cellY + i) % height;
		    if(y2 < 0)
			y2 += height;

                    if(isEntity(x2, y2, target)){
			result.set(0, x2);
			result.set(1, y2);
			return result;
		    }
                }


                for (int i = 1; i < d; i++) {
                    int x1 = (c.cellX - i) % width;
		    if(x1 < 0)
			x1 += width;
                    int y1 = (c.cellY + d - i) % height;
		    if(y1 < 0)
			y1 += height;

                    if(isEntity(x1, y1, target)){
			result.set(0, x1);
			result.set(1, y1);
			return result;
		    }

                    int x2 = (c.cellX + i) % width;
		    if(x2 < 0)
			x2 += width;
                    int y2 = (c.cellY - d + i) % height;
		    if(y2 < 0)
			y2 += height;

                    if(isEntity(x2, y2, target)){
			result.set(0, x2);
			result.set(1, y2);
			return result;
		    }
                }
            }
	    return result;
        }

        int directionToMove(int x1, int y1, int x2, int y2){
	    int x = x2 - x1;
	    int y = y2 - y1;
	    System.out.print("Direccion: ");
	    if(x > 0 && y > 0){
		System.out.println(4);
		return 4;
	    }else if(x == 0 && y > 0){
		System.out.println(5);
		return 5;
	    }else if(x < 0 && y > 0){
		System.out.println(6);
		return 6;
	    }else if(x > 0 && y == 0){
		System.out.println(3);
		return 3;
	    }else if(x < 0 && y == 0){
		System.out.println(8);
		return 8;
	    }else if(x > 0 && y < 0){
		System.out.println(2);
		return 2;
	    }else if(x == 0 && y < 0){
		System.out.println(1);
		return 1;
	    }else{
		System.out.println(0);
		return 0;
	    }
        }

	void moveToCity(City c, Party p){
	    int d = directionToMove(c.posX, c.posY, p.posX, p.posY);
	    if(is(p.posX, p.posY, d, "City"))
		enterCity(c, p, d);
	    else
		moveParty(p, d);
	}

	void moveToTreasure(Party p){
	    System.out.println("Finding treasure");
	    if(p.found){

		System.out.println("Found treasure");
		System.out.println("Treasure is located: (" + p.foundX + ", " + p.foundY + ")");
		System.out.println("I am located: (" + p.posX + ", " + p.posY + ")");
		int d = directionToMove(p.foundX, p.foundY, p.posX, p.posY);
		if(is(p.posX, p.posY, d, "Treasure")){
		    System.out.println("The treasure is next to me");
		    takeTreasure(p, d);
		}else{
		    System.out.println("Moving towards the treasure");
		    moveParty(p, d);
		}
	    }
	    else{
		boolean b = checkNear(cell[p.posX][p.posY], "Treasure", 10);
		if(b){
		    ArrayList<Integer> coordinates = findNearest(cell[p.posX][p.posY],
								 "Treasure");
		    // System.out.println(result.toString());
		    int x = coordinates.get(0);
		    int y = coordinates.get(1);
		    int d = directionToMove(x, y, p.posX, p.posY);
		    if(is(p.posX, p.posY, d, "Treasure"))
			takeTreasure(p, d);
		    else
			moveParty(p, d);
		    p.found = true;
		    p.foundX = x;
		    p.foundY = y;
		}else
		    moveParty(p ,directionRandomFront(p.direction));
	    }
	}

        void addTreasures(){
            int add = rnd.nextInt(2);
            int x; int y;
            while(true){
                if(add < 1)
                break;
                do{
                    x = rnd.nextInt(width);
                    y = rnd.nextInt(height);
                }while(cell[x][y].treasure == true ||
                cell[x][y].city == true ||
                cell[x][y].party == true);

		cell[x][y] = new Cell(x, y, true, (levelAverage * 100) *
                rnd.nextInt(10) + 1);
                treasures++;
                add--;
            }
        }

        int characterAverage(){
            int totalLevels = 0;
            for(City city : cityList){
		for(Party p: city.parties){
		    for(Character c : p.party){
			totalLevels = c.level + totalLevels;
		    }
		}
	    }
	    return totalLevels/characters;
        }

        void run(){
            for(City city : cityList){
                int i = 1;


		for(Party p: city.parties){
		    int d = directionRandomFront(p.direction);
		    if(p.inCity){
			if(p.partyMembers > 2){
			    if(i > 0){
				i--;
				moveToTreasure(p);
			    }
			}
		    }else{
			if(p.treasures > (100 * p.partyMembers * levelAverage))
			    moveToCity(city, p);
			else
			    moveToTreasure(p);

		    }
		    for(Character c: p.party)
			c.leveledUp();
                }
                city.leveledUp();
            }


            if(treasures < 100)
		addTreasures();
            else if(rnd.nextInt(8) <= 1)
		addTreasures();
            if(characterAverage() != levelAverage){
                levelAverage = characterAverage();
                System.out.println("The average level for characters is now: " +
				   characterAverage());
            }
            day++;
            if(day > 30){
                day = 1;
                month ++;
            }
            if(month > 12){
                month = 1;
                year++;
            }
        }
    }


    static public void main(String args[]){
        System.out.println("Starting up");
        PApplet.main(new String[]{"proyecto.Proyecto"});
    }
}
