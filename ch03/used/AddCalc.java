package chapter03.used;

public class AddCalc implements Calculator {
    @Override
    public Integer calc(Integer x, Integer y){
        return x+y;
    }
}
