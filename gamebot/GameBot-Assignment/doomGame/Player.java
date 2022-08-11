package edu.yu.cs.intro.doomGame;
import java.util.*;
import java.util.SortedSet;
import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
public class Player implements Comparable<Player> { 
    private String name;
    private int health;
    private Map<Weapon, Integer> weaponToAmmunition = new HashMap<>(); //Is there any reason I can't instantiate them here?
    private SortedSet<Weapon> weapons = new TreeSet<>();
    public Player(String name, int health) {
        this.name = name;
        this.health = health;
        this.weapons.add(Weapon.FIST);
        this.weaponToAmmunition.put(Weapon.FIST, Integer.MAX_VALUE);
    }

    public String getName(){
        return this.name;
    }

    public boolean hasWeapon(Weapon w){ 
        if (w == null) {
            throw new IllegalArgumentException("");         
        }
        if (this.weapons == null || !this.weapons.contains(w)) {
            return false;
        } else {
            return true;
        }
    }

    public int getAmmunitionRoundsForWeapon(Weapon w){ //how much ammunition does this player have for the given weapon?
        if (w == null) {
            throw new IllegalArgumentException("");         
        }
        if (this.weaponToAmmunition == null) {
            return 0;                   
        } else if (!this.weaponToAmmunition.containsKey(w)) {
            return 0;                 
        } else {
            if (w == Weapon.FIST) {
                this.weaponToAmmunition.put(w, Integer.MAX_VALUE);
            } 
            return this.weaponToAmmunition.get(w);
        }
    }

    public int changeAmmunitionRoundsForWeapon(Weapon weapon, int change){ //Change the ammunition amount by a positive or negative amount - @return the new total amount of ammunition the player has for the weapon.
        if (weapon == null) {
            throw new IllegalArgumentException("");         
        }
        if (this.weaponToAmmunition.containsKey(weapon)){
            if (weapon == Weapon.FIST) {
                this.weaponToAmmunition.put(weapon, Integer.MAX_VALUE);
                return Integer.MAX_VALUE;
            } else if ((this.weaponToAmmunition.get(weapon) + change) < 0) { 
                this.weaponToAmmunition.put(weapon, 0);
                return 0;
            } else {
                this.weaponToAmmunition.put(weapon, (this.weaponToAmmunition.get(weapon) + change));
                return this.weaponToAmmunition.get(weapon);                
            }
        } else {
            if (change < 0) {
                this.weaponToAmmunition.put(weapon, 0);
                return 0;
            } else {
                this.weaponToAmmunition.put(weapon, change);
                return change;                
            }
        }      
    }
    /**
     * A player can have ammunition for a weapon even without having the weapon itself.
     * @return the new total amount of ammunition the player has for the weapon
     */
    protected int addAmmunition(Weapon weapon, int rounds) {
        if(weapon == null || rounds < 0){
            throw new IllegalArgumentException("");         
        } else if (isDead()){
            throw new IllegalStateException(""); 
        } else if (this.weaponToAmmunition.containsKey(weapon)) {
            if (weapon == Weapon.FIST) {
                this.weaponToAmmunition.put(weapon, Integer.MAX_VALUE);
                return Integer.MAX_VALUE;
            } else {
                this.weaponToAmmunition.put(weapon, this.weaponToAmmunition.get(weapon) + rounds);
                return this.weaponToAmmunition.get(weapon); //return the new total amount of ammunition the player has for the weapon            
            }
        } else {
            this.weaponToAmmunition.put(weapon, rounds);
            return rounds;            
        }
    }

    //When a weapon is first added to a player, the player should automatically be given 5 rounds of ammunition.
    //If the player already has the weapon before this method is called, this method has no effect at all.
    //@return true if the weapon was added, false if the player already had it
    protected boolean addWeapon(Weapon weapon){
        if (weapon == null) {
            throw new IllegalArgumentException("");         
        } else if (isDead()){
            throw new IllegalStateException("");
        } else if (this.weapons.contains(weapon)){
            if (weapon.equals(Weapon.FIST)) {
                this.weaponToAmmunition.put(weapon, Integer.MAX_VALUE);
            } return false;
        } else {
            this.weapons.add(weapon);
            if (this.weaponToAmmunition.containsKey(weapon)) {
                this.weaponToAmmunition.put(weapon, this.weaponToAmmunition.get(weapon) + 5);
                return true;                             
            } else {
                this.weaponToAmmunition.put(weapon, 5);
                return true;                            
            }                    
        }
    }

    public int changeHealth(int amount){ //amount a positive or negative number, to increase or decrease the player's health
        if (this.isDead()) {
            throw new IllegalStateException("");            
        } 
        this.health += amount; 
        return this.health; //return the player's health level after the change
    }

    protected void setHealth(int amount){ 
        this.health = amount;
    }

    public int getHealth(){ 
        return this.health;
    }

    public boolean isDead(){ 
        if(this.health <= 0){
            return true;
        }else{
            return false;
        }
    }

    protected SortedSet<Weapon> getAllWeapons(){
        SortedSet<Weapon> allWeapons = new TreeSet<>();
        allWeapons.addAll(this.weapons);
        return allWeapons;
    }

    protected boolean hasStrongerWeapon(Weapon weapon){
        for (Weapon eachWeapon : getAllWeapons()) {
            if (eachWeapon.ordinal() > weapon.ordinal()) {
                return true;
            }
        } return false;
    }

    /**
     * Compare criteria, in order:
     * Does one have a greater weapon?
     * If they have the same greatest weapon, who has more ammunition for it?
     * If they are the same on weapon and ammunition, who has more health?
     * If they are the same on greatest weapon, ammunition for it, and health, they are equal.
     * Recall that all enums have a built-in implementation of Comparable, and they compare based on ordinal()
     * If they are the same on greatest weapon, ammunition for it, and health, they are equal.
     */
    @Override
    public int compareTo(Player other) {
        if (other == null) {
            throw new IllegalArgumentException("");  
        }
        if (this.weapons.last().ordinal() != other.weapons.last().ordinal()) {
            if (this.weapons.last().ordinal() > other.getAllWeapons().last().ordinal()) {
                return 1;
            } else {
                return -1;
            }
        } else if (this.getAmmunitionRoundsForWeapon(this.weapons.last()) != other.getAmmunitionRoundsForWeapon(other.getAllWeapons().last())) {
            if (this.getAmmunitionRoundsForWeapon(this.weapons.last()) > other.getAmmunitionRoundsForWeapon(other.getAllWeapons().last())) {
                return 1;
            } else {
                return -1;
            }
        } else if (this.getHealth() != other.getHealth()) {
            if (this.getHealth() > other.getHealth()) {
                return 1; 
            } else {
                return -1;
            }
        } else { 
            return 0; 
        }
    }

    @Override
    public boolean equals(Object o) { //Only equal if it is literally the same player 
        if (this == o) { 
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() { //return the hash code of the player's name
        return this.name.hashCode();
    }
}