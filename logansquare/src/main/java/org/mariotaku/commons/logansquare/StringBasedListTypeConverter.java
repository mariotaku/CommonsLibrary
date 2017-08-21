package org.mariotaku.commons.logansquare;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;

import java.io.IOException;

/**
 * Created by mariotaku on 2017/8/21.
 */
public abstract class StringBasedListTypeConverter<T> extends ListTypeConverter<T> {
    /**
     * Called to convert a String into an object of type T.
     *
     * @param string The String parsed from JSON.
     */
    public abstract T getItemFromString(String string);

    /**
     * Called to convert a an object of type T into a String.
     *
     * @param item The object being converted.
     */
    public abstract String convertItemToString(T item);

    @Override
    public T parseItem(JsonParser jsonParser) throws IOException {
        return getItemFromString(jsonParser.getValueAsString(null));
    }

    @Override
    public void serializeItem(T object, JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeString(convertItemToString(object));
    }
}
