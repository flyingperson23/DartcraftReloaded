package burn447.dartcraftReloaded.capablilities.ToolModifier;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class ToolModStorage implements Capability.IStorage<IToolModifier> {


    public ToolModStorage(){
    }

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IToolModifier> capability, IToolModifier instance, EnumFacing side) {
        NBTTagCompound nbt = new NBTTagCompound();
        //Speed
        nbt.setInteger("speed", instance.getSpeedLevel());

        //Heat
        nbt.setBoolean("heat", instance.hasHeat());

        //Force
        nbt.setInteger("force", instance.getForceLevel());
        return nbt;
    }

    @Override
    public void readNBT(Capability<IToolModifier> capability, IToolModifier instance, EnumFacing side, NBTBase nbtIn) {
        if(nbtIn instanceof NBTTagCompound){
            NBTTagCompound nbt = (NBTTagCompound) nbtIn;
            instance.setSpeed(nbt.getInteger("speed"));
            instance.setHeat(nbt.getBoolean("heat"));
            instance.setForce(nbt.getInteger("force"));
        }
    }


}
