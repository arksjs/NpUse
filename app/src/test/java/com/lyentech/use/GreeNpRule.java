package com.lyentech.use;

import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GreeNpRule implements TestRule {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                String methodName = description.getMethodName();
                //initHttp();
                //initRxjava2();
                System.out.println("开始测试-" + methodName);
                base.evaluate();
                System.out.println("结束测试-" + methodName);
            }
        };
    }

    public String doGet(String urlString) {//okhttp3有冲突
        String s = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 指定进行的是get请求
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5 * 1000);
            // 获取请求到的数据
            InputStream inputStream = connection.getInputStream();
            // 请求到的数据类型为 inputStream
            s = parseIsToString(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    private String parseIsToString(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        String result = stringBuilder.toString();
        return result;
    }
}
