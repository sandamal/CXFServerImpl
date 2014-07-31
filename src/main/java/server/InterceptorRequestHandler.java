package server;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.continuations.Continuation;

import java.util.concurrent.Callable;

/**
 * Created by sandamal on 7/23/14.
 */
public class InterceptorRequestHandler implements Callable {

    SoapMessage message;
    Continuation continuation;

    public  InterceptorRequestHandler(SoapMessage message, Continuation continuation){
        this.message = message;
        this.continuation = continuation;
    }

    @Override
    public Object call() throws Exception {

        Thread.sleep(3000);
        System.out.println("This is from the thread");

        this.continuation.resume();
        return null;
    }
}
