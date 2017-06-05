package seventh.ai.basic.teamstrategy.ObjectiveTeam;

import seventh.ai.basic.Brain;
import seventh.ai.basic.actions.Action;
import seventh.game.entities.BombTarget;

public class Defend extends ObjectiveTeamState{

   @Override
   public String name() {
      return "DEFEND";
   }

   @Override
   public Action getCurrentAction(Brain brain, AllObjectiveTeamStrategy OTS) {
      if(OTS.getZoneToAttack() != null) {
         BombTarget target = OTS.getPlantedBombTarget(OTS.getZoneToAttack());
         if(target!=null) {
             return OTS.getGoals().defendPlantedBomb(target);
         }
         else {
             return OTS.getGoals().defend(OTS.getZoneToAttack());
         }
     }
      return null;
   }
}
