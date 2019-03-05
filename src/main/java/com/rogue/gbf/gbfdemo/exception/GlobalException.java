package com.rogue.gbf.gbfdemo.exception;

import com.rogue.gbf.gbfdemo.result.CodeMsg;

/**
 * @author weigaosheng
 * @description 自定义全局异常处理类
 * @CalssName GlobleException
 * @date 2019/3/5
 * @params
 * @return
 */
public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private CodeMsg codeMsg;

    public GlobalException(CodeMsg cm){
        super(cm.toString());
        this.codeMsg = cm;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}
