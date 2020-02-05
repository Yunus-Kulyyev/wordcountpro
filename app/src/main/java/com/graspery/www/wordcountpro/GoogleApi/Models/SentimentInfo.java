package com.graspery.www.wordcountpro.GoogleApi.Models;

import com.google.api.services.language.v1.model.Sentiment;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * A {@link Parcelable} variant of {@link Sentiment}.
 */
public class SentimentInfo implements Parcelable {

    public static final Creator<SentimentInfo> CREATOR = new Creator<SentimentInfo>() {
        @Override
        public SentimentInfo createFromParcel(Parcel in) {
            return new SentimentInfo(in);
        }

        @Override
        public SentimentInfo[] newArray(int size) {
            return new SentimentInfo[size];
        }
    };

    /**
     * Score of the sentiment in the [-1.0, 1.0] range.
     */
    public final float score;

    /**
     * The absolute magnitude of sentiment in the [0, +inf) range.
     */
    public final float magnitude;

    public SentimentInfo(Sentiment sentiment) {
        score = sentiment.getScore();
        magnitude = sentiment.getMagnitude();
    }

    protected SentimentInfo(Parcel in) {
        score = in.readFloat();
        magnitude = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeFloat(score);
        out.writeFloat(magnitude);
    }

}