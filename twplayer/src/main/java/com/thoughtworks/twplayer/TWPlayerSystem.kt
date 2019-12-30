package com.thoughtworks.twplayer

import android.graphics.SurfaceTexture
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.view.Surface
import androidx.annotation.RequiresApi

class TWPlayerSystem(twPlayer: TWPlayer) : TWPlayerInterface(twPlayer),
    MediaPlayer.OnPreparedListener,
    MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener,
    MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener,
    MediaPlayer.OnVideoSizeChangedListener {

    var mMediaPlayer: MediaPlayer? = null

    override fun prepare() {
        release()
        mPlayerHandlerThread = HandlerThread("TWPlayer")
        mPlayerHandlerThread?.let {
            it.start()
            mPlayerHandler = Handler(it.looper)
        }
        mUIHandler = Handler()

        mPlayerHandler?.post {
            mMediaPlayer = MediaPlayer()
            mMediaPlayer?.let {
                it.setAudioStreamType(AudioManager.STREAM_MUSIC)
                it.setOnCompletionListener(this@TWPlayerSystem)
                it.setOnPreparedListener(this@TWPlayerSystem)
                it.setOnBufferingUpdateListener(this@TWPlayerSystem)
                it.setOnSeekCompleteListener(this@TWPlayerSystem)
                it.setOnErrorListener(this@TWPlayerSystem)
                it.setOnInfoListener(this@TWPlayerSystem)
                it.setOnVideoSizeChangedListener(this@TWPlayerSystem)

                it.setScreenOnWhilePlaying(true)
                it.setDataSource(mTWPlayer.dataSource?.getCurrentUrl())
                it.prepareAsync()
                it.setSurface(Surface(SAVED_SURFACE))
            }
        }
    }

    override fun start() {
        mPlayerHandler?.post {
            mMediaPlayer?.start()
        }
    }

    override fun pause() {
        mPlayerHandler?.post {
            mMediaPlayer?.pause()
        }
    }

    override fun seekTo(time: Int) {
        mPlayerHandler?.post {
            mMediaPlayer?.seekTo(time)
        }
    }

    override fun isPlaying(): Boolean {
        return mMediaPlayer?.isPlaying ?: false
    }

    override fun release() {
        TWPlayerInterface.SAVED_SURFACE = null
        mPlayerHandler?.post {
            mMediaPlayer?.setSurface(null)
            mMediaPlayer?.release()
            mPlayerHandlerThread?.quit()
        }
        mMediaPlayer = null
    }

    override fun getCurrentPosition(): Int {
        return mMediaPlayer?.currentPosition ?: 0
    }

    override fun getDuration(): Int {
        return mMediaPlayer?.duration ?: 0
    }

    override fun setVolume(leftVolume: Float, rightVolume: Float) {
        mPlayerHandler?.post {
            mMediaPlayer?.setVolume(leftVolume, rightVolume)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun setSpeed(speed: Float) {
        mMediaPlayer?.let {
            val playbackParams = it.playbackParams
            playbackParams.speed = speed
            it.playbackParams = playbackParams
        }

    }

    override fun setSurface(surface: Surface) {
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mUIHandler?.post {
            mTWPlayer.onPrepared()
        }
    }

    override fun onCompletion(mp: MediaPlayer?) {
        mUIHandler?.post {
            mTWPlayer.onAutoComplete()
        }
    }

    override fun onVideoSizeChanged(mp: MediaPlayer?, width: Int, height: Int) {
        mUIHandler?.post {
            mTWPlayer.onVideoSizeChanged()
        }
    }


    override fun onInfo(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
    }

    override fun onSeekComplete(mp: MediaPlayer?) {
        mUIHandler?.post { mTWPlayer.onSeekComplete() }
    }

    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
    }

}