package com.thoughtworks.twplayer

import android.graphics.SurfaceTexture
import android.os.Handler
import android.os.HandlerThread
import android.view.Surface
import android.view.TextureView

abstract class TWPlayerInterface(player: TWPlayer) : TextureView.SurfaceTextureListener {
    companion object {

        var SAVED_SURFACE: SurfaceTexture? = null
    }

    var mPlayerHandlerThread: HandlerThread? = null

    var mPlayerHandler: Handler? = null
    var mUIHandler: Handler? = null
    var mTWPlayer: TWPlayer = player


    abstract fun prepare()

    abstract fun start()

    abstract fun pause()

    abstract fun seekTo(time: Int)

    abstract fun isPlaying(): Boolean

    abstract fun release()

    abstract fun getCurrentPosition(): Int

    abstract fun getDuration(): Int

    abstract fun setVolume(leftVolume: Float, rightVolume: Float)

    abstract fun setSurface(surface: Surface)

    abstract fun setSpeed(speed: Float)

}