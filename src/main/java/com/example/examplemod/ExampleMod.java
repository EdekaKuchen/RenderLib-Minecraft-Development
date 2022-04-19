package com.example.examplemod;

import com.example.examplemod.RenderLib.ModelInfos;
import com.example.examplemod.RenderLib.RenderLib;
import friedrichlp.renderlib.library.RenderMode;
import friedrichlp.renderlib.render.ViewBoxes;
import friedrichlp.renderlib.tracking.RenderLayer;
import friedrichlp.renderlib.tracking.RenderManager;
import friedrichlp.renderlib.tracking.RenderObject;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;

@Mod(modid = ExampleMod.MODID, name = ExampleMod.NAME, version = ExampleMod.VERSION)
@Mod.EventBusSubscriber(Side.CLIENT)
public class ExampleMod
{
    public static final String MODID = "examplemod";
    public static final String NAME = "Example Mod";
    public static final String VERSION = "1.0";

    public static Logger logger;

    public ExampleMod() {
        RenderLib.setup();
    }

    //// Example Rendering

    public static RenderObject object;
    public static RenderLayer layer = RenderManager.addRenderLayer(ViewBoxes.ALWAYS);

    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event) {
        object = layer.addRenderObject(ModelInfos.EXAMPLE_MODEL);
    }

    @SubscribeEvent
    public static void render(RenderWorldLastEvent event) {
        RenderLib.updateCamPos();
        object.transform.setPosition(0,7,0);
        object.transform.rotate(0,0.3f,0);

        RenderLib.render(layer, RenderMode.USE_CUSTOM_MATS);
    }

    ////

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {

    }
}
