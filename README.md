# Minecraft Server Launcher

Um launcher personalizado para Minecraft que distribui automaticamente os mods do seu servidor.

## 🎮 Recursos

✅ Gerenciamento de contas Minecraft  
✅ Download automático de mods  
✅ Suporte a múltiplas versões  
✅ Gerenciador de versões  
✅ Interface gráfica intuitiva (JavaFX)  
✅ Suporte a Forge/Fabric  
✅ Logging completo  

## 📋 Requisitos

- Java 11 ou superior
- Maven 3.6+

## 🔧 Compilação

```bash
mvn clean package
```

O JAR compilado estará em `target/minecraft-launcher.jar`

## 📁 Estrutura do Projeto

```
laucher2/
├── src/
│   └── main/
│       ├── java/com/frix/launcher/
│       │   ├── LauncherMain.java          # Ponto de entrada
│       │   ├── config/
│       │   │   └── ConfigManager.java     # Gerenciador de configuração
│       │   ├── auth/
│       │   │   └── AccountManager.java    # Gerenciador de contas
│       │   ├── mods/
│       │   │   └── ModManager.java        # Gerenciador de mods
│       │   └── core/
│       │       └── GameLauncher.java      # Lançador do jogo
│       └── resources/
│           ├── config.json                # Arquivo de configuração
│           └── logback.xml                # Configuração de logging
├── .github/workflows/
│   └── build.yml                          # GitHub Actions CI/CD
├── pom.xml                                # Configuração Maven
├── .gitignore                             # Arquivos ignorados
└── README.md                              # Este arquivo
```

## ⚙️ Configuração

Edite `src/main/resources/config.json` para configurar:

```json
{
  "serverName": "Seu Servidor",
  "modsDownloadUrl": "https://seu-servidor.com/mods",
  "version": "1.20.1",
  "modLoader": "forge",
  "javaPath": "java",
  "gameDir": "~/.minecraft"
}
```

## 🚀 Desenvolvimento

1. Clone o repositório
   ```bash
   git clone https://github.com/frixnetworkserver-gif/laucher2.git
   cd laucher2
   ```

2. Abra em sua IDE favorita (IntelliJ, Eclipse, VS Code)

3. Execute
   ```bash
   mvn clean install
   ```

4. Rode `LauncherMain.java`

## 📦 Dependências Principais

- **GSON** - Processamento JSON
- **Apache HttpComponents** - Cliente HTTP
- **SLF4J + Logback** - Logging
- **JavaFX** - Interface gráfica
- **JUnit** - Testes

## 🔄 GitHub Actions

O projeto possui um workflow CI/CD automático que:
- ✅ Compila o projeto em cada push/PR
- ✅ Gera artefatos JAR
- ✅ Cria releases automaticamente com tags

## 📝 Classes Principais

### LauncherMain
Ponto de entrada da aplicação, inicializa a interface gráfica com JavaFX.

### ConfigManager
Gerencia a configuração do launcher, carregando e salvando em `config.json`.

### AccountManager
Gerencia contas Minecraft, armazenando username, accessToken e UUID.

### ModManager
Gerencia download, instalação e remoção de mods.

### GameLauncher
Responsável por construir e executar o comando de launch do Minecraft.

## 📄 Licença

MIT License

## 👨‍💻 Autor

frixnetworkserver-gif

---

**Desenvolvido com ❤️ para a comunidade Minecraft**
