package cn.atomicer.chopsticks.stream;

import cn.atomicer.chopsticks.annotation.NotNull;
import cn.atomicer.chopsticks.exception.AlreadyDisposed;
import cn.atomicer.chopsticks.exception.OnErrorNotCatcherException;
import cn.atomicer.chopsticks.function.Action;
import cn.atomicer.chopsticks.function.Consumer;
import cn.atomicer.chopsticks.function.Functions;

import java.util.LinkedList;
import java.util.List;

import static cn.atomicer.chopsticks.common.AssertHelper.assertNotNull;

/**
 *
 * This is the main class for perform a series of actions.
 *
 * <p>Here is an example of how to establish a stream to perform:
 *
 * <pre>
 *
 * final List<Integer> result = new ArrayList<>();
 *
 *  Task<Integer> task1 = new Task<Integer>().onNext((e) -> result.append(e + 1));
 *
 *  Task<Integer> task2 = new Task<Integer>().onNext((e) -> result.append(e + 2));
 *
 * Consumer<Integer> consumer = (e) -> result.append(e + 3);
 *
 *  Action onSucc = () -> result.add(4);
 *
 *  new Task<Integer>()
 *      .onNext(task1)
 *      .onNext(task2)
 *      .onNext(consumer)
 *      .onSuccess(onSucc)
 *      .accept(0);
 *
 * assertEquals(Arrays.asList(1, 2, 3, 4), result);
 * </pre></p>
 *
 * @author Rao-Mengnan
 * create on 2017/8/7.
 */
public class Task<T> implements Consumer<T> {

    private List<Consumer<T>> consumers;
    private Action doOnSuccess;
    private Consumer<Throwable> doOnError;
    private boolean disposed;


    public Task() {
        this.consumers = new LinkedList<>();
        this.doOnSuccess = Functions.EMPTY_ACTION;
        this.doOnError = DEFAULT_ON_ERROR;
    }

    /**
     * Dispose task will interrupt the flow
     */
    public void dispose() throws AlreadyDisposed {
        if (this.disposed) throw new AlreadyDisposed("dispose action duplication");
        this.disposed = true;
    }

    /**
     * Emmit the source data to execute the flow actions, the data will be past all the actions
     * @param t source data
     * @throws Exception process exception
     */
    @Override
    public void accept(@NotNull T t) throws Exception {
        assertNotNull(t, "object is null");

        for (Consumer<T> consumer : consumers) {
            if (disposed) break;
            try {
                consumer.accept(t);
            } catch (Throwable e) {
                doOnErrorAction(e);
            }
        }
        try {
            if (!disposed) doOnSuccess.run();
        } catch (Throwable e) {
            doOnErrorAction(e);
        }
    }


    /**
     * Add an action to the tail of the task flow
     * @param consumer the action to invoke when the Task calls.
     * @return self
     */
    public Task<T> onNext(@NotNull Consumer<T> consumer) {
        assertNotNull(consumer, "param is null");
        consumers.add(consumer);
        return this;
    }

    /**
     * Set onError event, it will be occurred on error happened.
     * @param onError the action to invoke if the source Task calls {@code doOnErrorAction}
     * @return self
     */
    public Task<T> onError(@NotNull Consumer<Throwable> onError) {
        assertNotNull(onError, "param is null");
        this.doOnError = onError;
        return this;
    }

    /**
     * Set onSuccess event, it will be executed after task flow run successfully.
     * @param doOnSuccess the action to invoke at the end of flow
     * @return self
     */
    public Task<T> onSuccess(@NotNull Action doOnSuccess) {
        assertNotNull(doOnSuccess, "param is null");
        this.doOnSuccess = doOnSuccess;
        return this;
    }

    private void doOnErrorAction(Throwable e) throws Exception {
        dispose();
        doOnError.accept(e);
    }

    private static final DefaultOnError DEFAULT_ON_ERROR = new DefaultOnError();
    private static class DefaultOnError implements Consumer<Throwable> {
        @Override
        public void accept(Throwable e) throws Exception {
            throw new OnErrorNotCatcherException(e);
        }
    }

}
