package com.frix.launcher.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages launcher configuration
 */
public class ConfigManager {
    
    private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);
    private static final String CONFIG_FILE = "config.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    private static Map<String, Object> config;
    
    static {
        loadConfig();
    }
    
    /**
     * Load configuration from file
     */
    public static void loadConfig() {
        try {
            File configFile = new File(CONFIG_FILE);
            if (configFile.exists()) {
                FileReader reader = new FileReader(configFile);
                config = gson.fromJson(reader, Map.class);
                reader.close();
                logger.info("Configuration loaded successfully");
            } else {
                config = getDefaultConfig();
                saveConfig();
                logger.info("Default configuration created");
            }
        } catch (IOException e) {
            logger.error("Error loading configuration", e);
            config = getDefaultConfig();
        }
    }
    
    /**
     * Save configuration to file
     */
    public static void saveConfig() {
        try {
            FileWriter writer = new FileWriter(CONFIG_FILE);
            gson.toJson(config, writer);
            writer.close();
            logger.info("Configuration saved successfully");
        } catch (IOException e) {
            logger.error("Error saving configuration", e);
        }
    }
    
    /**
     * Get default configuration
     */
    private static Map<String, Object> getDefaultConfig() {
        Map<String, Object> defaultConfig = new HashMap<>();
        defaultConfig.put("serverName", "Meu Servidor");
        defaultConfig.put("modsDownloadUrl", "https://seu-servidor.com/mods");
        defaultConfig.put("version", "1.20.1");
        defaultConfig.put("modLoader", "forge");
        defaultConfig.put("javaPath", "java");
        defaultConfig.put("gameDir", System.getProperty("user.home") + "/.minecraft");
        return defaultConfig;
    }
    
    /**
     * Get configuration value
     */
    public static Object get(String key) {
        return config.getOrDefault(key, null);
    }
    
    /**
     * Get configuration value as String
     */
    public static String getString(String key) {
        Object value = config.get(key);
        return value != null ? value.toString() : "";
    }
    
    /**
     * Set configuration value
     */
    public static void set(String key, Object value) {
        config.put(key, value);
        saveConfig();
    }
}
