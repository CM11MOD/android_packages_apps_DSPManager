/*
 *  Copyright (C) 2013 The OmniROM Project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.bel.android.dspmanager.modules.boefflacontrol;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.bel.android.dspmanager.activity.DSPManager;
import com.bel.android.dspmanager.activity.Utils;

import java.util.ArrayList;

/**
 * Helper class to control Boeffla Sound
 */
public class BoefflaControlHelper {

    //=========================
    // Fields
    //=========================
    private static final String TAG = "BoefflaControlHelper";
    private static BoefflaControlHelper boefflaControlHelper;
    //=========================
    private static SharedPreferences mSharedPrefs;
    private static SharedPreferences.Editor mSharedPrefsEditor;
    //=========================
    // Paths
    //=========================
    private static String BOEFFLA_CONTROL = "/sys/devices/virtual/misc/boeffla_sound/boeffla_sound";
    private static String DAC_DIRECT = "/sys/devices/virtual/misc/boeffla_sound/dac_oversampling";
    private static String DAC_OVERSAMPLING = "/sys/devices/virtual/misc/boeffla_sound/dac_oversampling";
    private static String OVER_SUPPRESS = "/sys/devices/virtual/misc/boeffla_sound/eq";
    private static String FLL_TUNING = "/sys/devices/virtual/misc/boeffla_sound/fll_tuning";
    private static String HEADPHONE_GAIN = "/sys/devices/virtual/misc/boeffla_sound/headphone_volume";
    private static String MIC_CALL = "/sys/devices/virtual/misc/boeffla_sound/mic_level_call";
    private static String MIC_GENERAL = "/sys/devices/virtual/misc/boeffla_sound/mic_level_general";
    private static String MONO_DOWNMIX = "/sys/devices/virtual/misc/boeffla_sound/mono_downmix";
    private static String PRIVACY_MODE = "/sys/devices/virtual/misc/boeffla_sound/privacy_mode";
    private static String SPEAKER_TUNING = "/sys/devices/virtual/misc/boeffla_sound/speaker_tuning";
    private static String SPEAKER_VOLUME = "/sys/devices/virtual/misc/boeffla_sound/speaker_volume";
    private static String STEREO_EXPANSION = "/sys/devices/virtual/misc/boeffla_sound/stereo_expansion";

    private BoefflaControlHelper(Context paramContext) {
        mSharedPrefs = paramContext.getSharedPreferences(
                DSPManager.SHARED_PREFERENCES_BASENAME + ".boefflacontrol",
                Context.MODE_MULTI_PROCESS);

        mSharedPrefsEditor = mSharedPrefs.edit();
    }

    /**
     * Get an instance of the BoefflaControlHelper
     *
     * @param paramContext The context of the current Activity
     * @return An instance of the Boeffla Control Helper
     */
    public static BoefflaControlHelper getBoefflaControlHelper(Context paramContext) {
        if (boefflaControlHelper == null) {
            boefflaControlHelper = new BoefflaControlHelper(paramContext);
        }
        return boefflaControlHelper;
    }

    //=========================
    // Save and Load
    //=========================

    /**
     * Loads the applied values
     */
    public void applyValues() {
        if (getBoefflaControl()) {

            if (getBoefflaControl()) {
                applyBoefflaControl(readBoefflaControl());
            }

            if (getDACDirect()) {
                applyDACDirect(readDACDirect());
            }

            if (getDACOversampling()) {
                applyDACOversampling(readDACOversampling());
            }

            if (getFLLTuning()) {
                applyFLLTuning(readFLLTuning());
            }

            if (getHeadphoneLeft()) {
                applyHeadphoneLeft(Integer.toString(readHeadphoneLeft() - 40));
            }

            if (getHeadphoneRight()) {
                applyHeadphoneRight(Integer.toString(readHeadphoneRight() - 40));
            }

            if (getMicrophoneCall()) {
                applyMicrophoneCall(Integer.toString(readMicrophoneCall()));
            }

            if (getMicrophoneGeneral()) {
                applyMicrophoneGeneral(Integer.toString(readMicrophoneGeneral()));
            }

            if (getMonoDownmix()) {
                applyMonoDownmix(readMonoDownmix());
            }

            if (getOverSuppress()) {
                applyOverSuppress(readOverSuppress());
            }

            if (getPrivacyMode()) {
                applyPrivacyMode(readPrivacyMode());
            }

            if (getSpeakerTuning()) {
                applySpeakerTuning(readSpeakerTuning());
            }

            if (getSpeakerVolume()) {
                applySpeakerVolume(Integer.toString(readSpeakerVolume() - 40));
            }

            if (getStereoExpansion()) {
                applyStereoExpansion(Integer.toString(readStereoExpansion()));
            }
        }
    }

