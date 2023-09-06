package com.sunset.discjockey.item;

import com.sunset.discjockey.client.gui.GUIUSBFlashDisk;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.loading.FMLEnvironment;

import static com.sunset.discjockey.DiscJockey.DEBUG_LOGGER;

public class ItemUSBFlashDisk extends Item {
    public ItemUSBFlashDisk() {
        super(ItemUSBFlashDisk.GetProperties());
    }

    public static Properties GetProperties() {
        return new Properties()
                .stacksTo(1);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        if (FMLEnvironment.dist == Dist.CLIENT && pLevel.isClientSide() && pPlayer.isShiftKeyDown()) {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                this.openGui(pPlayer.getItemInHand(pUsedHand));
            });
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }


    @OnlyIn(Dist.CLIENT)
    public void openGui(ItemStack itemStack) {
        Minecraft.getInstance().setScreen(new GUIUSBFlashDisk(itemStack));
    }

}
