package org.mariotaku.commons.logansquare;

import com.bluelinelabs.logansquare.LoganSquare;
import com.bluelinelabs.logansquare.typeconverters.TypeConverter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Convert json value to String directly
 * <br>
 * Created by mariotaku on 16/4/5.
 */
public class JsonStringConverter implements TypeConverter<String> {
    @Override
    public String parse(JsonParser jsonParser) throws IOException {
        JsonToken token = jsonParser.getCurrentToken();
        if (token == null) {
            token = jsonParser.nextToken();
        }
        if (token == null) {
            return null;
        }
        StringWriter sw = new StringWriter();
        JsonGenerator generator = LoganSquare.JSON_FACTORY.createGenerator(sw);
        int depth = 0;
        while (token != null) {
            switch (token) {
                case NOT_AVAILABLE: {
                    break;
                }
                case START_OBJECT: {
                    generator.writeStartObject();
                    depth++;
                    break;
                }
                case END_OBJECT: {
                    generator.writeEndObject();
                    depth--;
                    break;
                }
                case START_ARRAY: {
                    generator.writeStartArray();
                    depth++;
                    break;
                }
                case END_ARRAY: {
                    generator.writeEndArray();
                    depth--;
                    break;
                }
                case FIELD_NAME: {
                    generator.writeFieldName(jsonParser.getCurrentName());
                    break;
                }
                case VALUE_EMBEDDED_OBJECT: {
                    throw new UnsupportedOperationException();
                }
                case VALUE_STRING: {
                    generator.writeString(jsonParser.getValueAsString());
                    break;
                }
                case VALUE_NUMBER_INT: {
                    generator.writeNumber(jsonParser.getValueAsLong());
                    break;
                }
                case VALUE_NUMBER_FLOAT: {
                    generator.writeNumber(jsonParser.getValueAsDouble());
                    break;
                }
                case VALUE_TRUE: {
                    generator.writeBoolean(true);
                    break;
                }
                case VALUE_FALSE: {
                    generator.writeBoolean(false);
                    break;
                }
                case VALUE_NULL: {
                    generator.writeNull();
                    break;
                }
            }
            if (depth <= 0) break;
            token = jsonParser.nextToken();
        }
        generator.flush();
        sw.flush();
        return sw.toString();
    }

    @Override
    public void serialize(String object, String fieldName, boolean writeFieldNameForObject, JsonGenerator jsonGenerator) throws IOException {
        if (writeFieldNameForObject) {
            jsonGenerator.writeFieldName(fieldName);
        }
        if (object == null) {
            jsonGenerator.writeNull();
        } else {
            jsonGenerator.writeRawValue(object);
        }
    }
}