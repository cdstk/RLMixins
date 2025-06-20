package rlmixins.mixin.quark;

import baubles.api.BaublesApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rlmixins.layer.LayerWitchHat;
import vazkii.quark.base.module.Feature;
import vazkii.quark.vanity.feature.WitchHat;

import java.util.Map;

@Mixin(WitchHat.class)
public abstract class FeatureWitchHatMixin extends Feature {

    @Shadow(remap = false)
    public static Item witch_hat;

    @Inject(
            method = "hasWitchHat",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;getItemStackFromSlot(Lnet/minecraft/inventory/EntityEquipmentSlot;)Lnet/minecraft/item/ItemStack;"),
            cancellable = true
    )
    private static void rlmixins_quarkWitchHat_hasWitchHat(EntityLiving attacker, EntityLivingBase target, CallbackInfoReturnable<Boolean> cir){
        if(target instanceof EntityPlayer) {
            int witchHatFound = BaublesApi.isBaubleEquipped((EntityPlayer)target, witch_hat);
            if (witchHatFound != -1) cir.setReturnValue(true);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initClient(){
        rLMixins$addRenderLayers();
    }

    @SideOnly(Side.CLIENT)
    @Unique
    private static void rLMixins$addRenderLayers() {
        Map<String, RenderPlayer> skinMap = Minecraft.getMinecraft().getRenderManager().getSkinMap();
        rLMixins$addLayersToSkin(skinMap.get("default"), false);
        rLMixins$addLayersToSkin(skinMap.get("slim"), true);
    }

    @SideOnly(Side.CLIENT)
    @Unique
    private static void rLMixins$addLayersToSkin(RenderPlayer renderPlayer, boolean slim) {
        renderPlayer.addLayer(new LayerWitchHat(renderPlayer));
    }
}
