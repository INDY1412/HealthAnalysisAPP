import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dmg.pmml.FieldName;
import org.json.JSONException;
import org.json.JSONObject;

public class SimpleTCPServer {

    Socket socket=null;
    ServerSocket server=null;
    BufferedReader dis=null;
    PrintWriter dos =null;
    Model clf = new Model("//home//ubuntu//RandomForestClassifier_diabete.pmml");
    Model clf2 = new Model("//home//ubuntu//RandomForestClassifier_hypertension.pmml");
//    TableJob s=null;

    public SimpleTCPServer() {
//        try {
//            s = new TableJob();
////            s.SearchEvent(1,34,1);
//        }
//        catch (Exception e)
//        {
//            System.err.println("flink process is false");
//        }

        try
        {
            server = new ServerSocket(9000); // 1、创建服务器，指定端口
            System.out.println("占用端口成功！");
            System.out.println("服务器准备完成！");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        while (true)
        {
            try
            {
                socket = server.accept();// 2、接收客户端连接 阻塞式
                ServerThread th = new ServerThread(socket);
                th.start();

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    class ServerThread extends Thread
    {

        Socket sk = null;
        public ServerThread(Socket sk)
        {
            this.sk = sk;
        }
        public void run()
        {
            try
            {
                dis = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                dos = new PrintWriter(socket.getOutputStream(), true);

                String msg = dis.readLine();
                System.out.println("服务端从客户端接收到： " + msg);
                JSONObject jsonobj2 = new JSONObject(msg);


                List<String> featureNames = clf.getFeatureNames();
                System.out.println("feature: " + featureNames);

                // 构建待预测数据
                Map<FieldName, Number> waitPreSample = new HashMap<FieldName, Number>();
                waitPreSample.put(new FieldName("age_at_study_date"), jsonobj2.getInt("age_at_study_date"));
                waitPreSample.put(new FieldName("drink_tea"), jsonobj2.getInt("drink_tea"));
                waitPreSample.put(new FieldName("drink_alcohol"), jsonobj2.getInt("drink_alcohol"));
                waitPreSample.put(new FieldName("is_smokker"), jsonobj2.getInt("is_smokker"));
                waitPreSample.put(new FieldName("met_hours"), jsonobj2.getInt("met_hours"));
                waitPreSample.put(new FieldName("standing_height_cm"), jsonobj2.getInt("standing_height_cm"));
                waitPreSample.put(new FieldName("waist_kg"), jsonobj2.getInt("waist_kg"));
                waitPreSample.put(new FieldName("is_female"), jsonobj2.getInt("is_female"));

                System.out.println("diabete predict result: " + clf.predict(waitPreSample).toString());
                System.out.println("diabete predictProba result: " + clf.predictProba(waitPreSample).toString());

                System.out.println("hypertension predict result: " + clf2.predict(waitPreSample).toString());
                System.out.println("hypertension predictProba result: " + clf2.predictProba(waitPreSample).toString());

//                String maxsql=s.SearchEvent(1,jsonobj2.getInt("age_at_study_date"),jsonobj2.getInt("is_female"));
//                String minsql=s.SearchEvent(0,jsonobj2.getInt("age_at_study_date"),jsonobj2.getInt("is_female"));

                // String echo ="欢迎";
                Map map2 = new HashMap();
                map2.put("diabete", clf.predictProba(waitPreSample).toString());
                map2.put("hypertension", clf2.predictProba(waitPreSample).toString());
//                map2.put("maxsql", maxsql);
//                map2.put("minsql", minsql);

                JSONObject jsonobj3 = new JSONObject(map2);
                String jsonStr3 = jsonobj3.toString();
                dos.println(jsonStr3); // 3、发送数据

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }

    }
    public static void main(String [] args)
    {
        new SimpleTCPServer();
    }

}

