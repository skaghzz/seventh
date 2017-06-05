package seventh.client.entities.vehicles;

import seventh.client.ClientGame;
import seventh.math.Vector2f;

public abstract class TankFactory {
	public ClientTank create(ClientGame game, Vector2f pos){
		ClientTank tank = createTank(game, pos);
		return tank;
	}
	
	protected abstract ClientTank createTank(ClientGame game, Vector2f pos);
}
