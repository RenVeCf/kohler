package com.hiar.sdk.core;

public class HiarqTargetInfo {
    public int markerIndex; // index into realized marker set
    public int state; // just recognized=0, tracked=1, none=2
    public float[] pose;
}
