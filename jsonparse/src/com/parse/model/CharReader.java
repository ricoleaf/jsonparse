package com.parse.model;

import java.io.IOException;
import java.io.Reader;

/**
 *  read character
 */
public class CharReader {

    private  static final int BUFFER_SIZE = 1024;

    private char[] buffer;

    private Reader reader;

    private int pos;

    private int size;

    public CharReader(Reader reader) {
        this.reader = reader;
        buffer = new char[BUFFER_SIZE];
    }

    public char peek() throws IOException {
        if (pos - 1 >= size) {
            return (char)-1;
        }
        return buffer[Math.max(0, pos - 1)];
    }

    public char next() throws IOException {
        if (!hasMore()) {
            return buffer[pos - 1];
        }
        return buffer[pos++];
    }

    public void back() {
        pos = Math.max(0, --pos);
    }

    public boolean hasMore() throws IOException {
        if (pos < size) {
            return true;
        }

        fillBuffer();
        return pos < size;
    }

    void fillBuffer() throws IOException {
        int n = reader.read(buffer);
        if (n == -1) {
            return;
        }
        pos = 0;
        size = n;
    }
}

