/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import java.util.Map;
import javax.servlet.ServletContext;

/**
 *
 * @author Novixous
 */
public class MybossThread extends BaseThread implements Runnable{
    private  ServletContext context;
    public MybossThread(ServletContext context){
        this.context = context;
    }

    @Override
    public void run() {
        while(true){
            MybossCategoriesCrawler categoriesCrawler=new MybossCategoriesCrawler(context);
            Map<String, String> categories = categoriesCrawler.getCategories(AppConstant.urlMyboss);
            Thread crawlingThread = new Thread(new My)
        }
    }
    
}
