package de.detim.codegen.delegation;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.Pipe;
import net.bytebuddy.matcher.ElementMatchers;

import java.util.stream.IntStream;

public class MethodDelegationExample {

    public static void main(String[] args)
    throws IllegalAccessException, InstantiationException {
        String helloWorld = new ByteBuddy()
                .subclass(Source.class)
                .method(ElementMatchers.named("hello")
                                .and(ElementMatchers.takesArguments(String.class)))
                .intercept(MethodDelegation.to(Target.class))
                .make()
                .load(MethodDelegationExample.class.getClassLoader())
                .getLoaded()
                .newInstance()
                .hello("Detim");
        System.out.println(helloWorld);

        MemoryDatabase loggingDatabase = new ByteBuddy()
                .subclass(MemoryDatabase.class)
                .method(ElementMatchers.named("load"))
                .intercept(MethodDelegation.to(LoggerInterceptor.class))
                .make()
                .load(MemoryDatabase.class.getClassLoader())
                .getLoaded()
                .newInstance();

        System.out.println(loggingDatabase.load("test123"));

        MemoryDatabase loggingDatabaseForwarder = new ByteBuddy()
                .subclass(MemoryDatabase.class)
                .implement(IntStream.Builder.class)
                .method(ElementMatchers.named("load"))
                .intercept(MethodDelegation.withDefaultConfiguration()
                                   .withBinders(Pipe.Binder.install(Forwarder.class))
                                   .to(new ForwardingLoggerInterceptor(new MemoryDatabase())))
                .make()
                .load(MemoryDatabase.class.getClassLoader())
                .getLoaded()
                .newInstance();

        System.out.println(loggingDatabaseForwarder.load("test pipe forwarder"));
    }

}
