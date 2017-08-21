package org.mariotaku.commons.emojione;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by mariotaku on 2017/4/26.
 */

public class AbsShortnameToUnicodeTranslatorTest {

    private ShortnameToUnicodeTranslator translator;
    private Gson gson;
    private ValidateModel model;

    @Before
    public void setUp() throws IOException {
        gson = new Gson();
        translator = new ShortnameToUnicodeTranslator();
        try (Reader reader = new InputStreamReader(AbsShortnameToUnicodeTranslatorTest.class.getResourceAsStream("/validate.json"))) {
            model = gson.fromJson(reader, ValidateModel.class);
        }
    }

    @Test
    public void testTranslate() throws IOException {
        ValidateModel.Data data = model.findData("shortnameToUnicode");
        for (ValidateModel.Data.Test test : data.getTests()) {
            Assert.assertEquals(test.getExpected(), translator.translate(test.getText()));
        }
    }

}
