package dartcraftReloaded.events;

import dartcraftReloaded.Constants;
import dartcraftReloaded.capablilities.Modifiable.IModifiable;
import dartcraftReloaded.handlers.CapabilityHandler;
import dartcraftReloaded.handlers.PotionHandler;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class attackEntityEvent {

    @SubscribeEvent
    public void AttackEntityEvent(AttackEntityEvent event) {
        if(event.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = event.getEntityPlayer();
            if (player.getHeldItemMainhand().hasCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null)) {

                IModifiable cap = player.getHeldItemMainhand().getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null);
                if (cap.hasModifier(Constants.FORCE)) {
                    int level = cap.getLevel(Constants.FORCE);
                    float rotationYaw = event.getEntityPlayer().rotationYaw;
                    if (event.getTarget() instanceof EntityLivingBase) {
                        ((EntityLivingBase)event.getTarget()).knockBack(event.getEntityPlayer(), (float)level * 0.5F, MathHelper.sin(rotationYaw * 0.017453292F),(-MathHelper.cos(rotationYaw * 0.017453292F)));
                    }
                    else {
                        event.getTarget().addVelocity((-MathHelper.sin(rotationYaw * 0.017453292F) * (float) level * 0.5F), 0.1D,(MathHelper.cos(rotationYaw * 0.017453292F) * (float) level * 0.5F));
                    }

                }
                if (cap.hasModifier(Constants.HEAT)) {
                    int level = cap.getLevel(Constants.HEAT);
                    event.getTarget().setFire(4*level);
                }
                if (cap.hasModifier(Constants.BLEED)) {
                    if (event.getTarget() instanceof EntityLiving) {
                        EntityLiving liv = (EntityLiving) event.getTarget();
                        liv.addPotionEffect(new PotionEffect(PotionHandler.potionBleeding, 40*cap.getLevel(Constants.BLEED), cap.getLevel(Constants.BLEED)));
                    }
                }
            }
        }
    }
}
