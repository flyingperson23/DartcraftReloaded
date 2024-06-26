package dartcraftReloaded.capablilities.ExperienceTome;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
public class ExperienceTomeStorage implements Capability.IStorage<IExperienceTome> {

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IExperienceTome> capability, IExperienceTome instance, EnumFacing side) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("experience", instance.getExperienceValue());

        return nbt;
    }

    @Override
    public void readNBT(Capability<IExperienceTome> capability, IExperienceTome instance, EnumFacing side, NBTBase nbtIn) {
        if(nbtIn instanceof NBTTagCompound) {
            NBTTagCompound nbt = (NBTTagCompound) nbtIn;
            instance.setExperienceValue(nbt.getInteger("experience"));

        }
    }
}
