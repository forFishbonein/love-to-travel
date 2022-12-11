package com.lovetotravel.travel;

import org.junit.jupiter.api.Test;
import org.python.core.*;
import org.python.util.PythonInterpreter;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class TravelserviceApplicationTests {


    @Test
    void contextLoads() throws IOException {

        PythonInterpreter interpreter = new PythonInterpreter();
        //执行python语句
        interpreter.exec("print('start test')");
        interpreter.exec("print('='*50)");

        //加载并运行python文件
        interpreter.execfile("src/main/resources/static/test.py");
        interpreter.exec("print('='*50)");

        //调用py文件里的属性
        PyObject xyzobj = interpreter.get("xyz");
        System.out.println("xyz=" + xyzobj);
        PyObject abcobj = interpreter.get("abc");
        System.out.println("abc=" + abcobj);
        interpreter.set("newstr", "newString");//手动添加newstr变量
        System.out.println("newstr=" + interpreter.get("newstr"));
        interpreter.exec("print('='*50)");

        //调用py文件里的方法
        PyFunction funcCls = interpreter.get("func_cls", PyFunction.class);
        PyObject returnPyobj = funcCls.__call__(new PyFloat(2), new PyFloat(3));
        System.out.println("结果:" + returnPyobj.toString());
        System.out.println("class=" + returnPyobj.getClass());
        System.out.println("s=" + returnPyobj.__findattr__("s"));
        System.out.println("r=" + returnPyobj.__findattr__("r"));
        System.out.println("ss=" + returnPyobj.__findattr__("ss"));
//		System.out.println(returnPyobj.__getattr__("ss"));//Exception in thread "main" AttributeError: Result instance has no attribute 'ss'
        interpreter.exec("print('='*50)");

        PyFunction funStr = interpreter.get("func_str", PyFunction.class);
        System.out.println(funStr.__call__().__tojava__(PyString.class));
        System.out.println(funStr.__call__());
        PyFunction funList = interpreter.get("func_list", PyFunction.class);
        PyList list = (PyList) funList.__call__().__tojava__(PyList.class);
        System.out.println(list);
        System.out.println(list.get(2));
        PyFunction funDict = interpreter.get("func_dict", PyFunction.class);
        PyDictionary dict = (PyDictionary) funDict.__call__().__tojava__(PyDictionary.class);
        System.out.println(dict);
        System.out.println(dict.get(2));

        PyFunction funcNostr = interpreter.get("func_nostr", PyFunction.class);
        System.out.println(funcNostr.__call__());//手动添加newstr后可以正常调用


    }

}