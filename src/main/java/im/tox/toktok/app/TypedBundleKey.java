package im.tox.toktok.app;

import android.os.Bundle;

public class TypedBundleKey {
    public interface KeyValue<A> {
        void put(Bundle bundle);
    }

    public static final class StringKeyValue implements KeyValue<String> {
        private final String id;
        private final String value;

        StringKeyValue(String id, String value) {
            this.id = id;
            this.value = value;
        }

        @Override
        public void put(Bundle bundle) {
            bundle.putString(id, value);
        }
    }

    public static final class IntKeyValue implements KeyValue<Integer> {
        private final String id;
        private final int value;

        IntKeyValue(String id, int value) {
            this.id = id;
            this.value = value;
        }

        @Override
        public void put(Bundle bundle) {
            bundle.putInt(id, value);
        }
    }

    public static final class StringKey {
        private final String id;

        StringKey(String id) {
            this.id = id;
        }

        public StringKeyValue map(String value) {
            return new StringKeyValue(id, value);
        }

        public String get(Bundle bundle) {
            return bundle.getString(id);
        }
    }

    public static final class IntKey {
        private final String id;

        IntKey(String id) {
            this.id = id;
        }

        public IntKeyValue map(int value) {
            return new IntKeyValue(id, value);
        }

        public int get(Bundle bundle) {
            return bundle.getInt(id);
        }
    }

    public static Bundle SBundle(KeyValue... keyValues) {
        Bundle bundle = new Bundle();
        for (KeyValue<?> kv : keyValues) {
            kv.put(bundle);
        }
        return bundle;
    }
}
