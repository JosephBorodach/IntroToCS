package edu.yu.cs.intro.doomGame;
public class Monster implements Comparable<Monster>{ 
    private final MonsterType type;
    private final MonsterType customProtectedBy;
    private Room room;
    private int ammunitionCountNeededToKill;
    protected Monster(MonsterType type){ //create a monster with no customr protectors; its protectors will be determined by its MonsterType
        if (type == null) {
            throw new IllegalArgumentException ("");                    
        }        
        this.type = type;
        this.ammunitionCountNeededToKill = this.type.ammunitionCountNeededToKill; 
        this.customProtectedBy = null;
    }
//Could this method be called after the first constructor was already called? 
    public Monster(MonsterType type, MonsterType customProtectedBy){ //create a monster with a custom protector, i.e. a different protector than the one specified in its MonsterType
        if (type == null || customProtectedBy == null) {
            throw new IllegalArgumentException ("");                    
        }
        this.type = type; 
        this.ammunitionCountNeededToKill = this.type.ammunitionCountNeededToKill; 
        this.customProtectedBy = customProtectedBy;
    }

    protected void setRoom(Room room){ 
        if (room == null) {
            throw new IllegalArgumentException ("");                    
        }
        this.room = room;
    }

    public MonsterType getMonsterType(){ 
        return this.type;
    }

    /**
     * Attack this monster with the given weapon, firing the given number of rounds at it
     * @return indicates if the monster is dead after this attack
     * @throws IllegalArgumentException if the weapon is one that dones't hurt this monster, if the weapon is null, or if rounds < 1
     * @throws IllegalStateException if the monster is already dead
     */
    protected boolean attack(Weapon weapon, int rounds){
        if (weapon == null || weapon.ordinal() < this.type.weaponNeededToKill.ordinal() || rounds < 1) {
            throw new IllegalArgumentException(""); 
        } else if (this.isDead()) { 
            throw new IllegalStateException(""); 
        } else {
            this.ammunitionCountNeededToKill -= rounds; 
            return isDead(); 
        }
    }

    public boolean isDead(){ 
        if (this.ammunitionCountNeededToKill > 0) {
            return false;
        } else {
            return true;
        }
    }

    public MonsterType getProtectedBy(){ 
        if (this.customProtectedBy != null) { //if this monster has its customProtectedBy set, return it
            return this.customProtectedBy;
        } else {
            return this.type.getProtectedBy(); //Otherwise, return the protectedBy of this monster's type
        }
    }

    /**
     * Used to sort a set of monsters into the order in which they must be killed, assuming they are in the same room.
     * If the parameter refers to this monster, return 0
     * If this monster is protected by the other monster's type, return 1
     * If this monster's type protects the other monster, return -1
     * If this monster's ordinal is < the other's, return -1
     * If this monster's ordinal is > the other's, retuen 1
     * If(this.hashCode() < other.hashCode()), then return -1
     * Otherwise, return 1
     */
    @Override
    public int compareTo(Monster other) {
        if(other == this){
            return 0;
        }else if(this.getProtectedBy() == other.getMonsterType()){
            return 1;
        }else if(other.getProtectedBy() == this.getMonsterType()){
            return -1;
        }else if(this.getMonsterType().ordinal() < other.getMonsterType().ordinal()){
            return -1;
        }else if(this.getMonsterType().ordinal() > other.getMonsterType().ordinal()){
            return 1;
        }else if(this.hashCode() < other.hashCode()){
            return -1;
        }
        return 1;
    }
}