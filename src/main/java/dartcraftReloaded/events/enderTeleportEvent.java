package dartcraftReloaded.events;

import dartcraftReloaded.handlers.CapabilityHandler;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by BURN447 on 6/17/2018.
 */
public class enderTeleportEvent {

    @SubscribeEvent
    public void onEnderTeleportEvent(EnderTeleportEvent event){
        if(event.getEntity() instanceof EntityEnderman){
            EntityEnderman enderman = ((EntityEnderman) event.getEntity());
            if(!enderman.getCapability(CapabilityHandler.CAPABILITY_BANE, null).canTeleport()){
                event.setCanceled(true);
            }
        }
    }
}