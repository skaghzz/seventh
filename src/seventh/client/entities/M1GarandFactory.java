package seventh.client.entities;

import seventh.client.weapon.ClientM1Garand;
import seventh.client.weapon.ClientWeapon;

public class M1GarandFactory extends ClientWeaponFactory {
	protected ClientWeapon createWeapon(ClientPlayerEntity entity){
		return new ClientM1Garand(entity);
	}
}
