package seventh.client.entities.vehicles;

import seventh.client.ClientGame;
import seventh.math.Vector2f;

public class ShermanTankFactory extends TankFactory{
    protected ClientTank createTank(ClientGame game, Vector2f pos){
    	return new ClientShermanTank(game, pos);
    }
}
