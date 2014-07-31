package server;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.continuations.Continuation;
import org.apache.cxf.continuations.ContinuationProvider;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;

import java.util.concurrent.FutureTask;

/**
 * Created by sandamal on 7/21/14.
 */
public class CustomInterceptor extends AbstractSoapInterceptor {


    public CustomInterceptor() {
        super(Phase.INVOKE);
    }

    @Override
    public void handleMessage(SoapMessage soapMessage) throws Fault {

        System.out.println("Message Intercepted");

        ContinuationProvider continuationProvider = (ContinuationProvider) soapMessage.get(ContinuationProvider.class.getName());
        final Continuation continuation = continuationProvider.getContinuation();

        if(continuation != null){

            synchronized (continuation){

                if(continuation.isNew()){
                    //This is a new request
                    //execute a task asynchronously

                    FutureTask futureTask = new FutureTask(new InterceptorRequestHandler(soapMessage,continuation));
                    continuation.setObject(futureTask);
                    System.out.println("Suspending the main thread");
                    continuation.suspend(0);
                    System.out.println("Starting the second thread");
                    futureTask.run();
                    System.out.println("This is the first thread");

                }else{
                    FutureTask futureTask = (FutureTask)continuation.getObject();
                    if (!futureTask.isDone()) {
                        continuation.suspend(0);
                    }
                }
            }
        }
    }
}
