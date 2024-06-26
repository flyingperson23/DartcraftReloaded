package dartcraftReloaded.items;

import dartcraftReloaded.Constants;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBottledWither extends ItemBase {


    public ItemBottledWither() {
        super(Constants.BOTTLED_WITHER, "Right-Click to release Wither");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            EntityWither wither = new EntityWither(worldIn);
            wither.setLocationAndAngles(pos.getX(), pos.getY() + 2.0, pos.getZ(), 0.0F, 0.0F);
            worldIn.spawnEntity(wither);
        }
        player.getHeldItem(hand).shrink(1);
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
}
