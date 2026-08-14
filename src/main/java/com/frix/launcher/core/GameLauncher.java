package com.frix.launcher.core;

import com.frix.launcher.config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Core game launcher - handles launching Minecraft
 */
public class GameLauncher {
    
    private static final Logger logger = LoggerFactory.getLogger(GameLauncher.class);
    
    /**
     * Launch Minecraft with given parameters
     */
    public static boolean launch(String username, String accessToken, String uuid, String version) {
        try {
            logger.info("Preparing to launch Minecraft...");
            
            String javaPath = ConfigManager.getString("javaPath");
            String gameDir = ConfigManager.getString("gameDir");
            String modLoader = ConfigManager.getString("modLoader");
            
            List<String> command = buildLaunchCommand(
                javaPath, gameDir, username, accessToken, uuid, version, modLoader
            );
            
            logger.info("Launching command: " + String.join(" ", command));
            
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.directory(new java.io.File(gameDir));
            Process process = processBuilder.start();
            
            logger.info("Minecraft launched successfully with PID: " + process.pid());
            return true;
            
        } catch (IOException e) {
            logger.error("Error launching Minecraft", e);
            return false;
        }
    }
    
    /**
     * Build launch command
     */
    private static List<String> buildLaunchCommand(
            String javaPath, String gameDir, String username, 
            String accessToken, String uuid, String version, String modLoader) {
        
        List<String> command = new ArrayList<>();
        command.add(javaPath);
        
        // JVM Arguments
        command.add("-Xmx2G");
        command.add("-Xms1G");
        command.add("-Dfile.encoding=UTF-8");
        
        // Classpath and main class (simplified - actual implementation depends on version/modloader)
        command.add("-cp");
        command.add("."); // This should be properly constructed classpath
        command.add("net.minecraft.client.main.Main");
        
        // Game arguments
        command.add("--username=" + username);
        command.add("--uuid=" + uuid);
        command.add("--accessToken=" + accessToken);
        command.add("--gameDir=" + gameDir);
        command.add("--assetsDir=" + gameDir + "/assets");
        command.add("--assetIndex=" + version);
        command.add("--version=" + version);
        command.add("--versionType=release");
        
        return command;
    }
    
    /**
     * Stop Minecraft process
     */
    public static void stop(Process process) {
        if (process != null && process.isAlive()) {
            process.destroy();
            logger.info("Minecraft process terminated");
        }
    }
}
