package org.ferrymehdi.plugin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "plugins.lavadeezer.sources")
@Component
public class ConfigSources {

    private boolean deezer = false;

    public boolean isDeezer() {
        return this.deezer;
    }

    public void setDeezer(boolean deezer) {
        this.deezer = deezer;
    }

}
