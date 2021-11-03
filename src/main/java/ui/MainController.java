package ui;

import core.ShiroKeyDetect;
import entity.ControllersFactory;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button button;

    @FXML
    private TextField attackUrl;

    @FXML
    public TextArea result;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ControllersFactory.controllers.put(MainController.class.getSimpleName(),this);
    }

    @FXML
    public void execResult() throws Exception {
        Thread thread = new Thread(()->{
            ShiroKeyDetect shiroKeyDetect = new ShiroKeyDetect();
            String targetUrl = attackUrl.getText();
            if(ShiroKeyDetect.isShiro(targetUrl)){
                try {
                    shiroKeyDetect.ShiroKey(targetUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else {
                result.appendText("未检测到shiro框架"+"\n");
            }

        });
        thread.start();
    }

}
