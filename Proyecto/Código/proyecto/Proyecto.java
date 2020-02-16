package proyecto;
import proyecto.Cell;
import proyecto.Character;
import processing.core.PApplet;
import processing.core.PFont;

import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

public class Proyecto extends PApplet{
    PFont font;

    int height = 100;
    int width = 150;
    int cell = 4;
    int characters = 200;
    float density = 0.01f;
    CharacterModel model;

    @Override
    public void setup(){
	background(50);
	font = createFont("Arial",12,true);
	model = new CharacterModel(width, height, cell, characters, density);
    }

    @Override
    public void settings(){
	size(width * cell, (height * cell) + 32);
    }

    @Override
    public void draw(){
	//Draw Treasures
	for(int i = 0; i < height; i++){
	    for(int j = 0; j < width; j++){
		if(model.world[i][j].treasure){
		    fill(255,165,0);
		}else{
		    fill(50);
		}
		rect(j * model.size, i * model.size, model.size, model.size);
	    }
	}

	//Draw Characters
	for(Character c : model.characters){
	    fill(135,206,250);
	    rect(c.posX * model.size, c.posY * model.size,
		 model.size, model.size);
	}

	//Information
	fill(50);
	rect(0, height * cell, (width * height), 32);
	fill(255);
	textFont(font,10);
	text("Location: "+ model.width + " x " + model.height, 5,
	     (height * cell) + 12);
	text("Generation: " + model.generation, 128, (height * cell) + 12);
	text("Characters: " + model.characters.size(), 5, (height * cell) + 24);
	text("Percentage of treasures: " + density, 128, (height * cell) + 24);
    }

    class CharacterModel{
	int width, height;
	int size;
	int generation;
	Cell[][] world;
	ArrayList<Character> characters;
	Random rnd = new Random();

	CharacterModel(int width, int height, int size, int amount,
		       float density){
	    this.width = width;
	    this.height = height;
	    this.size = size;
	    this.generation = 0;

	    world = new Cell[height][width];
	    int treasures = 0;
	    boolean create;
	    for(int i = 0; i < height; i++){
		for(int j = 0; j < height; j++){
		    create = rnd.nextFloat() < density ? true : false;
		    world[i][j] = new Cell(i, j, create);
		    if(create){
			treasures++;
		    }
		}
	    }

	    characters = new ArrayList<Character>();
	    int x, y;
	    if(amount >= (width * height) - treasures){
		throw new IllegalArgumentException("The number of characters"+
						   " won't fit");
	    }
	    for(int i = 0; i < amount; i++){
		do {
		    x = rnd.nextInt(width);
		    y = rnd.nextInt(height);
		}while(world[y][x].treasure == true);
		characters.add(new Character(x, y, rnd.nextInt(8)));
	    }
	    model.run();
	}

	void moveCharacter(Character c, int direction){
	    switch (direction) {
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

	boolean isTreasure(Character c, int d){
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
		return world[y][x].treasure;
	    case 1:
	        y = (y - 1) % height;
                    if (y < 0) {
                        y += height;
                    }
		return world[y][x].treasure;
	    case 2:
		x = (x + 1) % width;
		if (x < 0) {
		    x += width;
		}
		y = (y - 1) % height;
		if (y < 0) {
		    y += height;
		}
		return world[y][x].treasure;
	    case 3:
		x = (x + 1) % width;
		if (x < 0) {
		    x += width;
		}
		return world[y][x].treasure;
	    case 4:
		x = (x + 1) % width;
		if (x < 0) {
		    x += width;
		}
		y = (y + 1) % height;
		if (y < 0) {
		    y += height;
		}
		return world[y][x].treasure;
	    case 5:
		y = (y + 1) % height;
		if (y < 0) {
		    y += height;
		}
		return world[y][x].treasure;
	    case 6:
		x = (x - 1) % width;
		if (x < 0) {
		    x += width;
                    }
		y = (y + 1) % height;
		if (y < 0) {
		    y += height;
		}
		return world[y][x].treasure;
	    default:
		x = (x - 1) % width;
                    if (x < 0) {
                        x += width;
                    }
		return world[y][x].treasure;
	    }
	}

	void takeTreasure(Character c, int d){
	    moveCharacter(c,d);
	    c.carrying = true;
	    world[c.posY][c.posX].treasure = false;
	}

	void run(){
	    for(Character c : characters){
		int d = directionRandomFront(c.direction);
		if(this.isTreasure(c, d)){
		    this.takeTreasure(c,d);
		}else{
		    this.moveCharacter(c,d);
		}
	    }
	    generation += 1;
	}
    }


    static public void main(String args[]){
	PApplet.main(new String[]{"proyecto.Proyecto"});
    }
}
