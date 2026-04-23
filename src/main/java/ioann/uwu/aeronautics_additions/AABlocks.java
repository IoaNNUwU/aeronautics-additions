package ioann.uwu.aeronautics_additions;

import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.data.*;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.simulated_team.simulated.data.SimBlockStateGen;
import dev.simulated_team.simulated.index.SimBlocks;
import dev.simulated_team.simulated.index.SimTags;
import ioann.uwu.aeronautics_additions.blocks.turbine.TurbineBlock;
import ioann.uwu.aeronautics_additions.blocks.redstone_cable_connector.RedstoneCableConnectorBlock;
import ioann.uwu.aeronautics_additions.datagen.AADatagen;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class AABlocks {

    private static final CreateRegistrate REGISTRATE = AeronauticsAdditions.getRegistrate();

    public static final BlockEntry<TurbineBlock> TURBINE = REGISTRATE.block("turbine", TurbineBlock::new)
            .initialProperties(SharedProperties::stone)
            .transform(TagGen.pickaxeOnly())
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

    public static final BlockEntry<RedstoneCableConnectorBlock> REDSTONE_CABLE_CONNECTOR = REGISTRATE.block("redstone_cable_connector", RedstoneCableConnectorBlock::new)
            .initialProperties(SharedProperties::stone)
            .blockstate(AADatagen::directionalConnectorBlock)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.MINEABLE_WITH_AXE, AllTags.AllBlockTags.BRITTLE.tag, SimTags.Blocks.SUPER_LIGHT)
            // .blockstate(BlockStateGen.directionalBlockProvider(true))
            .recipe((ctx, recipe) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ctx.get(), 1)
                    .requires(Items.REDSTONE)
                    .requires(SimBlocks.ROPE_CONNECTOR)
                    .unlockedBy("has_ingredient", RegistrateRecipeProvider.has(SimBlocks.ROPE_CONNECTOR))
                    .save(recipe)
            )
            .item()
            .transform(ModelGen.customItemModel())
            .register();

    public static void init() {
    }
}
