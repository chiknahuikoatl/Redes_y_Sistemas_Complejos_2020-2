package proyecto;
import proyecto.Cell;
import proyecto.Character;
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
    int characters = 100;
    int cities = 7;
    int levelAverage = 0;
    float density = 0.01f;
    World world;

    @Override
    public void setup(){
        font = createFont("Arial",12,true);
        System.out.println("Creating world...");
        world = new World(width, height, cell, characters, density);
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
            for(Character c : city.inhabitants){
                fill(0,0,255);
                rect(c.posX * world.size, c.posY * world.size,
                world.size, world.size);
            }
        }

        //Draw Cities
        for(City c : world.cityList){
            fill(255,255,255);
            rect((c.posX * world.size) -c.level/2 , (c.posY * world.size) - c.level/2,
            world.size * 2 * c.level, world.size * 2 * c.level);
        }


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
        int treasures;
        Cell[][] cell;
        ArrayList<City> cityList = new ArrayList<City>();
        Random rnd = new Random();

        int day = 0;
        int month = 0;
        int year = 0;

        World(int width, int height, int size, int characters,
        float density){
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

            if(characters >= ((width * height) - treasures)){
                throw new IllegalArgumentException("The number of characters"+
                " won't fit");
            }
            this.characters = characters;
        }
        //monstros characters  T
        void moveCharacter(Character c, int direction){

            if(!c.inCity)
            cell[c.posX][c.posY].character = false;
            switch (direction) {
                case -1:
                break;
                case 0:
                c.posX = (c.posX - 1) % width;
                if (c.posX < 0) {
                    c.posX += width;
                }
                c.posY = (c.posY - 1) % height;
                if (c.posY < 0) {
                    c.posY += height;
                }
                c.direction = direction;
                break;
                case 1:
                c.posY = (c.posY - 1) % height;
                if (c.posY < 0) {
                    c.posY += height;
                }
                c.direction = direction;
                break;
                case 2:
                c.posX = (c.posX + 1) % width;
                if (c.posX < 0) {
                    c.posX += width;
                }
                c.posY = (c.posY - 1) % height;
                if (c.posY < 0) {
                    c.posY += height;
                }
                c.direction = direction;
                break;
                case 3:
                c.posX = (c.posX + 1) % width;
                if (c.posX < 0) {
                    c.posX += width;
                }
                c.direction = direction;
                break;
                case 4:
                c.posX = (c.posX + 1) % width;
                if (c.posX < 0) {
                    c.posX += width;
                }
                c.posY = (c.posY + 1) % height;
                if (c.posY < 0) {
                    c.posY += height;
                }
                c.direction = direction;
                break;
                case 5:
                c.posY = (c.posY + 1) % height;
                if (c.posY < 0) {
                    c.posY += height;
                }
                c.direction = direction;
                break;
                case 6:
                c.posX = (c.posX - 1) % width;
                if (c.posX < 0) {
                    c.posX += width;
                }
                c.posY = (c.posY + 1) % height;
                if (c.posY < 0) {
                    c.posY += height;
                }
                c.direction = direction;
                break;
                case 7:
                c.posX = (c.posX - 1) % width;
                if (c.posX < 0) {
                    c.posX += width;
                }
                c.direction = direction;
                break;
            }
            cell[c.posX][c.posY].character = true;
        }

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

        boolean is(Character c, int d, String target){
            int x = c.posX;
            int y = c.posY;
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
            if(target.equals("Treasure"))
            return cell[x][y].treasure;
            else//(target.equals("City"))
            return cell[x][y].city;
        }

        ArrayList<Integer> divideCharacters(int characters, int cities){
            ArrayList<Integer> divided = new ArrayList<Integer>();
            int divisor = characters/cities;
            int sum = 0;
            int i = 0;
            int x;
            System.out.println("Dividing characters amongst each city...");
            for(int m = 1; m <= cities; m++)
            divided.add(0);
            while(characters > sum){
                System.out.println(divided.toString());
                x = rnd.nextInt(divisor) + 1;
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

        void takeTreasure(Character c, int d){
            moveCharacter(c,d);
            c.carrying = c.carrying + cell[c.posX][c.posY].treasureAmount;
            world.treasures--;
            cell[c.posX][c.posY].treasure = false;
            cell[c.posX][c.posY].treasureAmount = 0;
        }

        void enterCity(City city, Character character, int d){
            moveCharacter(character,d);
            character.inCity = true;
            if(character.carrying > 0){
                character.currentXP = character.currentXP +
                character.carrying;
                city.currentWealth = city.currentWealth + character.carrying;
            }
            character.amountSpent = character.amountSpent + character.carrying;
            character.carrying = 0;
            character.buyItem();
            System.out.println("Dropped off, my current wealth is: "+city.currentWealth + ", My level is now: " + city.level);
        }


        boolean checkNear(Cell c, int range, String target, int distance){
            for (int d = 1; d<distance; d++) {
                for (int i = 0; i < d + 1; i++) {
                    int x1 = c.cellX - d + i;
                    int y1 = c.cellY - i;

                    if(isEntity(x1, y1, target)) return true;

                    int x2 = c.cellX + d - i;
                    int y2 = c.cellY + i;

                    if(isEntity(x2, y2, target)) return true;
                }


                for (int i = 1; i < d; i++) {
                    int x1 = c.cellX - i;
                    int y1 = c.cellY + d - i;

                    if(isEntity(x1, y1, target)) return true;

                    int x2 = c.cellX + i;
                    int y2 = c.cellY - d + i;

                    if(isEntity(x2, y2, target)) return true;
                }
            }
            return false;
        }

        boolean isEntity(int x, int y, String target){
            if(cell[x][y] != null){
                if(target.equals("Character") && cell[x][y].character)
                    return true;
            if(target.equals("City") && cell[x][y].city)
                return true;
            // if(target.equals("Monster")
            //         && cell[x][y].monster)
            //     return true;
            }
            return false;
        }

        boolean checkNearest(){
            return true;
        }

        boolean checkPosition(){
            return true;
        }

        int moveTowards(int x, int y){
            int direction = 0;
            return direction;
        }

        ArrayList<Integer> findPosition(){
            ArrayList<Integer> pair = new ArrayList<Integer>();
            return pair;
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
                cell[x][y].character == true);

                cell[x][y] = new Cell(x, y, true, (levelAverage * 100) *
                rnd.nextInt(levelAverage) + 1);
                treasures++;
                add--;
            }
        }

        int characterAverage(){
            int totalLevels = 0;
            for(City city : cityList){
                for(Character c : city.inhabitants){
                    totalLevels = c.level + totalLevels;
                }
            }
            return totalLevels/characters;

        }

        void run(){
            for(City city : cityList){
                int i = 1;
                for(Character c : city.inhabitants){
                    if(c.inCity){
                        if(i > 0){
                            i--;
                            int d = directionRandomFront(c.direction);
                            if(this.is(c, d, "Treasure")){
                                this.takeTreasure(c,d);
                            }else{
                                this.moveCharacter(c,d);
                            }
                            c.inCity = false;
                        }
                    }else{
                        int d = directionRandomFront(c.direction);
                        if(this.is(c, d, "Treasure")){
                            this.takeTreasure(c,d);
                        }else if(this.is(c, d, "City")){
                            this.enterCity(city, c, d);
                        }else
                        this.moveCharacter(c,d);

                    }
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
