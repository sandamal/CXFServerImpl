package server;

import org.apache.cxf.continuations.Continuation;
import org.apache.cxf.message.Exchange;

import java.util.concurrent.Callable;

/**
 * Created by sandamal on 7/22/14.
 */
public class RequestHandler implements Callable {

    Exchange exchange;
    Continuation continuation;

    public RequestHandler(Exchange exchange, Continuation continuation){
        this.continuation = continuation;
        this.exchange = exchange;
    }


    @Override
    public Object call() throws Exception {
        Thread.sleep(5000);
        System.out.println("This is from the thread");
        return null;
    }
}
