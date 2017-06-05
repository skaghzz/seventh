/*
 * see license.txt 
 */
package seventh.ai.basic.teamstrategy.ObjectiveTeam;

import java.util.ArrayList;
import java.util.List;

import seventh.ai.basic.Brain;
import seventh.ai.basic.DefaultAISystem;
import seventh.ai.basic.Stats;
import seventh.ai.basic.World;
import seventh.ai.basic.Zone;
import seventh.ai.basic.Zones;
import seventh.ai.basic.actions.Action;
import seventh.ai.basic.actions.Actions;
import seventh.game.GameInfo;
import seventh.game.Player;
import seventh.game.PlayerInfo;
import seventh.game.Team;
import seventh.game.entities.BombTarget;
import seventh.game.entities.PlayerEntity;
import seventh.shared.Randomizer;
import seventh.shared.TimeStep;

/**
 * Handles the defensive strategy objective based game type.
 * 
 * @author Tony
 *
 */
public class DefenseObjectiveTeamStrategy extends AllObjectiveTeamStrategy {

    private World world;
    
    private List<PlayerEntity> playersInZone;
    
    /**
     * 
     */
    public DefenseObjectiveTeamStrategy(DefaultAISystem aiSystem, Team team) {
       super(aiSystem, team);
       this.playersInZone = new ArrayList<>();
        
    }
    
    /* (non-Javadoc)
     * @see seventh.ai.basic.teamstrategy.TeamStrategy#getDesirability(seventh.ai.basic.Brain)
     */
    @Override
    public double getDesirability(Brain brain) {    
        return 0.7;
    }

    /* (non-Javadoc)
     * @see seventh.ai.AIGameTypeStrategy#startOfRound(seventh.game.Game)
     */
    @Override
    public void startOfRound(GameInfo game) {        
        this.setZoneToAttack(calculateZoneToAttack());    
        this.currentState = new Random();     
        this.timeUntilOrganizedAttack = 30_000 + random.nextInt(60_000);        
        this.world = this.aiSystem.getWorld();
    }
    
    /**
     * @return determine which {@link Zone} to attack
     */
    protected Zone calculateZoneToAttack() {
        Zone zoneToAttack = null;
        
        List<Zone> zonesWithBombs = this.zones.getBombTargetZones();
        if(!zonesWithBombs.isEmpty()) {
            
            /* Look for targets that are being planted or have been
             * planted so that we can give them highest priority
             */
            List<Zone> zonesToAttack = new ArrayList<>();
            for(int i = 0; i < zonesWithBombs.size(); i++) {
                Zone zone = zonesWithBombs.get(i);
                
                if(zone.hasActiveBomb()) {
                    zonesToAttack.add(zone);
                }
            }
            
            
            if(zonesToAttack.isEmpty()) {
                
                /* All targets are free of bombs, so lets pick a random one to 
                 * go to
                 */
                zoneToAttack = zonesWithBombs.get(random.nextInt(zonesWithBombs.size()));

                /* check to see if there are too many agents around this bomb */
                if(world != null && zonesToAttack != null) {
                    world.playersIn(this.playersInZone, zoneToAttack.getBounds());
                    
                    int numberOfFriendliesInArea = team.getNumberOfPlayersOnTeam(playersInZone);
                    if(numberOfFriendliesInArea > 0) {
                        float percentageOfTeamInArea = team.getNumberOfAlivePlayers() / numberOfFriendliesInArea;
                        if(percentageOfTeamInArea > 0.3f ) {
                            zoneToAttack = world.findAdjacentZone(zoneToAttack, 30);
                        }
                    }
                }
            }
            else {
                /* someone has planted or is planting a bomb on a target, go rush to defend
                 * it
                 */
                zoneToAttack = zonesToAttack.get(random.nextInt(zonesToAttack.size()));
            }
            
        }
        
        /*
         * If all else fails, just pick a statistically
         * good Zone to go to
         */
        if(zoneToAttack == null) {
            zoneToAttack = stats.getDeadliesZone();
        }
        
        return zoneToAttack;
    }

    /* (non-Javadoc)
     * @see seventh.ai.AIGameTypeStrategy#endOfRound(seventh.game.Game)
     */
    @Override
    public void endOfRound(GameInfo game) {        
        this.currentState = new Defend();
        this.setZoneToAttack(null);
    }
    
    /**
     * @return true if a bomb has been planted
     */
    private boolean isBombPlanted() {
        List<BombTarget> targets = world.getBombTargets();
        for(int i = 0; i < targets.size(); i++) {
            BombTarget target = targets.get(i);
            if(target.isAlive()) {
                if(target.bombActive()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * @param zone
     * @return true if there is a {@link BombTarget} that has been planted
     * and someone is actively disarming it
     */
    private boolean isBombBeingDisarmed(Zone zone) {
        boolean bombDisarming = false;
        
        List<BombTarget> targets = zone.getTargets();
        for(int i = 0; i < targets.size(); i++) {
            BombTarget target = targets.get(i);
            if(target.isBombAttached() && target.bombDisarming()) {                
                bombDisarming = true;
                break;
            }
        }
        
        return bombDisarming;
    }
        
    /* (non-Javadoc)
     * @see seventh.ai.AIGameTypeStrategy#update(seventh.shared.TimeStep, seventh.game.Game)
     */
    @Override
    public void update(TimeStep timeStep, GameInfo game) {
    
        /* drop everything and go disarm the bomb */
        if(isBombPlanted()) {
            setZoneToAttack(calculateZoneToAttack());
            
            if(isBombBeingDisarmed(getZoneToAttack())) {
                giveOrders(new AttackZone());
            }
            else {
                giveOrders(new DefuseBomb());
            }
        }
        else {
        
            /* lets do some random stuff for a while, this
             * helps keep things dynamic
             */
            if(this.timeUntilOrganizedAttack > 0) {
                this.timeUntilOrganizedAttack -= timeStep.getDeltaTime();
                
                giveOrders(new Random());
                return;
            }
            
            setZoneToAttack(calculateZoneToAttack());            
            if(getZoneToAttack() != null) {                
                giveOrders(new AttackZone());
            }
            
        }
    }

}
