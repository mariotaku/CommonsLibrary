package org.mariotaku.commons.emojione;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mariotaku on 2017/4/26.
 */

public class ValidateModel {

    @SerializedName("data")
    List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public Data findData(String module) {
        for (Data item : data) {
            if (module.equals(item.module)) return item;
        }
        return null;
    }

    public static class Data {

        @SerializedName("module")
        String module;

        @SerializedName("tests")
        List<Test> tests;

        public String getModule() {
            return module;
        }

        public List<Test> getTests() {
            return tests;
        }

        public static class Test {
            @SerializedName("description")
            String description;
            @SerializedName("text")
            String text;
            @SerializedName("expected")
            String expected;

            public String getDescription() {
                return description;
            }

            public String getText() {
                return text;
            }

            public String getExpected() {
                return expected;
            }
        }

    }
}
