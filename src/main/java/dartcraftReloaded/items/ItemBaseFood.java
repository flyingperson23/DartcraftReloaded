package dartcraftReloaded.items;

import dartcraftReloaded.DartcraftReloaded;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class ItemBaseFood extends ItemFood {

    protected String name;

    public ItemBaseFood(String name, int amount, float saturation, boolean isWolfFood){
        super(amount, saturation, isWolfFood);
        setTranslationKey(name);
        setRegistryName(name);
        this.name = name;
        this.setCreativeTab(DartcraftReloaded.creativeTab);
        if(name.equals("cookie_fortune")){
            this.setAlwaysEdible();
            this.maxStackSize = 1;
        }
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (Objects.equals(name, "cookie_fortune")) {
            tooltip.add("Eat me");
        }
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
            entityplayer.getFoodStats().addStats(this, stack);
            worldIn.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
            this.onFoodEaten(stack, worldIn, entityplayer);
            entityplayer.addStat(StatList.getObjectUseStats(this));

            if (entityplayer instanceof EntityPlayerMP)
            {
                CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP)entityplayer, stack);
            }
            if(Objects.equals(name, "cookie_fortune")){
                entityplayer.addItemStackToInventory(new ItemStack(ModItems.fortune));
            }
            if(Objects.equals(name, "soul_wafer") && !worldIn.isRemote){
                this.randPotionEffect(entityplayer);
            }

        }
        stack.shrink(1);
        return stack;
    }

    public void registerItemModel() {
        DartcraftReloaded.proxy.registerItemRenderer(this, 0, name);
    }

    public ItemBaseFood randPotionEffect(EntityLivingBase entityLiving){
        Random rnd = new Random();
        int rand = rnd.nextInt(16);
        EntityPlayer entityplayer = (EntityPlayer)entityLiving;

        switch(rand){
            case 0:
                //Speed
                entityplayer.addPotionEffect(new PotionEffect(Potion.getPotionById(1), 1000));
                break;
            case 1:
                //Haste
                entityplayer.addPotionEffect(new PotionEffect(Potion.getPotionById(3), 1000));
                break;
            case 2:
                //Strength
                entityplayer.addPotionEffect(new PotionEffect(Potion.getPotionById(5), 1000));
                break;
            case 3:
                //Jump Boost
                entityplayer.addPotionEffect(new PotionEffect(Potion.getPotionById(8), 1000));
                break;
            case 4:
                //Regeneration
                entityplayer.addPotionEffect(new PotionEffect(Potion.getPotionById(10), 1000));
                break;
            case 5:
                //Resistance
                entityplayer.addPotionEffect(new PotionEffect(Potion.getPotionById(11), 1000));
                break;
            case 6:
                //Fire Resistance
                entityplayer.addPotionEffect(new PotionEffect(Potion.getPotionById(12), 1000));
                break;
            case 7:
                //Water Breathing
                entityplayer.addPotionEffect(new PotionEffect(Potion.getPotionById(13), 1000));
                break;
            case 8:
                //Invisibility
                entityplayer.addPotionEffect(new PotionEffect(Potion.getPotionById(14), 1000));
                break;
            case 9:
                //Night Vision
                entityplayer.addPotionEffect(new PotionEffect(Potion.getPotionById(16), 1000));
                break;
            case 10:
                //Health Boost
                entityplayer.addPotionEffect(new PotionEffect(Potion.getPotionById(21), 1000));
                break;
            case 11:
                //Absorption
                entityplayer.addPotionEffect(new PotionEffect(Potion.getPotionById(22), 1000));
                break;
            case 12:
                //Saturation
                entityplayer.addPotionEffect(new PotionEffect(Potion.getPotionById(23), 1000));
                break;
            case 13:
                //Glowing
                entityplayer.addPotionEffect(new PotionEffect(Potion.getPotionById(24), 1000));
                break;
            case 14:
                //Levitation
                entityplayer.addPotionEffect(new PotionEffect(Potion.getPotionById(25), 1000));
                break;
            case 15:
                //Luck
                entityplayer.addPotionEffect(new PotionEffect(Potion.getPotionById(26), 1000));
                break;
        }
        return this;
    }

}
