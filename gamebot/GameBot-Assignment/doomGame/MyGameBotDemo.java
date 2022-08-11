package edu.yu.cs.intro.doomGame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeSet;

public class MyGameBotDemo {
    public static void main(String[] args) {
        MyGameBotDemo demo = new MyGameBotDemo();
        demo.play();
        demo.playAnother();
        demo.singleWeaponKill();
        demo.tenBarons();
    }

    void play(){
//Player class tests
        Player player = new Player("Player",100);
        assert player.getName() == "Player";
        assert player.getHealth() == 100;

    //hasWeapon tests
        assert player.hasWeapon(Weapon.FIST) == true;
        assert player.hasWeapon(Weapon.CHAINSAW) == false;        
        assert player.hasWeapon(Weapon.PISTOL) == false;
        assert player.hasWeapon(Weapon.SHOTGUN) == false;

        assert player.getAllWeapons().size() == 1;

    //getAmmunitionRoundsForWeapon tests
        assert player.getAmmunitionRoundsForWeapon(Weapon.CHAINSAW) == 0;
        assert player.getAmmunitionRoundsForWeapon(Weapon.PISTOL) == 0;
        assert player.getAmmunitionRoundsForWeapon(Weapon.SHOTGUN) == 0;

        assert player.addWeapon(Weapon.CHAINSAW) == true;
        assert player.hasWeapon(Weapon.CHAINSAW) == true;
        assert player.getAmmunitionRoundsForWeapon(Weapon.CHAINSAW) == 5;
        assert player.addAmmunition(Weapon.CHAINSAW, 20) == 25;        

    //changeAmmunitionRoundsForWeapon tests
        assert player.getAmmunitionRoundsForWeapon(Weapon.CHAINSAW) == 25;
        assert player.changeAmmunitionRoundsForWeapon(Weapon.CHAINSAW, -20) == 5; 
        assert player.changeAmmunitionRoundsForWeapon(Weapon.CHAINSAW, -20) == 0; 

    //addWeapon tests
        assert player.addWeapon(Weapon.FIST) == false;        
        assert player.addWeapon(Weapon.CHAINSAW) == false;
        assert player.addWeapon(Weapon.PISTOL) == true;
        assert player.addWeapon(Weapon.SHOTGUN) == true;

        assert player.addWeapon(Weapon.PISTOL) == false;
        assert player.addWeapon(Weapon.SHOTGUN) == false;

        //He should know have all weapons 
        assert player.hasWeapon(Weapon.FIST) == true;
        assert player.hasWeapon(Weapon.CHAINSAW) == true;        
        assert player.hasWeapon(Weapon.PISTOL) == true;
        assert player.hasWeapon(Weapon.SHOTGUN) == true;

        assert player.getAmmunitionRoundsForWeapon(Weapon.CHAINSAW) == 0;
        assert player.getAmmunitionRoundsForWeapon(Weapon.PISTOL) == 5;
        assert player.getAmmunitionRoundsForWeapon(Weapon.SHOTGUN) == 5;

//New Player
        Player player1 = new Player("Player 1",1000);
        assert player1.getName() == "Player 1";
        assert player1.getHealth() == 1000;

        assert player1.compareTo(player) == -1; //Should be -3 because player has a shotgun, while player1 only has a FIST

        assert player1.getAmmunitionRoundsForWeapon(Weapon.CHAINSAW) == 0;
        assert player1.getAmmunitionRoundsForWeapon(Weapon.PISTOL) == 0;
        assert player1.getAmmunitionRoundsForWeapon(Weapon.SHOTGUN) == 0;

    //add ammunition tests
        assert player1.addAmmunition(Weapon.CHAINSAW, 20) == 20; 
        assert player1.addAmmunition(Weapon.PISTOL, 100) == 100;         
        assert player1.addAmmunition(Weapon.SHOTGUN, 10) == 10;         

        assert player1.getAmmunitionRoundsForWeapon(Weapon.CHAINSAW) == 20;         
        assert player1.getAmmunitionRoundsForWeapon(Weapon.PISTOL) == 100;         
        assert player1.getAmmunitionRoundsForWeapon(Weapon.SHOTGUN) == 10;         

    //add weapon after the player already has ammunition - should all increase by 5
        assert player1.addWeapon(Weapon.CHAINSAW) == true;
        assert player1.addWeapon(Weapon.PISTOL) == true;
        assert player1.addWeapon(Weapon.SHOTGUN) == true;

        assert player1.getAmmunitionRoundsForWeapon(Weapon.CHAINSAW) == 25;         
        assert player1.getAmmunitionRoundsForWeapon(Weapon.PISTOL) == 105;         
        assert player1.getAmmunitionRoundsForWeapon(Weapon.SHOTGUN) == 15;         

    //Some health tests
        assert player1.changeHealth(-500) == 500;
        assert player1.getHealth() == 500;
        player1.setHealth(1000);
        assert player1.getHealth() == 1000;
        assert player1.isDead() == false;
        assert player1.getAllWeapons().size() == 4;


        assert player.getAmmunitionRoundsForWeapon(Weapon.SHOTGUN) == 5;
        assert player1.getAmmunitionRoundsForWeapon(Weapon.SHOTGUN) == 15;         
        assert player1.compareTo(player) == 1;

        assert player.addAmmunition(Weapon.SHOTGUN, 10) == 15;
        assert player.getAmmunitionRoundsForWeapon(Weapon.SHOTGUN) == 15;
        assert player1.getAmmunitionRoundsForWeapon(Weapon.SHOTGUN) == 15;         


        assert player1.getHealth() == 1000;
        assert player1.compareTo(player) == 1;

        player1.setHealth(100);
        assert player1.getHealth() == 100;
        assert player1.compareTo(player) == 0; //bc everything is equal

        assert player1.equals(player) == false; //bc everything is equal
        assert player1.equals(player1) == true; //bc everything is equal

        assert player.hashCode() != player1.hashCode(); 



    //Throwing error tests
        boolean caught = false; 
        try { 
            player.addAmmunition(Weapon.CHAINSAW, -20);                     
        } catch (IllegalArgumentException e) { 
            caught = true; 
        } 
        assert caught == true; 



//Monster class
    //Tests on the getProtectedBy and the 1st and 2nd monster constructors
        Monster normalDemon = new Monster(MonsterType.DEMON);
        assert normalDemon.getProtectedBy() == MonsterType.BARON_OF_HELL;

        Monster customProtectedDemon = new Monster(MonsterType.DEMON,MonsterType.IMP);
        assert customProtectedDemon.getProtectedBy() == MonsterType.IMP;
        assert customProtectedDemon.getProtectedBy() != MonsterType.BARON_OF_HELL;

    //Tests for getMonsterType()
        assert normalDemon.getMonsterType() == MonsterType.DEMON;
        assert customProtectedDemon.getMonsterType() == MonsterType.DEMON;

    //Tests for isDead
        assert normalDemon.isDead() == false;
        assert customProtectedDemon.isDead() == false;

    //tests for attack
        caught = false; 
        try { 
            normalDemon.attack(Weapon.FIST, 100); //Should cause an IAE because the weapon ordinal is bellow the required ammount                     
        } catch (IllegalArgumentException e) { 
            caught = true; 
        } 
        assert caught == true; 

        caught = false; 
        try { 
            normalDemon.attack(Weapon.SHOTGUN, -15); //Should cause an IAE because rounds are bellow 0
        } catch (IllegalArgumentException e) { 
            caught = true; 
        } 
        assert caught == true; 


    //Kill the monster
        assert normalDemon.attack(Weapon.SHOTGUN, 1) == true;

        //Should throw an ISE bc the monster is already dead
        caught = false; 
        try { 
            normalDemon.attack(Weapon.SHOTGUN, 100); 
        } catch (IllegalStateException e) { 
            caught = true; 
        } 
        assert caught == true; 

        assert normalDemon.isDead() == true;


    //New monster
        Monster normalBaron = new Monster(MonsterType.BARON_OF_HELL);

        assert normalBaron.attack(Weapon.SHOTGUN, 6) == false; 
        assert normalBaron.attack(Weapon.SHOTGUN, 6) == true; 

    //Tests for compareTo
        //If the parameter refers to this monster, return 0
        assert normalDemon.compareTo(normalDemon) == 0;        

        //If this monster is protected by the other monster's type, return 1
        assert normalDemon.compareTo(normalBaron) == 1;        
    
        //If this monster's type protects the other monster, return -1
        Monster protecterDemon = new Monster(MonsterType.DEMON);
        Monster protectedIMP = new Monster(MonsterType.IMP, MonsterType.DEMON);
        assert protecterDemon.compareTo(protectedIMP) == -1;            

        //If this monster's ordinal is < the other's, return -1
        Monster normalSpectre = new Monster(MonsterType.SPECTRE);
        assert normalDemon.compareTo(normalSpectre) == -1;            

        //If this monster's ordinal is > the other's, retuen 1
        Monster normalIMP = new Monster(MonsterType.IMP);
        assert normalDemon.compareTo(normalIMP) == 1;            

        //If(this.hashCode() < other.hashCode()), then return -1
            //IDK how to rest this

        //Otherwise, return 1




//Test for the Room class
        Monster roomIMP = new Monster(MonsterType.IMP);
        Monster roomDemon = new Monster(MonsterType.DEMON);
        Monster roomSpectre = new Monster(MonsterType.SPECTRE);
        Monster roomBaron = new Monster(MonsterType.BARON_OF_HELL);
        TreeSet<Monster> groupOfMonsters = new TreeSet<>();
        groupOfMonsters.add(roomIMP);
        groupOfMonsters.add(roomDemon);
        groupOfMonsters.add(roomSpectre);
        groupOfMonsters.add(roomBaron);

        HashSet<Weapon> rewardWeapons = new HashSet<>();
        rewardWeapons.add(Weapon.CHAINSAW);
        rewardWeapons.add(Weapon.PISTOL);
        Map<Weapon,Integer> rewardAmmunition = new HashMap<>();
        rewardAmmunition.put(Weapon.CHAINSAW,20);
        rewardAmmunition.put(Weapon.PISTOL,30);
        rewardAmmunition.put(Weapon.SHOTGUN,40);

        Monster roomIMP2 = new Monster(MonsterType.IMP);
        Monster roomDemon2 = new Monster(MonsterType.DEMON);
        Monster roomSpectre2 = new Monster(MonsterType.SPECTRE);
        Monster roomBaron2 = new Monster(MonsterType.BARON_OF_HELL);
        TreeSet<Monster> groupOfMonsters2 = new TreeSet<>();
        groupOfMonsters2.add(roomIMP2);
        groupOfMonsters2.add(roomDemon2);
        groupOfMonsters2.add(roomSpectre2);
        groupOfMonsters2.add(roomBaron2);

        //Should throw an IAE bc the ammoWonUponCompletion map is empty
        Map<Weapon,Integer> nullMap = new HashMap<>();
        nullMap = null;
        caught = false; 
        try { 
            Room testRoom = new Room(groupOfMonsters, rewardWeapons, nullMap, 100, "The Big Test Room");
        } catch (IllegalArgumentException e) { 
            caught = true; 
        } 
        assert caught == true; 

        Room testRoom = new Room(groupOfMonsters, rewardWeapons, rewardAmmunition, 100, "The Big Test Room");
        Room secondTestRoom = new Room(groupOfMonsters2, rewardWeapons, rewardAmmunition, 100, "The SECOND Big Test Room");

    //Tests for the getDangerLevel method
        assert testRoom.getDangerLevel() == 10;
        assert secondTestRoom.getDangerLevel() == 10;

    //Tests for getName
        assert testRoom.getName() == "The Big Test Room";
        assert secondTestRoom.getName() == "The SECOND Big Test Room";

    //Tests for getWeaponsWonUponCompletion
        //Try to modify the set returned, causing an UnsupportedOperationException
        caught = false; 
        try { 
            testRoom.getWeaponsWonUponCompletion().remove(Weapon.PISTOL);
        } catch (UnsupportedOperationException e) { 
            caught = true; 
        } 
        assert caught == true; 

        //Check that the set contains all of the weapons
        assert testRoom.getWeaponsWonUponCompletion().containsAll(rewardWeapons) == true;

        HashSet<Weapon> fakeRewardWeapons = new HashSet<>();
        fakeRewardWeapons.add(Weapon.CHAINSAW);
        fakeRewardWeapons.add(Weapon.PISTOL);
        fakeRewardWeapons.add(Weapon.SHOTGUN);
        assert testRoom.getWeaponsWonUponCompletion().containsAll(fakeRewardWeapons) == false;

    //Tests for getAmmoWonUponCompletion
        //Try to modify the set returned, causing an UnsupportedOperationException
        caught = false; 
        try { 
            testRoom.getAmmoWonUponCompletion().put(Weapon.PISTOL, 500);
        } catch (UnsupportedOperationException e) { 
            caught = true; 
        } 
        assert caught == true; 

        //Check that the set contains all of the weapons
        assert testRoom.getAmmoWonUponCompletion().containsKey(Weapon.CHAINSAW) == true;
        assert testRoom.getAmmoWonUponCompletion().containsKey(Weapon.PISTOL) == true;
        assert testRoom.getAmmoWonUponCompletion().containsKey(Weapon.SHOTGUN) == true;
        assert testRoom.getAmmoWonUponCompletion().get(Weapon.CHAINSAW) == 20;
        assert testRoom.getAmmoWonUponCompletion().get(Weapon.PISTOL) == 30;
        assert testRoom.getAmmoWonUponCompletion().get(Weapon.SHOTGUN) == 40;

        assert testRoom.getHealthWonUponCompletion() == 100;
        assert testRoom.getHealthWonUponCompletion() == secondTestRoom.getHealthWonUponCompletion();

        assert testRoom.isCompleted() == false;
        assert secondTestRoom.isCompleted() == false;

    //Tests for getMonsters, getDeadMonsters, getLiveMonsters
        assert testRoom.getMonsters().containsAll(groupOfMonsters) == true;
        assert testRoom.getMonsters().size() == 4;
        
        assert testRoom.getDeadMonsters().containsAll(groupOfMonsters) == false;        
        assert testRoom.getDeadMonsters().size() == 0;
        
        assert testRoom.getLiveMonsters().containsAll(groupOfMonsters) == true;        
        assert testRoom.getLiveMonsters().size() == 4;        
        
        assert testRoom.getPlayerHealthLostPerEncounter() == 7; //7 bc there are on of each monster

        //Try to modify the set returned, causing an UnsupportedOperationException
        Monster badBaron = new Monster(MonsterType.BARON_OF_HELL);
        caught = false; 
        try { 
            testRoom.getMonsters().add(badBaron);
        } catch (UnsupportedOperationException e) { 
            caught = true; 
        } 
        assert caught == true; 


    //Test for compareTO
        assert testRoom.compareTo(secondTestRoom) == 0; //Should be 0 becasuse their danger levels are the same

    //Manually kill the roomBaron and check that it properly influences all of the methods - there is no priority to protectors in the room class itself, only in the GameBot class
        testRoom.monsterKilled(roomBaron);
        assert roomBaron.attack(Weapon.SHOTGUN, 15) == true; //It can be attacked by more, just not less
        assert roomBaron.isDead() == true;

        assert testRoom.getDangerLevel() == 6;
        assert secondTestRoom.getDangerLevel() == 10;
        assert testRoom.compareTo(secondTestRoom) == -4; //Should be -4 bc the BOH was killed

        assert testRoom.getMonsters().contains(roomBaron) == true; //Should not be affected
        assert testRoom.getDeadMonsters().contains(roomBaron) == true;                
        assert testRoom.getLiveMonsters().contains(roomBaron) == false;        
        
        assert testRoom.getPlayerHealthLostPerEncounter() == 4; //(1,1,2)

    //Manually kill the rest of the monsters on the testRoom
        testRoom.monsterKilled(roomIMP);
        assert roomIMP.attack(Weapon.SHOTGUN, 15) == true; //It can be attacked by more, just not less
        assert roomIMP.isDead() == true;

        testRoom.monsterKilled(roomDemon);
        assert roomDemon.attack(Weapon.SHOTGUN, 15) == true; //It can be attacked by more, just not less
        assert roomDemon.isDead() == true;

        testRoom.monsterKilled(roomSpectre);
        assert roomSpectre.attack(Weapon.SHOTGUN, 15) == true; //It can be attacked by more, just not less
        assert roomSpectre.isDead() == true;

        assert testRoom.isCompleted() == true;

        assert testRoom.getMonsters().containsAll(groupOfMonsters) == true; //Should not be affected
        assert testRoom.getMonsters().size() == 4;

        assert testRoom.getDeadMonsters().containsAll(groupOfMonsters) == true;                
        assert testRoom.getDeadMonsters().size() == 4;
        
        assert testRoom.getLiveMonsters().containsAll(groupOfMonsters) == false;        
        assert testRoom.getLiveMonsters().size() == 0;        
        
        assert testRoom.getPlayerHealthLostPerEncounter() == 0; //7 bc there are one of each monster
        assert testRoom.getDangerLevel() == 0;
        assert secondTestRoom.getDangerLevel() == 10;
        assert testRoom.compareTo(secondTestRoom) == -10; //Should be -4 bc the BOH was killed



    /**
     * Tests for the GameBot Class!
    */
        Monster gameIMP = new Monster(MonsterType.IMP);        
        Monster gameDemon = new Monster(MonsterType.DEMON);
        Monster gameSpectre = new Monster(MonsterType.SPECTRE);
        Monster gameBaron = new Monster(MonsterType.BARON_OF_HELL);
        TreeSet<Monster> gameMonsters = new TreeSet<>();
        gameMonsters.add(gameIMP);
        gameMonsters.add(gameDemon);
        gameMonsters.add(gameSpectre);
        gameMonsters.add(gameBaron);

        HashSet<Weapon> gameRewardWeapons = new HashSet<>();
        gameRewardWeapons.add(Weapon.CHAINSAW);
        gameRewardWeapons.add(Weapon.PISTOL);
        Map<Weapon,Integer> gameRewardAmmunition = new HashMap<>();
        gameRewardAmmunition.put(Weapon.CHAINSAW,20);
        gameRewardAmmunition.put(Weapon.PISTOL,30);
        gameRewardAmmunition.put(Weapon.SHOTGUN,40);

        Room gameRoom = new Room(gameMonsters, gameRewardWeapons, gameRewardAmmunition, 100, "Game Time Baby");
        TreeSet<Room> gameRooms = new TreeSet<>();
        gameRooms.add(gameRoom);

    //Just one player
        Player gamePlayer = new Player("Game Player",100);
        TreeSet<Player> gamePlayers = new TreeSet<>();
        gamePlayers.add(gamePlayer);
        GameBot simpleGame = new GameBot(gameRooms, gamePlayers);

    //Before playing the game, let's test the methods individually
        assert simpleGame.getCompletedRooms().size() == 0;
        assert simpleGame.getAllRooms().size() == 1;
        assert simpleGame.getLivePlayers().size() == 1;
        assert simpleGame.getLivePlayersWithWeaponAndAmmunition(Weapon.FIST, 100).size() == 1;
        assert simpleGame.getLivePlayersWithWeaponAndAmmunition(Weapon.SHOTGUN, 40).size() == 0;


        //Try to modify the set returned, causing an UnsupportedOperationException
        caught = false; 
        try { 
            simpleGame.getAllRooms().add(testRoom);
        } catch (UnsupportedOperationException e) { 
            caught = true; 
        } 
        assert caught == true; 

        //We got some cheaters: Try to modify the set returned, causing an UnsupportedOperationException
        caught = false; 
        try { 
            simpleGame.getAllRooms().remove(gameRoom);
        } catch (UnsupportedOperationException e) { 
            caught = true; 
        } 
        assert caught == true; 

        assert simpleGame.getAllProtectorsInRoom(gameIMP, gameRoom).size() == 0;
        assert simpleGame.getAllProtectorsInRoom(gameSpectre, gameRoom).size() == 0;
        assert simpleGame.getAllProtectorsInRoom(gameDemon, gameRoom).size() == 2; //BOH, which itself is protected by SPECTRE
        assert simpleGame.getAllProtectorsInRoom(gameBaron, gameRoom).size() == 1; 
        
        TreeSet<Monster> testMonsterGroup = new TreeSet<>();
        testMonsterGroup.add(gameBaron);
        testMonsterGroup.add(gameSpectre);
        assert simpleGame.getAllProtectorsInRoom(gameDemon, gameRoom).containsAll(testMonsterGroup); 
        
        assert simpleGame.getAllProtectorsInRoom(gameBaron, gameRoom).contains(gameSpectre); 

    //Tests for 1st canKill(Player player, Monster monster, Room room)
        assert gamePlayer.getHealth() == 100;
        assert simpleGame.canKill(gamePlayer, gameIMP, gameRoom) == true; 
        assert gamePlayer.getHealth() == 100;//Check that health did actually detract 

        assert simpleGame.canKill(gamePlayer, gameDemon, gameRoom) == false; 
        assert simpleGame.canKill(gamePlayer, gameSpectre, gameRoom) == false; 
        assert simpleGame.canKill(gamePlayer, gameBaron, gameRoom) == false; 

    //Let's help him out! 
        //DEMON
        assert gamePlayer.addWeapon(Weapon.CHAINSAW) == true; 
        assert simpleGame.canKill(gamePlayer, gameDemon, gameRoom) == false; //His protectors are still alive!
        assert simpleGame.getLivePlayersWithWeaponAndAmmunition(Weapon.CHAINSAW,5).size() == 1;


        //SPECTRE - he can die once the weapon is added with suffiecient ammunition bc he doesn't have any protectors
        assert gamePlayer.addWeapon(Weapon.PISTOL) == true; 
        assert simpleGame.canKill(gamePlayer, gameSpectre, gameRoom) == false; //He only needs 1
        assert simpleGame.getLivePlayersWithWeaponAndAmmunition(Weapon.PISTOL,5).size() == 1;

        //He still doens't have enough ammo!
        assert gamePlayer.addAmmunition(Weapon.PISTOL, 11) == 16;
        assert simpleGame.canKill(gamePlayer, gameSpectre, gameRoom) == true; //He only needs 1

        //BOH
        assert gamePlayer.addWeapon(Weapon.SHOTGUN) == true; 
        assert simpleGame.getLivePlayersWithWeaponAndAmmunition(Weapon.SHOTGUN,5).size() == 1;
        assert gamePlayer.addAmmunition(Weapon.SHOTGUN, 6) == 11;
        assert simpleGame.canKill(gamePlayer, gameBaron, gameRoom) == false; 

        assert gamePlayer.addAmmunition(Weapon.SHOTGUN, 11) == 22;
        assert gamePlayer.getAmmunitionRoundsForWeapon(Weapon.CHAINSAW) == 5; 
        assert gamePlayer.getAmmunitionRoundsForWeapon(Weapon.PISTOL) == 16; 
        assert gamePlayer.getAmmunitionRoundsForWeapon(Weapon.SHOTGUN) == 22; 
        assert gameBaron.isDead() == false;
        assert gameRoom.getLiveMonsters().contains(gameBaron);
    

    /**
     * Got to figure out why this is not working properly 
    */
        //assert simpleGame.canKill(gamePlayer, gameBaron, gameRoom) == true; //He only needs 1

        assert gamePlayer.getHealth() == 100; //Make sure that none of those shanangons affected his health

    /**
     * Kill some monsters
     */
        assert gameRoom.isCompleted() == false;
        assert gameRoom.getPlayerHealthLostPerEncounter() == 7; 
        assert gameRoom.getDangerLevel() == 10;

        //SPECTRE - he can die once the weapon is added with suffiecient ammunition bc he doesn't have any protectors
        assert simpleGame.canKill(gamePlayer, gameSpectre, gameRoom) == true; //He only needs 1
        simpleGame.killMonster(gamePlayer, gameRoom, gameSpectre); 
        assert gamePlayer.isDead() == false;
        assert gamePlayer.getHealth() == 93;
        assert gamePlayer.getAmmunitionRoundsForWeapon(Weapon.PISTOL) == 10;
        assert gameSpectre.isDead() == true;
        assert gameRoom.getLiveMonsters().contains(gameSpectre) == false;
        assert gameRoom.getDeadMonsters().contains(gameSpectre) == true;
        assert gameRoom.getPlayerHealthLostPerEncounter() == 5; 
        assert gameRoom.getDangerLevel() == 7;


        assert simpleGame.canKill(gamePlayer, gameIMP, gameRoom) == true; //He only needs 1
        simpleGame.killMonster(gamePlayer, gameRoom, gameIMP); 
        assert gamePlayer.isDead() == false;
        assert gamePlayer.getHealth() == 88;
        assert gameIMP.isDead() == true;
        assert gameRoom.getLiveMonsters().contains(gameIMP) == false;
        assert gameRoom.getDeadMonsters().contains(gameIMP) == true;
        assert gameRoom.getPlayerHealthLostPerEncounter() == 4; 
        assert gameRoom.getDangerLevel() == 6;

        assert simpleGame.canKill(gamePlayer, gameBaron, gameRoom) == true; //He only needs 1
        simpleGame.killMonster(gamePlayer, gameRoom, gameBaron); 
        assert gamePlayer.isDead() == false;
        assert gamePlayer.getHealth() == 84;
        assert gamePlayer.getAmmunitionRoundsForWeapon(Weapon.SHOTGUN) == 10;
        assert gameBaron.isDead() == true;
        assert gameRoom.getLiveMonsters().contains(gameBaron) == false;
        assert gameRoom.getDeadMonsters().contains(gameBaron) == true;
        assert gameRoom.getPlayerHealthLostPerEncounter() == 1; 
        assert gameRoom.getDangerLevel() == 2;

        assert simpleGame.canKill(gamePlayer, gameDemon, gameRoom) == true; //He only needs 1
        simpleGame.killMonster(gamePlayer, gameRoom, gameDemon); 
        assert gamePlayer.isDead() == false;
        assert gamePlayer.getHealth() == 83;
        assert gamePlayer.getAmmunitionRoundsForWeapon(Weapon.CHAINSAW) == 4;
        assert gameDemon.isDead() == true;
        assert gameRoom.getLiveMonsters().contains(gameDemon) == false;
        assert gameRoom.getDeadMonsters().contains(gameDemon) == true;
        assert gameRoom.getPlayerHealthLostPerEncounter() == 0; 
        assert gameRoom.getDangerLevel() == 0;

    //Tests for the other GameBot variables
        assert gameRoom.isCompleted() == true;
        assert simpleGame.getCompletedRooms().contains(gameRoom); 
        assert simpleGame.getAllRooms().contains(gameRoom); 


    //Reward the player
        assert gamePlayer.getHealth() == 83;
        assert gamePlayer.hasWeapon(Weapon.CHAINSAW) == true;
        assert gamePlayer.hasWeapon(Weapon.PISTOL) == true;
        assert gamePlayer.getAmmunitionRoundsForWeapon(Weapon.CHAINSAW) == 4;
        assert gamePlayer.getAmmunitionRoundsForWeapon(Weapon.PISTOL) == 10;
        assert gamePlayer.getAmmunitionRoundsForWeapon(Weapon.SHOTGUN) == 10;

        simpleGame.reapCompletionRewards(gamePlayer, gameRoom);
        assert gamePlayer.getHealth() == 183; //reward = 100
        assert gamePlayer.getAmmunitionRoundsForWeapon(Weapon.CHAINSAW) == 24; //reward = 20
        assert gamePlayer.getAmmunitionRoundsForWeapon(Weapon.PISTOL) == 40; //reward = 30
        assert gamePlayer.getAmmunitionRoundsForWeapon(Weapon.SHOTGUN) == 50; //reward = 40



/*
        HashSet<Weapon> gameRewardWeapons = new HashSet<>();
        gameRewardWeapons.add(Weapon.CHAINSAW);
        gameRewardWeapons.add(Weapon.PISTOL);
        Map<Weapon,Integer> gameRewardAmmunition = new HashMap<>();
        gameRewardAmmunition.put(Weapon.CHAINSAW,20);
        gameRewardAmmunition.put(Weapon.PISTOL,30);
        gameRewardAmmunition.put(Weapon.SHOTGUN,40);

        Room gameRoom = new Room(gameMonsters, gameRewardWeapons, gameRewardAmmunition, 100, "Game Time Baby");
*/
/*
        //DEMON
        assert simpleGame.canKill(gamePlayer, gameDemon, gameRoom) == true; //His protectors are still alive!
        assert simpleGame.getLivePlayersWithWeaponAndAmmunition(Weapon.CHAINSAW,5).size() == 1;

        //He still doens't have enough ammo!
        assert gamePlayer.addAmmunition(Weapon.PISTOL, 11) == 16;
        assert simpleGame.canKill(gamePlayer, gameSpectre, gameRoom) == true; //He only needs 1

        //BOH
        assert gamePlayer.addWeapon(Weapon.SHOTGUN) == true; 
        assert simpleGame.getLivePlayersWithWeaponAndAmmunition(Weapon.SHOTGUN,5).size() == 1;
        assert gamePlayer.addAmmunition(Weapon.SHOTGUN, 6) == 11;
        assert simpleGame.canKill(gamePlayer, gameBaron, gameRoom) == false; 

        assert gamePlayer.addAmmunition(Weapon.SHOTGUN, 11) == 22;
        assert gamePlayer.getAmmunitionRoundsForWeapon(Weapon.CHAINSAW) == 5; 
        assert gamePlayer.getAmmunitionRoundsForWeapon(Weapon.PISTOL) == 16; 
        assert gamePlayer.getAmmunitionRoundsForWeapon(Weapon.SHOTGUN) == 22; 
        assert gameBaron.isDead() == false;
        assert gameRoom.getLiveMonsters().contains(gameBaron);
*/






    //

/*
    //Since the room is now completed

        assert simpleGame.getCompletedRooms().size() == 1;
        assert simpleGame.getAllRooms().size() == 1;
        assert simpleGame.getLivePlayers().size() == 1;
        assert simpleGame.getLivePlayersWithWeaponAndAmmunition(Weapon.FIST, 100).size() == 1;
        assert simpleGame.getLivePlayersWithWeaponAndAmmunition(Weapon.SHOTGUN, 40).size() == 1;


    //Kill the player
        assert simpleGame.getLivePlayers().size() == 0;
        assert simpleGame.getLivePlayersWithWeaponAndAmmunition(Weapon.FIST, 100).size() == 0;
*/

    /**
     * Short game
    */
        Monster shortGameIMP = new Monster(MonsterType.IMP);        
        Monster shortGameDemon = new Monster(MonsterType.DEMON);
        Monster shortGameBaron = new Monster(MonsterType.BARON_OF_HELL);
        Monster shortGameBaron1 = new Monster(MonsterType.BARON_OF_HELL);
        Monster shortGameBaron2 = new Monster(MonsterType.BARON_OF_HELL);
        TreeSet<Monster> shortGameMonsters = new TreeSet<>();
        shortGameMonsters.add(shortGameIMP);
        shortGameMonsters.add(shortGameDemon);
        shortGameMonsters.add(shortGameBaron);
        shortGameMonsters.add(shortGameBaron1);
        shortGameMonsters.add(shortGameBaron2);


        HashSet<Weapon> shortGameRewardWeapons = new HashSet<>();
        shortGameRewardWeapons.add(Weapon.CHAINSAW);
        shortGameRewardWeapons.add(Weapon.PISTOL);
        Map<Weapon,Integer> shortGameRewardAmmunition = new HashMap<>();
        shortGameRewardAmmunition.put(Weapon.CHAINSAW,20);
        shortGameRewardAmmunition.put(Weapon.PISTOL,30);
        shortGameRewardAmmunition.put(Weapon.SHOTGUN,40);

        Room shortGameRoom = new Room(shortGameMonsters, shortGameRewardWeapons, shortGameRewardAmmunition, 100, "Short Game");
        TreeSet<Room> shortGameRooms = new TreeSet<>();
        shortGameRooms.add(shortGameRoom);

    //Just one player
        Player shortGamePlayer = new Player("Player 1",100);
        assert shortGamePlayer.addWeapon(Weapon.SHOTGUN) == true;
        assert shortGamePlayer.addAmmunition(Weapon.SHOTGUN, 95) == 100;
        TreeSet<Player> shortGamePlayers = new TreeSet<>();
        shortGamePlayers.add(shortGamePlayer);
        GameBot shortGame = new GameBot(shortGameRooms, shortGamePlayers);

        assert shortGame.getAllProtectorsInRoom(shortGameDemon, shortGameRoom).size() == 3; //BOH, which itself is protected by SPECTRE

        assert shortGame.canKill(shortGamePlayer, shortGameBaron, shortGameRoom) == true;

    /**
     * Let's crank up the heat!
    */
    //

    //Tests for the play method        

    }

