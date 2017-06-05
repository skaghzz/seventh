package seventh.client.entities;

import seventh.client.weapon.ClientRocketLauncher;
import seventh.client.weapon.ClientWeapon;

public class RocketLauncherFactory extends ClientWeaponFactory {
	protected ClientWeapon createWeapon(ClientPlayerEntity entity){
		return new ClientRocketLauncher(entity);
	}
}
