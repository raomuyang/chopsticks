package cn.atomicer.chopsticks.function;

/**
 * Created by Rao-Mengnan
 * on 2018/1/30.
 */
public interface Function4<T1, T2, T3, T4, R> {
    R apply(T1 key1, T2 key2, T3 key3, T4 key4) throws Exception;
}