    void playAnother(){
        Monster gameIMP = new Monster(MonsterType.IMP, MonsterType.DEMON);        
        Monster gameDemon = new Monster(MonsterType.DEMON, MonsterType.BARON_OF_HELL);
        Monster gameSpectre = new Monster(MonsterType.SPECTRE);
        Monster gameBaron = new Monster(MonsterType.BARON_OF_HELL, MonsterType.SPECTRE);
        TreeSet<Monster> gameMonsters = new TreeSet<>();
        gameMonsters.add(gameIMP);
        gameMonsters.add(gameDemon);
        gameMonsters.add(gameSpectre);
        gameMonsters.add(gameBaron);

        HashSet<Weapon> gameRewardWeapons = new HashSet<>();
        gameRewardWeapons.add(Weapon.CHAINSAW);
        gameRewardWeapons.add(Weapon.PISTOL);
        Map<Weapon,Integer> gameRewardAmmunition = new HashMap<>();
        gameRewardAmmunition.put(Weapon.CHAINSAW,20);
        gameRewardAmmunition.put(Weapon.PISTOL,30);
        gameRewardAmmunition.put(Weapon.SHOTGUN,40);

        Room gameRoom = new Room(gameMonsters, gameRewardWeapons, gameRewardAmmunition, 100, "Game Time Baby");
        TreeSet<Room> gameRooms = new TreeSet<>();
        gameRooms.add(gameRoom);

    //Just one player
        Player gamePlayer = new Player("Game Player",100);
        TreeSet<Player> gamePlayers = new TreeSet<>();
        gamePlayers.add(gamePlayer);
        GameBot simpleGame = new GameBot(gameRooms, gamePlayers);

        assert simpleGame.passThroughRooms().isEmpty();           

    //Let's give the player the weapons he needs to win
        assert gamePlayer.getHealth() == 100;
        assert gamePlayer.addWeapon(Weapon.CHAINSAW) == true; 
        assert gamePlayer.addWeapon(Weapon.PISTOL) == true; 
        assert gamePlayer.addAmmunition(Weapon.PISTOL, 11) == 16;
        assert gamePlayer.addWeapon(Weapon.SHOTGUN) == true; 
        assert gamePlayer.addAmmunition(Weapon.SHOTGUN, 17) == 22;

        assert simpleGame.canKill(gamePlayer, gameDemon, gameRoom) == true; 
        assert simpleGame.canKill(gamePlayer, gameSpectre, gameRoom) == true; //He only needs 1
        assert simpleGame.canKill(gamePlayer, gameBaron, gameRoom) == true; //He only needs 1


        assert simpleGame.passThroughRooms().contains(gameRoom);           
        assert gamePlayer.getHealth() == 185; //reward = 100        
        assert gamePlayer.getAmmunitionRoundsForWeapon(Weapon.CHAINSAW) == 24; //reward = 20
        assert gamePlayer.getAmmunitionRoundsForWeapon(Weapon.PISTOL) == 40; //reward = 30
        assert gamePlayer.getAmmunitionRoundsForWeapon(Weapon.SHOTGUN) == 50; //reward = 40



    }
    void singleWeaponKill(){
        Monster gameDemon = new Monster(MonsterType.DEMON);
        Monster gameDemon2 = new Monster(MonsterType.DEMON);
        Monster gameSpectre = new Monster(MonsterType.SPECTRE);
        Monster gameSpectre2 = new Monster(MonsterType.SPECTRE);
        Monster gameBaron = new Monster(MonsterType.BARON_OF_HELL);
        Monster gameBaron2 = new Monster(MonsterType.BARON_OF_HELL);
        TreeSet<Monster> gameMonsters = new TreeSet<>();
        gameMonsters.add(gameDemon);
        gameMonsters.add(gameDemon2);
        gameMonsters.add(gameSpectre);
        gameMonsters.add(gameSpectre2);
        gameMonsters.add(gameBaron);
        gameMonsters.add(gameBaron2);

        HashSet<Weapon> gameRewardWeapons = new HashSet<>();
        gameRewardWeapons.add(Weapon.CHAINSAW);
        gameRewardWeapons.add(Weapon.PISTOL);
        Map<Weapon,Integer> gameRewardAmmunition = new HashMap<>();
        gameRewardAmmunition.put(Weapon.CHAINSAW,20);
        gameRewardAmmunition.put(Weapon.PISTOL,30);
        gameRewardAmmunition.put(Weapon.SHOTGUN,40);

        Room gameRoom = new Room(gameMonsters, gameRewardWeapons, gameRewardAmmunition, 100, "One Weapon War");
        TreeSet<Room> gameRooms = new TreeSet<>();
        gameRooms.add(gameRoom);

        //Just one player
        Player gamePlayer = new Player("Game Player",100);
        TreeSet<Player> gamePlayers = new TreeSet<>();
        gamePlayers.add(gamePlayer);
        GameBot simpleGame = new GameBot(gameRooms, gamePlayers);

        //Let's give the player the weapons he needs to win
        assert gamePlayer.getHealth() == 100;
        assert gamePlayer.addAmmunition(Weapon.PISTOL, 11) == 11; //This doesn't really matter for this set of tests
        assert gamePlayer.addWeapon(Weapon.SHOTGUN) == true;
        //assert gamePlayer.addAmmunition(Weapon.SHOTGUN, 500) == 505;
        assert gamePlayer.hasWeapon(Weapon.PISTOL) == false;
        assert gamePlayer.addAmmunition(Weapon.SHOTGUN, 33) == 38;

        assert simpleGame.canKill(gamePlayer, gameSpectre, gameRoom) == true;
        assert simpleGame.canKill(gamePlayer, gameSpectre2, gameRoom) == true;

        assert simpleGame.canKill(gamePlayer, gameBaron, gameRoom) == true;
        assert simpleGame.canKill(gamePlayer, gameBaron2, gameRoom) == true;

        assert simpleGame.canKill(gamePlayer, gameDemon2, gameRoom) == true;
        assert simpleGame.canKill(gamePlayer, gameDemon, gameRoom) == true;


        assert simpleGame.passThroughRooms().contains(gameRoom);
        //System.out.println(gamePlayer.getHealth());
        assert gamePlayer.getHealth() == 162; //reward = 100
        //System.out.println(gamePlayer.getAmmunitionRoundsForWeapon(Weapon.CHAINSAW));
        assert gamePlayer.getAmmunitionRoundsForWeapon(Weapon.CHAINSAW) == 25; //reward = 20
        //System.out.println(gamePlayer.getAmmunitionRoundsForWeapon(Weapon.PISTOL));
        assert gamePlayer.getAmmunitionRoundsForWeapon(Weapon.PISTOL) == 46; //reward = 30
        //System.out.println(gamePlayer.getAmmunitionRoundsForWeapon(Weapon.SHOTGUN));
        assert gamePlayer.getAmmunitionRoundsForWeapon(Weapon.SHOTGUN) == 40; //reward = 40
    }

