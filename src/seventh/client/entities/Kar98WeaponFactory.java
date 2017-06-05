package seventh.client.entities;

import seventh.client.weapon.ClientKar98;
import seventh.client.weapon.ClientWeapon;

public class Kar98WeaponFactory extends ClientWeaponFactory {
	protected ClientWeapon createWeapon(ClientPlayerEntity entity){
		return new ClientKar98(entity);
	}
}
