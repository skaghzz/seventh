/*
 * see license.txt 
 */
package seventh.game.weapons;

import leola.vm.types.LeoMap;
import seventh.game.Game;
import seventh.game.entities.Entity;
import seventh.game.entities.Entity.Type;
import seventh.math.Vector2f;
import seventh.shared.SoundType;
import seventh.shared.WeaponConstants;

/**
 * @author Tony
 *
 */
public class Thompson extends Weapon {

    private int roundsPerSecond;
    private final int spread;
    
    /**
     * @param game
     * @param owner
     */
    public Thompson(Game game, Entity owner) {
        super(game, owner, Type.THOMPSON);
        
        this.roundsPerSecond = 7;
        this.damage = 21;
        this.reloadTime = 1600;
        this.clipSize = 30; 
        this.totalAmmo = 180;
        this.spread = 8;
        this.bulletsInClip = this.clipSize;        
        this.lineOfSight = WeaponConstants.THOMPSON_LINE_OF_SIGHT;
        this.weaponWeight = WeaponConstants.THOMPSON_WEIGHT;
        
        applyScriptAttributes("thompson");        
        this.bulletsInClip = this.clipSize;
        setWeaponFire(new ThompsonFire(game, owner));
    }
    

    /* (non-Javadoc)
     * @see seventh.game.weapons.Weapon#applyScriptAttributes(java.lang.String)
     */
    @Override
    protected LeoMap applyScriptAttributes(String weaponName) {
        LeoMap attributes = super.applyScriptAttributes(weaponName);
        if(attributes!=null) {
            this.roundsPerSecond = attributes.getInt("rounds_per_second");
        }
        return attributes;
    }


    /* (non-Javadoc)
     * @see seventh.game.Weapon#reload()
     */
    @Override
    public boolean reload() {    
        boolean reloaded = super.reload();
        if(reloaded) {
            game.emitSound(getOwnerId(), SoundType.THOMPSON_RELOAD, getPos());
        }
        
        return reloaded;
    }
    
    /* (non-Javadoc)
     * @see palisma.game.Weapon#beginFire()
     */
    @Override
    public boolean beginFire() {
        getWeaponFire().beginFire();
        return false;
    }
    
    /* (non-Javadoc)
     * @see palisma.game.Weapon#calculateVelocity(palisma.game.Direction)
     */
    @Override
    protected Vector2f calculateVelocity(Vector2f facing) {    
        return spread(facing, spread);
    }
        
}
