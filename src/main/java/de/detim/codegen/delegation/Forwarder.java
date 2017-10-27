package de.detim.codegen.delegation;

public interface Forwarder<T, S> {
    T to(S target);
}