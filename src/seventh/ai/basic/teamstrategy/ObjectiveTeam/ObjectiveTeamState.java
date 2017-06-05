package seventh.ai.basic.teamstrategy.ObjectiveTeam;

import seventh.ai.basic.Brain;
import seventh.ai.basic.actions.Action;

public abstract class ObjectiveTeamState {
   public abstract String name();
   public Action getCurrentAction(Brain brain, AllObjectiveTeamStrategy OTS){
      if(OTS.getZoneToAttack() != null) {
         return OTS.getGoals().infiltrate(OTS.getZoneToAttack());
     }
      return null;
   }
}