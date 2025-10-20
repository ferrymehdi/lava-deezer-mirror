package org.ferrymehdi.plugin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.github.topi314.lavasrc.mirror.MirroringAudioSourceManager ;


@ConfigurationProperties(prefix = "plugins.lavadeezer")
@Component
public class ConfigPlugin {
    private String[] providers = {
            "ytsearch:\"" + MirroringAudioSourceManager.ISRC_PATTERN + "\"",
            "ytsearch:" + MirroringAudioSourceManager.QUERY_PATTERN
    };

    public String[] getProviders() {
        return this.providers;
    }

    public void setProviders(String[] providers) {
        this.providers = providers;
    }

}
