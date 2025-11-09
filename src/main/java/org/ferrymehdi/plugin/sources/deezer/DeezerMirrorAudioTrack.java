package org.ferrymehdi.plugin.sources.deezer;

import java.net.URI;

import com.github.topi314.lavasrc.mirror.MirroringAudioTrack;
import com.github.topi314.lavasrc.mirror.TrackNotFoundException;
import com.sedmelluq.discord.lavaplayer.container.mp3.Mp3AudioTrack;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.tools.io.PersistentHttpStream;
import com.sedmelluq.discord.lavaplayer.tools.io.SeekableInputStream;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import com.sedmelluq.discord.lavaplayer.track.InternalAudioTrack;
import com.sedmelluq.discord.lavaplayer.track.playback.LocalAudioTrackExecutor;

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

    private static MirroringAudioTrack getTrack(MirroringAudioTrack track, DeezerMirrorSourceManager sourceManager) {
        AudioTrackInfo originalInfo = track.getInfo();
        try {
            var json = sourceManager.getJson(DeezerMirrorSourceManager.PUBLIC_API_BASE + "/track/" + track.getInfo().identifier);            if (json != null) {
                String isrc = json.get("isrc").text();
                if (isrc != null && !isrc.isEmpty()) {
                    return new DeezerMirrorAudioTrack(
                        new AudioTrackInfo(
                            originalInfo.title,
                            originalInfo.author,
                            originalInfo.length,
                            originalInfo.identifier,
                            originalInfo.isStream,
                            originalInfo.uri,
                            originalInfo.artworkUrl,
                            isrc
                        ),
                        track.getAlbumName(),
                        track.getAlbumUrl(),
                        track.getArtistUrl(),
                        track.getArtistArtworkUrl(),
                        track.getPreviewUrl(),
                        track.isPreview(),
                        sourceManager
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return track;
        }

        return track;
    }

    @Override
    public void process(LocalAudioTrackExecutor executor) throws Exception {
        if (this.isPreview) {
            if (this.previewUrl == null) {
                throw new FriendlyException("No preview url found", FriendlyException.Severity.COMMON, new IllegalArgumentException());
            }
            try (var httpInterface = this.sourceManager.getHttpInterface()) {
                try (var stream = new PersistentHttpStream(httpInterface, new URI(this.previewUrl), this.trackInfo.length)) {
                    processDelegate(createAudioTrack(this.trackInfo, stream), executor);
                }
            }
            return;
        }
        MirroringAudioTrack item;

        if(this.getInfo().isrc == null){
            item = getTrack(this, (DeezerMirrorSourceManager) this.sourceManager);
        } else {
            item = this;
        }

        var track = this.sourceManager.getResolver().apply(item);
        if (track instanceof AudioPlaylist) {
            var tracks = ((AudioPlaylist) track).getTracks();
            if (tracks.isEmpty()) {
                throw new TrackNotFoundException("No tracks found in playlist or search result for track");
            }
            track = tracks.get(0);
        }
        if (track instanceof InternalAudioTrack) {
        ((InternalAudioTrack) track).setUserData(this.getUserData());
            var internalTrack = (InternalAudioTrack) track;
            processDelegate(internalTrack, executor);
            return;
        }

        throw new TrackNotFoundException("No mirror found for track");
    }

}
