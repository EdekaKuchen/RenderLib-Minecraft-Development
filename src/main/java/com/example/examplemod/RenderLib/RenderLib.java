package com.example.examplemod.RenderLib;

import com.example.examplemod.ExampleMod;
import friedrichlp.renderlib.RenderLibRegistry;
import friedrichlp.renderlib.RenderLibSettings;
import friedrichlp.renderlib.library.RenderMode;
import friedrichlp.renderlib.math.Matrix4f;
import friedrichlp.renderlib.math.Vector3;
import friedrichlp.renderlib.tracking.RenderLayer;
import friedrichlp.renderlib.tracking.RenderManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MinecraftError;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class RenderLib {

    public static void setup() {
        GLSetup();
        CacheHandler.CacheSetup();
        ModelInfos.loadModelInfos();
        RenderManager.setRenderDistance(1000);
    }

    public static void render(RenderLayer layer, RenderMode renderMode) {
        GlStateManager.pushMatrix();
        RenderManager.render(layer, renderMode);
        GlStateManager.popMatrix();
    }

    public static double getPlayerGravity() {
        if (Minecraft.getMinecraft().player.hasNoGravity()) {
            return 0;
        } else return 0.0784000015258789;
    }

    public static void updateCamPos() {
        //Vec3d cameraPos = new Vec3d(
        //        Minecraft.getMinecraft().player.posX + (Minecraft.getMinecraft().player.motionX * Minecraft.getMinecraft().getRenderPartialTicks()),
        //        Minecraft.getMinecraft().player.posY - ((Minecraft.getMinecraft().player.motionY + getPlayerGravity())) / 2,
        //        Minecraft.getMinecraft().player.posZ + (Minecraft.getMinecraft().player.motionZ * Minecraft.getMinecraft().getRenderPartialTicks())
        //);
        Vec3d cameraPos = Minecraft.getMinecraft().player.getPositionEyes(Minecraft.getMinecraft().getRenderPartialTicks());
        RenderManager.setCameraPos(new Vector3((float) cameraPos.x, (float) cameraPos.y, (float) cameraPos.z));
        RenderLibSettings.Rendering.CAMERA_HEIGHT_OFFSET = 0;
    }

    public static void GLSetup() {
        try {
            RenderLibRegistry.Compatibility.MODEL_VIEW_PROJECTION_PROVIDER = () -> {
                FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
                FloatBuffer projection = BufferUtils.createFloatBuffer(16);
                for(int i = 0; i < 16; i++) {
                    modelView.put(0);
                    projection.put(0);
                }
                modelView.flip();
                projection.flip();
                GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelView);
                GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
                return new Matrix4f(modelView).multiply(new Matrix4f(projection));
            };
            RenderLibRegistry.Compatibility.MODEL_VIEW_PROVIDER = () -> {
                FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
                for(int i = 0; i < 16; i++) modelView.put(0);
                modelView.flip();
                GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelView);
                return new Matrix4f(modelView);
            };
            RenderLibRegistry.Compatibility.PROJECTION_PROVIDER = () -> {
                FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
                for(int i = 0; i < 16; i++) modelView.put(0);
                modelView.flip();
                GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, modelView);
                return new Matrix4f(modelView);
            };
            RenderLibRegistry.Compatibility.GL_SET_MATRIX_PARAMETER = (program, loc, buf) -> ARBShaderObjects.glUniformMatrix4ARB(loc, false, buf);
            RenderLibRegistry.Compatibility.GL_GET_VERTEX_ATTRIB = (index, name) -> {
                IntBuffer params = BufferUtils.createIntBuffer(4);
                GL20.glGetVertexAttrib(index, name, params);
                return params.get(0);
            };
            RenderLibRegistry.Compatibility.GL_HAS_CONTEXT = () -> {
                try {
                    GLContext.getCapabilities();
                    return true;
                } catch (IllegalStateException e) {
                    return false;
                }
            };
        } catch (Exception e) {
            ExampleMod.logger.warn("Could not setup OpenGL for 1.12.2!");
            e.printStackTrace();
        }
    }

}
