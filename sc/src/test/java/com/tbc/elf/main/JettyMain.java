package com.tbc.elf.main;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class JettyMain {
    public static void main(String[] args) throws Exception {
        Server jettyServer = new Server();

        SocketConnector conn = new SocketConnector();
        conn.setPort(8080);
        jettyServer.setConnectors(new Connector[]{conn});

        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath("/sc");
        webAppContext.setWar("src/main/webapp");
        jettyServer.setHandler(webAppContext);
        jettyServer.start();
    }
}
