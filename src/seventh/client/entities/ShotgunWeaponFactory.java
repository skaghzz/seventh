package seventh.client.entities;

import seventh.client.weapon.ClientShotgun;
import seventh.client.weapon.ClientWeapon;

public class ShotgunWeaponFactory extends ClientWeaponFactory{
	protected ClientWeapon createWeapon(ClientPlayerEntity entity){
		return new ClientShotgun(entity);
	}
}
