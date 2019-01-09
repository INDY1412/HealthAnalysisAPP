import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.json.*;


public class SimpleTCPClient {
    public static void main(String[] args) throws UnknownHostException, IOException {
        // 1、创建客户端，必须指定服务器+端口，此时就在连接
        Socket client = new Socket("111.230.95.62", 9000);
        BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter pw = new PrintWriter(client.getOutputStream(), true);

        Map<String, Number> waitPreSample = new HashMap<String, Number>();
        waitPreSample.put("age_at_study_date", 45);
        waitPreSample.put("drink_tea", 0);
        waitPreSample.put("drink_alcohol", 0);
        waitPreSample.put("is_smokker", 0);
        waitPreSample.put("met_hours", 2);
        waitPreSample.put("standing_height_cm", 180);
        waitPreSample.put("waist_kg", 70);
        waitPreSample.put("is_female", 1);

        JSONObject jsonobj = new JSONObject(waitPreSample);
        String jsonStr = jsonobj.toString();
         System.out.println(jsonStr);
        // String msg = "注册";
        pw.println(jsonStr); // 2、向服务器发送"注册"字符串
        String echo = br.readLine();// 3、接收服务器响应数据
        System.out.println("客户端接收到服务器响应： " + echo);

    }

}