package org.sec;

import com.beust.jcommander.JCommander;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import org.sec.input.Command;
import org.sec.input.Logo;
import org.sec.module.IdentifyModule;
import org.sec.module.StringModule;
import org.sec.module.SwitchModule;
import org.sec.module.XORModule;
import org.sec.util.FileUtil;
import org.sec.util.RandomUtil;
import org.sec.util.WriteUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@SuppressWarnings("all")
public class Main {
    public static void main(String[] args) throws IOException {
        Logo.PrintLogo();
        System.out.println("Wait 1 Minute ... ");

        Command command = new Command();
        JCommander jc = JCommander.newBuilder().addObject(command).build();
        jc.parse(args);
        if (command.help) {
            jc.usage();
        }
        String password = command.password;
        boolean useUnicode = command.unicode;

        if (command.javascript) {
            MethodDeclaration jsMethod = getMethod("JS.java");
            MethodDeclaration decMethod = getMethod("Dec.java");
            if (jsMethod == null || decMethod == null) {
                return;
            }
            String newValue = SwitchModule.shuffle(jsMethod);
            SwitchModule.changeSwitch(jsMethod, newValue);
            int offset = StringModule.encodeString(jsMethod);
            StringModule.changeRef(jsMethod, offset);
            IdentifyModule.doIdentify(jsMethod);
            XORModule.doXOR(jsMethod);
            XORModule.doXOR(jsMethod);

            int decOffset = StringModule.encodeString(decMethod);
            StringModule.changeRef(decMethod, decOffset);
            IdentifyModule.doIdentify(decMethod);
            XORModule.doXOR(decMethod);

            WriteUtil.write(jsMethod, decMethod, password, useUnicode);
            System.out.println("Finish!");
            return;
        }
        if (command.superModule) {
            MethodDeclaration superMethod = getMethod("Javac.java");
            MethodDeclaration decMethod = getMethod("Dec.java");
            if (superMethod == null || decMethod == null) {
                return;
            }
            String newValue = SwitchModule.shuffle(superMethod);
            SwitchModule.changeSwitch(superMethod, newValue);
            int offset = StringModule.encodeString(superMethod);
            StringModule.changeRef(superMethod, offset);
            IdentifyModule.doIdentify(superMethod);
            XORModule.doXOR(superMethod);
            XORModule.doXOR(superMethod);

            int decOffset = StringModule.encodeString(decMethod);
            StringModule.changeRef(decMethod, decOffset);
            IdentifyModule.doIdentify(decMethod);
            XORModule.doXOR(decMethod);

            WriteUtil.writeSuper(superMethod, decMethod, password, useUnicode);
            System.out.println("Finish!");
            return;
        }
        if (command.antSword) {

            MethodDeclaration antMethod = getMethod("Ant.java");
            MethodDeclaration antDecMethod = getMethod("AntBase64.java");
            MethodDeclaration decMethod = getMethod("AntDec.java");
            List<ClassOrInterfaceDeclaration> antClasses = getAntClass("Ant.java");
            String antClassName = RandomUtil.getRandomString(10);

            List<ArrayInitializerExpr> arrayExpr = antMethod.findAll(ArrayInitializerExpr.class);
            StringLiteralExpr expr = (StringLiteralExpr) arrayExpr.get(0).getValues().get(1);
            expr.setValue(command.password);

            String antClassCode = null;
            String antDecCode = null;
            String antCode = null;
            String decCode = null;

            // Ant Class
            for (ClassOrInterfaceDeclaration c : antClasses) {
                if (!c.getNameAsString().equals("Ant")) {
                    c.setName(antClassName);
                    ConstructorDeclaration cd = c.findFirst(ConstructorDeclaration.class).get();
                    cd.setName(antClassName);
                    IdentifyModule.doConstructIdentify(cd);
                    XORModule.doXORForConstruct(cd);
                    c.findAll(MethodDeclaration.class).forEach(m -> {
                                IdentifyModule.doIdentify(m);
                                XORModule.doXOR(m);
                                XORModule.doXOR(m);
                            }
                    );
                    antClassCode = c.toString();
                }
            }
            // Base64 Dec
            int offset = StringModule.encodeString(antDecMethod);
            StringModule.changeRef(antDecMethod, offset);
            XORModule.doXOR(antDecMethod);
            XORModule.doXOR(antDecMethod);
            antDecCode = antDecMethod.toString();
            // Ant Code
            antMethod.findAll(ClassOrInterfaceType.class).forEach(ci->{
                if(ci.getNameAsString().equals("U")){
                    ci.setName(antClassName);
                }
            });
            String newValue = SwitchModule.shuffle(antMethod);
            SwitchModule.changeSwitch(antMethod, newValue);
            int antOffset = StringModule.encodeString(antMethod);
            StringModule.changeRef(antMethod, antOffset);
            IdentifyModule.doIdentify(antMethod);
            XORModule.doXOR(antMethod);
            XORModule.doXOR(antMethod);
            String antCodeTmp = antMethod.getBody().isPresent() ?
                    antMethod.getBody().get().toString() : null;
            antCode = antCodeTmp.substring(1, antCodeTmp.length() - 2);
            // Dec Code
            int decOffset = StringModule.encodeString(decMethod);
            StringModule.changeRef(decMethod, decOffset);
            IdentifyModule.doIdentify(decMethod);
            XORModule.doXOR(decMethod);
            decCode = decMethod.toString();

            WriteUtil.writeAnt(antClassCode, antCode, antDecCode, decCode);
            return;
        }

        MethodDeclaration method = getMethod("Base.java");
        MethodDeclaration decMethod = getMethod("Dec.java");

        if (method == null || decMethod == null) {
            return;
        }

        String newValue = SwitchModule.shuffle(method);
        SwitchModule.changeSwitch(method, newValue);
        int offset = StringModule.encodeString(method);
        StringModule.changeRef(method, offset);
        IdentifyModule.doIdentify(method);
        XORModule.doXOR(method);
        XORModule.doXOR(method);

        int decOffset = StringModule.encodeString(decMethod);
        StringModule.changeRef(decMethod, decOffset);
        IdentifyModule.doIdentify(decMethod);
        XORModule.doXOR(decMethod);

        WriteUtil.write(method, decMethod, password, useUnicode);
        System.out.println("Finish!");
    }

    private static MethodDeclaration getMethod(String name) throws IOException {
        InputStream in = Main.class.getClassLoader().getResourceAsStream(name);
        String code = FileUtil.readFile(in);
        CompilationUnit unit = StaticJavaParser.parse(code);
        return unit.findFirst(MethodDeclaration.class).isPresent() ?
                unit.findFirst(MethodDeclaration.class).get() : null;
    }

    private static List<ClassOrInterfaceDeclaration> getAntClass(String name) throws IOException {
        InputStream in = Main.class.getClassLoader().getResourceAsStream(name);
        String code = FileUtil.readFile(in);
        CompilationUnit unit = StaticJavaParser.parse(code);
        return unit.findAll(ClassOrInterfaceDeclaration.class);
    }
}

