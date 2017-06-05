package seventh.client.entities;

import seventh.client.weapon.ClientWeapon;

public abstract class ClientWeaponFactory {
	public ClientWeapon create(ClientPlayerEntity entity){
		ClientWeapon weapon = createWeapon(entity);
		return weapon;
	}
	
	protected abstract ClientWeapon createWeapon(ClientPlayerEntity entity);
}
