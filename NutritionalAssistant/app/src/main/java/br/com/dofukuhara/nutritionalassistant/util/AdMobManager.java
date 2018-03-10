package br.com.dofukuhara.nutritionalassistant.util;

import com.google.android.gms.ads.AdRequest;

/**
 * Created by dofukuhara on 10/03/2018.
 */

public class AdMobManager {
    private static AdRequest sAdRequest;

    // Ensure only one AdRequest at time
    public static AdRequest getAdRequest() {
        if (sAdRequest == null) {
            // Create an ad request.
            sAdRequest = new AdRequest.Builder().build();
        }

        return sAdRequest;
    }
}
