package net.ethanol.de.gui;

import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.gui.GuiThemes;
import meteordevelopment.meteorclient.gui.WindowScreen;
import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
import meteordevelopment.meteorclient.gui.widgets.containers.WTable;
import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
import meteordevelopment.meteorclient.systems.Systems;
import net.ethanol.de.Client;
import net.ethanol.de.Ethanol.EthanolSystem;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import rocks.ethanol.ethanolapi.EthanolAPI;
import rocks.ethanol.ethanolapi.server.listener.EthanolServer;
import rocks.ethanol.ethanolapi.server.listener.EthanolServerListener;
import rocks.ethanol.ethanolapi.server.listener.exceptions.EthanolServerListenerConnectException;

import java.io.IOException;

public class EthanolScreen extends WindowScreen {

    public EthanolScreen() {
        super(GuiThemes.get(), "Ethanol");
    }
    private boolean waitingForAuth = false;

    @Override
    public void initWidgets() {
        EthanolServerListener listener = Client.EthanolListener;
        String authToken = EthanolSystem.get().apiKey;

        if (authToken.isEmpty()) {
            WHorizontalList widgetList = add(theme.horizontalList()).expandX().widget();
            widgetList.add(theme.label("Please authenticate with Discord. "));
            waitingForAuth = true;

            WButton loginButton = widgetList.add(theme.button("Login")).widget();
            loginButton.action = () -> {
                if (this.client == null) return;
                this.client.setScreen(new LoginWithDiscordScreen(this));
            };
            return;
        }

        if (listener == null){
            listener = EthanolAPI.createServerListener(authToken);
            Client.EthanolListener = listener;
            try {
                listener.run();
            } catch (IOException | EthanolServerListenerConnectException ignored) {

            }
        }

        WHorizontalList accountList = add(theme.horizontalList()).expandX().widget();

        WButton logoutButton = accountList.add(theme.button("Logout")).widget();
        logoutButton.action = () -> {
            Systems.get(EthanolSystem.class).apiKey = "";
            reload();
        };
//        WTable userInfoList = add(theme.table()).widget();
//        userInfoList.add(theme.label(EthanolSystem.get().apiKey));

        if (listener.getServers() == null || listener.getServers().length == 0) {
            add(theme.label("No Infected Servers Found")).expandX();
            return;
        }

        add(theme.label("Found %s server(s):".formatted(listener.getServers().length)));

        WTable table = add(theme.table()).widget();
//        table.row();

        int minWidth = (int)(MeteorClient.mc.getWindow().getWidth() * 0.2);
        table.add(theme.label("Server IP")).minWidth(minWidth);
        table.add(theme.label("Players")).minWidth(minWidth);
//        table.add(theme.label("Version")).minWidth(minWidth);
        table.add(theme.label("Actions"));

        table.row();
        table.add(theme.horizontalSeparator()).expandX();
        table.row();

        for (EthanolServer server : listener.getServers()){
            String serverIP = server.getAddress().toString().substring(1);
            String Players = "%s/%s".formatted(server.getOnlinePlayers(), server.getMaxPlayers());
            String Version = server.getVersion();

            table.add(theme.label(serverIP)).minWidth(minWidth);
            table.add(theme.label(Players)).minWidth(minWidth);
//            table.add(theme.label(Version)).minWidth(minWidth);

            WButton joinButton = theme.button("Join");
            joinButton.action = () -> {
                ServerInfo info = new ServerInfo(serverIP ,serverIP, ServerInfo.ServerType.OTHER);
                ConnectScreen.connect(this, MeteorClient.mc, ServerAddress.parse(serverIP), info, false);
            };
            table.add(joinButton);

            WButton consoleButton = theme.button("Console");
            consoleButton.action = () -> {
                MeteorClient.mc.setScreen(new EthanolConsoleScreen(server.getAuthentication()));
            };
            table.add(consoleButton);
            table.row();
        }
    }

    @Override
    public void tick() {
        if (waitingForAuth) {
            String authToken = EthanolSystem.get().apiKey;
            if (!authToken.isEmpty()) {
                this.reload();
                this.waitingForAuth = false;
            }
        }
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
