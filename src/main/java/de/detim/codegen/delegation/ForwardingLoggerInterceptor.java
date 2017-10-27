package de.detim.codegen.delegation;

import net.bytebuddy.implementation.bind.annotation.Pipe;

import java.util.List;

class ForwardingLoggerInterceptor {

    private final MemoryDatabase memoryDatabase;

    public ForwardingLoggerInterceptor(MemoryDatabase memoryDatabase) {
        this.memoryDatabase = memoryDatabase;
    }

    public List<String> log(
            @Pipe Forwarder<List<String>, MemoryDatabase> pipe
    ) {
        System.out.println("Calling database");
        try {
            return pipe.to(memoryDatabase);
        } finally {
            System.out.println("Returned from database");
        }
    }
}