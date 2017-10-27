package de.detim.codegen.delegation;

import java.util.Arrays;
import java.util.List;

class MemoryDatabase {
    public List<String> load(String info) {
        return Arrays.asList(info + ": foo", info + ": bar");
    }
}