    //=========================
    // Apply
    //=========================

    /**
     * Applies DAC Direct
     */
    public void applyDACDirect(int val) {
        Utils.writeValue(DAC_DIRECT, Integer.toString(val));
    }

    /**
     * Applies DAC Oversampling
     */
    public void applyDACOversampling(int val) {
        Utils.writeValue(DAC_OVERSAMPLING, Integer.toString(val));
    }

    /**
     * Applies Boeffla Control
     */
    public void applyBoefflaControl(int val) {
        Utils.writeValue(BOEFFLA_CONTROL, Integer.toString(val));
    }

    /**
     * Applies FLL Tuning
     */
    public void applyFLLTuning(int val) {
        Utils.writeValue(FLL_TUNING, Integer.toString(val));
    }

    /**
     * Applies Headphone volume (left)
     */
    public void applyHeadphoneLeft(String val) {
        int str = 40 + Integer.parseInt(val);
        Utils.writeValue(HEADPHONE_GAIN, str + " " + readHeadphoneRight());
    }

    /**
     * Applies Headphone volume (right)
     */
    public void applyHeadphoneRight(String val) {
        int str = 40 + Integer.parseInt(val);
        Utils.writeValue(HEADPHONE_GAIN, readHeadphoneLeft() + " " + str);
    }

    /**
     * Applies Microphone volume (call)
     */
    public void applyMicrophoneCall(String val) {
        Utils.writeValue(MIC_CALL, val);
    }

    /**
     * Applies Microphone volume (general)
     */
    public void applyMicrophoneGeneral(String val) {
        Utils.writeValue(MIC_GENERAL, val);
    }

    /**
     * Applies Mono Downmix
     */
    public void applyMonoDownmix(int val) {
        Utils.writeValue(MONO_DOWNMIX, Integer.toString(val));
    }

    /**
     * Applies Over-Saturation Suppress
     */
    public void applyOverSuppress(int val) {
        Utils.writeValue(OVER_SUPPRESS, Integer.toString(val));
    }

    /**
     * Applies Privacy Mode
     */
    public void applyPrivacyMode(int val) {
        Utils.writeValue(PRIVACY_MODE, Integer.toString(val));
    }

    /**
     * Applies Speaker Tuning
     */
    public void applySpeakerTuning(int val) {
        Utils.writeValue(SPEAKER_TUNING, Integer.toString(val));
    }

    /**
     * Applies Speaker Volume
     */
    public void applySpeakerVolume(String val) {
        int str = 40 + Integer.parseInt(val);
        Utils.writeValue(SPEAKER_VOLUME, Integer.toString(str));
    }

    /**
     * Applies Stereo Expansion
     */
    public void applyStereoExpansion(String val) {
        Utils.writeValue(STEREO_EXPANSION, val);
    }

    //=========================
    // Read
    //=========================

    /**
     * Returns the value of dac_direct
     */
    public int readDACDirect() {
        String str = Utils.readOneLine(DAC_DIRECT).split(": ")[1];
        return Integer.parseInt(str);
    }

    /**
     * Returns the value of boeffla_sound
     */
    public int readBoefflaControl() {
        String str = Utils.readOneLine(BOEFFLA_CONTROL).split(": ")[1];
        return Integer.parseInt(str);
    }

    /**
     * Returns the value of dac_oversampling
     */
    public int readDACOversampling() {
        String str = Utils.readOneLine(DAC_OVERSAMPLING).split(": ")[1];
        return Integer.parseInt(str);
    }

