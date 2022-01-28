package com.zubayr.listProduct.exception;

public class MaxCountToListException extends RuntimeException{

    @Override
    public String getMessage() {
        return "Создано максимальное количество листов";
    }
}
