package net.ethanol.de.gui;

import lombok.SneakyThrows;
import meteordevelopment.meteorclient.gui.GuiThemes;
import meteordevelopment.meteorclient.gui.WindowScreen;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.Settings;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import rocks.ethanol.ethanolapi.EthanolAPI;
import rocks.ethanol.ethanolapi.server.connector.EthanolServerConnector;

import java.util.function.Consumer;

public class EthanolConsoleScreen extends WindowScreen {
    private final Settings settings = new Settings();
    private final SettingGroup sg = settings.getDefaultGroup();

    private EthanolServerConnector connector;

    private boolean connectionOpen = false;
    private boolean connecting = true;

    public EthanolConsoleScreen(String authKey) {
        super(GuiThemes.get(), "EthanolConsole");

        connector = EthanolAPI.connect(authKey);
        connector.startAsync().thenAccept(b -> {
                connecting = false;
            }
        );
    }

    @Override
    public void initWidgets() {
        if (connecting){
            add(theme.label("connecting..."));
        }

        if (connectionOpen){
            add(theme.label("Connected"));
            Consumer<String> printConsumer = str -> ChatUtils.info("Received: " + str);
            connector.listen(printConsumer);
        }
    }

    @Override
    public void tick() {
        if (!connectionOpen && connector.isOpened() && !connecting){
            connectionOpen = true;
            reload();
        }
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @SneakyThrows
    @Override
    public void close() {
        connector.close();
        super.close();
    }
}
