package server;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.continuations.Continuation;
import org.apache.cxf.continuations.ContinuationProvider;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by sandamal on 7/21/14.
 */
public class CustomInterceptor extends AbstractSoapInterceptor {


    public CustomInterceptor() {
        super(Phase.READ);
    }

    @Override
    public void handleMessage(SoapMessage soapMessage) throws Fault {
        String contentType = (String) soapMessage.get(SoapMessage.CONTENT_TYPE);
        System.out.println("--------"+contentType);
        System.out.println("Intercepted");

/*
        ContinuationProvider provider = (ContinuationProvider)soapMessage.get(ContinuationProvider.class.getName());
        final Continuation continuation = provider.getContinuation();

        synchronized (continuation){
            // it means this is a new request
            // we execute a task asynchronously
            if(continuation.isNew()){
                FutureTask futureResponse = new FutureTask(new Callable() {

                    @Override
                    public String call() throws Exception {
                        // execute the asynchronous job here
                        Thread.sleep(5000);
                        // resume the request
                        continuation.resume();
                        return "Hello ";
                    }
                });
                taskExecutor.execute(futureResponse);
                // we store the future task in the continuation object for future retrieval
                continuation.setObject(futureResponse);
                // and we suspend the request
                continuation.suspend(6000);
                // this will not be returned to the client
                return null;
            }
            // it means the request has been resumed or that a timeout occurred
            FutureTask futureTask = (FutureTask) continuation.getObject();
            return futureTask.get();
*/
        System.out.println("Completed continuation");
    }
}
