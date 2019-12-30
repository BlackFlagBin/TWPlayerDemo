package com.thoughtworks.twplayer

abstract class TWPlayer {
    var dataSource: TWDataSource? = null

    fun onPrepared() {
    }

    fun onAutoComplete() {
    }

    fun onVideoSizeChanged() {
    }

    fun onSeekComplete() {

    }

}