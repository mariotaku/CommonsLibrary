package org.mariotaku.commons.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtils {

    private static PreCopyListener EMPTY_PRE_COPY = new PreCopyListener() {
        @Override
        public long onPreCopy(long max) {
            return max;
        }
    };
    private static CopyListener EMPTY_COPY = new CopyListener() {
        @Override
        public boolean onCopied(long len, long total) {
            return true;
        }
    };

    public static long copy(InputStream is, OutputStream os, PreCopyListener preCopyListener,
                            CopyListener copyListener)
            throws IOException {
        byte[] buf = new byte[8192];
        int len;
        long total = 0;
        if (preCopyListener == null) {
            preCopyListener = EMPTY_PRE_COPY;
        }
        if (copyListener == null) {
            copyListener = EMPTY_COPY;
        }
        while ((len = is.read(buf, 0, (int) preCopyListener.onPreCopy(buf.length))) != -1) {
            os.write(buf, 0, len);
            total += len;
            if (!copyListener.onCopied(len, total)) break;
        }
        return total;
    }

    public interface PreCopyListener {
        long onPreCopy(long max);
    }

    public interface CopyListener {
        boolean onCopied(long len, long total);
    }
}
