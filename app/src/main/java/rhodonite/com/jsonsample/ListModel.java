package rhodonite.com.jsonsample;

import java.util.ArrayList;

public class ListModel {
    initListModel iLm = new initListModel();
    private  class initListModel{
        String nickName;
        String imageUrl;
        ArrayList<String> tags = new ArrayList<String>();
    }

    public void addData(String name,String url,ArrayList<String> data){
        iLm.nickName = name;
        iLm.imageUrl = url;
        iLm.tags = data;

    }
    public String getName(){
        return iLm.nickName;
    }
    public String getUrl(){
        return iLm.imageUrl;
    }
    public ArrayList<String> getTag(){
        return iLm.tags;
    }

}
