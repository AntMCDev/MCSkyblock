package com.ant.mcskyblock.mixin;

import com.ant.mcskyblock.common.crafting.shaped.*;
import com.ant.mcskyblock.common.crafting.shapeless.*;
import com.ant.mcskyblock.common.crafting.CraftingHelper;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mixin(RecipeManager.class)
public class MixinRecipeManager {
    private static final List<IShapedRecipe> SHAPED_CRAFTING_RECIPES = List.of(
            new SporeBlossomRecipe(),
            new HeartOfTheSeaRecipe(),
            new EndPortalFrameRecipe(),
            new BundleRecipe(),
            new RedSandRecipe(),
            new CobWebRecipe()
    );
    private static final List<IShapelessRecipe> SHAPELESS_CRAFTING_RECIPES = List.of(
            new BrainCoralBlockRecipe(),
            new BubbleCoralBlockRecipe(),
            new FireCoralBlockRecipe(),
            new HornCoralBlockRecipe(),
            new TubeCoralBlockRecipe(),
            new KelpRecipe(),
            new NetherRackRecipe()
    );

    @Inject(at = @At("HEAD"), method = "apply")
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profilerFiller, CallbackInfo ci) {
        SHAPED_CRAFTING_RECIPES.stream().filter(IShapedRecipe::enabled).forEach(r -> map.put(r.getResourceLocation(), CraftingHelper.toJSON(r)));
        SHAPELESS_CRAFTING_RECIPES.stream().filter(IShapelessRecipe::enabled).forEach(r -> map.put(r.getResourceLocation(), CraftingHelper.toJSON(r)));
    }

    @Inject(at = @At("RETURN"), method = "getRecipeIds", cancellable = true)
    public void getRecipeIds(CallbackInfoReturnable<Stream<ResourceLocation>> cir) {
        List<ResourceLocation> resourceLocations = cir.getReturnValue().collect(Collectors.toList());
        SHAPED_CRAFTING_RECIPES.stream().filter(IShapedRecipe::enabled).forEach(r -> resourceLocations.add(r.getResourceLocation()));
        SHAPELESS_CRAFTING_RECIPES.stream().filter(IShapelessRecipe::enabled).forEach(r -> resourceLocations.add(r.getResourceLocation()));
        cir.setReturnValue(resourceLocations.stream());
    }

    @Inject(at = @At("RETURN"), method = "byKey", cancellable = true)
    public void byKey(ResourceLocation resourceLocation, CallbackInfoReturnable<Optional<? extends Recipe<?>>> cir) {
        if (cir.getReturnValue().isEmpty()) {
            Optional<IShapedRecipe> shapedRecipe = SHAPED_CRAFTING_RECIPES.stream().filter(r -> r.getResourceLocation().equals(resourceLocation)).findFirst();
            if (shapedRecipe.isPresent()) {
                cir.setReturnValue(Optional.ofNullable(RecipeManager.fromJson(resourceLocation, CraftingHelper.toJSON(shapedRecipe.get()))));
                return;
            }

            Optional<IShapelessRecipe> shapelessRecipe = SHAPELESS_CRAFTING_RECIPES.stream().filter(r -> r.getResourceLocation().equals(resourceLocation)).findFirst();
            if (shapelessRecipe.isPresent()) {
                cir.setReturnValue(Optional.ofNullable(RecipeManager.fromJson(resourceLocation, CraftingHelper.toJSON(shapelessRecipe.get()))));
                return;
            }
        }
    }
}
