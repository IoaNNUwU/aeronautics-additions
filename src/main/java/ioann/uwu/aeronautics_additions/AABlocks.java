package ioann.uwu.aeronautics_additions;

import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.data.*;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import ioann.uwu.aeronautics_additions.blocks.jet_engine.JetEngineBlock;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.level.block.SoundType;

public class AABlocks {

    private static final CreateRegistrate REGISTRATE = AeronauticsAdditions.getRegistrate();

    public static final BlockEntry<JetEngineBlock> JET_ENGINE = REGISTRATE.block("jet_engine", JetEngineBlock::new)
            .initialProperties(SharedProperties::wooden)
            .transform(TagGen.axeOrPickaxe())
            .properties(p -> p
                    .sound(SoundType.METAL)
                    .noOcclusion()
                    .dynamicShape()
            )
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .transform(ModelGen.customItemModel())
            .recipe((ctx, recipe) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ctx.get(), 1)
                    .pattern("#P#")
                    .pattern("#P#")
                    .pattern("#P#")
                    .define('P', AllItems.PROPELLER.get())
                    .define('#', AllItems.IRON_SHEET.get())
                    .unlockedBy("has_ingredient", RegistrateRecipeProvider.has(AllItems.PROPELLER.get()))
                    .save(recipe))
            .register();

    public static void init() {
    }
}
