package com.hiar.sdk.core;

public class HiarqOptions {
    public boolean filterEnable; // true: filter open; false: filter close
    public boolean viewFinderEnable;
    public int[] viewFinderRect;
    public int trackingQuality;        // tracking quality, 1~5(5:best quality, but may be slow), default 4
    public int recogQuality;            // recog quality, 1~5(5:best quality, but may be slow), default 4
}
