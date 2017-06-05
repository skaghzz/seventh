package seventh.ai.basic.teamstrategy.ObjectiveTeam;

import seventh.ai.basic.Brain;
import seventh.ai.basic.actions.Action;

public class PlantBomb extends OffensiveState{

   @Override
   public String name() {
      return "PLANT_BOMB";
   }

   @Override
   public Action getCurrentAction(Brain brain, AllObjectiveTeamStrategy OTS) {
      return OTS.getGoals().plantBomb();
   }
  
}
