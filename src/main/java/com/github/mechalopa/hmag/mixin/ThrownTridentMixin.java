package com.github.mechalopa.hmag.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.mechalopa.hmag.world.entity.EnderExecutorEntity;

import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.phys.EntityHitResult;

@Mixin(ThrownTrident.class)
public abstract class ThrownTridentMixin
{
	@Inject(method = "onHitEntity(Lnet/minecraft/world/phys/EntityHitResult;)V", at = @At(value = "INVOKE", target="Lnet/minecraft/world/entity/Entity;getType()Lnet/minecraft/world/entity/EntityType;"), cancellable = true)
	public void hmag_onHitEntity(EntityHitResult result, CallbackInfo ci)
	{
		if (result.getEntity() instanceof EnderExecutorEntity)
		{
			ci.cancel();
		}
	}
}