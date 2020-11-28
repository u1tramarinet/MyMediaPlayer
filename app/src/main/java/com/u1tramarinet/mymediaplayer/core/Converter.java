package com.u1tramarinet.mymediaplayer.core;

import androidx.annotation.NonNull;

import com.google.android.exoplayer2.MediaItem;

import java.util.ArrayList;
import java.util.List;

class Converter {
    private Converter() {
    }

    static MediaItem convertToMediaItem(@NonNull Item item) {
        return MediaItem.fromUri("");
    }

    static List<MediaItem> convertToMediaItems(@NonNull List<Item> items) {
        List<MediaItem> mediaItems = new ArrayList<>();
        for (Item item : items) {
            mediaItems.add(convertToMediaItem(item));
        }
        return mediaItems;
    }
}
