package Client;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import server.HelloWorld;

import java.net.URL;

/**
 * Created by sandamal on 7/19/14.
 */
public class Client {

    private Client() {
    }

    public static void main(String args[]) throws Exception {

        SpringBusFactory bf = new SpringBusFactory();
        URL busFile = Client.class.getResource("/client.xml");
        Bus bus = bf.createBus(busFile.toString());
        BusFactory.setDefaultBus(bus);

        ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
        factory.setAddress("http://localhost:9000/Hello");

        HelloWorld client = factory.create(HelloWorld.class);
        System.out.println("Invoke sayHi()....");
        System.out.println(client.sayHi("Jack"));

        bus.shutdown(true);
        System.exit(0);
    }

}