    void tenBarons(){
        Monster gameBaron1 = new Monster(MonsterType.BARON_OF_HELL);
        Monster gameBaron2 = new Monster(MonsterType.BARON_OF_HELL);
        Monster gameBaron3 = new Monster(MonsterType.BARON_OF_HELL);
        Monster gameBaron4 = new Monster(MonsterType.BARON_OF_HELL);
        Monster gameBaron5 = new Monster(MonsterType.BARON_OF_HELL);
        Monster gameBaron6 = new Monster(MonsterType.BARON_OF_HELL);
        Monster gameBaron7 = new Monster(MonsterType.BARON_OF_HELL);
        Monster gameBaron8 = new Monster(MonsterType.BARON_OF_HELL);
        Monster gameBaron9 = new Monster(MonsterType.BARON_OF_HELL);
        Monster gameBaron10 = new Monster(MonsterType.BARON_OF_HELL);
        TreeSet<Monster> gameMonsters = new TreeSet<>();
        gameMonsters.add(gameBaron1);
        gameMonsters.add(gameBaron2);
        gameMonsters.add(gameBaron3);
        gameMonsters.add(gameBaron4);
        gameMonsters.add(gameBaron5);
        gameMonsters.add(gameBaron6);
        gameMonsters.add(gameBaron7);
        gameMonsters.add(gameBaron8);
        gameMonsters.add(gameBaron9);
        gameMonsters.add(gameBaron10);

        HashSet<Weapon> gameRewardWeapons = new HashSet<>();
        Map<Weapon,Integer> gameRewardAmmunition = new HashMap<>();

        Room gameRoom = new Room(gameMonsters, gameRewardWeapons, gameRewardAmmunition, 0, "Ten Barons and 31 bullets");
        TreeSet<Room> gameRooms = new TreeSet<>();
        gameRooms.add(gameRoom);

        //Just one player
        Player gamePlayer = new Player("Lone Survivor",166);
        TreeSet<Player> gamePlayers = new TreeSet<>();
        gamePlayers.add(gamePlayer);
        GameBot simpleGame = new GameBot(gameRooms, gamePlayers);

        //Let's give the player the weapons he needs to win
        assert gamePlayer.getHealth() == 166;
        assert gamePlayer.addWeapon(Weapon.SHOTGUN) == true;
        assert gamePlayer.addAmmunition(Weapon.SHOTGUN, 115) == 120;

        assert simpleGame.canKill(gamePlayer, gameBaron1, gameRoom) == true;
        assert simpleGame.canKill(gamePlayer, gameBaron2, gameRoom) == true;
        assert simpleGame.canKill(gamePlayer, gameBaron3, gameRoom) == true;
        assert simpleGame.canKill(gamePlayer, gameBaron4, gameRoom) == true;
        assert simpleGame.canKill(gamePlayer, gameBaron5, gameRoom) == true;
        assert simpleGame.canKill(gamePlayer, gameBaron6, gameRoom) == true;
        assert simpleGame.canKill(gamePlayer, gameBaron7, gameRoom) == true;
        assert simpleGame.canKill(gamePlayer, gameBaron8, gameRoom) == true;
        assert simpleGame.canKill(gamePlayer, gameBaron9, gameRoom) == true;
        assert simpleGame.canKill(gamePlayer, gameBaron10, gameRoom) == true;

        assert simpleGame.passThroughRooms().contains(gameRoom);
        //System.out.println("current health: " + gamePlayer.getHealth());
        assert gamePlayer.getHealth() == 1; 
        //System.out.println(gamePlayer.getAmmunitionRoundsForWeapon(Weapon.CHAINSAW));
        assert gamePlayer.getAmmunitionRoundsForWeapon(Weapon.CHAINSAW) == 0; //reward = 20
        //System.out.println(gamePlayer.getAmmunitionRoundsForWeapon(Weapon.PISTOL));
        assert gamePlayer.getAmmunitionRoundsForWeapon(Weapon.PISTOL) == 0; //reward = 30
        //System.out.println(gamePlayer.getAmmunitionRoundsForWeapon(Weapon.SHOTGUN));
        assert gamePlayer.getAmmunitionRoundsForWeapon(Weapon.SHOTGUN) == 0; //reward = 40
    }
}














