package com.linln.component.actionLog.action.model;

import com.linln.modules.system.enums.ActionLogEnum;
import lombok.Getter;

/**

 * @date 10/15
 */
@Getter
public class SystemType extends BusinessType{

    /** 日志类型 */
    protected Byte type = ActionLogEnum.SYSTEM.getCode();

    public SystemType(String message) {
        super(message);
    }

    public SystemType(String name, String message) {
        super(name, message);
    }
}
