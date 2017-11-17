package org.mariotaku.commons.emojione;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BenchmarkTest {

    private ShortnameToUnicodeTranslator translator;
    private Gson gson;
    private ValidateModel model;

    @Before
    public void setUp() throws IOException, ClassNotFoundException {
        gson = new Gson();
        translator = new ShortnameToUnicodeTranslator();
        try (Reader reader = new InputStreamReader(AbsShortnameToUnicodeTranslatorTest.class.getResourceAsStream("/validate.json"))) {
            model = gson.fromJson(reader, ValidateModel.class);
        }
        Class.forName(Emojione.class.getName());
    }

    @Test
    public void testEmojione() {
        ValidateModel.Data data = model.findData("shortnameToUnicode");
        for (int i = 0; i < 1000000; i++) {
            for (ValidateModel.Data.Test test : data.getTests()) {
                Emojione.shortnameToUnicode(test.getText() + "i" + i);
            }
        }
    }

    @Test
    public void testLibrary() {
        ValidateModel.Data data = model.findData("shortnameToUnicode");
        for (int i = 0; i < 1000000; i++) {
            for (ValidateModel.Data.Test test : data.getTests()) {
                translator.translate(test.getText() + "i" + i);
            }
        }
    }

}
