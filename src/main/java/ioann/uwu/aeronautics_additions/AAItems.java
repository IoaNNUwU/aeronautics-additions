package ioann.uwu.aeronautics_additions;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.simulated_team.simulated.index.SimItems;
import ioann.uwu.aeronautics_additions.items.RedstoneCableItem;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

public class AAItems {
    private static final CreateRegistrate REGISTRATE = AeronauticsAdditions.getRegistrate();

    public static ItemEntry<RedstoneCableItem> REDSTONE_CABLE = REGISTRATE
            .item("redstone_cable", RedstoneCableItem::new)
            .recipe((ctx, recipe) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ctx.get(), 1)
                    .pattern("RSR")
                    .pattern("NSN")
                    .pattern("RSR")
                    .define('S', Tags.Items.STRINGS)
                    .define('N', Tags.Items.NUGGETS_IRON)
                    .define('R', Tags.Items.DUSTS_REDSTONE)
                    .unlockedBy("has_ingredient", RegistrateRecipeProvider.has(Tags.Items.DUSTS_REDSTONE))
                    .save(recipe)
            )
            .recipe((ctx, recipe) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ctx.get(), 1)
                    .requires(Items.REDSTONE, 4)
                    .requires(SimItems.ROPE_COUPLING, 1)
                    .unlockedBy("has_ingredient", RegistrateRecipeProvider.has(Tags.Items.DUSTS_REDSTONE))
                    .save(recipe)
            )
            .register();

    public static void init() {
    }
}
