package ioann.uwu.aeronautics_additions;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.eriksonn.aeronautics.registry.AeroRegistrate;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;

@Mod(AeronauticsAdditions.MOD_ID)
public class AeronauticsAdditions {
    public static final String MOD_ID = "aeronautics_additions";
    public static final String MOD_NAME = "Aeronautics Additions";

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final NonNullSupplier<CreateRegistrate> REGISTRATE = NonNullSupplier.lazy(
            () -> CreateRegistrate.create(MOD_ID).defaultCreativeTab(MOD_ID).build()
    );

    public static CreateRegistrate getRegistrate() {
        return REGISTRATE.get();
    }

    public AeronauticsAdditions(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        REGISTRATE.get().registerEventListeners(modEventBus);

        AAItems.init();
        AABlocks.init();
        AABlockEntityTypes.init();

        AAPartialModels.init();

        NeoForge.EVENT_BUS.register(this);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
}
