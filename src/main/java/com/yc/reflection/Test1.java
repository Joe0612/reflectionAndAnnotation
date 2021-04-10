package com.yc.reflection;

import com.yc.Showable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @program: reflectionAndAnnotation
 * @description:
 * @author: Joe
 * @create: 2021-03-29 19:02
 */
public class Test1 {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Scanner sc=new Scanner(System.in);
        while (true){
            System.out.println("请输入类的路径");
            String path=sc.nextLine();
            System.out.println("待解析的类为："+path);

            Class c=Class.forName(path);
            String name=c.getName();
            System.out.println(name);

            Field [] fs=c.getDeclaredFields();  //Declared 自己声明的
            if(fs!=null &&fs.length>0 ){
                for(Field f:fs){
                    String modifier="";
                    switch (f.getModifiers()){
                        case 1:
                            modifier="public";break;
                        case 2:
                            modifier="private";break;
                    }
                    System.out.println(modifier+"\t"+f.getName());
                    //算法：位图算法
                }
            }

            Method [] ms=c.getDeclaredMethods();
            if(ms!=null && ms.length>0){
                for(Method m:ms){
                    System.out.println(m.getModifiers()+"\t"+m.getReturnType().toString()+"\t"+m.getName());
                }
            }

            Constructor []cs=c.getConstructors();
            if(cs!=null &&cs.length>0){
                for(Constructor m:cs){
                    System.out.println(m.getModifiers()+"\t"+m.getName());
                }
            }

            //利用反射完成实例化操作
            Object o=c.newInstance();
            //这个是已知接口名... instanceof
            if(o instanceof Showable){
                Showable p=(Showable)o;
                p.show();
            }

            //利用反射调用某个方法 适合J2EE中的规范化方法调用场景：setXXX getXxxx()
            System.out.println("=====");
            if(ms!=null && ms.length>0){
                for(Method m:ms){
                    if(m.getName().startsWith("sh")){
                        //调用此方法 show() 它有两个参数：第一个是实例  第二个实参数组
                        m.invoke(o);
                    }
                }
            }

            Map<String,String> pMap=new HashMap<String,String>();
            pMap.put("name","张三");
            pMap.put("age",20+"");
            Object oo=setValues(pMap,c);
            System.out.println(oo.toString());

        }
    }

    //反射功能模块 ：将map中保存的 属性值存到 cls对应的对象中。注意已知 cls满足j2ee的javabean规范（setXXX() getXxxx()）
    public static Object setValues(Map<String,String> map,Class cls) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Object o=null;
        o=cls.newInstance();
        Method []ms=cls.getDeclaredMethods();
        if(ms!=null &&ms.length>0){
            for(Method m:ms){
                //只有setXXX才会激活
                if(m.getName().startsWith("set")){
                    String mName=m.getName();
                    String fName=mName.substring(3).toLowerCase();
                    String value=map.get(fName);
                    if("Integer".equalsIgnoreCase(m.getParameterTypes()[0].getTypeName()) ||"Int".equalsIgnoreCase(m.getParameterTypes()[0].getTypeName()) ){
                        m.invoke(o,Integer.parseInt(value));
                    }else{
                        m.invoke(o,value);
                    }
                }
            }
        }
        return o;
    }

}

