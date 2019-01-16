package com.emate.wechat.menu;


/**
 * 复杂按钮（父按钮） 
 * ComplexButton.java
 * @author likk
 * 2015-7-22 下午03:07:47
 *
 */
public class ComplexButton extends Button {  
    private Button[] sub_button;  
  
    public Button[] getSub_button() {  
        return sub_button;  
    }  
  
    public void setSub_button(Button[] sub_button) {  
        this.sub_button = sub_button;  
    }  
}  
