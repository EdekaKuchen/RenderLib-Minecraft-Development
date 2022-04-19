package com.example.examplemod.RenderLib;

import com.example.examplemod.ExampleMod;
import friedrichlp.renderlib.RenderLibSettings;

import java.io.File;
import java.io.IOException;

public class CacheHandler {

    public static void CacheSetup() {
        try {
            String path = new File(".").getCanonicalPath() + "\\config\\RenderLib\\" + ExampleMod.MODID + "\\cache";
            System.out.println("Setting cache location: " + path);
            File cacheFolder = new File(path);
            deleteCache(cacheFolder);
            RenderLibSettings.Caching.CACHE_LOCATION = path;
            RenderLibSettings.Caching.CACHE_VERSION = "1";

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteCache(File cacheLocation)
    {
        try {
            if (cacheLocation.isDirectory())
            {
                String[] children = cacheLocation.list();
                for (int i=0; i<children.length; i++)
                    deleteCache(new File(cacheLocation, children[i]));
            }
        } catch (Exception e) {
            ExampleMod.logger.warn("Could not delete old cache!");
            e.printStackTrace();
        }
    }

}
