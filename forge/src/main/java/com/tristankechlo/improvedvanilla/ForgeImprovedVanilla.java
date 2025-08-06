package com.tristankechlo.improvedvanilla;

import com.tristankechlo.improvedvanilla.commands.ImprovedVanillaCommand;
import com.tristankechlo.improvedvanilla.config.ConfigManager;
import com.tristankechlo.improvedvanilla.eventhandler.CropRightClickHandler;
import com.tristankechlo.improvedvanilla.eventhandler.EasyPlantingHandler;
import com.tristankechlo.improvedvanilla.eventhandler.MobDropHandler;
import com.tristankechlo.improvedvanilla.eventhandler.SpawnerHandler;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(ImprovedVanilla.MOD_ID)
public class ForgeImprovedVanilla {

    public ForgeImprovedVanilla() {
        // register event listeners
        PlayerInteractEvent.RightClickBlock.BUS.addListener(this::cropRightClicking);
        PlayerInteractEvent.RightClickBlock.BUS.addListener(this::easyPlanting);
        LivingDropsEvent.BUS.addListener(this::mobDropHandler);
        BlockEvent.BreakEvent.BUS.addListener(this::onSpawnerBroken);

        // register commands
        RegisterCommandsEvent.BUS.addListener(this::registerCommands);

        // setup configs
        ServerAboutToStartEvent.BUS.addListener(this::commonSetup);
    }

    // setup configs
    private void commonSetup(final ServerAboutToStartEvent event) {
        ConfigManager.loadAndVerifyConfig(event.getServer().registryAccess());
    }

    // register commands
    private void registerCommands(final RegisterCommandsEvent event) {
        ImprovedVanillaCommand.register(event.getDispatcher());
    }

    // right click crops to harvest
    private void cropRightClicking(final PlayerInteractEvent.RightClickBlock event) {
        InteractionResult result = CropRightClickHandler.onPlayerRightClickBlock(event.getEntity(), event.getLevel(), event.getHand(), event.getHitVec());
        event.setCancellationResult(result);
    }

    // easy planting
    private void easyPlanting(final PlayerInteractEvent.RightClickBlock event) {
        InteractionResult result = EasyPlantingHandler.onPlayerRightClickBlock(event.getEntity(), event.getLevel(), event.getHand(), event.getHitVec());
        event.setCancellationResult(result);
    }

    // drop spawn egg on entity death
    private void mobDropHandler(final LivingDropsEvent event) {
        MobDropHandler.onMobDeath(event.getEntity().level(), event.getEntity(), event.getSource());
    }

    // drop spawner and spawn-eggs on block break
    private void onSpawnerBroken(final BlockEvent.BreakEvent event) {
        SpawnerHandler.onSpawnerBreak((Level) event.getLevel(), event.getPlayer(), event.getPos(), event.getState());
    }

}
