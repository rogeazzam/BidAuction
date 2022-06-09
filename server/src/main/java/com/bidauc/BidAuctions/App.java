package com.bidauc.BidAuctions;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App
{
	
	private static SimpleServer server;
    public static void main( String[] args ) throws IOException
    {
        server = new SimpleServer(2523);
        server.listen();
    }
}
