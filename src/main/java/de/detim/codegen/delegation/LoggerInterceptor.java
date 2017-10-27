package de.detim.codegen.delegation;

import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.Super;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

class LoggerInterceptor {

    // A method call that has been delegated to another may invoke the original method.
    // Therefore the supercall can be injected: @SuperCall Callable<List<String>> zuper
    // Instead of Callable -> Runnable is allowed, but the return value will get dropped
    //@BindingPriority(1)
    public static List<String> log(@SuperCall Callable<List<String>> zuper)
    throws Exception {
        System.out.println("Calling database @SuperCall");
        try {
            // This will call the overridden method with the original parameters
            return zuper.call();
        } finally {
            System.out.println("Returned from database @SuperCall");
        }
    }

    public static List<String> log(String info, @Super MemoryDatabase zuper) {
        System.out.println("Calling database @Super");
        try {
            // This will call the overridden method with different parameters
            // The @Super object instance however has a different identity which
            // denys it access to the originals attributes. By default it is
            // constructed using the default constructor.
            return zuper.load(info + " (logged access)");
        } finally { System.out.println("Returned from database @Super");
        }

    }
    /*

    // To intercept methods with different Parameters and Return types, One may
    // use the RuntimeType annotation. However this opens up the possibility for
    // ClassCastExceptions.
    @RuntimeType
    public static Object intercept(@RuntimeType Object value) {
        System.out.println("Invoked method with @RuntimeType: " + value);
        return Arrays.asList(value);
    }

    // You can inject super object to call the respective intercepted method.
    // Return type must match the expected methods return type, otherwise throws
    // ClassCastException.
    @RuntimeType
    public static Object interceptSuper(
            @RuntimeType Object value,
            @Super MemoryDatabase zuper
    ) {
        System.out.println("Invoked method with @RuntimeType: " + value);
        return zuper.load((String) value);
    }
*/
}