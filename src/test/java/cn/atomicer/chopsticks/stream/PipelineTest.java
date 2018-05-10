package cn.atomicer.chopsticks.stream;

import cn.atomicer.chopsticks.function.Function;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Rao Mengnan
 *         on 2018/5/9.
 */
public class PipelineTest {

    @Test
    public void testProcess() throws Throwable {
        Pipeline<Integer> pipeline = new Pipeline<>();
        // '0' -> 0 -> 0 + 1 -> 1 / 1 -> 1 - 1 -> 0 + 1 -> 0
        pipeline
                .addPipe(fCast)
                .addPipe(fAdd)
                .addPipe(fDivide)
                .addPipe(fMinus)
                .addPipe(fAdd);

        Integer res = pipeline.process("0");
        assertEquals(1, res.intValue());
    }

    @Test(expected = ArithmeticException.class)
    public void testThrowExceptionInProcess() throws Throwable {
        Pipeline<Integer> pipeline = new Pipeline<>();
        // '0' -> 0 -> 0 + 1 -> -> 1 - 1 ->  1 / 0 -> throw
        pipeline
                .addPipe(fCast)
                .addPipe(fAdd)
                .addPipe(fMinus)
                .addPipe(fDivide)
                .addPipe(fAdd); // interrupted
        pipeline.process("0");
    }

    @Test
    public void testProcessOnError() throws Throwable {
        Pipeline<Integer> pipeline = new Pipeline<>();
        // '0' -> 0 -> 0 + 1 -> -> 1 - 1 ->  1 / 0 -> throw
        pipeline
                .addPipe(fCast)
                .addPipe(fAdd)
                .addPipe(fMinus)
                .addPipe(fDivide)
                .addPipe(fCast); // interrupted

        pipeline.addErrorPipe(fCatch);
        Integer result = pipeline.process("0");
        assertNull(result);

    }


    private static final Function<Object, Integer> fCast = new Function<Object, Integer>() {
        @Override
        public Integer apply(Object integer) throws Exception {
            return Integer.valueOf((String) integer);
        }
    };

    private static final Function<Object, Integer> fAdd = new Function<Object, Integer>() {
        @Override
        public Integer apply(Object integer) throws Exception {
            return (Integer) integer + 1;
        }
    };

    private static final Function<Object, Integer> fDivide = new Function<Object, Integer>() {
        @Override
        public Integer apply(Object integer) throws Exception {
            return 1 / (Integer) integer;
        }
    };

    private static final Function<Object, Integer> fMinus = new Function<Object, Integer>() {
        @Override
        public Integer apply(Object integer) throws Exception {
            return (Integer) integer - 1;
        }
    };

    private static final Function<Throwable, Throwable> fCatch = new Function<Throwable, Throwable>() {
        @Override
        public Throwable apply(Throwable throwable) throws Exception {
            return throwable;
        }
    };

}