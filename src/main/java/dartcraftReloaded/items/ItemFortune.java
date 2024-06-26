package dartcraftReloaded.items;

import dartcraftReloaded.Constants;
import dartcraftReloaded.DartcraftReloaded;
import dartcraftReloaded.handlers.GUIHandler;
import dartcraftReloaded.util.StringHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ItemFortune extends ItemBase {
    private static final String[] fortunes = new String[186];

    static {
        for (int i = 0; i <= 185; i++) {
            fortunes[i] = "text.dartcraftReloaded.fortune" + i;
        }
    }
    public ItemFortune() {
        super(Constants.FORTUNE);
        this.setCreativeTab(DartcraftReloaded.creativeTab);
        setMaxStackSize(1);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("Read me.");
        if (StringHelper.isShiftKeyDown()) {
            tooltip.add("Right-click, genius");
        }
    }


    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        NBTTagCompound nbt;
        if (stack.hasTagCompound()) {
            nbt = stack.getTagCompound();
        } else {
            nbt = new NBTTagCompound();
        }
        if(!nbt.hasKey("message")) {
            addMessage(stack, nbt);
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        playerIn.openGui(DartcraftReloaded.instance, GUIHandler.FORTUNE, worldIn, playerIn.getPosition().getX(), playerIn.getPosition().getY(), playerIn.getPosition().getZ());
        return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }



    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        player.openGui(DartcraftReloaded.instance, GUIHandler.FORTUNE, worldIn, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
        return EnumActionResult.PASS;
    }

    public static void addMessage(ItemStack stack, NBTTagCompound nbt) {
        Random generator = new Random();
        int rand = generator.nextInt(fortunes.length);
        nbt.setString("message", fortunes[rand]);
        stack.setTagCompound(nbt);
    }
}
