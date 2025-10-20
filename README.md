# lava-deezer-mirror

A Plugin for [Lavalink](https://github.com/lavalink-devs/Lavalink) that mirrors Deezer audio sources to other providers like YouTube.
*Thanks to [topi314](https://github.com/topi314) — this plugin is based on [LavaSrc](https://github.com/topi314/LavaSrc).*

## Lavalink Usage

This plugin requires Lavalink `v4` or greater.

To install this plugin, download the latest release and place it into your `plugins` folder.

> **Note**
> For a full `application.yml` example, check the repo.

This plugin works by using Deezer metadata (ISRC or track info) to search for and play audio from mirror providers such as YouTube.

## Supported URLs and Queries

* `dzsearch:animals architects`
* `dzisrc:USEP42058010`
* `dzrec:1090538082` (`dzrec:{TRACK_ID}`, `dzrec:track={TRACK_ID}` or `dzrec:artist={ARTIST_ID}`)
* https://deezer.page.link/U6BTQ2Q1KpmNt2yh8
* https://www.deezer.com/track/1090538082
* https://www.deezer.com/album/175537082
* https://www.deezer.com/playlist/8164349742
* https://www.deezer.com/artist/159126

## Mirror Providers

By default, the plugin uses:

* `"ytsearch:\"%ISRC%\""`
* `"ytmsearch:%QUERY%"`

You can customize this in your configuration if needed.
