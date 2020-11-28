package com.u1tramarinet.mymediaplayer.core;

import android.content.Context;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
    @NonNull
    private static final Player INSTANCE = new Player();
    @Nullable
    private static SimpleExoPlayer EXOPLAYER = null;
    private Player() {
    }

    public static Player getInstance() {
        return INSTANCE;
    }

    public synchronized void initialize(@NonNull Context context) {
        if (EXOPLAYER != null) {
            return;
        }
        EXOPLAYER = new SimpleExoPlayer.Builder(context).build();
    }

    public void updatePlaylist(@NonNull List<Item> playlist) throws IllegalStateException {
        verifyPlayerInitialized();
        List<MediaItem> mediaItems = Converter.convertToMediaItems(playlist);
        EXOPLAYER.setMediaItems(mediaItems);
    }

    public void addPlaylist(@NonNull List<Item> playlist) {
        verifyPlayerInitialized();
        List<MediaItem> mediaItems = Converter.convertToMediaItems(playlist);
        EXOPLAYER.setMediaItems(mediaItems);
    }

    public void clearPlaylist() {
        verifyPlayerInitialized();
        EXOPLAYER.clearMediaItems();
    }

    public void addItem(@NonNull Item item) {
        updatePlaylist(convert(item));
    }

    public void insertItem(@NonNull Item item, int itemIndex) {
        if (itemIndex >= EXOPLAYER.getMediaItemCount()) {
            addItem(item);
        }
        EXOPLAYER.addMediaItem(itemIndex, Converter.convertToMediaItem(item));
    }

    public void removeItem(int itemIndex) {
        if (itemIndex >= EXOPLAYER.getMediaItemCount()) {
            return;
        }
        EXOPLAYER.removeMediaItem(itemIndex);
    }

    public void moveItem(int srcItemIndex, int destItemIndex) {
        if (srcItemIndex >= EXOPLAYER.getMediaItemCount()
                || srcItemIndex == destItemIndex) {
            return;
        }
        EXOPLAYER.moveMediaItem(srcItemIndex, destItemIndex);
    }

    public synchronized void terminate() {
        if (EXOPLAYER == null) {
            return;
        }
        EXOPLAYER.release();
        EXOPLAYER = null;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        terminate();
    }

    private <E> List<E> convert(@NonNull E item) {
        List<E> items = new ArrayList<>();
        items.add(item);
        return items;
    }

    private void verifyPlayerInitialized() throws IllegalStateException {
        if (EXOPLAYER == null) {
            throw new IllegalStateException();
        }
    }
}
