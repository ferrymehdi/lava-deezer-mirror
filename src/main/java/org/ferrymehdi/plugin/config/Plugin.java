package org.ferrymehdi.plugin.config;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import dev.arbjerg.lavalink.api.AudioPlayerManagerConfiguration;

import org.ferrymehdi.plugin.sources.deezer.DeezerMirrorSourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Plugin implements AudioPlayerManagerConfiguration {

    private static final Logger log = LoggerFactory.getLogger(Plugin.class);

    private final ConfigPlugin pluginConfig;
    private final ConfigSources sourcesConfig;

    public Plugin(ConfigPlugin pluginConfig, ConfigSources sourcesConfig) {
        log.info("Loading LavaDeezer plugin...");
        this.pluginConfig = pluginConfig;
        this.sourcesConfig = sourcesConfig;
    }

    @Override
    public AudioPlayerManager configure(AudioPlayerManager manager) {
        if (this.sourcesConfig.isDeezer()) {
            log.info("Registering Deezer audio source manager");
            var deezerMirrorSourceManager = new DeezerMirrorSourceManager(pluginConfig.getProviders(), manager);
            manager.registerSourceManager(deezerMirrorSourceManager);
        }
        return manager;
    }

}
