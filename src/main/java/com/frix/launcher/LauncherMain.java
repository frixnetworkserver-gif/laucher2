package com.frix.launcher;

import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main entry point for the Minecraft Launcher application
 */
public class LauncherMain extends Application {
    
    private static final Logger logger = LoggerFactory.getLogger(LauncherMain.class);
    
    public static void main(String[] args) {
        logger.info("Starting Minecraft Server Launcher...");
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        logger.info("Initializing UI...");
        
        primaryStage.setTitle("Minecraft Server Launcher");
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        
        // TODO: Load FXML and set scene
        
        primaryStage.show();
        logger.info("Launcher started successfully");
    }
}
