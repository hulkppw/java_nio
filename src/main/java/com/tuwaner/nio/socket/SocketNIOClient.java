package com.tuwaner.nio.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Set;

/**
 * Created on 2017/3/9.
 */
public class SocketNIOClient {

    /*ReadBuffer*/
    private static ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    /*WriteBuffer*/
    private static ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
    /*server address*/
    private InetSocketAddress SERVER;
    private static Selector selector;
    private static SocketChannel client;
    private static String receiveText;
    private static String sendText;
    private static int count=0;

    public SocketNIOClient(int port){
        SERVER = new InetSocketAddress("localhost", port);
        init();
    }

    /**
     * init
     */
    private void init() {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            socketChannel.connect(SERVER);

            while (true) {
                selector.select();
                Set<SelectionKey> keySet = selector.selectedKeys();
                for (final SelectionKey key : keySet) {
                    handle(key);
                }
                keySet.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handle(SelectionKey selectionKey) throws IOException {
        if (selectionKey.isConnectable()){
            client = (SocketChannel) selectionKey.channel();
            if(client.isConnectionPending()){
                client.finishConnect();
                System.out.println("connect success !");
                writeBuffer.clear();
                writeBuffer.put((new Date().toLocaleString()+" connected!").getBytes());
                writeBuffer.flip();
                client.write(writeBuffer);
                new Thread(){
                    public void run(){
                        while (true) {
                            writeBuffer.clear();
                            InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                            try {
                                sendText = bufferedReader.readLine();
                                writeBuffer.put(sendText.getBytes());
                                writeBuffer.flip();
                                client.write(writeBuffer);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();
            }
            client.register(selector, SelectionKey.OP_READ);
        } else if(selectionKey.isReadable()){
            client = (SocketChannel) selectionKey.channel();
            readBuffer.clear();
            count = client.read(readBuffer);
            if (count > 0){
                receiveText = new String(readBuffer.array(), 0, count);
                System.out.println(receiveText);
                client = (SocketChannel) selectionKey.channel();
                client.register(selector, SelectionKey.OP_READ);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        SocketNIOClient client = new SocketNIOClient(7777);
    }
}
