package net.ethanol.de.gui;

import lombok.SneakyThrows;
import meteordevelopment.meteorclient.gui.GuiThemes;
import meteordevelopment.meteorclient.gui.WindowScreen;
import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
import meteordevelopment.meteorclient.systems.Systems;
import net.ethanol.de.Ethanol.EthanolSystem;
import net.minecraft.util.Util;
import rocks.ethanol.ethanolapi.EthanolAPI;
import rocks.ethanol.ethanolapi.auth.DiscordAuthURL;
import rocks.ethanol.ethanolapi.structure.ThrowingConsumer;

import java.io.IOException;

import static meteordevelopment.meteorclient.MeteorClient.mc;

public class LoginWithDiscordScreen extends WindowScreen {

    private boolean canClose = false;

    WindowScreen parent;

    public LoginWithDiscordScreen(WindowScreen parent) {
        super(GuiThemes.get(), "Login with Discord");
        this.parent = parent;
    }

    @SneakyThrows
    @Override
    public void initWidgets() {
        ThrowingConsumer<DiscordAuthURL, IOException> opener = url -> {
            Util.getOperatingSystem().open(url.toURL());
        };

        add(theme.label("Please authenticate with Discord in your browser."));

        add(theme.label("The browser didn't open? Click below to copy the link and open it manually")).expandX();
        WButton copy = add(theme.button("Copy")).expandX().widget();
        copy.action = () -> {
            String url = EthanolAPI.DEFAULT_AUTHENTICATOR.getUrl().toString();
            mc.keyboard.setClipboard(url);
        };

        EthanolAPI.DEFAULT_AUTHENTICATOR.authenticateAsync(
            60000, opener
        ).thenAccept(result -> {
            Systems.get(EthanolSystem.class).apiKey = result;
            this.canClose = true;
            close();
        }).exceptionally(ex -> {
            this.canClose = true;
            add(theme.label("Failed to authenticate with Discord."));
            return null;
        });
    }

    @Override
    public void tick() {
//        String authToken = EthanolSystem.get().apiKey;
//        if (!authToken.isEmpty()) {
//            mc.setScreen(this.parent);
//        }
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return this.canClose;
    }
}
