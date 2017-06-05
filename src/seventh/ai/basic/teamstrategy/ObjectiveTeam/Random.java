package seventh.ai.basic.teamstrategy.ObjectiveTeam;

import seventh.ai.basic.Brain;
import seventh.ai.basic.actions.Action;

public class Random extends ObjectiveTeamState{

   @Override
   public String name() {
      return "RANDOM";
   }

   @Override
   public Action getCurrentAction(Brain brain, AllObjectiveTeamStrategy OTS) {
      return OTS.getGoals().moveToRandomSpot(brain);
   }
}
