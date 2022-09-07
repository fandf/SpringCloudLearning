package com.fandf.demo.design.cellve;

import org.springframework.beans.factory.InitializingBean;

/**
 * 模板方法设计模式
 *
 * @author fandongfeng
 * @date 2022-9-7 10:42
 */
public abstract class AbstractHandler implements InitializingBean {

    /**
     * 是否可以正常使用
     *
     * @return T 可以 F 不可以
     */
    abstract boolean effective();

    /**
     * 支付
     *
     * @return T 成功 F 失败
     */
     boolean pay(){
         throw new UnsupportedOperationException();
     }

    public boolean processBiz() {
        if (effective()) {
            return pay();
        }
        return false;
    }

}
