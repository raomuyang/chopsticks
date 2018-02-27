package cn.atomicer.chopsticks.function;

/**
 * Created by Rao-Mengnan
 * on 2018/1/30.
 */
public interface Function2<T1, T2, R> {
    R apply(T1 key1, T2 key2) throws Exception;
}
