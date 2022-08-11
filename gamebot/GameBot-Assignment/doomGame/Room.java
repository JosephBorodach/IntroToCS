package edu.yu.cs.intro.doomGame;
import java.util.*;
import java.util.SortedSet;
import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
public class Room implements Comparable<Room>{ //A Room in the game, which contains both monsters as well as rewards for the player that completes the room, which is defined as the player who kills the last living monster in the room
    private SortedSet<Monster> monsters;
    private Set<Weapon> weaponsWonUponCompletion;
    private Map<Weapon,Integer> ammoWonUponCompletion;
    private int healthWonUponCompletion;
    private String roomName;
    private SortedSet<Monster> monstersDead;
    public Room(SortedSet<Monster> monsters, Set<Weapon> weaponsWonUponCompletion,Map<Weapon,Integer> ammoWonUponCompletion,int healthWonUponCompletion,String name){
        if (monsters == null || weaponsWonUponCompletion == null || ammoWonUponCompletion == null || ammoWonUponCompletion == null) { 
            throw new IllegalArgumentException ("");        
        } 
        this.monsters = new TreeSet<>();
        this.weaponsWonUponCompletion = new HashSet<>();
        this.ammoWonUponCompletion = new HashMap<>();
        this.monstersDead = new TreeSet<>();
        for (Monster monster : monsters) { 
            if (monster != null) {
                this.monsters.add(monster);
            }
        }
        for (Weapon weapon : weaponsWonUponCompletion) {
            if (weapon != null) {
                this.weaponsWonUponCompletion.add(weapon);
            }
        }
        for (Weapon weapon : ammoWonUponCompletion.keySet()) {
            if (weapon != null) {
                this.ammoWonUponCompletion.put(weapon, ammoWonUponCompletion.get(weapon));
            }
        }
        this.healthWonUponCompletion = healthWonUponCompletion;
        this.roomName = name;
    }

    protected void monsterKilled(Monster monster){ //Mark the given monster as being dead. Reduce the danger level of this room by monster.getMonsterType().ordinal()+1
        if (monster == null) {
            throw new IllegalArgumentException ("");
        }
        this.monstersDead.add(monster); 
    }

    public int getDangerLevel(){ //The danger level of the room is defined as the sum of the ordinal+1 value of all living monsters, i.e. adding up (m.getMonsterType().ordinal() + 1) of all the living monsters
        int getDangerLevel = 0;
        for (Monster monster : getLiveMonsters()) {
            getDangerLevel += (monster.getMonsterType().ordinal()+1);
        } return getDangerLevel; //return the danger level of this room
    }

    public String getName(){ //return name of the room
        return this.roomName;
    }

    @Override 
    public int compareTo(Room other) { //compares based on danger level
        if (other == null) {
            throw new IllegalArgumentException ("");        
        } return this.getDangerLevel() - other.getDangerLevel();
    }

    public Set<Weapon> getWeaponsWonUponCompletion(){ //return the set of weapons the player who completes the room is rewarded with. Make sure you don't allow the caller to modify the actual set! - @see java.util.Collections#unmodifiableSet(Set)
        Set<Weapon> getWeaponsWonUponCompletion = new HashSet<>();
        if (this.weaponsWonUponCompletion != null) {
            getWeaponsWonUponCompletion = Collections.unmodifiableSet(this.weaponsWonUponCompletion);
        } return getWeaponsWonUponCompletion;
    }

    public Map<Weapon,Integer> getAmmoWonUponCompletion(){ //return The set of ammunition the player who completes the room is rewarded with. Make sure you don't allow the caller to modify the actual set! - @see java.util.Collections#unmodifiableSet(Set)
        Map<Weapon,Integer> getAmmoWonUponCompletion = new HashMap<>();
        if (this.ammoWonUponCompletion.keySet() != null) {
            getAmmoWonUponCompletion = Collections.unmodifiableMap(this.ammoWonUponCompletion);
        } return getAmmoWonUponCompletion;
    }

    public int getHealthWonUponCompletion(){ //return The amount of health this room
        return this.healthWonUponCompletion;
    }

    public boolean isCompleted(){ //indicates if all the monsters in the room are dead
        if (this.getLiveMonsters().isEmpty()) { 
            return true; 
        } else {
            return false;
        }
    }

    public SortedSet<Monster> getMonsters(){ //return the SortedSet of all monsters in the room (unmodifiableSortedSet)
        SortedSet<Monster> getMonsters = new TreeSet<>();
        if (this.monsters != null && !this.monsters.isEmpty()) {
            getMonsters = Collections.unmodifiableSortedSet(this.monsters);
        } return getMonsters;
    }

    public SortedSet<Monster> getLiveMonsters(){ //return the set of monsters in this room that are alive
        SortedSet<Monster> getLiveMonsters = new TreeSet<>();
        if (!this.monsters.isEmpty()) {
            for (Monster monster : this.monsters) { 
                if (!monster.isDead()) {
                    getLiveMonsters.add(monster);
                }
            }              
        } return getLiveMonsters;       
    }

    /**
     * Every time a player enters a room, he loses health points based on the monster in the room.
     * The amount lost is the sum of the values of playerHealthLostPerExposure of all the monsters in the room (@see MonsterType#playerHealthLostPerExposure)
     * @return the amount of health lost
     */
    public int getPlayerHealthLostPerEncounter(){
        int getPlayerHealthLostPerEncounter = 0;
        if (!getLiveMonsters().isEmpty()) {
            for (Monster monster : getLiveMonsters()) {
                getPlayerHealthLostPerEncounter += monster.getMonsterType().playerHealthLostPerExposure; 
            } 
        } return getPlayerHealthLostPerEncounter;
    }

    public SortedSet<Monster> getDeadMonsters(){ //return the set of monsters in this room that are dead
        SortedSet<Monster> getDeadMonsters = new TreeSet<>();
        if (this.monsters != null && !this.monsters.isEmpty()) {
            for (Monster monster : this.monsters) {
                if (monster.isDead()) {
                    getDeadMonsters.add(monster);
                }
            }            
        } return getDeadMonsters;            
    }
}