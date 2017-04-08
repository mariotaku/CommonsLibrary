package org.mariotaku.commons.logansquare;

import com.bluelinelabs.logansquare.Commons_ParameterizedTypeAccessor;
import com.bluelinelabs.logansquare.JsonMapper;
import com.bluelinelabs.logansquare.LoganSquare;
import com.bluelinelabs.logansquare.ParameterizedType;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by mariotaku on 16/2/19.
 */
public class LoganSquareMapperFinder {
    private static FutureExecutor defaultExecutor = new FutureExecutor() {

        private final ExecutorService pool = Executors.newSingleThreadExecutor();

        @Override
        public <T> Future<T> submit(final Callable<T> callable) {
            return pool.submit(callable);
        }
    };

    private LoganSquareMapperFinder() {
    }

    public static <T> JsonMapper<T> mapperFor(Class<T> cls) throws ClassLoaderDeadLockException {
        return mapperFor(Commons_ParameterizedTypeAccessor.<T>create(cls));
    }

    public static <T> JsonMapper<T> mapperFor(Type type) throws ClassLoaderDeadLockException {
        return mapperFor(Commons_ParameterizedTypeAccessor.<T>create(type));
    }

    public static <T> JsonMapper<T> mapperFor(final ParameterizedType<T> type) throws ClassLoaderDeadLockException {
        final Future<JsonMapper<T>> future = defaultExecutor.submit(new Callable<JsonMapper<T>>() {
            @Override
            public JsonMapper<T> call() {
                return LoganSquare.mapperFor(type);
            }
        });
        final JsonMapper<T> mapper;
        //noinspection TryWithIdenticalCatches
        try {
            mapper = future.get(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new ClassLoaderDeadLockException("ClassLoader deadlock at " + Thread.currentThread().toString(), e);
        }
        return mapper;
    }

    public static synchronized void setDefaultExecutor(FutureExecutor executor) {
        if (executor == null) throw new IllegalArgumentException();
        defaultExecutor = executor;
    }

    public static class ClassLoaderDeadLockException extends IOException {
        public ClassLoaderDeadLockException() {
            super();
        }

        public ClassLoaderDeadLockException(String detailMessage) {
            super(detailMessage);
        }

        public ClassLoaderDeadLockException(String message, Throwable cause) {
            super(message, cause);
        }

        public ClassLoaderDeadLockException(Throwable cause) {
            super(cause);
        }
    }

    public interface FutureExecutor {
        <T> Future<T> submit(Callable<T> callable);
    }
}
