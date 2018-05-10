package cn.atomicer.chopsticks.stream;

import cn.atomicer.chopsticks.function.Action;
import cn.atomicer.chopsticks.function.Consumer;
import cn.atomicer.chopsticks.exception.OnErrorNotCatcherException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Rao Mengnan
 *         on 2018/5/9.
 */
public class TaskTest {
    @Test
    public void testNormalCall() throws Exception {
        final int value = 1;

        new Task<Integer>().onNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer e) throws Exception {
                assertEquals(value, e.intValue());
            }
        }).accept(value);
    }

    @Test
    public void testTaskStream() throws Exception {
        final List<Integer> result = new ArrayList<>();

        Task<Integer> task1 = new Task<Integer>().onNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer e) throws Exception {
                result.add(e + 1);
            }
        });

        Task<Integer> task2 = new Task<Integer>().onNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer e) throws Exception {
                result.add(e + 2);
            }
        });

        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer e) throws Exception {
                result.add(e + 3);
            }
        };

        Action onSucc = new Action() {
            @Override
            public void run() throws Exception {
                result.add(4);
            }
        };

        new Task<Integer>()
                .onNext(task1)
                .onNext(task2)
                .onNext(consumer)
                .onSuccess(onSucc)
                .accept(0);

        assertEquals(4, result.size());
        assertEquals(Arrays.asList(1, 2, 3, 4), result);
    }


    @Test(expected = OnErrorNotCatcherException.class)
    public void testCatchException() throws Exception {
        final List<Object> result = new ArrayList<>();
        new Task<>()
                .onNext(new Consumer<Object>() {
                    @Override
                    public void accept(Object e) throws Exception {
                        int a = Integer.valueOf("one");
                        System.out.println(a);
                    }
                })
                .onError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        result.add(e);
                    }
                })
                .accept(0);
        assertEquals(1, result.size());
        assertEquals(NumberFormatException.class, result.get(0).getClass());


        new Task<>()
                .onSuccess(new Action() {
                    @Override
                    public void run() throws Exception {
                        int a = Integer.valueOf("one");
                        System.out.println(a);
                    }
                })
                .onError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        result.add(e);
                    }
                })
                .accept(0);
        assertEquals(2, result.size());
        assertEquals(NumberFormatException.class, result.get(1).getClass());

        new Task<>().onNext(new Consumer<Object>() {
            @Override
            public void accept(Object e) throws Exception {
                int a = Integer.valueOf("one");
                System.out.println(a);
            }
        }).accept(0);
    }

}