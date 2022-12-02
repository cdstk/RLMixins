package rlmixins.mixin.lycanites.boundingbox;

import com.lycanitesmobs.core.entity.creature.EntityAsmodeus;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityAsmodeus.class)
public abstract class EntityAsmodeusRenderMixin {

    /**
     * Fix oversized render bounding box
     */
    @Inject(
            method = "getRenderBoundingBox",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    public void rlmixins_entityAsmodeus_getRenderBoundingBox(CallbackInfoReturnable<AxisAlignedBB> cir) {
        //if(ForgeConfigHandler.client.targetEntity.equals("asmodeus")) cir.setReturnValue(((EntityAsmodeus)(Object)this).getEntityBoundingBox().grow(ForgeConfigHandler.client.growX, ForgeConfigHandler.client.growY, ForgeConfigHandler.client.growZ).offset(0.0, ForgeConfigHandler.client.offsetY, 0.0));
        cir.setReturnValue(((EntityAsmodeus)(Object)this).getEntityBoundingBox().grow(7.0, 0.0, 7.0));
    }
}