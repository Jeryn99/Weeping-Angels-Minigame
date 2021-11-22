package craig.software.mc.angelsgame.client;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.Input;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEvents {

	public static boolean isSeenTest = true;
	
    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Pre renderPlayerEvent){
        Player player = renderPlayerEvent.getPlayer();
        PlayerRenderer renderer = renderPlayerEvent.getRenderer();
        PlayerModel<AbstractClientPlayer> playerModel = renderer.getModel();

        RenderWeepingAngel.render(player, renderer, renderPlayerEvent.getBuffers(), renderPlayerEvent.getMatrixStack(), renderPlayerEvent.getLight());
    }

    @SubscribeEvent
    public static void onInput(InputUpdateEvent  input) {
    	isSeenTest = true;
    	if(!isSeenTest) return;
        Input moveType = input.getMovementInput();
        moveType.right = false;
        moveType.left = false;
        moveType.down = false;
        moveType.jumping = false;
        moveType.forwardImpulse = 0.0F;
        moveType.shiftKeyDown = false;
        moveType.leftImpulse = 0.0F;
    }
    
    static float cameraPitch = 0;
    static float cameraRoll = 0;
    static float cameraYaw = 0;
    
    // TODO Figure out a serverside way, this works visually, but does not work on the server
    @SubscribeEvent
    public static void onView(CameraSetup camera) {
    	if(!isSeenTest) {
    		cameraPitch = camera.getPitch();
    		cameraYaw = camera.getYaw();
    		cameraRoll = camera.getRoll();
    	} else {
    		camera.setPitch(cameraPitch);
    		camera.setRoll(cameraRoll);
    		camera.setYaw(cameraYaw);
    	}
    }
    
    
}
