package nkichev.wooanna.octopusgameteamwork.GameAudio;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import java.io.IOException;

/**
 * Created by Woo on 13.10.2014 г..
 */
public class GameAudio implements Audio {
    AssetManager assets;
    SoundPool soundPool;

    public GameAudio(Activity activity) {

        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        this.assets = activity.getAssets();

        this.soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    }

    @Override
    public Music newMusic(String filename) {

        try {
            AssetFileDescriptor assetDescriptor = assets.openFd(filename);
            return new GameMusic(assetDescriptor);
        } 	catch (IOException e) {
            throw new RuntimeException("Couldn't load music '" + filename + "'");
        }
    }
}

