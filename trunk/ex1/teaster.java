import java.util.Iterator;

import oop.ex1.dataStructures.*;
import oop.ex1.processToolKit.*;
import oop.ex1.processToolKit.Process;
public class teaster {

    public static void main(String[] args) {
        cheakRendomIterator();
        cheakLIPO();
        cheakPriorityQueue();
        cheakPriorityQueueAdvanced();
        cheakProcessManager();

    }
    public static void cheakProcessManager(){
        System.out.println("-----------------------running with same priorety---------------");
        //               ----a----I--b--Ic-d update
        cheakProcess5311(5,5,5,5,5,5,5,5,5,5,5);
        System.out.println("-----------runing with bad priorety updateup--------------");
        //               ----a----I--b--Ic-d--update
        cheakProcess5311(5,5,5,5,5,8,6,3,1,-1,9);
        System.out.println("-----------runing with bad priorety up date down--------------");
        //               ----a----I--b--Ic-d--update
        cheakProcess5311(5,5,5,5,5,8,6,3,1,-1,-5);
    }
    public static void cheakProcess5311(int a1p,int a11p, int a111p,int a112p,int a12p,
            int b1p,int b11p,int b111p,int c1p,int d1p,int updatea11){
        Process a1=new Process("a1",null,a1p);
        Process a11=new Process("a11",a1,a11p);
        Process a111=new Process("a111",a11,a111p);
        Process a112=new Process("a112",a11,a112p);
        Process a=new Process("a12",a1,a12p);
        Process b1=new Process("b1",null,b1p);
        Process b11=new Process("b11",b1,b11p);
        Process b111=new Process("b111",b11,b111p);
        Process c1=new Process("c1",null,c1p);
        Process d1=new Process("d1",null,d1p);
        ProcessManager manager1=new ProcessManager();
        manager1.addProcessTree(a1);
        manager1.addProcessTree(b1);
        manager1.addProcessTree(c1);
        manager1.addProcessTree(d1);
        try {manager1.updatePriority(a11,updatea11);} catch (ObjectNotFoundException e1) {}
        try {manager1.runAllProcesses();} 
        catch (DisorderException e) {System.out.println("dissorder!!!!!!!!!!!!!");}
    }
    public static void cheakPriorityQueue(){
        int i,x=10,c=0;
        System.out.print("cheacking PriorityQueue by rendomazing a queue, puting it in anothe queue and taking it out");
        ArrayPriorityQueue preeQueue= new ArrayPriorityQueue();
        for(i=0; i <x;i++)  preeQueue.push(new ObjectWithPriority(i));
        Iterator<ComparableObject> itr=preeQueue.randomIterator();
        ArrayPriorityQueue queue= new ArrayPriorityQueue();
        for(i=0; i <x;i++)  queue.push(itr.next());
        for(i=0; i<x+1;i++){
            ObjectWithPriority obj=null;
            try {obj=(ObjectWithPriority)queue.poll();} 
            catch (EmptyQueueException e) {c++;}
            if(c==0 && obj._priority!=(x-i-1)){
                System.out.println("erorr: bad queue");
                break;
            }
        }
        if(i==x+1 && c==1) System.out.println("....passed");
    }
    public static void cheakPriorityQueueAdvanced(){
        int i,x=100,c=0;
        System.out.print("Advenced cheacking PriorityQueue by puting 2 rendomized queues into one and cheak if it is orgenized");
        ArrayPriorityQueue preeQueue= new ArrayPriorityQueue();
        for(i=0; i <x;i++)  preeQueue.push(new ObjectWithPriority(i));
        Iterator<ComparableObject> itr=preeQueue.randomIterator();
        Iterator<ComparableObject> itr2=preeQueue.randomIterator();
        ArrayPriorityQueue queue= new ArrayPriorityQueue();
        for(i=0; i <x;i++){
            queue.push(itr.next());
            queue.push(itr2.next());
        }
        
        for(i=0; i <2*x+1;i++){
            ObjectWithPriority obj=null;
            try {obj=(ObjectWithPriority)queue.poll();} 
            catch (EmptyQueueException e) {c++;}
            if(c==0 && obj._priority!=(x-1-i/2)){
                System.out.println("erorr: bad queue");
                break;
            }
        }
        if(i==2*x+1 && c==1) System.out.println("....passed");
    }
    public static void cheakRendomIterator(){
        int i,c=0;
        System.out.print("cheacking RendomIterator by itrating over 2 known seeds");
        LIFOComparableQueue stack= new LIFOComparableQueue();
        for(i=0; i <4;i++)  stack.push(new ObjectWithPriority(i));
        //50000001 ==> 3 2 1 0
        //40000000 ==> 0 1 2 3
        Iterator<ComparableObject> itr=stack.randomIterator(40000000);
        Iterator<ComparableObject> itr2=stack.randomIterator(50000001);
        for(i=0; i <5;i++){
            ObjectWithPriority obj=null;
            ObjectWithPriority obj2=null;
            try {obj=(ObjectWithPriority)itr.next(); obj2=(ObjectWithPriority)itr2.next();} 
            catch (NoMoreElementsException e){ c++;}
            if(c==0 && (obj._priority!=i || obj2._priority!=4-i-1)){
                System.out.println("erorr: bad rendom");
                break;
            }
        }
        if(i==5 && c==1) System.out.println("....passed");
    }
    public static void cheakLIPO(){
        int i,x=100,c=0;
        System.out.print("cheacking LIPO and itrator by puting "+ x
                +" elements and pulling them");
        LIFOComparableQueue stack= new LIFOComparableQueue();
        for(i=0; i <x;i++)  stack.push(new ObjectWithPriority(i));
        Iterator<ComparableObject> itr=stack.LIFOIterator();
        for(i=0; i <x+1;i++){
            ObjectWithPriority obj=null;
            try {obj=(ObjectWithPriority)stack.poll();} 
            catch (EmptyQueueException e){ c++;}
            if(obj!=null && obj._priority!=(x-i-1)){
                System.out.println("erorr: not in order");
                break;
            }
            try{obj=(ObjectWithPriority)itr.next();}
            catch(NoMoreElementsException e){c++;}
            if(obj!=null && obj._priority!=(x-i-1)){
                System.out.println("erorr: not in order");
                break;
            }
        }
        itr=stack.LIFOIterator();
        if(!itr.hasNext()) c++;
        if(i==x+1 && c==3) System.out.println("....passed");
    }

}
