package com.example.aavax.ui;

/**
 * interface to ensure name of toolbar is set & inflation of fragments in Activity
 */
public interface IMainActivity {

    void setToolbarTitle(String fragmentTag);

    void inflateFragment(String fragmentTag, String message);
}
