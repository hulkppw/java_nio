package com.tuwaner.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2017/2/26.
 */
public class NIOClient {

    public static void client() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        SocketChannel channel = null;

        try {
            channel = SocketChannel.open();
            //设置为非阻塞channel
            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress("127.0.0.1", 8080));
            if (channel.finishConnect()) {
                int i = 0;
                while (true) {
                    TimeUnit.SECONDS.sleep(1);
                    String info = "I'm " + i++ + "-th information from client";
                    buffer.clear();
                    buffer.put(info.getBytes());
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        System.out.println(buffer);
                        channel.write(buffer);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (channel != null)
                    channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        //        for (())
        client();
    }
}
