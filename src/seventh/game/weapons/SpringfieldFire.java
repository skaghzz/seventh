package seventh.game.weapons;

import seventh.game.Game;
import seventh.game.entities.Entity;
import seventh.game.entities.Entity.Type;
import seventh.math.Vector2f;
import seventh.shared.SoundType;

public class SpringfieldFire extends Springfield implements WeaponFire{

	public SpringfieldFire(Game game, Entity owner) {
		super(game, owner);
		// TODO Auto-generated constructor stub
	}

	
	 public boolean beginFire() {
	        if ( canFire() ) {
	            game.emitSound(getOwnerId(), SoundType.SPRINGFIELD_FIRE, getPos());
	            game.emitSound(getOwnerId(), SoundType.SPRINGFIELD_RECHAMBER, getPos());
	            
	            newBullet(true);
	            bulletsInClip--;
	            weaponTime = 1300;
	            
	            setFireState(); 
	            return true;
	        }
	        else if (reloading) {
	            reloading = false;
	        }
	        else if (bulletsInClip <= 0 ) {
	            setFireEmptyState();            
	        }
	                
	        this.endFire = false;
	        return false;
	    }
	    
	    /* (non-Javadoc)
	     * @see palisma.game.Weapon#endFire()
	     */
	    @Override
	    public boolean endFire() {    
	        this.endFire = true;
	        return false;
	    }
	    
	    @Override
	    public boolean canFire() {    
	        return super.canFire() && this.endFire;
	    }

		@Override
		protected Vector2f calculateVelocity(Vector2f facing) {
			// TODO Auto-generated method stub
			return null;
		}
	    
	    /* (non-Javadoc)
	     * @see palisma.game.Weapon#calculateVelocity(palisma.game.Direction)
	     */
	  
}
