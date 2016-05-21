package example.irkitsample.irkit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * IRKit信号情報
 */
public class IRKitMessage {
    public static final String FORMAT = "format";
    public static final String FREQ = "freq";
    public static final String DATA = "data";

    private String mFormat;
    private int mFreq;
    private JSONArray mData;

    public IRKitMessage(String format, int freq, JSONArray data){
        mFormat = format;
        mFreq = freq;
        mData = data;
    }

    public IRKitMessage(JSONObject jsonObject){
        if (jsonObject != null){
            try {
                mFormat = jsonObject.getString(FORMAT);
                mFreq = jsonObject.getInt(FREQ);
                mData = jsonObject.getJSONArray(DATA);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getFormat() {
        return mFormat;
    }

    public int getFreq() {
        return mFreq;
    }

    public JSONArray getData() {
        return mData;
    }

    public JSONObject toJSONObject(){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(FORMAT, mFormat);
            jsonObject.put(FREQ,mFreq);
            jsonObject.put(DATA,mData);
        } catch (JSONException e) {
            return null;
        }

        return jsonObject;
    }

    @Override
    public String toString(){
        return
            FORMAT + ":" + mFormat + "\n"
            + FREQ + ":" + mFreq + "\n"
            + DATA + ":" + mData + "\n";
    }
}
