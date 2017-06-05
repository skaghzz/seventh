package seventh.ai.basic.teamstrategy.ObjectiveTeam;

import seventh.ai.basic.Brain;
import seventh.ai.basic.actions.Action;

public class Infiltrate extends OffensiveState{

   @Override
   public String name() {
      return "INFILTRATE";
   }

   @Override
   public Action getCurrentAction(Brain brain, AllObjectiveTeamStrategy OTS) {
      if(OTS.getZoneToAttack() != null) {
         return OTS.getGoals().infiltrate(OTS.getZoneToAttack());
     }
      return OTS.getGoals().moveToRandomSpot(brain);
   }
   
}
