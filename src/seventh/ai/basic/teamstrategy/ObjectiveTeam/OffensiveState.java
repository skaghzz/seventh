package seventh.ai.basic.teamstrategy.ObjectiveTeam;

import seventh.ai.basic.Brain;
import seventh.ai.basic.actions.Action;

public abstract class OffensiveState extends ObjectiveTeamState{
   public Action getCurrentAction(Brain brain, OffenseObjectiveTeamStrategy OOT){
      if(OOT.getZoneToAttack() != null) {
         return OOT.getGoals().infiltrate(OOT.getZoneToAttack());
     }
      return null;
   }
}
