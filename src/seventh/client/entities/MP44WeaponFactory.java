package seventh.client.entities;

import seventh.client.weapon.ClientMP44;
import seventh.client.weapon.ClientWeapon;

public class MP44WeaponFactory extends ClientWeaponFactory {
	protected ClientWeapon createWeapon(ClientPlayerEntity entity){
		return new ClientMP44(entity);
	}
}
