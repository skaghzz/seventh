package seventh.client.entities;

import seventh.client.weapon.ClientSpringfield;
import seventh.client.weapon.ClientWeapon;

public class SpringfieldFactory extends ClientWeaponFactory {
	protected ClientWeapon createWeapon(ClientPlayerEntity entity){
		return new ClientSpringfield(entity);
	}
}
