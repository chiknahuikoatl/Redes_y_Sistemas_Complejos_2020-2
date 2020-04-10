package proyecto;

import proyecto.Character;
import proyecto.Monster;
import java.util.ArrayList;
import java.util.Random;

class Battle{
    int charactersAlive;
    ArrayList<Character> characters = new ArrayList<Character>();
    Monster m;
    Random rnd;

    Battle(Party p, Monster m){
	this.m = m;
	for(Character c: p.party){
	    characters.add(c);
	}
	this.charactersAlive = p.partyMembers;
    }

    boolean rollInitiative(){
	int aliveTracker = charactersAlive;
	boolean alive = true;
	do{
	    for(Character c : characters){
		if(c.currentHealth > 0)
		   attackMonster(c);
		else
		    aliveTracker -= 1;
	    }
	    if(aliveTracker < 1)
		alive = false;

	    for(Character c: characters){
		if(c.currentHealth > 0){
		    attackCharacter(c);
		    break;
		}
	    }
	}while(alive && m.currentHealth > 0);

	if(characters.isEmpty()){return true;}
	else if(m.currentHealth <= 0){return false;}
    return alive;
    }

    double roll1d20(int modifier){
	return rnd.nextInt(19) + 1 + modifier;
    }

    void attackMonster(Character c){
	if(m.AC < roll1d20((int) c.attackHit))
	   m.currentHealth = m.currentHealth - c.attackDamage;
    }

    void attackCharacter(Character c){
	if(c.AC < roll1d20((int) m.attackHit))
	   c.currentHealth = c.currentHealth - m.attackDamage;
    }

}
