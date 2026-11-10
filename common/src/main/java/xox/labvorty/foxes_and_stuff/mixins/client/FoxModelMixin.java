package xox.labvorty.foxes_and_stuff.mixins.client;

import net.minecraft.client.model.animal.fox.FoxModel;
import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import xox.labvorty.foxes_and_stuff.mixin_helpers.FoxModelAccessor;

@Mixin(FoxModel.class)
public interface FoxModelMixin extends FoxModelAccessor {
    @Override
    @Accessor("tail")
    public ModelPart foxesstuff$getTail();

    @Override
    @Accessor("body")
    public ModelPart foxesstuff$getBody();
}
