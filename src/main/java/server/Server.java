package server;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.CXFBusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.frontend.ServerFactoryBean;

import java.net.URL;

/**
 * Created by sandamal on 7/19/14.
 */
public class Server {

    protected Server() throws Exception{

        SpringBusFactory bf = new SpringBusFactory();
//        CXFBusFactory bf = new CXFBusFactory();
        URL busFile = Server.class.getResource("/server.xml");
        Bus bus = bf.createBus(busFile.toString());
        CXFBusFactory bbf = new CXFBusFactory();
        bbf.createBus();


        BusFactory.setDefaultBus(bus);

        HelloWorldImpl helloWorld = new HelloWorldImpl();
        ServerFactoryBean serverFactory = new ServerFactoryBean();
        serverFactory.setBus(bus);

        serverFactory.getInInterceptors().add(new CustomInterceptor());
        serverFactory.getInInterceptors().add(new MessageInterceptor());
        serverFactory.setInvoker(new CustomInvoker(helloWorld));

        //serverFactory.setServiceClass(HelloWorld.class);
        serverFactory.setServiceBean(helloWorld);
        serverFactory.setAddress("http://localhost:9001/Hello");
        serverFactory.create();


        System.out.println("bla bla bla");

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
