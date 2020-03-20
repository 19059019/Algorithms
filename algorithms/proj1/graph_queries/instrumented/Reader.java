import java.io.DataInputStream;
import java.io.IOException;

public class Reader {

    final private int BUFF_SIZE = 1 << 16;

    private DataInputStream din;

    private byte[] buffer;

    private int bufferPointer, bytesRead;

    public Reader() {
        din = new DataInputStream(System.in);
        za.ac.sun.cs.Cream.Spoon.hit(13, "Reader.java");
        buffer = new byte[BUFF_SIZE];
        za.ac.sun.cs.Cream.Spoon.hit(14, "Reader.java");
        bufferPointer = bytesRead = 0;
        za.ac.sun.cs.Cream.Spoon.hit(15, "Reader.java");
    }

    public int nextInt() throws IOException {
        int ret = 0;
        za.ac.sun.cs.Cream.Spoon.hit(19, "Reader.java");
        byte c = read();
        za.ac.sun.cs.Cream.Spoon.hit(20, "Reader.java");
        while (c <= ' ') c = read();
        boolean neg = (c == '-');
        za.ac.sun.cs.Cream.Spoon.hit(23, "Reader.java");
        if (neg)
            c = read();
        do {
            ret = ret * 10 + c - '0';
            za.ac.sun.cs.Cream.Spoon.hit(27, "Reader.java");
        } while ((c = read()) >= '0' && c <= '9');
        if (neg)
            return -ret;
        return ret;
    }

    private void fillBuffer() throws IOException {
        bytesRead = din.read(buffer, bufferPointer = 0, BUFF_SIZE);
        za.ac.sun.cs.Cream.Spoon.hit(36, "Reader.java");
        if (bytesRead == -1)
            buffer[0] = -1;
    }

    private byte read() throws IOException {
        if (bufferPointer == bytesRead)
            fillBuffer();
        return buffer[bufferPointer++];
    }

    public void close() throws IOException {
        if (din == null)
            return;
        din.close();
        za.ac.sun.cs.Cream.Spoon.hit(50, "Reader.java");
    }
}
