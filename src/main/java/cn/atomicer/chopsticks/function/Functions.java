package cn.atomicer.chopsticks.function;

/**
 * @author Rao Mengnan
 *         on 2018/5/9.
 */
public class Functions {
    public static final Action EMPTY_ACTION = new Action() {
        @Override
        public void run() throws Exception {

        }
    };

    public static final Consumer EMPTY_CONSUMER = new Consumer() {
        @Override
        public void accept(Object e) throws Exception {

        }
    };

    public static final Function EMPTY_FUNCTION = new Function() {
        @Override
        public Object apply(Object key) throws Exception {
            return null;
        }
    };
}
