package com.nuosi.flow.logic.model.element;

import java.util.List;

/**
 * @author nuosi fsofs@163.com
 * @version 0.1.0
 * @name Output
 * @desc TODO
 * @date 2021/3/6 12:19
 */
public class Output {
    private List<Var> vars;

    public List<Var> getVars() {
        return vars;
    }

    public Output setVars(List<Var> vars) {
        this.vars = vars;
        return this;
    }
}
