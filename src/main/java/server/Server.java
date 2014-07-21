package server;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ServerFactoryBean;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.message.Message;

import java.net.URL;
import java.util.List;

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
        serverFactory.setBus(bus);

        serverFactory.getInInterceptors().add(new CustomInterceptor());

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
