package com.frix.launcher.auth;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages Minecraft accounts
 */
public class AccountManager {
    
    private static final Logger logger = LoggerFactory.getLogger(AccountManager.class);
    private static final String ACCOUNTS_FILE = "accounts.json";
    private static final Gson gson = new Gson();
    
    private static List<MinecraftAccount> accounts;
    
    static {
        loadAccounts();
    }
    
    /**
     * Load accounts from file
     */
    public static void loadAccounts() {
        try {
            File accountsFile = new File(ACCOUNTS_FILE);
            if (accountsFile.exists()) {
                FileReader reader = new FileReader(accountsFile);
                Type type = new TypeToken<ArrayList<MinecraftAccount>>(){}.getType();
                accounts = gson.fromJson(reader, type);
                reader.close();
                logger.info("Accounts loaded successfully: " + accounts.size());
            } else {
                accounts = new ArrayList<>();
                logger.info("No accounts file found, starting with empty list");
            }
        } catch (IOException e) {
            logger.error("Error loading accounts", e);
            accounts = new ArrayList<>();
        }
    }
    
    /**
     * Save accounts to file
     */
    public static void saveAccounts() {
        try {
            FileWriter writer = new FileWriter(ACCOUNTS_FILE);
            gson.toJson(accounts, writer);
            writer.close();
            logger.info("Accounts saved successfully");
        } catch (IOException e) {
            logger.error("Error saving accounts", e);
        }
    }
    
    /**
     * Add new account
     */
    public static void addAccount(String username, String accessToken, String uuid) {
        MinecraftAccount account = new MinecraftAccount(username, accessToken, uuid);
        accounts.add(account);
        saveAccounts();
        logger.info("Account added: " + username);
    }
    
    /**
     * Get all accounts
     */
    public static List<MinecraftAccount> getAccounts() {
        return new ArrayList<>(accounts);
    }
    
    /**
     * Get account by username
     */
    public static MinecraftAccount getAccount(String username) {
        return accounts.stream()
                .filter(acc -> acc.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Remove account
     */
    public static void removeAccount(String username) {
        accounts.removeIf(acc -> acc.getUsername().equals(username));
        saveAccounts();
        logger.info("Account removed: " + username);
    }
    
    /**
     * Minecraft Account class
     */
    public static class MinecraftAccount {
        private String username;
        private String accessToken;
        private String uuid;
        
        public MinecraftAccount() {}
        
        public MinecraftAccount(String username, String accessToken, String uuid) {
            this.username = username;
            this.accessToken = accessToken;
            this.uuid = uuid;
        }
        
        // Getters and Setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        
        public String getAccessToken() { return accessToken; }
        public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
        
        public String getUuid() { return uuid; }
        public void setUuid(String uuid) { this.uuid = uuid; }
    }
}