    /**
     * Returns the value of fll_tuning
     */
    public int readFLLTuning() {
        String str = Utils.readOneLine(FLL_TUNING).split(": ")[1];
        return Integer.parseInt(str);
    }

    /**
     * Returns the value of headphone_volume (left)
     */
    public int readHeadphoneLeft() {
        String str = Utils.readManyLines(HEADPHONE_GAIN)[1].split(": ")[1];
        return Integer.parseInt(str);
    }

    /**
     * Returns the value of headphone_volume (right)
     */
    public int readHeadphoneRight() {
        String str = Utils.readManyLines(HEADPHONE_GAIN)[2].split(": ")[1];
        return Integer.parseInt(str);
    }

    /**
     * Returns the value of mic_level_call
     */
    public int readMicrophoneCall() {
        String str = Utils.readOneLine(MIC_CALL).split("ll ")[1];
        return Integer.parseInt(str);
    }

    /**
     * Returns the value of mic_level_general
     */
    public int readMicrophoneGeneral() {
        String str = Utils.readOneLine(MIC_GENERAL).split("al ")[1];
        return Integer.parseInt(str);
    }

    /**
     * Returns the value of mono_downmix
     */
    public int readMonoDownmix() {
        String str = Utils.readOneLine(MONO_DOWNMIX).split(": ")[1];
        return Integer.parseInt(str);
    }

    /**
     * Returns the value of eq
     */
    public int readOverSuppress() {
        String str = Utils.readOneLine(OVER_SUPPRESS).split(": ")[1];
        return Integer.parseInt(str);
    }

    /**
     * Returns the value of privacy_mode
     */
    public int readPrivacyMode() {
        String str = Utils.readOneLine(PRIVACY_MODE).split(": ")[1];
        return Integer.parseInt(str);
    }

    /**
     * Returns the value of speaker_tuning
     */
    public int readSpeakerTuning() {
        String str = Utils.readOneLine(SPEAKER_TUNING).split(": ")[1];
        return Integer.parseInt(str);
    }

    /**
     * Returns the value of speaker_volume
     */
    public int readSpeakerVolume() {
        String str = Utils.readManyLines(SPEAKER_VOLUME)[1].split(": ")[1];
        return Integer.parseInt(str);
    }

    /**
     * Returns the value of stereo_expansion
     */
    public int readStereoExpansion() {
        String str = Utils.readOneLine(STEREO_EXPANSION).split(": ")[1];
        return Integer.parseInt(str);
    }


    //=========================
    // Get
    //=========================

    /**
     * @return null if not existing
     */

    public boolean getBoefflaControl() {
        return Utils.readOneLine(BOEFFLA_CONTROL) != null;
    }

    public boolean getDACDirect() {
        return Utils.readOneLine(DAC_DIRECT) != null;
    }

    public boolean getDACOversampling() {
        return Utils.readOneLine(DAC_OVERSAMPLING) != null;
    }

    public boolean getFLLTuning() {
        return Utils.readOneLine(FLL_TUNING) != null;
    }

    public boolean getHeadphoneLeft() {
        return Utils.readOneLine(HEADPHONE_GAIN) != null;
    }

    public boolean getHeadphoneRight() {
        return Utils.readOneLine(HEADPHONE_GAIN) != null;
    }

    public boolean getMicrophoneCall() {
        return Utils.readOneLine(MIC_CALL) != null;
    }

    public boolean getMicrophoneGeneral() {
        return Utils.readOneLine(MIC_GENERAL) != null;
    }

    public boolean getMonoDownmix() {
        return Utils.readOneLine(MONO_DOWNMIX) != null;
    }

    public boolean getOverSuppress() {
        return Utils.readOneLine(OVER_SUPPRESS) != null;
    }

    public boolean getPrivacyMode() {
        return Utils.readOneLine(PRIVACY_MODE) != null;
    }

    public boolean getSpeakerTuning() {
        return Utils.readOneLine(SPEAKER_TUNING) != null;
    }

    public boolean getSpeakerVolume() {
        return Utils.readOneLine(SPEAKER_VOLUME) != null;
    }

    public boolean getStereoExpansion() {
        return Utils.readOneLine(STEREO_EXPANSION) != null;
    }
}
