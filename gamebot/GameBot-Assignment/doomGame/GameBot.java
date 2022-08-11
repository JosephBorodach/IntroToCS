package edu.yu.cs.intro.doomGame;

import java.util.*;
import java.util.SortedSet;
import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;

/**
 * Double check that the 2nd canKill properlly subtracts health
 */
public class GameBot { //Plays through a given game scenario. i.e. tries to kill all the monsters in all the rooms and thus complete the game, using the given set of players
    private SortedSet<Room> rooms; 
    private SortedSet<Player> players; 
    public GameBot(SortedSet<Room> rooms, SortedSet<Player> players) { 
        if (rooms == null || players == null) {
            throw new IllegalArgumentException ("");        
        } 
        this.rooms = new TreeSet<>();
        this.players = new TreeSet<>();
        for (Room room : rooms) {
            if (room != null) {
                this.rooms.add(room);
            }
        }
        for (Player player : players) {
            if (player != null) {
                this.players.add(player);
            }
        }
    }

    /**
     * Step #1: Try to complete killing all monsters in all rooms using the given set of players.
            - It could take multiple passes through the set of rooms to complete the task of killing every monster in every room.
            - This method should call #passThroughRooms in some loop that tracks whether all the rooms have been completed OR we
              have reached a point at which no progress can be made. 
                    - If we are "stuck", i.e. we haven't completed all rooms but calls to #passThroughRooms are no longer 
                      increasing the number of rooms that have been completed, return false to indicate that we can't complete the game. 
                      As long as the number of completed rooms continues to rise, keep calling #passThroughRooms.
                    - Throughout our attempt/logic to play the game, we rely on and take advantage of the fact that Room, Monster,
                      and Player all implement Comparable, and the sets we work with are all SortedSets
        Step #2: return true if all rooms were completed, false if not
     */
    public boolean play() { 
        while (getCompletedRooms().size() != getAllRooms().size()) { //!getCompletedRooms().containsAll(getAllRooms())
            if (passThroughRooms().isEmpty()) { 
                return false;
            }
        } return true; 
    }

    /**
     * Pass through the rooms, killing any monsters that can be killed, and thus attempt to complete the rooms
     * @return the set of rooms that were completed in this pass
        //for every room that is not completed,
            //for every living monster in that room
                //See if any of your players can kill the monster. If so, have the capable player kill it.
                //The player that causes the room to be completed by killing a monster reaps the rewards for completing that room.
        //Return the set of completed rooms.
     * Print Statements:
     *      System.out.println("\n\nPASSING TRHOUGH the following Room: " + room.getName());
     *      System.out.println("PASS TRHOUGH --> Room: " + room.getName() + ". Player " + player.getName() + " has killed a " + monster.getMonsterType() + ". " + "Health is now " + player.getHealth() + ". The weapon needed to kill was: " + monster.getMonsterType().weaponNeededToKill + "; with the following ammount of ammo: " + monster.getMonsterType().ammunitionCountNeededToKill + ". The player now has: " + player.getAmmunitionRoundsForWeapon(monster.getMonsterType().weaponNeededToKill)); 
     *      System.out.println("Room: " + room.getName() + " has been completed. " + "The rewards are: weapon won: " + room.getWeaponsWonUponCompletion() + ", ammo won: " + room.getAmmoWonUponCompletion() + ", health won: " + room.getHealthWonUponCompletion() + "."); 
     *      System.out.println("Previous Player stats " + player.getName() + ". Health is: " + player.getHealth() + "; Chainsaw ammo is: " + player.getAmmunitionRoundsForWeapon(Weapon.CHAINSAW) + "; for Pistol: " + player.getAmmunitionRoundsForWeapon(Weapon.PISTOL) + "; for Shotgun: " + player.getAmmunitionRoundsForWeapon(Weapon.SHOTGUN)); 
     *      System.out.println("Ammo for FIST is: " + player.getAmmunitionRoundsForWeapon(Weapon.FIST));
     *      System.out.println("Player " + player.getName() + " has completed " + "Room: " + room.getName() + ". Health is now " + player.getHealth() + ". The player now has: for Chainsaw: " + player.getAmmunitionRoundsForWeapon(Weapon.CHAINSAW) + "; for Pistol: " + player.getAmmunitionRoundsForWeapon(Weapon.PISTOL) + "; for Shotgun: " + player.getAmmunitionRoundsForWeapon(Weapon.SHOTGUN)); 
     */
    protected Set<Room> passThroughRooms() {
        Set<Room> roomsCompleted = new HashSet<>();  
        for (Room room : getAllRooms()) {
            if (!room.isCompleted()) {
                for (Monster monster : room.getLiveMonsters()) { 
                    for (Player player : getLivePlayers()) {
                        if (!room.isCompleted() && !monster.isDead()) {
                            if (canKill(player, monster, room)) {
                                killMonster(player, room, monster);
                            }
                            if (room.isCompleted()) { 
                                reapCompletionRewards(player, room);
                                roomsCompleted.add(room);                         
                            }
                        }
                    }
                }
            }
        } return roomsCompleted; //Return the set of completed rooms
    }

