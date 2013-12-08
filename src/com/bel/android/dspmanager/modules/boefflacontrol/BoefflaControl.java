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
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.widget.Toast;

import com.bel.android.dspmanager.R;
import com.bel.android.dspmanager.activity.DSPManager;
import com.bel.android.dspmanager.preference.SeekBarPreference;

import java.util.Locale;

/**
 * Controls Boeffla Sound
 */
public class BoefflaControl extends PreferenceFragment
        implements Preference.OnPreferenceChangeListener {

    //=========================
    // Fields
    //=========================
    public static final String NAME = "BoefflaControl";
    private Toast mToast;
    // General
    private SwitchPreference mBoefflaControl;
    private CheckBoxPreference mDACDirect;
    private CheckBoxPreference mDACOversampling;
    private CheckBoxPreference mFLLTuning;
    private CheckBoxPreference mPrivacyMode;
    // Advanced Tuning
    private CheckBoxPreference mSpeakerTuning;
    private CheckBoxPreference mMonoDownmix;
    private CheckBoxPreference mOverSuppress;
    private SeekBarPreference mStereoExpansion;
    // Headphone Volumes
    private SeekBarPreference mHeadphoneLeft;
    private SeekBarPreference mHeadphoneRight;
    // SpeakerVolume
    private SeekBarPreference mSpeakerVolume;
    // Microphone
    private SeekBarPreference mMicrophoneCall;
    private SeekBarPreference mMicrophoneGeneral;
    //=========================
    // Preference Keys
    //=========================
    private static final String BOEFFLA_CONTROL = "boeffla_control";
    private static final String DAC_DIRECT = "dac_direct";
    private static final String DAC_OVERSAMPLING = "dac_oversampling";
    private static final String FLL_TUNING = "fll_tuning";
    private static final String HEADPHONE_LEFT = "boeffla_sc_headphone_left";
    private static final String HEADPHONE_RIGHT = "boeffla_sc_headphone_right";
    private static final String MICROPHONE_CALL = "boeffla_sc_microphone_call";
    private static final String MICROPHONE_GENERAL = "boeffla_sc_microphone_gen";
    private static final String MONO_DOWNMIX = "mono_downmix";
    private static final String OVER_SUPPRESS = "over_suppress";
    private static final String PRIVACY_MODE = "privacy_mode";
    private static final String SPEAKER_VOLUME = "boeffla_sc_speaker";
    private static final String SPEAKER_TUNING = "speak_tuning";
    private static final String STEREO_EXPANSION = "stereo_expansion";

    //=========================
    // Overridden Methods
    //=========================

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        getPreferenceManager().setSharedPreferencesName(
                DSPManager.SHARED_PREFERENCES_BASENAME + ".boefflacontrol");
        getPreferenceManager().setSharedPreferencesMode(Context.MODE_MULTI_PROCESS);

        addPreferencesFromResource(R.xml.boefflacontrol_preferences);

        mBoefflaControl = (SwitchPreference) findPreference(BOEFFLA_CONTROL);
        updateBoefflaControl();

        // General
        mDACDirect = (CheckBoxPreference) findPreference(DAC_DIRECT);
        updateDACDirect();
        mDACOversampling = (CheckBoxPreference) findPreference(DAC_OVERSAMPLING);
        updateDACOversampling();
        mFLLTuning = (CheckBoxPreference) findPreference(FLL_TUNING);
        updateFLLTuning();
        mPrivacyMode = (CheckBoxPreference) findPreference(PRIVACY_MODE);
        updatePrivacyMode();

        // Advanced
        mMonoDownmix = (CheckBoxPreference) findPreference(MONO_DOWNMIX);
        updateMonoDownmix();
        mOverSuppress = (CheckBoxPreference) findPreference(OVER_SUPPRESS);
        updateOverSuppress();
        mSpeakerTuning = (CheckBoxPreference) findPreference(SPEAKER_TUNING);
        updateSpeakerTuning();
        mStereoExpansion = (SeekBarPreference) findPreference(STEREO_EXPANSION);
        updateStereoExpansion();
        mStereoExpansion.setOnPreferenceChangeListener(this);

        // Headphone
        mHeadphoneLeft = (SeekBarPreference) findPreference(HEADPHONE_LEFT);
        updateHeadphoneLeft();
        mHeadphoneLeft.setOnPreferenceChangeListener(this);
        mHeadphoneRight = (SeekBarPreference) findPreference(HEADPHONE_RIGHT);
        updateHeadphoneRight();
        mHeadphoneRight.setOnPreferenceChangeListener(this);

        // Microphone
        mMicrophoneCall = (SeekBarPreference) findPreference(MICROPHONE_CALL);
        updateMicrophoneCall();
        mMicrophoneCall.setOnPreferenceChangeListener(this);
        mMicrophoneGeneral = (SeekBarPreference) findPreference(MICROPHONE_GENERAL);
        updateMicrophoneGeneral();
        mMicrophoneGeneral.setOnPreferenceChangeListener(this);

        // SpeakerVolume
        mSpeakerVolume = (SeekBarPreference) findPreference(SPEAKER_VOLUME);
        updateSpeakerVolume();
        mSpeakerVolume.setOnPreferenceChangeListener(this);

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

        if (preference == mBoefflaControl) {
            mBoefflaControl.setChecked(Boolean.valueOf(newValue.toString()));
            getScHelper().applyBoefflaControl(Integer.parseInt(newValue.toString()));
            return true;
        } else if (preference == mDACOversampling) {
            mDACOversampling.setChecked(Boolean.valueOf(newValue.toString()));
            getScHelper().applyDACOversampling(Integer.parseInt(newValue.toString()));
            return true;
        } else if (preference == mDACDirect) {
            mDACDirect.setChecked(Boolean.valueOf(newValue.toString()));
            getScHelper().applyDACDirect(Integer.parseInt(newValue.toString()));
            return true;
        } else if (preference == mFLLTuning) {
            mFLLTuning.setChecked(Boolean.valueOf(newValue.toString()));
            getScHelper().applyFLLTuning(Integer.parseInt(newValue.toString()));
            return true;
        } else if (preference == mHeadphoneLeft) {
            getScHelper().applyHeadphoneLeft(newValue.toString());
            return true;
        } else if (preference == mHeadphoneRight) {
            getScHelper().applyHeadphoneRight(newValue.toString());
            return true;
        } else if (preference == mMicrophoneCall) {
            getScHelper().applyMicrophoneCall(newValue.toString());
            return true;
        } else if (preference == mMicrophoneGeneral) {
            getScHelper().applyMicrophoneGeneral(newValue.toString());
            return true;
        } else if (preference == mMonoDownmix) {
            mMonoDownmix.setChecked(Boolean.valueOf(newValue.toString()));
            getScHelper().applyMonoDownmix(Integer.parseInt(newValue.toString()));
            return true;
        } else if (preference == mOverSuppress) {
            mOverSuppress.setChecked(Boolean.valueOf(newValue.toString()));
            getScHelper().applyOverSuppress(Integer.parseInt(newValue.toString()));
            return true;
        } else if (preference == mPrivacyMode) {
            mPrivacyMode.setChecked(Boolean.valueOf(newValue.toString()));
            getScHelper().applyPrivacyMode(Integer.parseInt(newValue.toString()));
            return true;
        } else if (preference == mSpeakerTuning) {
            mSpeakerTuning.setChecked(Boolean.valueOf(newValue.toString()));
            getScHelper().applySpeakerTuning(Integer.parseInt(newValue.toString()));
            return true;
        } else if (preference == mSpeakerVolume) {
            getScHelper().applySpeakerVolume(newValue.toString());
            return true;
        } else if (preference == mStereoExpansion) {
            getScHelper().applyStereoExpansion(newValue.toString());
            return true;
        }
        return false;
    }

    //=========================
    // Methods
    //=========================

    private void makeToast(String message) {
        makeToast(message, false);
    }

    private void makeToast(String message, boolean query) {
        if (mToast != null) {
            if (!query) {
                mToast.cancel();
            }
        }
        mToast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        mToast.show();
    }

    /**
     * Update values for Enable Boeffla or disable if not available
     */
    private void updateBoefflaControl() {
        if (getScHelper().getBoefflaControl()) {
            mBoefflaControl.setChecked(Boolean.valueOf(Integer.toString(getScHelper().readBoefflaControl())));
        } else {
            mBoefflaControl.setEnabled(false);
        }
    }
    /**
     * Update values for DAC Direct or disable if not available
     */
    private void updateDACDirect() {
        if (getScHelper().getDACDirect()) {
            mDACDirect.setChecked(Boolean.valueOf(Integer.toString(getScHelper().readDACDirect())));
        } else {
            mDACDirect.setEnabled(false);
        }
    }

    /**
     * Update values for DAC Oversampling or disable if not available
     */
    private void updateDACOversampling() {
        if (getScHelper().getDACOversampling()) {
            mDACOversampling.setChecked(Boolean.valueOf(Integer.toString(getScHelper().readDACOversampling())));
        } else {
            mDACOversampling.setEnabled(false);
        }
    }

    /**
     * Update values for FLL Tuning or disable if not available
     */
    private void updateFLLTuning() {
        if (getScHelper().getFLLTuning()) {
            mFLLTuning.setChecked(Boolean.valueOf(Integer.toString(getScHelper().readFLLTuning())));
        } else {
            mFLLTuning.setEnabled(false);
        }
    }

    /**
     * Update values for Headphone (left) or disable if not available
     */
    private void updateHeadphoneLeft() {
        if (getScHelper().getHeadphoneLeft()) {
            mHeadphoneLeft.setValue(getScHelper().readHeadphoneLeft() - 40);
        } else {
            mHeadphoneLeft.setEnabled(false);
        }
    }

    /**
     * Update values for Headphone (right) or disable if not available
     */
    private void updateHeadphoneRight() {
        if (getScHelper().getHeadphoneRight()) {
            mHeadphoneRight.setValue(getScHelper().readHeadphoneRight() - 40);
        } else {
            mHeadphoneRight.setEnabled(false);
        }
    }

    /**
     * Update values for Microphone Call or disable if not available
     */
    private void updateMicrophoneCall() {
        if (getScHelper().getMicrophoneCall()) {
            mMicrophoneCall.setValue(getScHelper().readMicrophoneCall());
        } else {
            mMicrophoneCall.setEnabled(false);
        }
    }

    /**
     * Update values for Microphone General or disable if not available
     */
    private void updateMicrophoneGeneral() {
        if (getScHelper().getMicrophoneGeneral()) {
            mMicrophoneGeneral.setValue(getScHelper().readMicrophoneGeneral());
        } else {
            mMicrophoneGeneral.setEnabled(false);
        }
    }

    /**
     * Update values for Mono Downmix or disable if not available
     */
    private void updateMonoDownmix() {
        if (getScHelper().getMonoDownmix()) {
            mMonoDownmix.setChecked(Boolean.valueOf(Integer.toString(getScHelper().readMonoDownmix())));
        } else {
            mMonoDownmix.setEnabled(false);
        }
    }

    /**
     * Update values for Over Suppress or disable if not available
     */
    private void updateOverSuppress() {
        if (getScHelper().getOverSuppress()) {
            mOverSuppress.setChecked(Boolean.valueOf(Integer.toString(getScHelper().readOverSuppress())));
        } else {
            mOverSuppress.setEnabled(false);
        }
    }

    /**
     * Update values for Privacy Mode or disable if not available
     */
    private void updatePrivacyMode() {
        if (getScHelper().getPrivacyMode()) {
            mPrivacyMode.setChecked(Boolean.valueOf(Integer.toString(getScHelper().readPrivacyMode())));
        } else {
            mPrivacyMode.setEnabled(false);
        }
    }

    /**
     * Update values for Speaker Tuning or disable if not available
     */
    private void updateSpeakerTuning() {
        if (getScHelper().getSpeakerTuning()) {
            mSpeakerTuning.setChecked(Boolean.valueOf(Integer.toString(getScHelper().readSpeakerTuning())));
        } else {
            mSpeakerTuning.setEnabled(false);
        }
    }

    /**
     * Update values for Speaker Volume or disable if not available
     */
    private void updateSpeakerVolume() {
        if (getScHelper().getSpeakerVolume()) {
            mSpeakerVolume.setValue(getScHelper().readSpeakerVolume());
        } else {
            mSpeakerVolume.setEnabled(false);
        }
    }

    /**
     * Update values for Stereo Expansion or disable if not available
     */
    private void updateStereoExpansion() {
        if (getScHelper().getStereoExpansion()) {
            mStereoExpansion.setValue(getScHelper().readStereoExpansion());
        } else {
            mStereoExpansion.setEnabled(false);
        }
    }

    /**
     * Everyone hates typing much, so we created a method for doing the same with less typing.
     *
     * @return An instance of the boeffla Control Helper
     */
    private BoefflaControlHelper getScHelper() {
        return BoefflaControlHelper.getBoefflaControlHelper(getActivity());
    }
}
