package seventh.game.weapons;

import seventh.game.Game;
import seventh.game.entities.Entity;
import seventh.game.entities.Entity.Type;
import seventh.math.Vector2f;
import seventh.shared.SoundType;

public class ThompsonFire  extends Weapon implements WeaponFire {

	 public ThompsonFire(Game game, Entity owner, Type type) {
		super(game, owner, type);
		// TODO Auto-generated constructor stub
	}
	@Override
	    public boolean beginFire() {
	        if(canFire()) {
	            newBullet();
	            game.emitSound(getOwnerId(), SoundType.THOMPSON_FIRE, getPos());
	            
	            int roundsPerSecond = 7;
				weaponTime = 1000/roundsPerSecond ;
	            bulletsInClip--;
	            
	            setFireState(); 
	            return true;
	        }
	        else if (bulletsInClip <= 0 ) {                
	            setFireEmptyState();            
	        }

	        return false;
	    }
	@Override
	public boolean endFire() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canFire() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected Vector2f calculateVelocity(Vector2f facing) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
