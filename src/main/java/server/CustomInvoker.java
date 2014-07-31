package server;

import org.apache.cxf.continuations.Continuation;
import org.apache.cxf.continuations.ContinuationProvider;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.invoker.AbstractInvoker;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by sandamal on 7/22/14.
 */
public class CustomInvoker extends AbstractInvoker {

    private Object bean;
    public CustomInvoker(Object bean){
        this.bean = bean;
    }

    @Override
    public Object getServiceObject(Exchange exchange) {
        return bean;
    }

    @Override
    public Object invoke(Exchange exchange, Object o) {
        System.out.println("Invoked!!!!");


        ContinuationProvider continuationProvider = (ContinuationProvider) exchange.getInMessage().get(ContinuationProvider.class.getName());
        final Continuation continuation = continuationProvider.getContinuation();

        synchronized (continuation){

            if(continuation.isNew()){
                //This is a new request
                //execute a task asynchronously
                FutureTask futureTask = new FutureTask(new InvokerRequestHandler(exchange,continuation));
                continuation.setObject(futureTask);
                System.out.println("Suspending the main thread");
                continuation.suspend(0);
                System.out.println("Starting the second thread");
                futureTask.run();
                System.out.println("This is the first thread");

            }else{
                FutureTask futureTask = (FutureTask)continuation.getObject();
                if (futureTask.isDone()) {
                    try {
                        return futureTask.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                } else {
                    continuation.suspend(0);
                }
            }

        }
//        return super.invoke(exchange, o);
        System.out.println("finished with the continuation");
        return null;
    }
}
