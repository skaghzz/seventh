package seventh.client.entities;

import seventh.client.weapon.ClientThompson;
import seventh.client.weapon.ClientWeapon;

public class ThompsonWeaponFactory extends ClientWeaponFactory {
	protected ClientWeapon createWeapon(ClientPlayerEntity entity){
		return new ClientThompson(entity);
	}
}
