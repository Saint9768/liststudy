package com.saint.mybatis;

import org.apache.ibatis.annotations.Select;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

interface UserMapper {
    @Select("select * from user where id = #{id} and name = #{name}")
    List<String> selectUserList(int id, String name);
}

/**
 * @author Saint
 * @version 1.0
 * @createTime 2020-09-16 22:05
 */
public class MyBatisJob {

    public static void main(String[] args) {
        UserMapper userMapper = (UserMapper) Proxy.newProxyInstance(MyBatisJob.class.getClassLoader(),
                new Class<?>[]{UserMapper.class},
                (proxy, method, args1) -> {
                    System.out.println(method.getName());
                    Select annotation = method.getAnnotation(Select.class);
                    // 1. 获取方法的参数列表和参数值对应关系的MAP
                    Map<String, Object> argNameMap = buildMethodArgNameMap(method, args1);
                    if (annotation != null) {
                        String[] value = annotation.value();
                        // 注解里面的值
                        System.out.println(value.toString());
                        String sql = value[0];
                        sql = parseSQL(sql, argNameMap);
                        System.out.println(sql);
                        //获取返回类型
                        System.out.println(method.getReturnType());
                        // 获取返回类型的泛型形式
                        System.out.println(method.getGenericReturnType());
                    }
                    return null;
                });
        userMapper.selectUserList(1, "test");
    }

    /**
     * 格式化SQL语句
     *
     * @param sql        带#{}的SQL语句
     * @param nameArgMap 参数集合
     * @return
     */
    public static String parseSQL(String sql, Map<String, Object> nameArgMap) {
        StringBuilder stringBuilder = new StringBuilder();
        int length = sql.length();
        // 查找#{
        for (int i = 0; i < length; i++) {
            char c = sql.charAt(i);
            if (c == '#') {
                int nextIndex = i + 1;
                char nextChar = sql.charAt(nextIndex);
                if (nextChar != '{') {
                    throw new RuntimeException(String.format("这里应该为#{\nsql:%s\nindex:%d", stringBuilder.toString(),
                            nextIndex));
                }
                StringBuilder args = new StringBuilder();
                //查找右括号的位置 + 1，然后将获取到的参数名加入到args中。
                i = parseSQLArg(args, sql, nextIndex);
                String argName = args.toString();
                Object argValue = nameArgMap.get(argName);
                if (null == argValue) {
                    throw new RuntimeException(String.format("找不到参数值：%s", argName));
                }
                // 将参数的值拼接到SQL中
                stringBuilder.append(argValue.toString());
                continue;
            }
            // 拼接原SQL表达式字符到标准SQL中
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    /**
     * 计算#{ 中间的内容  }
     *
     * @param args      中间的内容 需要添加到的StringBuilder
     * @param sql       有占位符的SQL语句
     * @param nextIndex 下一次遍历SQL字符串的索引下标
     * @return
     */
    private static int parseSQLArg(StringBuilder args, String sql, int nextIndex) {
        //从左括号的下一个字符开始遍历
        nextIndex++;
        for (; nextIndex < sql.length(); nextIndex++) {
            char c = sql.charAt(nextIndex);
            if ('}' != c) {
                args.append(c);
                continue;
            }
            if ('}' == c) {
                return nextIndex;
            }
        }
        throw new RuntimeException(String.format("缺少右括号。\nindex:%d",
                nextIndex));
    }

    /**
     * 获取method中的参数
     *
     * @param method 方法
     * @param args   参数集合
     * @return
     */
    public static Map<String, Object> buildMethodArgNameMap(Method method, Object[] args) {
        Map<String, Object> nameArgMap = new HashMap<>();
        Parameter[] parameters = method.getParameters();
        //为了绕过Java虚拟机的检测 --foreach
        int[] index = {0};
        Arrays.asList(parameters).forEach(parameter -> {
            String name = parameter.getName();
            nameArgMap.put(name, args[index[0]]);
            index[0]++;
        });
        return nameArgMap;
    }

}
