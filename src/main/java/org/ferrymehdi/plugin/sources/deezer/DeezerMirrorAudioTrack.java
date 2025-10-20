package org.ferrymehdi.plugin.sources.deezer;

import com.github.topi314.lavasrc.mirror.MirroringAudioTrack;
import com.sedmelluq.discord.lavaplayer.container.mp3.Mp3AudioTrack;
import com.sedmelluq.discord.lavaplayer.tools.io.SeekableInputStream;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import com.sedmelluq.discord.lavaplayer.track.InternalAudioTrack;

public class DeezerMirrorAudioTrack extends MirroringAudioTrack {

public DeezerMirrorAudioTrack(AudioTrackInfo trackInfo, DeezerMirrorSourceManager sourceManager) {
        this(trackInfo, null, null, null, null, null, false, sourceManager);
    }

    public DeezerMirrorAudioTrack(AudioTrackInfo trackInfo, String albumName, String albumUrl, String artistUrl, String artistArtworkUrl, String previewUrl, boolean isPreview, DeezerMirrorSourceManager sourceManager) {
        super(trackInfo, albumName, albumUrl, artistUrl, artistArtworkUrl, previewUrl, isPreview, sourceManager);
    }

    @Override
    protected InternalAudioTrack createAudioTrack(AudioTrackInfo trackInfo, SeekableInputStream stream) {
        return new Mp3AudioTrack(trackInfo, stream);
    }

    @Override
    protected AudioTrack makeShallowClone() {
        return new DeezerMirrorAudioTrack(this.trackInfo, (DeezerMirrorSourceManager) this.sourceManager);
    }

}
