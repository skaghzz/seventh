package seventh.client.entities;

import seventh.client.weapon.ClientMP40;
import seventh.client.weapon.ClientWeapon;

public class MP40WeaponFactory extends ClientWeaponFactory {
	protected ClientWeapon createWeapon(ClientPlayerEntity entity){
		return new ClientMP40(entity);
	}
}