    protected void reapCompletionRewards(Player player, Room room) { //give the player the weapons, ammunition, and health that come from completing the given room
        if (player == null || room == null) {
            throw new IllegalArgumentException ("");                    
        } 
        if (room.getWeaponsWonUponCompletion() != null) {
            for (Weapon weapon : room.getWeaponsWonUponCompletion()) {
                if (!player.hasWeapon(weapon)) {
                    player.addWeapon((weapon));
                }
            }
        }
        if (room.getAmmoWonUponCompletion() != null) {
            for (Map.Entry<Weapon,Integer> entry : room.getAmmoWonUponCompletion().entrySet()) {
                if (entry.getKey() != null) {
                    player.addAmmunition(entry.getKey(), entry.getValue());
                }
            }
        }
        player.changeHealth(room.getHealthWonUponCompletion());
    }

    /**
     * Have the given player kill the given monster in the given room.
     * Step 1: Call getAllProtectorsInRoom to get a sorted set of all the monster's protectors in this room
     *      Player must kill the protectors before it can kill the monster, so kill all the protectors - first via a recursive call to killMonster on each one.
     * Step 2: 
     *      Reduce the player's health by the amount given by room.getPlayerHealthLostPerEncounter().
     *      Attack (and thus kill) the monster with the kind of weapon, and amount of ammunition, needed to kill it.
     * Print statements:
     *      System.out.println("Room: " + room.getName() + ". Player " + player.getName() + " has killed a " + monsterToKill.getMonsterType() + ". " + "Health is now " + player.getHealth()); 
     *      System.out.println("Room: " + room.getName() + ". Player " + player.getName() + " has killed a " + monsterToKill.getMonsterType() + ". " + "Health is now " + player.getHealth() + ". The weapon needed to kill was: " + monsterToKill.getMonsterType().weaponNeededToKill + "; with the following ammount of ammo: " + monsterToKill.getMonsterType().ammunitionCountNeededToKill + ". The player now has: " + player.getAmmunitionRoundsForWeapon(monsterToKill.getMonsterType().weaponNeededToKill) + ". The player now has: for Chainsaw: " + player.getAmmunitionRoundsForWeapon(Weapon.CHAINSAW) + "; for Pistol: " + player.getAmmunitionRoundsForWeapon(Weapon.PISTOL) + "; for Shotgun: " + player.getAmmunitionRoundsForWeapon(Weapon.SHOTGUN)); 
     */
    protected void killMonster(Player player, Room room, Monster monsterToKill) { 
        if (player == null || room == null || monsterToKill == null) {
            throw new IllegalArgumentException ("");                    
        } 
        for (Monster eachProtector : getAllProtectorsInRoom(monsterToKill, room)) { 
            killMonster(player, room, eachProtector);
        }
        player.changeHealth(-room.getPlayerHealthLostPerEncounter());
        room.monsterKilled(monsterToKill);
        if (player.hasWeapon(monsterToKill.getMonsterType().weaponNeededToKill) && player.getAmmunitionRoundsForWeapon(monsterToKill.getMonsterType().weaponNeededToKill) >= monsterToKill.getMonsterType().ammunitionCountNeededToKill) {
            player.changeAmmunitionRoundsForWeapon(monsterToKill.getMonsterType().weaponNeededToKill, -monsterToKill.getMonsterType().ammunitionCountNeededToKill);
            monsterToKill.attack(monsterToKill.getMonsterType().weaponNeededToKill, monsterToKill.getMonsterType().ammunitionCountNeededToKill); 
        } else {
            boolean didPlayerKillYet = false;
            for (Weapon weapon : player.getAllWeapons()) {
                if (didPlayerKillYet == false) {
                    if (weapon.ordinal() > monsterToKill.getMonsterType().weaponNeededToKill.ordinal() && player.getAmmunitionRoundsForWeapon(weapon) >= monsterToKill.getMonsterType().ammunitionCountNeededToKill) {
                        monsterToKill.attack(weapon, monsterToKill.getMonsterType().ammunitionCountNeededToKill); 
                        player.changeAmmunitionRoundsForWeapon(weapon, -monsterToKill.getMonsterType().ammunitionCountNeededToKill);
                        didPlayerKillYet = true;
                    }
                }
            }
        }
    }
    
