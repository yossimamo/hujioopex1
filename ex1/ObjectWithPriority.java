import oop.ex1.dataStructures.ComparableObject;

public class ObjectWithPriority implements ComparableObject {
    public int _priority;
    public ObjectWithPriority(int prioroty){
        _priority=prioroty;
    }
    
    public int compare(ComparableObject other) {
        ObjectWithPriority otherO=(ObjectWithPriority)other;
        if(_priority>otherO._priority) return 1;
        if(_priority<otherO._priority) return -1;
        return 0;
    }

    
    public boolean equals(ComparableObject other) {
        ObjectWithPriority otherO=(ObjectWithPriority)other;
        return(_priority==otherO._priority);
    }
    public String toString(){
        return _priority + " ";
    }

}
