package seventh.client.entities;

import seventh.client.weapon.ClientPistol;
import seventh.client.weapon.ClientWeapon;

public class PistolWeaponFactory extends ClientWeaponFactory {
	protected ClientWeapon createWeapon(ClientPlayerEntity entity){
		return new ClientPistol(entity);
	}
}
