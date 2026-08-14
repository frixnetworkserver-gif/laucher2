package com.frix.launcher.mods;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages mod downloading and installation
 */
public class ModManager {
    
    private static final Logger logger = LoggerFactory.getLogger(ModManager.class);
    private static final HttpClient httpClient = HttpClients.createDefault();
    
    private String modsDirectory;
    
    public ModManager(String modsDirectory) {
        this.modsDirectory = modsDirectory;
        ensureDirectoryExists();
    }
    
    /**
     * Ensure mods directory exists
     */
    private void ensureDirectoryExists() {
        try {
            Files.createDirectories(Paths.get(modsDirectory));
            logger.info("Mods directory ensured at: " + modsDirectory);
        } catch (IOException e) {
            logger.error("Error creating mods directory", e);
        }
    }
    
    /**
     * Download mod from URL
     */
    public boolean downloadMod(String modUrl, String modName) {
        try {
            logger.info("Downloading mod: " + modName);
            
            ClassicHttpRequest request = ClassicRequestBuilder.get(modUrl).build();
            
            httpClient.execute(request, response -> {
                String filePath = modsDirectory + File.separator + modName;
                try (InputStream inputStream = response.getEntity().getContent();
                     FileOutputStream outputStream = new FileOutputStream(filePath)) {
                    
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    
                    logger.info("Mod downloaded successfully: " + modName);
                } catch (IOException e) {
                    logger.error("Error writing mod file", e);
                }
                return response;
            });
            
            return true;
        } catch (IOException e) {
            logger.error("Error downloading mod: " + modName, e);
            return false;
        }
    }
    
    /**
     * Get list of installed mods
     */
    public List<String> getInstalledMods() {
        List<String> mods = new ArrayList<>();
        try {
            File dir = new File(modsDirectory);
            File[] files = dir.listFiles((d, name) -> name.endsWith(".jar"));
            
            if (files != null) {
                for (File file : files) {
                    mods.add(file.getName());
                }
            }
            logger.info("Found " + mods.size() + " installed mods");
        } catch (Exception e) {
            logger.error("Error listing installed mods", e);
        }
        return mods;
    }
    
    /**
     * Remove mod
     */
    public boolean removeMod(String modName) {
        try {
            String filePath = modsDirectory + File.separator + modName;
            Files.delete(Paths.get(filePath));
            logger.info("Mod removed: " + modName);
            return true;
        } catch (IOException e) {
            logger.error("Error removing mod: " + modName, e);
            return false;
        }
    }
    
    /**
     * Clear all mods
     */
    public void clearAllMods() {
        try {
            File dir = new File(modsDirectory);
            File[] files = dir.listFiles((d, name) -> name.endsWith(".jar"));
            
            if (files != null) {
                for (File file : files) {
                    Files.delete(file.toPath());
                }
            }
            logger.info("All mods cleared");
        } catch (IOException e) {
            logger.error("Error clearing mods", e);
        }
    }
}
