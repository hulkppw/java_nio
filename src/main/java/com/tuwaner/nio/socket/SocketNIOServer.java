package com.tuwaner.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created on 2017/3/9.
 */
public class SocketNIOServer {
    /*port*/
    private int port = 8888;
    /*charset*/
    private Charset charset = Charset.forName("UTF-8");
    /*ReadBuffer*/
    private static ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    /*WriteBuffer*/
    private static ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
    /*client channel*/
    private static Map<String, SocketChannel> clientsMap = new HashMap<String, SocketChannel>();
    /*selector*/
    private static Selector selector;

    /*
    * Constructor
    */
    public SocketNIOServer(int port){
        this.port = port;
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * init method
     */
    private void init() throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        /*nonblocking*/
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(port));
        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("server start on port: "+port);
    }

    private void listen(){
        while (true){
            try {
                if (selector.select() == 0) {
                    //System.out.println("... ...");
                    continue;
                }
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    if (key.isAcceptable()) {
                        acceptHandle(key);
                    }
                    if (key.isReadable()) {
                        readHandle(key);
                    }
                    iter.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void acceptHandle(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
        SocketChannel client = server.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }

    private void readHandle(SelectionKey selectionKey) throws IOException {
        String receiveText = null;
        int count = 0;
            SocketChannel client = (SocketChannel) selectionKey.channel();
            readBuffer.clear();
            count = client.read(readBuffer);
            if(count > 0) {
                readBuffer.flip();
                receiveText = String.valueOf(charset.decode(readBuffer).array());
                System.out.println(client.toString()+" : "+receiveText);
                dispatch(client, receiveText);
                client = (SocketChannel) selectionKey.channel();
                client.register(selector, SelectionKey.OP_READ);
        }
    }

    private void dispatch(SocketChannel client, String info) throws IOException {
        Socket socket = client.socket();
        String name = "[ " + socket.getInetAddress().toString().substring(1) + " : " + Integer.toHexString(client.hashCode()) + " ]";
        if(!clientsMap.isEmpty()){
            for (Map.Entry<String, SocketChannel> entry : clientsMap.entrySet()) {
                SocketChannel temp = entry.getValue();
                if(!client.equals(temp)){
                    writeBuffer.clear();
                    writeBuffer.put((name + " : " + info).getBytes());
                    writeBuffer.flip();
                    temp.write(writeBuffer);
                }
            }
        }
        clientsMap.put(name, client);
    }

    public static void main(String[] args) throws IOException {
        SocketNIOServer server = new SocketNIOServer(7777);
        server.listen();
    }
}
