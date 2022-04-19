package com.example.examplemod.RenderLib;

import friedrichlp.renderlib.library.RenderEffect;
import friedrichlp.renderlib.model.ModelLoaderProperty;
import friedrichlp.renderlib.tracking.Model;
import friedrichlp.renderlib.tracking.ModelInfo;
import friedrichlp.renderlib.tracking.ModelManager;
import scala.tools.nsc.io.Jar;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class ModelInfos {

    public static ModelInfo EXAMPLE_MODEL;


    public static void loadModelInfos() {

        // IDE
        EXAMPLE_MODEL = ModelManager.registerModel(new File(Model.class.getClassLoader().getResource("assets/examplemod/renderlib/models/barrel/test.obj").getFile()), new ModelLoaderProperty(0.0f));
        //Jar
        //EXAMPLE_MODEL = ModelManager.registerModel(new JarLoader().getFileFromResource("assets/examplemod/renderlib/models/", "model.obj", "model.mtl", "model.png"), new ModelLoaderProperty(0.0f));

        EXAMPLE_MODEL.addRenderEffect(RenderEffect.NORMAL_LIGHTING);
    }

    public static boolean runningFromIntelliJ()
    {
        String classPath = System.getProperty("java.class.path");
        return classPath.contains("idea_rt.jar");
    }

}
