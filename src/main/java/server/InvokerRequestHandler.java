package server;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.continuations.Continuation;
import org.apache.cxf.message.Exchange;

import java.util.Set;
import java.util.concurrent.Callable;

/**
 * Created by sandamal on 7/22/14.
 */
public class InvokerRequestHandler implements Callable {

    Exchange exchange;
    Continuation continuation;

    public InvokerRequestHandler(Exchange exchange, Continuation continuation){
        this.continuation = continuation;
        this.exchange = exchange;
    }


    @Override
    public Object call() throws Exception {


        Set set = exchange.getInMessage().getContentFormats();
        System.out.println(set);
        Thread.sleep(10000);
        System.out.println("This is from the thread");
        this.continuation.resume();
        return null;
    }
}
