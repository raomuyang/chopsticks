package cn.atomicer.chopsticks.stream;

import cn.atomicer.chopsticks.exception.OnErrorNotCatcherException;
import cn.atomicer.chopsticks.function.Function;

import java.util.LinkedList;
import java.util.List;

/**
 *
 *  Examples:
 *  This code show how to process data in pipeline <pre>{@code
 *         Pipeline<Integer> pipeline = new Pipeline<>();
 *         // '0' -> 0 -> 0 + 1 -> 1 / 1 -> 1 - 1 -> 0 + 1 -> 0
 *         pipeline
 *              .addPipe((Object s) -> Integer.valueOf((String) s))
 *              .addPipe(integer -> (Integer) integer + 1)
 *              .addPipe(integer -> 1 / (Integer) integer)
 *              .addPipe(integer -> (Integer) integer - 1)
 *              .addPipe(integer -> (Integer) integer + 1);
 *
 *         Integer res = pipeline.process("0");
 *         assertEquals(1, res.intValue());
 *  }</pre>
 *  This code show how to catch exceptions in pipeline <pre>{@code
 *        Pipeline<Integer> pipeline = new Pipeline<>();
 *        // '0' -> 0 -> 0 + 1 -> -> 1 - 1 ->  1 / 0 -> throw
 *        pipeline
 *             .addPipe(s -> Integer.valueOf((String) s))
 *             .addPipe(integer -> (Integer) integer + 1)
 *             .addPipe(integer -> (Integer) integer - 1)
 *             .addPipe(integer -> 1 / (Integer) integer)
 *             .addPipe(integer -> (Integer) integer + 1);
 *        pipeline.addErrorPipe(throwable -> null);
 *        pipeline.process("0");
 *
 * }</pre>
 * @author Rao Mengnan
 *         on 2018/5/9.
 */
public class Pipeline<T> {
    private List<Function> pipes;
    private List<Function<? extends Throwable, ?>> errorPipes;

    public Pipeline() {
        this.pipes = new LinkedList<>();
        this.errorPipes = new LinkedList<>();
    }

    public Pipeline addPipe(Function pipeline) {
        this.pipes.add(pipeline);
        return this;
    }

    public Pipeline addErrorPipe(Function<? extends Throwable, ?> pipeline) {
        this.errorPipes.add(pipeline);
        return this;
    }


    @SuppressWarnings("unchecked")
    public T process(Object obj) throws Throwable {
        if (pipes.size() == 0) return null;
        DataTransform dataTransform = new DataTransform(obj);

        try {
            for (Function pipe: pipes) {
                dataTransform.accept(pipe);
            }
            return (T) dataTransform.value;
        } catch (Throwable e) {
            processError(e);
        }
        return null;
    }

    private void processError(Throwable e) throws Throwable {
        if (e == null) return;
        if (errorPipes.size() == 0) throw e;

        try {

            DataTransform pipe = new DataTransform(e);
            for (Function pipeline: errorPipes) {
                pipe.accept(pipeline);
            }
        } catch (Throwable t) {
            throw new OnErrorNotCatcherException("error pipeline: process failed.", t);
        }

    }

    class DataTransform {
        Object value;

        DataTransform(Object value) {
            this.value = value;
        }

        @SuppressWarnings("unchecked")
        void accept(Function pipeline) throws Exception {
            this.value = pipeline.apply(value);
        }
    }

}
