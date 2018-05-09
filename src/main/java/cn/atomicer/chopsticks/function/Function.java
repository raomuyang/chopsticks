package cn.atomicer.chopsticks.function;

/**
 * Created by Rao-Mengnan
 * on 2018/1/30.
 */
public interface Function<T, R> {
    R apply(T key) throws Exception;
}