    public Set<Room> getCompletedRooms() { //return a set of all the rooms that have been completed
        Set<Room> getCompletedRooms = new HashSet<>();
        for (Room room : this.rooms) {
            if (room.isCompleted()) {
                getCompletedRooms.add(room);
            } 
        } return getCompletedRooms;
    }

    public SortedSet<Room> getAllRooms() { //return an unmodifiable collection of all the rooms in the game
        SortedSet<Room> getAllRooms = new TreeSet<>();
        if (!this.rooms.isEmpty()) { 
            getAllRooms = Collections.unmodifiableSortedSet(this.rooms);
        } return getAllRooms; 
    }

    protected SortedSet<Player> getLivePlayers() { //return a sorted set of all the live players in the game
        SortedSet<Player> getLivePlayers = new TreeSet<>();
        for (Player player : this.players) {
            if (!player.isDead()) {
                getLivePlayers.add(player);
            }
        } return getLivePlayers;     
    }

    protected SortedSet<Player> getLivePlayersWithWeaponAndAmmunition(Weapon weapon, int ammunition) { //return a sorted set of all the players that have the given weapon with the given amount of ammunition for it
        if (weapon == null) { 
            throw new IllegalArgumentException ("");                    
        }
        SortedSet<Player> getLivePlayersWithWeaponAndAmmunition = new TreeSet<>();
        for (Player player : this.players) {
            if (!player.isDead() && player.hasWeapon(weapon) && (player.getAmmunitionRoundsForWeapon(weapon) >= ammunition)) {
                getLivePlayersWithWeaponAndAmmunition.add(player);
            }
        } return getLivePlayersWithWeaponAndAmmunition;         
    }

    /** 
     * Get the set of all monsters that would need to be killed first before you could kill this one.
     * Remember that a protector may itself be protected by other monsters, so you will have to recursively check for protectors 
     *      1st: Find if the monster even has 1 live protector monster (of the protector type)
     *      2nd: After at least one protector monster has been found, gather the other monsters of the same type
     */
    protected static SortedSet<Monster> getAllProtectorsInRoom(Monster monster, Room room) {
        if (monster == null || room == null) { 
            throw new IllegalArgumentException ("");                    
        }
        return getAllProtectorsInRoom(new TreeSet<Monster>(), monster, room); //this is a hint about how to handle canKill as well
    }

    private static SortedSet<Monster> getAllProtectorsInRoom(SortedSet<Monster> protectors, Monster monster, Room room) {
        if (protectors == null || monster == null || room == null) { 
            throw new IllegalArgumentException ("");                    
        }
        if (monster.getProtectedBy() != null) {
            for (Monster protectorMonster : room.getLiveMonsters()) {
                if (protectorMonster.getMonsterType().equals(monster.getProtectedBy())) { 
                    protectors.add(protectorMonster);
                    for (Monster protectorType : room.getLiveMonsters()) {
                        if (protectorType.getMonsterType().equals(protectorMonster.getMonsterType())) { 
                            protectors.add(protectorMonster);                 
                        }
                    } 
                    getAllProtectorsInRoom(protectors, protectorMonster, room);  
                }
            } 
        } return protectors;
    }

