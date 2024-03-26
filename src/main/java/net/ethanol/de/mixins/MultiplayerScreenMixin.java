package net.ethanol.de.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import net.ethanol.de.gui.EthanolScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiplayerScreen.class)
public class MultiplayerScreenMixin extends Screen {

    protected MultiplayerScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("HEAD"))
    private void initWidgets(CallbackInfo ci){
        this.addDrawableChild(ButtonWidget.builder( Text.literal( "Clipboard Connect"), b -> {
            String clipboard = MinecraftClient.getInstance().keyboard.getClipboard();
            if (clipboard == null) return;

            clipboard = clipboard.replaceAll("[\\t\\n\\r]+", "");
            clipboard = clipboard.replace(" ", "");
            ConnectScreen.connect(this, this.client, ServerAddress.parse(clipboard), new ServerInfo("Clipboard Connect", clipboard, ServerInfo.ServerType.OTHER), false);

        }).dimensions(this.width / 2 - 258, this.height - 54, 100, 20).build());

        this.addDrawableChild(
            new ButtonWidget.Builder(
                Text.literal("Ethanol"),
                onPress -> {
                    if (this.client == null) return;
                    this.client.setScreen(new EthanolScreen());
                }
            )
                .position(150, 3)
                .width(80)
                .build()
        );
    }
}
