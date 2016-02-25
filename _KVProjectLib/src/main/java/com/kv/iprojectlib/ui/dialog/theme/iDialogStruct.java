package com.kv.iprojectlib.ui.dialog.theme;

public interface iDialogStruct {

    /**
     * 是否有标题栏.
     * @return
     */
    public boolean hasTitleBar();
    
    /**
     * 是否有边角
     * @return
     */
    public boolean hasCorner();
    
    /**
     * 对话框背景颜色
     * @return
     */    
    public int getDialogBGColor();
    
    
    /**
     * 对话框主颜色
     * @return
     */
    public int getDialogMainColor();
    

    /**
     * 主按键颜色
     * @return
     */
    public int getPositionBtnBGColor();
    
    /**
     * 主按键按下颜色
     * @return
     */
    public int getPositionBtnOnBGColor();    
    
    /**
     * 副按键颜色
     * @return
     */
    public int getNegativeBtnBGColor(); 
    
    /**
     * 副按键按下颜色
     * @return
     */
    public int getNegativeBtnOnBGColor();
    
    /**
     * 主按键文字颜色
     * @return
     */
    public int getPostionTxtColor();
    
    /**
     * 主按键按下文字颜色
     * @return
     */
    public int getPostionTxtOnColor();    
    
    /**
     * 副按键文字颜色
     * @return
     */
    public int getNegativeTxtColor();
    
    /**
     * 副按键按下文字颜色
     * @return
     */
    public int getNegativeTxtOnColor();    
    
    /**
     * 文字大小
     * @return
     */
    public int getMainTxtSize();
    
    /**
     * 文字颜色.
     * @return
     */
    public int getMainTxtColor();

    

    
    
}
