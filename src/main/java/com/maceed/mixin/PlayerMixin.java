package com.maceed.mixin;

import com.maceed.HitboxConfig;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {

	@Inject(method = "getDefaultDimensions", at = @At("RETURN"), cancellable = true)
	private void maceed$scaleDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> cir) {
		cir.setReturnValue(cir.getReturnValue().scale(HitboxConfig.width, HitboxConfig.height));
	}
}
