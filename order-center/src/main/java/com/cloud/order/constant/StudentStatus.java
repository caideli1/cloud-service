package com.cloud.order.constant;

/**
 * @author  zhujingtao
 */
public enum StudentStatus {
    /**
     * 0:非学生
     */
    NOTSTUDENT(0),
    /**
     * 1:学生
     */
    ISSTUDENT(1);
    public int num = 0;
    StudentStatus(int num){
        this.num=num;
    }
}
