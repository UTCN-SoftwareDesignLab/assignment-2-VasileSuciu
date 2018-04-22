package demo.controllers;

public class MyLong {
    private Long value;

    public MyLong(){
        value = 0L;
    }

    public void setValue(Long myLong) {
        this.value = myLong;
    }

    public Long getValue() {
        return value;
    }
}
