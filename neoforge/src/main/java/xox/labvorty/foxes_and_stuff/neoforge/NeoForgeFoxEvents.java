package xox.labvorty.foxes_and_stuff.neoforge;

import net.minecraft.world.InteractionResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import xox.labvorty.foxes_and_stuff.FoxesStuffMod;
import xox.labvorty.foxes_and_stuff.data.events.FoxEvents;

@EventBusSubscriber(modid = FoxesStuffMod.MOD_ID)
public final class NeoForgeFoxEvents {
    private NeoForgeFoxEvents() {}

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (FoxEvents.handleEntityInteraction(event.getEntity(), event.getTarget(), event.getHand())) {
            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
        }
    }
}
