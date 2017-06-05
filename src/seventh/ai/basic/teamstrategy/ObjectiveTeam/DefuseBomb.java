package seventh.ai.basic.teamstrategy.ObjectiveTeam;

import seventh.ai.basic.Brain;
import seventh.ai.basic.actions.Action;

public class DefuseBomb extends DefensiveState{

   @Override
   public String name() {
      return "DEFUSE_BOMB";
   }

   @Override
   public Action getCurrentAction(Brain brain, AllObjectiveTeamStrategy OTS) {
      return OTS.getGoals().defuseBomb();    
   }
}
