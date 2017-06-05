package seventh.client.entities;

import seventh.client.weapon.ClientRisker;
import seventh.client.weapon.ClientWeapon;

public class RiskerWeaponFactory extends ClientWeaponFactory{
	protected ClientWeapon createWeapon(ClientPlayerEntity entity){
		return new ClientRisker(entity);
	}
}
