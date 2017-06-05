package seventh.client.entities;

import seventh.client.weapon.ClientFlameThrower;
import seventh.client.weapon.ClientWeapon;

public class FlameThrowerFactory extends ClientWeaponFactory {
	protected ClientWeapon createWeapon(ClientPlayerEntity entity){
		return new ClientFlameThrower(entity);
	}
}
