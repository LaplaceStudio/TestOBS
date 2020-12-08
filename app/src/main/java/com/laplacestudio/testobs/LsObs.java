package com.laplacestudio.testobs;


import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import com.obs.services.model.ObsObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LsObs {
    final private String END_POINT="https://obs.cn-south-1.myhuaweicloud.com";
    final private String AK="TMCAB0AIYOIS5N24HXBQ";
    final private String SK="JIiyOhFmkEIJxENaQdF2AuIqCxc035VcCG8IPMUO";

    final private String BUCKET_NAME="published-app";
    final private String OBJ_UPDATE="update.json";

    private ObsClient obsClient;
    public LsObs(){
        ObsConfiguration config = new ObsConfiguration();
        config.setSocketTimeout(30000);
        config.setConnectionTimeout(10000);
        config.setEndPoint(END_POINT);
        obsClient=new ObsClient(AK,SK,config);
    }

    public void close(){
        try{
            obsClient.close();;
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public String getUpdate(){
        StringBuilder update= new StringBuilder();
        ObsObject object=obsClient.getObject(BUCKET_NAME,OBJ_UPDATE);
        InputStream content=object.getObjectContent();
        if(content==null) return "Get update file failed.";
        BufferedReader reader=new BufferedReader(new InputStreamReader(content));
        try{

            while (true){
                String line=reader.readLine();
                if(line==null)break;
                update.append(line);
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
            return "Error occur.";
        }
        return update.toString();
    }
}
