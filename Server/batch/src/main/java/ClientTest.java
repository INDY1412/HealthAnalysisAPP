import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.json.*;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class ClientTest extends AbstractJavaSamplerClient{

    private static final Logger logger = LoggerFactory.getLogger(ClientTest.class);


    private String num1;
    private String num2;


    /**
     * 这个方法是用来自定义java方法入参的
     * params.addArgument("num1","");表示入参名字叫num1，默认值为空。
     * @return
     */
    @Override
    public Arguments getDefaultParameters() {
        Arguments params = new Arguments();
        params.addArgument("num1", "");
        params.addArgument("num2", "");
        return params;
    }


    /**
     * 每个线程测试前执行一次，做一些初始化工作
     * 获取输入的参数,赋值给变量,参数也可以在下面的runTest方法中获取,这里是为了展示该方法的作用
     * @param arg0
     */
    @Override
    public void setupTest(JavaSamplerContext arg0) {
        num1 = arg0.getParameter("num1");
        num2 = arg0.getParameter("num2");
    }


    /**
     * 真正执行逻辑的方法
     * @param arg0
     * @return
     */
    @Override
    public SampleResult runTest(JavaSamplerContext arg0) {
        SampleResult sr = new SampleResult();
        sr.setSamplerData("请求参数num1："+num1+"\n请求参数num2："+num2);


        logger.info("请求参数num1：{} 请求参数num2：{}",num1,num2);


        try {
            // jmeter 开始统计响应时间标记
            sr.sampleStart();


            int sum = Integer.parseInt(num1)+Integer.parseInt(num2);


            // 通过下面的操作就可以将被测方法的响应输出到Jmeter的察看结果树中的响应数据里面了。
            sr.setResponseData("结果是："+sum, "utf-8");
            logger.info("结果是：{}",sum);
            sr.setDataType(SampleResult.TEXT);


            //设置响应执行成功
            sr.setSuccessful(true);
        } catch (Throwable e) {
            //有异常,执行失败
            sr.setSuccessful(false);
            e.printStackTrace();
        } finally {
            // jmeter 结束统计响应时间标记
            sr.sampleEnd();
        }
        return sr;
    }




    /**
     * 测试结束后调用
     * @param arg0
     */
    @Override
    public void teardownTest(JavaSamplerContext arg0) {


    }


    /**
     * main方法测试程序是否可用,打包时 注释掉
     * @param args
     */
//    public static void main(String[] args) {
//        Arguments params = new Arguments();
//        //设置参数，并赋予默认值1
//        params.addArgument("num1", "1");
//        //设置参数，并赋予默认值2
//        params.addArgument("num2", "2");
//        JavaSamplerContext arg0 = new JavaSamplerContext(params);
//        JavaTest test = new JavaTest();
//        test.setupTest(arg0);
//        test.runTest(arg0);
//        test.teardownTest(arg0);
//    }

}
