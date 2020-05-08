package proyecto;

import proyecto.Printable;
import proyecto.Cell;
import java.util.Vector;
import java.util.Random;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.Iterable;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;

public class CSVWriter {

    public CSVWriter(){}

    public void printStruct(ArrayList<Printable> iter,int i){

        Iterator<Printable> it = iter.iterator();
        String className = it.next().getClass().getSimpleName();
        String iStr = String.format("%02d", i);
        String file = "archivos/"+className+"-"+iStr+".csv";
        String header = it.next().getHeader();

        try (PrintWriter writer = new PrintWriter(new File(file)) ){

            StringBuilder sb = new StringBuilder();
            sb.append(header);

            while(it.hasNext()){
                sb.append(it.next().toString());
                sb.append('\n');
                it.remove();
            }

            writer.write(sb.toString());
            System.out.println("done!");

        } catch (FileNotFoundException e) {
          System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) {
        Random r = new Random();
        ArrayList<Printable> v = new ArrayList();
        for(int i = 0; i < 15; i++){
            boolean treasure;
            int amount;
            treasure = r.nextBoolean();
            amount = treasure ? r.nextInt(200) : 0;
            Cell c = new Cell(r.nextInt(50), r.nextInt(50), treasure, amount);
            c.city = r.nextBoolean();
            c.party = r.nextBoolean();
            c.monster = r.nextBoolean();
            c.taken = r.nextBoolean();
            v.add((Printable) c);
        }
        CSVWriter csv = new CSVWriter();
        csv.printStruct((ArrayList<Printable>) v, 0);
    }

    public static void sop(String s) {
        System.out.println(s);
    }


}
