package com.fandf.demo.dataarchive.DeferredResult.demo2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ResponseMsg<T> {

    private int code;

    private String msg;

    private T data;

}