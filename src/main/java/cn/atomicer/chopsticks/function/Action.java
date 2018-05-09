package cn.atomicer.chopsticks.function;

/**
 * Created by Rao-Mengnan
 * on 2017/8/7.
 */
public interface Action<T> {
    void run(T e) throws Exception;
}
