package seventh.ai.basic.teamstrategy.ObjectiveTeam;

import java.util.List;

import seventh.ai.basic.Brain;
import seventh.ai.basic.DefaultAISystem;
import seventh.ai.basic.Stats;
import seventh.ai.basic.Zone;
import seventh.ai.basic.Zones;
import seventh.ai.basic.actions.Action;
import seventh.ai.basic.actions.Actions;
import seventh.ai.basic.teamstrategy.TeamStrategy;
import seventh.game.GameInfo;
import seventh.game.Player;
import seventh.game.PlayerInfo;
import seventh.game.Team;
import seventh.game.entities.BombTarget;
import seventh.shared.Randomizer;
import seventh.shared.TimeStep;
import seventh.shared.Debugable.DebugInformation;

public abstract class AllObjectiveTeamStrategy implements TeamStrategy{
   protected Team team;
   protected DefaultAISystem aiSystem;    
   
   protected Zones zones;
   protected Randomizer random;
   
   protected Zone zoneToAttack;
   
   protected Stats stats;
   protected ObjectiveTeamState currentState;
       
   protected Actions goals;
   protected long timeUntilOrganizedAttack;
   
   public AllObjectiveTeamStrategy(DefaultAISystem aiSystem, Team team) {
      this.aiSystem = aiSystem;
      this.team = team;
      
      this.stats = aiSystem.getStats();
      this.zones = aiSystem.getZones();
      this.random = aiSystem.getRandomizer();
      
      this.setGoals(aiSystem.getGoals()); 
   }
   
   /* (non-Javadoc)
    * @see seventh.shared.Debugable#getDebugInformation()
    */
   @Override
   public DebugInformation getDebugInformation() {
       DebugInformation me = new DebugInformation();
       me.add("zone_to_attack", this.getZoneToAttack())
         .add("time_to_attack", this.timeUntilOrganizedAttack)
         .add("state", this.currentState.name())
         ;
       return me;
   }

   /* (non-Javadoc)
    * @see seventh.ai.basic.teamstrategy.TeamStrategy#getTeam()
    */
   @Override
   public Team getTeam() {
       return this.team;
   }

   /* (non-Javadoc)
    * @see seventh.ai.basic.teamstrategy.TeamStrategy#getGoal(seventh.ai.basic.Brain)
    */
   @Override
   public Action getAction(Brain brain) {
       return getCurrentAction(brain);
   }

   /**
    * @param brain
    * @return the current marching orders
    */
   protected Action getCurrentAction(Brain brain) {
       Action action = null;
       
       if(this.getZoneToAttack()==null) {
           this.setZoneToAttack(calculateZoneToAttack());
       }
       action = currentState.getCurrentAction(brain, this);
       
       return action;
   }
   
   protected abstract Zone calculateZoneToAttack();
   
   @Override
   public void startOfRound(GameInfo game) {
      // TODO Auto-generated method stub
      
   }

   /* (non-Javadoc)
    * @see seventh.ai.basic.AIGameTypeStrategy#playerSpawned(seventh.game.PlayerInfo)
    */
   @Override
   public void playerSpawned(PlayerInfo player) {
       if(player.isBot()) {
           Brain brain = aiSystem.getBrain(player);
           
           Action action = getCurrentAction(brain);
           if(action != null) {
               brain.getCommunicator().post(action);
           }
       }    
   }
   

   /* (non-Javadoc)
    * @see seventh.ai.basic.AIGameTypeStrategy#playerKilled(seventh.game.PlayerInfo)
    */
   @Override
   public void playerKilled(PlayerInfo player) {
   }

   @Override
   public void update(TimeStep timeStep, GameInfo game) {
      // TODO Auto-generated method stub
      
   }
   
   /**
    * Gives all the available Agents orders
    */
   protected void giveOrders(ObjectiveTeamState state) {
       if(currentState.getClass() != state.getClass()) {
           currentState = state;
           
           List<Player> players = team.getPlayers();
           for(int i = 0; i < players.size(); i++) {
               Player player = players.get(i);
               if(player.isBot() && player.isAlive()) {
                   Brain brain = aiSystem.getBrain(player);
                   if( !brain.getMotion().isDefusing() ) {                    
                       brain.getCommunicator().post(getCurrentAction(brain));
                   }
               }
           }
       }
   }
   
   /**
    * @param zone
    * @return the {@link BombTarget} that has a bomb planted on it in the supplied {@link Zone}
    */
   public BombTarget getPlantedBombTarget(Zone zone) {
       List<BombTarget> targets = zone.getTargets();
       for(int i = 0; i < targets.size(); i++) {
           BombTarget target = targets.get(i);
           if(target.bombPlanting() || target.bombActive()) {
               return target;
           }
       }
       return null;
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString() {
       return getDebugInformation().toString();
   }

  public Zone getZoneToAttack() {
     return zoneToAttack;
  }

  public void setZoneToAttack(Zone zoneToAttack) {
     this.zoneToAttack = zoneToAttack;
  }

  public Actions getGoals() {
     return goals;
  }

  public void setGoals(Actions goals) {
     this.goals = goals;
  }
}
