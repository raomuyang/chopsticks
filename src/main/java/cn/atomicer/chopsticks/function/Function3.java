package cn.atomicer.chopsticks.function;

/**
 * Created by Rao-Mengnan
 * on 2018/1/30.
 */
public interface Function3<T1, T2, T3, R> {
    R apply(T1 key1, T2 key2, T3 key3) throws Exception;
}
