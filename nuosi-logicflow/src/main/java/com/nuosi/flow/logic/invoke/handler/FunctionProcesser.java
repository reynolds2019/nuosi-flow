package com.nuosi.flow.logic.invoke.handler;

import com.ai.ipu.data.JMap;
import com.nuosi.flow.logic.inject.function.FunctionManager;
import com.nuosi.flow.logic.inject.function.IDomainFunction;
import com.nuosi.flow.logic.model.action.Function;

import java.util.List;
import java.util.Map;

/**
 * <p>desc: 节点执行处理器的函数功能类型实现 </p>
 * <p>date: 2021/5/17 23:13 </p>
 *
 * @author nuosi fsofs@163.com
 * @version v1.0.0
 */
public class FunctionProcesser implements IActionProcesser{

    @Override
    public Object execute(Map<String, Object> databus, Object... param) throws Exception {
        List<Function> functions = (List<Function>) param[0];

        IDomainFunction domainFunction;
        Object result = null;
        for(Function function : functions){
            domainFunction = FunctionManager.getDomainFunction(function.getDomain());
            result = domainFunction.invoke(databus, function);
            if(function.getOutkey()!=null){
                databus.put(function.getOutkey(), result);
            }
        }
        return result;
    }
}
