/*
 * see license.txt 
 */
package seventh.ai.basic.teamstrategy.ObjectiveTeam;

import java.util.ArrayList;
import java.util.List;

import seventh.ai.basic.Brain;
import seventh.ai.basic.DefaultAISystem;
import seventh.ai.basic.Stats;
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
import seventh.math.Vector2f;
import seventh.shared.Randomizer;
import seventh.shared.TimeStep;

/**
 * Handles the objective based game type.
 * 
 * @author Tony
 *
 */
public class OffenseObjectiveTeamStrategy extends AllObjectiveTeamStrategy {
    
    /**
     * 
     */
    public OffenseObjectiveTeamStrategy(DefaultAISystem aiSystem, Team team) {
        super(aiSystem, team);
    }
    
    /* (non-Javadoc)
     * @see seventh.ai.basic.teamstrategy.TeamStrategy#getDesirability(seventh.ai.basic.Brain)
     */
    @Override
    public double getDesirability(Brain brain) {    
        return 0.8;
    }

    /* (non-Javadoc)
     * @see seventh.ai.AIGameTypeStrategy#startOfRound(seventh.game.Game)
     */
    @Override
    public void startOfRound(GameInfo game) {        
        this.setZoneToAttack(calculateZoneToAttack());    
        this.currentState = new Random();        
        this.timeUntilOrganizedAttack = 15_000 + (random.nextInt(25) * 1000);                
    }
    
    /**
     * @return determine which {@link Zone} to attack
     */
    private List<Zone> calculateZonesOfInterest() {
        List<Zone> validZones = new ArrayList<>();
        List<Zone> zonesWithBombs = this.zones.getBombTargetZones();
        if(!zonesWithBombs.isEmpty()) {
    
            for(Zone zone : zonesWithBombs) {
                if(zone.isTargetsStillActive()) {
                    validZones.add(zone);
                }
            }
            
        }
        else {
            validZones.add(stats.getDeadliesZone());
        }
        
        return validZones;
    }
    
    /**
     * @return determine which {@link Zone} to attack
     */
    private Zone calculateZoneToAttack(Brain brain) {
        Zone zoneToAttack = null;
        
        List<Zone> validZones = calculateZonesOfInterest();
        if(!validZones.isEmpty()) {
            PlayerEntity ent = brain.getEntityOwner();
            
            float distance = Float.MAX_VALUE;
            Vector2f closest = new Vector2f();
            for(int i = 0; i < validZones.size(); i++) {
                Zone zone = validZones.get(i);
                closest.set(zone.getBounds().x, zone.getBounds().y);
                
                float otherDistance = ent.distanceFromSq(closest);
                if(zoneToAttack==null || otherDistance < distance) {
                    zoneToAttack = zone;
                    distance = otherDistance;
                }
            }
            
            //zoneToAttack = validZones.get(random.nextInt(validZones.size()));
        }        
        else {
            zoneToAttack = stats.getDeadliesZone();
        }
        
        return zoneToAttack;
    }
    
    /**
     * @return determine which {@link Zone} to attack
     */
    protected Zone calculateZoneToAttack() {
        Zone zoneToAttack = null;
        
        List<Zone> validZones = calculateZonesOfInterest();
        if(!validZones.isEmpty()) {                
            zoneToAttack = validZones.get(random.nextInt(validZones.size()));
        }        
        else {
            zoneToAttack = stats.getDeadliesZone();
        }
        
        return zoneToAttack;
    }
    
    /* (non-Javadoc)
     * @see seventh.ai.AIGameTypeStrategy#endOfRound(seventh.game.Game)
     */
    @Override
    public void endOfRound(GameInfo game) {        
        this.currentState = new Random();
        this.setZoneToAttack(null);
    }
    
    
    /**
     * @param zone
     * @return true if there is a {@link BombTarget} that is being planted or
     * is planted in the supplied {@link Zone}
     */
    private boolean isBombPlantedInZone(Zone zone) {
        boolean bombPlanted = false;
        
        List<BombTarget> targets = zone.getTargets();
        for(int i = 0; i < targets.size(); i++) {
            BombTarget target = targets.get(i);
            if(/*target.bombPlanting() ||*/ target.bombActive()) {                
                bombPlanted = true;
                break;
            }
        }
        
        return bombPlanted;
    }
        
    /* (non-Javadoc)
     * @see seventh.ai.AIGameTypeStrategy#update(seventh.shared.TimeStep, seventh.game.Game)
     */
    @Override
    public void update(TimeStep timeStep, GameInfo game) {
                
        
        /* lets do some random stuff for a while, this
         * helps keep things dynamic
         */
        if(this.timeUntilOrganizedAttack > 0) {
            this.timeUntilOrganizedAttack -= timeStep.getDeltaTime();
            
            giveOrders(new Random());
            return;
        }
        
        
        /* if no zone to attack, exit out */
        if(getZoneToAttack() == null) {
            return;
        }
        
            
        /* Determine if the zone to attack still has
         * Bomb Targets on it
         */
        if(getZoneToAttack().isTargetsStillActive()) {
            
            /* check to see if the bomb has been planted, if so set our state
             * to defend the area
             */                        
            if(isBombPlantedInZone(getZoneToAttack()) ) {                
                giveOrders(new Defend());
            }
            else {
                
                /* Send a message to the Agents we need to plant the bomb 
                 */                
                giveOrders(new PlantBomb());                            
            }            
    
        }
        else {
            
            /* no more bomb targets, calculate a new 
             * zone to attack...
             */
            setZoneToAttack(calculateZoneToAttack());
            if(getZoneToAttack() != null) {
                giveOrders(new Infiltrate());
            }
        }
        
        
        List<Player> players = team.getPlayers();
        for(int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            if(player.isBot() && player.isAlive()) {
                Brain brain = aiSystem.getBrain(player);
                
                // TODO: Let the brain tell us when we are ready
                // to accept new orders
                if(!brain.getCommunicator().hasPendingCommands()) {
                    // TODO
                    //System.out.println("Orders posted: " + currentState);
                    brain.getCommunicator().post(getCurrentAction(brain));
                }
            }
        }
    }

}
