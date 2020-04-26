package proyecto;

import proyecto.Cell;
import proyecto.Pair;
import processing.core.PApplet;
import processing.core.PFont;
import java.util.Random;

public class Test extends PApplet{
    PFont font;
    World world;
    int height = 10;
    int width = 10;
    int cell = 50;
    int contador = 0;

    @Override
    public void setup(){
	font = createFont("Arial",12,true);
        System.out.println("Creating world...");
        world = new World(width, height, cell);
        System.out.println("Setup finished");
    }

    @Override
    public void settings(){
        size(width * cell, (height * cell) + 32);
    }

    @Override
    public void draw(){
	//Party - Posicion actual
	//City - Ya paso por ese posicion
	//Taken - Camino de regreso
	//Treasure - Busqueda


        background(0, 102, 0);
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                if(world.cell[i][j].party){
                    fill(0,255,255);
                    rect(i * world.size,j * world.size, world.size, world.size);
                }else if(world.cell[i][j].city){
                    fill(255,120,0);
                    rect(i * world.size,j * world.size, world.size, world.size);
		}else if(world.cell[i][j].taken){
                    fill(5,255,100);
                    rect(i * world.size,j * world.size, world.size, world.size);
		}else if(world.cell[i][j].treasure){
		    fill(255,255,0);
                    rect(i * world.size,j * world.size, world.size, world.size);
		}else {
    fill(0,0,0);
    rect(i * world.size,j * world.size, world.size, world.size);

  }
        }
    }




	/*
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
	 */
        // world.run();
        pintar(contador);
        contador ++;
        delay(500);
    }

    void pintar(int contador){
        int contAux = 0;
        Cell c = world.cell[2][2];
        String target = "Treasure";
        world.cell[6][6].treasure = true;
          for (int d = 1; d <= 1; d++) { //max(height+1,width+1)
                  for (int i = 0; i < d + 1; i++) {
                      int x1 = (c.cellX - d + i) % width;
              if(x1 < 0)
              x1 += width;
                      int y1 = (c.cellY - i) % height;
              if(y1 < 0)
              y1 += height;
              if(contAux > contador) break;
              world.cell[x1][y1].city = true;
              contAux++;
                      // rect(x1 * world.size,y1 * world.size, world.size, world.size);

                      int x2 = (c.cellX + d - i) % width;
              if(x2 < 0)
              x2 += width;
                      int y2 = (c.cellY + i) % height;
              if(y2 < 0)
              y2 += height;

              if(contAux > contador) break;
              world.cell[x2][y2].city = true;
              contAux++;

                  }


              //     for (int i = 1; i < d; i++) {
              //         int x1 = (c.cellX - i) % width;
              // if(x1 < 0)
              // x1 += width;
              //         int y1 = (c.cellY + d - i) % height;
              // if(y1 < 0)
              // y1 += height;
              //
              // if(contAux > contador) break;
              // world.cell[x1][y1].city = true;
              // contAux++;
              //
              //
              //         int x2 = (c.cellX + i) % width;
              // if(x2 < 0)
              // x2 += width;
              //         int y2 = (c.cellY - d + i) % height;
              // if(y2 < 0)
              // y2 += height;
              //
              // if(contAux > contador) break;
              // world.cell[x2][y2].city = true;
              // contAux++;
              //
              //     }
              }
              System.out.println("No lo encuentra");
          // return new Pair(-1, -1);


    }

    class World{
        int width, height;
        int size;
        Cell[][] cell;
        Random rnd = new Random();

        World(int width, int height, int size){
            this.width = width;
            this.height = height;
            this.size = size;
            cell = new Cell[width][height];


            for(int i = 0; i < width; i++){
                for(int j = 0; j < height; j++){
                    cell[i][j] = new Cell(i, j, false, 1);
                }
            }

	    cell[2][2].party = true;
	    // cell[5][7].treasure = true;
        }


	void moveParty(int x, int y, int direction){
	    cell[x][y].party = false;
	    cell[x][y].city = true;
            switch (direction) {
	    case -1:
                break;
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
	    case 7:
                x = (x - 1) % width;
                if (x < 0) {
                    x += width;
                }
                break;
            }
            cell[x][y].party = true;
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

      Pair findNearest(Cell c, String target){

	    for (int d = 1; d<max(height,width); d++) {
                for (int i = 0; i < d + 1; i++) {
                    int x1 = (c.cellX - d + i) % width;
		    if(x1 < 0)
			x1 += width;
                    int y1 = (c.cellY - i) % height;
		    if(y1 < 0)
			y1 += height;

                    if(isEntity(x1, y1, target)){
			return new Pair(x1, y1);
		    }

                    int x2 = (c.cellX + d - i) % width;
		    if(x2 < 0)
			x2 += width;
                    int y2 = (c.cellY + i) % height;
		    if(y2 < 0)
			y2 += height;

                    if(isEntity(x2, y2, target)){
			return new Pair(x2, y2);
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
			return new Pair(x1, y1);
		    }

                    int x2 = (c.cellX + i) % width;
		    if(x2 < 0)
			x2 += width;
                    int y2 = (c.cellY - d + i) % height;
		    if(y2 < 0)
			y2 += height;

                    if(isEntity(x2, y2, target)){
			return new Pair(x2, y2);
		    }
                }
            }
            System.out.println("No lo encuentra");
	    return new Pair(-1, -1);
        }

        int directionToMove(int x1, int y1, int x2, int y2){

	    int x = x2 - x1;
	    int y = y2 - y1;
	    if(x > 0 && y > 0){
		return 4;
	    }else if(x == 0 && y > 0){
		return 5;
	    }else if(x < 0 && y > 0){
		return 6;
	    }else if(x > 0 && y == 0){
		return 3;
	    }else if(x < 0 && y == 0){
		return 8;
	    }else if(x > 0 && y < 0){
		return 2;
	    }else if(x == 0 && y < 0){
		return 1;
        }else if(x < 0 && y < 0){
		return 0;
    	} else{
        return -1;
        }
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
			return true;
            }
            return false;
        }

	/**void moveToCity(City c, Party p){
	    int d = directionRandomFront(p.direction);//directionToMove(c.posX, c.posY, p.posX, p.posY);
	    if(is(p.posX, p.posY, d, "City"))
		enterCity(c, p, d);
	    else
		moveParty(p, d);
		}**/

	void run(){
	    int x = 0; int y = 0;
	    for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                if(cell[i][j].party){
			        x = i;
	                y = j;
                  break;
	            }
            }
	    }
        int direction = rnd.nextInt(7);
        cell[4][6].treasure = true;
        if(checkNear(cell[x][y], "Treasure", 5)){
            sopD("No hay");
            Pair p = findNearest(cell[x][y], "Treasure");
            if (p.posX == -1 && p.posY == -1){
                direction = directionRandomFront(direction);
            }else{
                direction = directionToMove(x, y, p.posX, p.posY);
            }
            sopD("Treasure is at: " + p.posX + " " + p.posY );
            sopD(""+direction);
            moveParty(x,y, direction);
        }
        moveParty(x,y,direction);

    //   if(checkNear(cell[x][y], "Treasure", 50)){
    //     System.out.println("Found treasure");
    //       Pair p = findNearest(cell[x][y], "Treasure");
    //       int direction = directionToMove(x, y, p.posX, p.posY);
    //
    //       moveParty(p.posX, p.posY, direction);
    //   }
    //   else
    //   System.out.println("Could not find treasure");
	}
}

public void sopD(String s){
    System.out.println(s);
    delay(1000);
}

static public void main(String args[]){
    System.out.println("Starting up");
    PApplet.main(new String[]{"proyecto.Test"});
}
}
