package server;

/**
 * Created by sandamal on 7/19/14.
 */

public class HelloWorldImpl implements HelloWorld {

    @Override
    public String sayHi(String text) {

        System.out.println("sayHi called");
        System.out.println("1");
        return "Hello " + text;
    }
}
