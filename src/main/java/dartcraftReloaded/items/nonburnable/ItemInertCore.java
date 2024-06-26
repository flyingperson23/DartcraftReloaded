package dartcraftReloaded.items.nonburnable;

import dartcraftReloaded.items.ItemBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemInertCore extends ItemBase {
    public ItemInertCore(String name) {
        super(name);
    }

    /* Non Flamable */

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        EntityItem entity = new EntityNonBurnableItem(world, location.posX, location.posY, location.posZ, itemstack);
        if (location instanceof EntityItem) {
            NBTTagCompound tag = new NBTTagCompound();
            location.writeToNBT(tag);
            entity.setPickupDelay(tag.getShort("PickupDelay"));
        }
        entity.motionX = location.motionX;
        entity.motionY = location.motionY;
        entity.motionZ = location.motionZ;
        return entity;
    }
}
