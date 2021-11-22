package craig.software.mc.angelsgame.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import me.suff.mc.angels.client.models.entity.ModelDisasterAngel;
import me.suff.mc.angels.client.models.entity.WAModels;
import me.suff.mc.angels.client.poses.WeepingAngelPose;
import me.suff.mc.angels.common.entities.AngelEnums;
import me.suff.mc.angels.common.variants.AngelTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.Input;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderWeepingAngel {

    private static ModelDisasterAngel disasterAngel = new ModelDisasterAngel(Minecraft.getInstance().getEntityModels().bakeLayer(WAModels.ANGEL_DISASTER));

    public static void render(Player player, PlayerRenderer renderer, MultiBufferSource buffers, PoseStack matrixStack, int light) {
        matrixStack.pushPose();
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180));
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(180));
        WeepingAngelPose pose = WeepingAngelPose.ANGRY;
        
        PlayerModel<AbstractClientPlayer> playerModel = renderer.getModel();
        
        matrixStack.translate(0, -1.5, 0);
        disasterAngel.setAngelPose(pose);
        disasterAngel.setupAnim(null,0, 0, 0, 0, 0);
        
        if(!player.isShiftKeyDown()) {
        disasterAngel.head.copyFrom(playerModel.head);
        }
        
        playerModel.leftArm.copyFrom(disasterAngel.rightArm);

        playerModel.setAllVisible(false);
        
        if(ClientEvents.isSeenTest) {
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(player.yBodyRot));
        }
        
        disasterAngel.renderToBuffer(matrixStack, buffers.getBuffer(RenderType.entityTranslucent(disasterAngel.generateTex(pose, AngelTypes.NORMAL.get()))), light, OverlayTexture.NO_OVERLAY, 1F,1F,1F, 1);
        matrixStack.popPose();
    }
    

    
}
