package com.example.examplemod.RenderLib;

import com.example.examplemod.ExampleMod;
import sun.tools.jar.resources.jar;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JarLoader {

    public String getLoaderLocation() {
        return System.getProperty("java.io.tmpdir") + ExampleMod.MODID;
    }

    // This method is copying the needed resource files from the jar onto the temp directory of the user, so that the files can then be used by RenderLib.

    public File getFileFromResource(String path, String name, String mtlName, String textureName) {
        String loaderLocation = getLoaderLocation();
        try {
            deleteLoaderCache(new File(loaderLocation));
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream modelIS = classLoader.getResourceAsStream(path + name);
            InputStream modelMTL = classLoader.getResourceAsStream(path + mtlName);
            InputStream modelTEXTURE = classLoader.getResourceAsStream(path + textureName);

            if (modelIS == null) {
                throw new IllegalArgumentException("model not found! " + path + name);
            }
            if (modelMTL == null) {
                throw new IllegalArgumentException("mtl file not found! " + path + name);
            }
            if (modelTEXTURE == null) {
                throw new IllegalArgumentException("texture not found! " + path + name);
            }

            try {
                try {
                    new File(loaderLocation).mkdir();
                } catch (Exception ignored) {}
                // Model
                System.out.println("JarLoader: Using path [" + loaderLocation + "\\" + name + "] for model file \"" + name + "\"");
                Files.copy(modelIS, Paths.get(loaderLocation + "\\" + name));
                // MTL
                System.out.println("JarLoader: Using path [" + loaderLocation + "\\" + mtlName + "] for mtl file \"" + mtlName + "\"");
                Files.copy(modelMTL, Paths.get(loaderLocation + "\\" + mtlName));
                // TEXTURE
                System.out.println("JarLoader: Using path [" + loaderLocation + "\\" + textureName + "] for texture file \"" + textureName + "\"");
                Files.copy(modelTEXTURE, Paths.get(loaderLocation + "\\" + textureName));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new File(loaderLocation + "\\" + name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void deleteLoaderCache(File location)
    {
        try {
            if (location.isDirectory())
            {
                String[] children = location.list();
                for (int i=0; i<children.length; i++)
                    deleteLoaderCache(new File(location, children[i]));
            }
        } catch (Exception e) {
            ExampleMod.logger.warn("Could not delete jar loader cache!");
            e.printStackTrace();
        }
    }

}
