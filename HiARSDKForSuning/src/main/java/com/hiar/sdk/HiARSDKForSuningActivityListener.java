package com.hiar.sdk;

public interface HiARSDKForSuningActivityListener {
    void OnCatchButtonClicked(HiARSDKForSuningState param);

    void OnShareButtonClicked(HiARSDKForSuningState param);

    void OnAllpetsButtonClicked(HiARSDKForSuningState param);

    void OnSeeResultButtonClicked(HiARSDKForSuningState param);

    void OnBackButtonClicked(HiARSDKForSuningState param);

    void OnResourceError(HiARSDKForSuningState param, String filePath);
}
