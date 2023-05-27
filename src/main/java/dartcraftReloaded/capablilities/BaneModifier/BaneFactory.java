package dartcraftReloaded.capablilities.BaneModifier;

import net.minecraft.nbt.NBTTagCompound;

import java.util.concurrent.Callable;

/**
 * Created by BURN447 on 6/16/2018.
 */
public class BaneFactory implements Callable<IBaneModifier> {
    @Override
    public IBaneModifier call() throws Exception {
        return new IBaneModifier() {

            boolean canTeleport = true;

            @Override
            public boolean canTeleport() {
                return canTeleport;
            }

            @Override
            public void setTeleportAbility(boolean canTeleport) {
                this.canTeleport = canTeleport;
            }

            @Override
            public NBTTagCompound serializeNBT() {
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setBoolean("canTeleport", canTeleport);
                return nbt;
            }

            @Override
            public void deserializeNBT(NBTTagCompound nbt) {
                canTeleport = nbt.getBoolean("canTeleport");
            }
        };
    }
}
