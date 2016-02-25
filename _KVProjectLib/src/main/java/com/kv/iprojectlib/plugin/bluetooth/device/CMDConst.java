package com.kv.iprojectlib.plugin.bluetooth.device;


public class CMDConst {

    //汉字16  英文32
    
    public static String INIT_PRINT = new String(new byte[]{27,64}); //初始化打印机
    
    public static String LF = new String(new byte[]{0x0A});//换行
    
    public static String GO_N_LINE = new String(new byte[]{27,100, 0x05}); //打印并走5行  注意:第三个参数设置貌似无效
    
    /**
     * 设置字体模式 
     * [xx(倍宽)(倍高)xx(反相)x]   8位 bit
     * @return
     */
    public static String setPrintMode(String mode) {
        int modeIntVal = Integer.parseInt(mode, 2);
        return new String(new byte[]{27,33,(byte) modeIntVal});
    } 
    
    
}
