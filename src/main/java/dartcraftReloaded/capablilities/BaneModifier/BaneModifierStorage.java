package dartcraftReloaded.capablilities.BaneModifier;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
public class BaneModifierStorage implements Capability.IStorage<IBaneModifier> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IBaneModifier> capability, IBaneModifier instance, EnumFacing side) {
        NBTTagCompound nbt = new NBTTagCompound();
        
        nbt.setBoolean("canDoAbility", instance.canDoAbility());
        return nbt;
    }

    @Override
    public void readNBT(Capability<IBaneModifier> capability, IBaneModifier instance, EnumFacing side, NBTBase nbtIn) {
        if(nbtIn instanceof NBTTagCompound){
            NBTTagCompound nbt = ((NBTTagCompound) nbtIn);

            instance.setAbility(nbt.getBoolean("canDoAbility"));
        }
    }
}
