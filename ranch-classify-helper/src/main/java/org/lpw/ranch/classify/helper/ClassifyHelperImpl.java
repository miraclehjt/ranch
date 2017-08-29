package org.lpw.ranch.classify.helper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.lpw.ranch.util.ServiceHelperSupport;
import org.lpw.tephra.util.Numeric;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lpw
 */
@Service("ranch.classify.helper")
public class ClassifyHelperImpl extends ServiceHelperSupport implements ClassifyHelper {
    @Inject
    private Numeric numeric;
    @Value("${ranch.classify.key:ranch.classify}")
    private String key;

    @Override
    public JSONObject find(String code, String key) {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("code", code);
        parameter.put("key", key);

        return carousel.service(this.key + ".find", null, parameter, true, JSONObject.class);
    }

    @Override
    public String value(String code, String key) {
        return find(code, key).getString("value");
    }

    @Override
    public int valueAsInt(String code, String key, int defaultValue) {
        return numeric.toInt(value(code, key), defaultValue);
    }

    @Override
    public double valueAsDouble(String code, String key, double defaultValue) {
        return numeric.toDouble(value(code, key), defaultValue);
    }

    @Override
    public JSONArray list(String code, String key, String name) {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("code", code);
        parameter.put("key", key);
        parameter.put("name", name);

        return carousel.service(this.key + ".list", null, parameter, true, JSONArray.class);
    }

    @Override
    public JSONArray fill(JSONArray array, String code, String[] names) {
        if (validator.isEmpty(array) || validator.isEmpty(code) || validator.isEmpty(names))
            return array;

        for (int i = 0, size = array.size(); i < size; i++) {
            JSONObject object = array.getJSONObject(i);
            for (String name : names) {
                JSONObject classify = find(code, object.getString(name));
                if (!classify.isEmpty())
                    object.put(name, classify);
            }
        }

        return array;
    }

    @Override
    protected String getKey() {
        return key;
    }
}
