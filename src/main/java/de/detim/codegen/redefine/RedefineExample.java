package de.detim.codegen.redefine;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;

public class RedefineExample {

    public static void main(String[] args) {

        Foo foo = new Foo();
        System.out.println(foo);

        ByteBuddyAgent.install();
        new ByteBuddy()
                .redefine(Bar.class)
                .name(Foo.class.getName())
                .make()
                .load(Foo.class.getClassLoader(),
                      ClassReloadingStrategy.fromInstalledAgent());

        System.out.println(foo);
    }

}
