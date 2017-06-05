package seventh.ai.basic.teamstrategy.ObjectiveTeam;

import seventh.ai.basic.Brain;
import seventh.ai.basic.actions.Action;

public abstract class ObjectiveTeamState {
   public abstract String name();
   public abstract Action getCurrentAction(Brain brain, AllObjectiveTeamStrategy OTS);
   //public abstract Action getCurrentAction(Brain brain, OffenseObjectiveTeamStrategy OOT);
}