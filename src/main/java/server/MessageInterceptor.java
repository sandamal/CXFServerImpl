package server;

import org.apache.cxf.binding.soap.interceptor.MustUnderstandInterceptor;
import org.apache.cxf.binding.soap.interceptor.SoapActionInInterceptor;
import org.apache.cxf.binding.soap.interceptor.SoapHeaderInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.Interceptor;
//import org.apache.cxf.jaxws.handler.logical.LogicalHandlerInInterceptor;
//import org.apache.cxf.jaxws.handler.soap.SOAPHandlerInterceptor;
//import org.apache.cxf.jaxws.interceptors.SwAInInterceptor;
//import org.apache.cxf.jaxws.interceptors.WrapperClassInInterceptor;
import org.apache.cxf.jaxws.handler.logical.LogicalHandlerInInterceptor;
import org.apache.cxf.jaxws.handler.soap.SOAPHandlerInterceptor;
import org.apache.cxf.jaxws.interceptors.SwAInInterceptor;
import org.apache.cxf.jaxws.interceptors.WrapperClassInInterceptor;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.wsdl.interceptors.DocLiteralInInterceptor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by sandamal on 7/24/14.
 */
public class MessageInterceptor extends AbstractPhaseInterceptor<Message> {

    public MessageInterceptor() {
        super(Phase.UNMARSHAL);
        getBefore().add(DocLiteralInInterceptor.class.getName());
    }



    @Override
    public void handleMessage(Message message) throws Fault {

        System.out.println("List of interceptors");
        System.out.println(message.getInterceptorChain().toString());

//        removeInterceptor(DocLiteralInInterceptor.class,message);
//        removeInterceptor(SoapActionInInterceptor.class,message);
//        removeInterceptor(MustUnderstandInterceptor.class,message);
//        removeInterceptor(SOAPHandlerInterceptor.class,message);
//        removeInterceptor(LogicalHandlerInInterceptor.class,message);
//        removeInterceptor(SoapHeaderInterceptor.class,message);
//        removeInterceptor(WrapperClassInInterceptor.class,message);
//        removeInterceptor(SwAInInterceptor.class,message);


        Set<Class<? extends Interceptor<?>>> interceptorSet = new HashSet<Class<? extends Interceptor<?>>>();
        interceptorSet.add(DocLiteralInInterceptor.class);
        interceptorSet.add(SoapActionInInterceptor.class);
        interceptorSet.add(MustUnderstandInterceptor.class);
        interceptorSet.add(SOAPHandlerInterceptor.class);
        interceptorSet.add(LogicalHandlerInInterceptor.class);
        interceptorSet.add(SoapHeaderInterceptor.class);
        interceptorSet.add(WrapperClassInInterceptor.class);
        interceptorSet.add(SwAInInterceptor.class);

        removeInterceptors(interceptorSet,message);
        System.out.println("List of interceptors after removing");
        System.out.println(message.getInterceptorChain().toString());
    }

    private void removeInterceptor(Class<? extends Interceptor<?>>
                                                 interceptorType, Message message) {
        Iterator<Interceptor<? extends Message>> iterator =
                message.getInterceptorChain().iterator();

        Interceptor<?> removeInterceptor = null;
        for (; iterator.hasNext();) {

            Interceptor<?> interceptor = iterator.next();

            if (interceptorType.isInstance(interceptor)) {
                removeInterceptor = interceptor;
                break;
            }
        }

        if (removeInterceptor != null) {

            System.out.println("Removing interceptor "+removeInterceptor.getClass().getName());
            message.getInterceptorChain().remove(removeInterceptor);
        }
    }

    private void removeInterceptors(Set<Class<? extends Interceptor<?>>> interceptorSet, Message message){

        Iterator<Interceptor<? extends Message>> iterator = message.getInterceptorChain().iterator();

        for (; iterator.hasNext();) {

            Interceptor<?> interceptor = iterator.next();
            Class interceptorClass = interceptor.getClass();

            if(interceptorSet.contains(interceptorClass)){
                message.getInterceptorChain().remove(interceptor);
            }
        }
    }




}
