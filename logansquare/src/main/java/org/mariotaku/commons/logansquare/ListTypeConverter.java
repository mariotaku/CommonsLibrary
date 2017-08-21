package org.mariotaku.commons.logansquare;

import com.bluelinelabs.logansquare.typeconverters.TypeConverter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mariotaku on 2017/8/21.
 */
public abstract class ListTypeConverter<T> implements TypeConverter<List<T>> {

    public abstract T parseItem(JsonParser jsonParser) throws IOException;

    public abstract void serializeItem(T item, JsonGenerator jsonGenerator) throws IOException;

    @Override
    public List<T> parse(JsonParser jsonParser) throws IOException {
        if (jsonParser.getCurrentToken() != JsonToken.START_ARRAY) return null;
        ArrayList<T> list = new ArrayList<>();
        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
            list.add(parseItem(jsonParser));
        }
        return list;
    }

    @Override
    public void serialize(List<T> list, String fieldName, boolean writeFieldNameForObject, JsonGenerator jsonGenerator) throws IOException {
        if (writeFieldNameForObject) {
            jsonGenerator.writeFieldName(fieldName);
        }
        if (list == null) {
            jsonGenerator.writeNull();
            return;
        }
        jsonGenerator.writeStartArray();
        for (T item : list) {
            if (item != null) {
                serializeItem(item, jsonGenerator);
            }
        }
        jsonGenerator.writeEndArray();
    }
}
