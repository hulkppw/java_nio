package com.tuwaner.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by wanglingyun on 2017/2/26.
 */
public class NIOTest {

    public static void main(String[] args) {
        bioMethod();

        nioMethod();
    }

    /**
     * nio
     */
    public static void nioMethod() {
        RandomAccessFile aFile = null;
        try {
            aFile = new RandomAccessFile("java.io", "rw");
            FileChannel fileChannel = aFile.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(2048);
            int bytesRead = fileChannel.read(buf);
            System.out.println(bytesRead);
            while (bytesRead != -1) {
                buf.flip();
                while (buf.hasRemaining()) {
                    System.out.println((char) buf.get());
                }
                buf.compact();
                bytesRead = fileChannel.read(buf);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (aFile != null) {
                try {
                    aFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * bio
     */
    public static void bioMethod() {
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream("java.io"));
            byte[] buf = new byte[2048];
            int bytesRead = in.read(buf);
            while (bytesRead != -1) {
                for (int i = 0; i < bytesRead; i++) {
                    System.out.println((char) buf[i]);
                }
                bytesRead = in.read(buf);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
