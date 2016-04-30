package org.mariotaku.commons.parcel;

import android.os.Parcel;
import android.os.Parcelable;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by mariotaku on 16/4/30.
 */
public class ParcelUtilsTest {

    @Test
    public void testClone() {
        TestObject object = new TestObject();
        object.setIntField(10);
        Assert.assertEquals(object, ParcelUtils.clone(object));
    }

    static class TestObject implements Parcelable {
        public static final Creator<TestObject> CREATOR = new Creator<TestObject>() {
            @Override
            public TestObject createFromParcel(Parcel in) {
                return new TestObject(in);
            }

            @Override
            public TestObject[] newArray(int size) {
                return new TestObject[size];
            }
        };
        int intField;

        protected TestObject(Parcel in) {
            intField = in.readInt();
        }

        TestObject() {

        }

        public int getIntField() {
            return intField;
        }

        public void setIntField(int intField) {
            this.intField = intField;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(intField);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestObject object = (TestObject) o;

            return intField == object.intField;

        }

        @Override
        public int hashCode() {
            return intField;
        }
    }

}
