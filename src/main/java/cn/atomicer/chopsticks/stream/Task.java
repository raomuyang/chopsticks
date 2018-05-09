package cn.atomicer.chopsticks.stream;

import cn.atomicer.chopsticks.function.Action;

import static cn.atomicer.chopsticks.common.AssertUtils.assertNotNull;

/**
 * Created by Rao-Mengnan
 * on 2017/8/7.
 */
public class Task<T> {
    private Stream stream = new Stream();
    private Action<T> action;

    public static <T> Task<T> create(Action<T> action) {
        Task<T> t = new Task<>();
        t.action = action;
        return t;
    }

    public Stream stream() {
        return this.stream;
    }

    public void call(T t) throws Exception {

        assertNotNull(t, "object is null");
        try {
            Task<T> task = stream.next();
            while (task != null) {
                task.action.run(t);
                task = stream.next();
            }
            if (stream.doOnSuccess != null) {
                stream.doOnSuccess.call(t);
            }
        } catch (Throwable e) {
            if (stream.doOnError != null) {
                stream.doOnError.call(e);
            } else {
                throw e;
            }
        }
    }

    public class Stream {
        private Task<T> head;
        private Task<T> next;
        private Task<Throwable> doOnError;
        private Task<T> doOnSuccess;

        Stream() {
            head = Task.this;
        }

        public Stream onNext(Action<T> action) {
            assertNotNull(action, "function is null");
            Task<T> task = Task.create(action);
            if (head.action == null) {
                head = task;
                return this;
            }

            if (next == null) {
                next = task;
            } else {
                next.stream.onNext(action);
            }
            return this;
        }

        public Stream onError(Task<Throwable> errorTask) {
            assertNotNull(errorTask, "error task is null");
            this.doOnError = errorTask;
            return this;
        }

        public Stream onSuccess(Task<T> doOnSuccess) {
            assertNotNull(doOnSuccess, "on success function is null");
            this.doOnSuccess = doOnSuccess;
            return this;
        }

        public void start(T t) throws Exception {
            assertNotNull(t, "object is null");
            Task.this.call(t);
        }

        private Task<T> next() {
            Task<T> task = head;
            if (task == null) {
                return null;
            }
            head = next;
            next = next != null ? next.stream.next : null;
            return task;
        }
    }

}
