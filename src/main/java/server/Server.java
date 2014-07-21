package server;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.frontend.ServerFactoryBean;

import java.net.URL;

/**
 * Created by sandamal on 7/19/14.
 */
public class Server {

    protected Server() throws Exception{

        SpringBusFactory bf = new SpringBusFactory();
        URL busFile = Server.class.getResource("/server.xml");
        Bus bus = bf.createBus(busFile.toString());

        BusFactory.setDefaultBus(bus);

        HelloWorldImpl helloWorld = new HelloWorldImpl();
        ServerFactoryBean serverFactory = new ServerFactoryBean();
        serverFactory.setServiceClass(HelloWorld.class);
        serverFactory.setAddress("http://localhost:9000/Hello");
        serverFactory.setServiceBean(helloWorld);
        serverFactory.create();
    }

    public static void main(String[] args){
        try {
            new Server();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("server.Server ready...");
    }


}