    /**
     * Can the given player kill the given monster in the given room?
     *      @throws IllegalArgumentException if the monster is not located in the room or is dead
     *      #1: Going into the room exposes the player to all the monsters in the room. If the player's health is
     *          not > room.getPlayerHealthLostPerEncounter(), you can return immediately.
     *      #2: Call the private canKill method, to determine if this player can kill this monster.
     *      #3: Before returning from this method, reset the player's health to what it was before you called the private canKill
     * Print statements:
     *      System.out.println("\n" + player.getName() + " is attempting to kill: " + monster.getMonsterType() + "; current helath is: " + player.getHealth() + "; the current health lost per encounter is: " + room.getPlayerHealthLostPerEncounter());         
     *      System.out.println("CAN KILL: true. "  + "Room: " + room.getName() + ". Player " + player.getName() + " has killed a " + monster.getMonsterType() + ". " + "Health is now " + player.getHealth() + ". The weapon needed to kill was: " + monster.getMonsterType().weaponNeededToKill + "; with the following ammount of ammo: " + monster.getMonsterType().ammunitionCountNeededToKill + ". The player now has: " + player.getAmmunitionRoundsForWeapon(monster.getMonsterType().weaponNeededToKill)); 
     *      System.out.println("CAN KILL: false. "  + "Room: " + room.getName() + ". Player " + player.getName() + " has killed a " + monster.getMonsterType() + ". " + "Health is now " + player.getHealth() + ". The weapon needed to kill was: " + monster.getMonsterType().weaponNeededToKill + "; with the following ammount of ammo: " + monster.getMonsterType().ammunitionCountNeededToKill + ". The player now has: " + player.getAmmunitionRoundsForWeapon(monster.getMonsterType().weaponNeededToKill)); 
     */
    protected static boolean canKill(Player player, Monster monster, Room room) {
        if (player == null || monster == null || room == null || !monsterStillAlive(monster, room)) { // 
            throw new IllegalArgumentException ("");       
        }
        int resetHealth = player.getHealth(); 
        if (resetHealth <= room.getPlayerHealthLostPerEncounter()) { 
            return false;
        } 
        if (canKill(player, monster, room, new TreeMap<Weapon, Integer>(), new HashSet<Monster>())) { 
            player.setHealth(resetHealth);
            return true;
        } else {
            player.setHealth(resetHealth);                
            return false;
        }
    }

//Should canKill ever be returning false bc the monster is not alived? 
    /**
    * Step #1: Remove all the monsters already marked / looked at by this series of recursive calls to canKill from the set of liveMonsters in the room before you check if the monster is alive and in the room. Be sure to NOT alter the actual set of live monsters in your Room object!
    * Step #2: Check if monster is in the room and alive.
    * Step #3: Check what weapon is needed to kill the monster, see if player has it. If not, return false.
    * Step #4: Check what protects the monster. If the monster is protected, the player can only kill this monster if it can kill its protectors as well.
    *       Be sure to remove all members of alreadyMarkedByCanKill from the set of protectors before you recursively call canKill on the protectors.
    *       Make recursive calls to canKill to see if player can kill its protectors.
    * Step #5: If all the recursive calls to canKill on all the protectors returned true: 
    *   Check what amount of ammunition is needed to kill the monster, and see if player has it after we subtract from his total ammunition the number stored in roundsUsedPerWeapon for the given weapon, IF ANY.
    *   add how much ammunition will be used up to kill this monster to roundsUsedPerWeapon
    *   Add up the playerHealthLostPerExposure of all the live monsters, and see if when that is subtracted from the player if his health is still > 0. If not, return false.
    *   If health is still > 0, subtract the above total from the player's health
    *   add this monster to alreadyMarkedByCanKill, and return true.
    * Step #6: If the player does not have the weapon needed to kill or does not have enough ammunition for it - See if this player has a stronger weapon with sufficient amount of ammunition
    *   add how much ammunition will be used up to kill this monster to roundsUsedPerWeapon
    *   Add up the playerHealthLostPerExposure of all the live monsters, and see if when that is subtracted from the player if his health is still > 0. If not, return false.
    *   If health is still > 0, subtract the above total from the player's health
    *   add this monster to alreadyMarkedByCanKill, and return true.
    */
    private static boolean canKill(Player player, Monster monster, Room room, SortedMap<Weapon, Integer> roundsUsedPerWeapon, Set<Monster> alreadyMarkedByCanKill) {
        if (player == null || monster == null || room == null || roundsUsedPerWeapon == null || alreadyMarkedByCanKill == null) { //!room.getMonsters().contains(monster) || monster.isDead() 
            throw new IllegalArgumentException ("");       
        }
        if (!monsterStillAlive(monster, room) || isMonsterAlreadyMarkedByCanKill(monster, alreadyMarkedByCanKill)) {
            return false;
        } 
        if (!player.hasWeapon(monster.getMonsterType().weaponNeededToKill) && !player.hasStrongerWeapon(monster.getMonsterType().weaponNeededToKill)) { 
            return false; 
        } 
        for (Monster protectorMonster : getAllProtectorsInRoom(monster, room)) {
            if (monsterStillAlive(protectorMonster, room) && !isMonsterAlreadyMarkedByCanKill(protectorMonster, alreadyMarkedByCanKill)) {
                if (!canKill(player, protectorMonster, room, roundsUsedPerWeapon, alreadyMarkedByCanKill)) { 
                    return false;
                }
            }
        }
        int roundsCheck = player.getAmmunitionRoundsForWeapon(monster.getMonsterType().weaponNeededToKill);
        if (roundsUsedPerWeapon.containsKey(monster.getMonsterType().weaponNeededToKill)) { 
           roundsCheck -= roundsUsedPerWeapon.get(monster.getMonsterType().weaponNeededToKill);
        }
        if (player.hasWeapon(monster.getMonsterType().weaponNeededToKill) && roundsCheck >= monster.getMonsterType().ammunitionCountNeededToKill) { 
            int roundsUpdate = monster.getMonsterType().ammunitionCountNeededToKill;
            if (roundsUsedPerWeapon.containsKey(monster.getMonsterType().weaponNeededToKill)) { 
                roundsUpdate += roundsUsedPerWeapon.get(monster.getMonsterType().weaponNeededToKill); 
            }
            roundsUsedPerWeapon.put(monster.getMonsterType().weaponNeededToKill, roundsUpdate); 
            int healthLoss = 0;
            healthLoss = room.getPlayerHealthLostPerEncounter() - alreadyMarkedHealthReduction(alreadyMarkedByCanKill);
            if ((player.getHealth() - healthLoss) > 0) { 
                player.setHealth(player.getHealth() - healthLoss); 
                alreadyMarkedByCanKill.add(monster); 
                return true;
            } else {     
                return false;
            }
        } else if (player.hasStrongerWeapon(monster.getMonsterType().weaponNeededToKill)) { 
            for (Weapon weapon : player.getAllWeapons()) {
                if (weapon.ordinal() > monster.getMonsterType().weaponNeededToKill.ordinal()) { 
                    int roundsChecker = player.getAmmunitionRoundsForWeapon(weapon);
                    if (roundsUsedPerWeapon.containsKey(weapon)) {
                        roundsChecker -= roundsUsedPerWeapon.get(weapon);
                    }
                    if (roundsChecker >= monster.getMonsterType().ammunitionCountNeededToKill) {
                        int roundsUpdate = monster.getMonsterType().ammunitionCountNeededToKill; 
                        if (roundsUsedPerWeapon.containsKey(weapon)) {
                            roundsUpdate += roundsUsedPerWeapon.get(weapon); 
                        }
                        roundsUsedPerWeapon.put(weapon, roundsUpdate);  
                        int healthLoss = 0;
                        healthLoss = room.getPlayerHealthLostPerEncounter() - alreadyMarkedHealthReduction(alreadyMarkedByCanKill);
                        if ((player.getHealth() - healthLoss) > 0) { 
                            player.setHealth(player.getHealth() - healthLoss); 
                            alreadyMarkedByCanKill.add(monster); 
                            return true;
                        } else {                        
                            return false;
                        }
                    }
                }
            }            
        } return false; 
    }

    private static boolean monsterStillAlive (Monster monster, Room room) {
        for (Monster liveMonster : room.getLiveMonsters()) {
            if (liveMonster.equals(monster)) { 
                return true;
            }
        }
        return false;
    }

    private static boolean isMonsterAlreadyMarkedByCanKill (Monster monster, Set<Monster> alreadyMarkedByCanKill) {
        if (!alreadyMarkedByCanKill.isEmpty()) {
            for (Monster alreadyMarked : alreadyMarkedByCanKill) {
                if (alreadyMarked.equals(monster)) { 
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    private static int alreadyMarkedHealthReduction (Set<Monster> alreadyMarkedByCanKill) {
        int healthLossReduction = 0;
        if (!alreadyMarkedByCanKill.isEmpty()) {
            for (Monster alreadyMarked : alreadyMarkedByCanKill) {
                healthLossReduction += alreadyMarked.getMonsterType().playerHealthLostPerExposure;
            }
        }
        return healthLossReduction;
    }

}


























