package org.sec.module;

import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.NameExpr;
import org.apache.log4j.Logger;
import org.sec.util.RandomUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IdentifyModule {
    private static final Logger logger = Logger.getLogger(IdentifyModule.class);

    public static void doIdentify(MethodDeclaration method) {
        logger.info("do identify operate in method");
        doCallableIdentify(method);
    }

    public static void doConstructIdentify(ConstructorDeclaration cd) {
        logger.info("do identify operate in constructor");
        doCallableIdentify(cd);
    }

    private static void doCallableIdentify(CallableDeclaration<?> c) {
        Map<String, String> vas = new HashMap<>();
        List<VariableDeclarator> vaList = c.findAll(VariableDeclarator.class);
        for (VariableDeclarator va : vaList) {
            String newName = RandomUtil.getRandomString(20);
            vas.put(va.getNameAsString(), newName);
            va.setName(newName);
        }
        c.findAll(NameExpr.class).forEach(n -> {
            if (vas.containsKey(n.getNameAsString())) {
                n.setName(vas.get(n.getNameAsString()));
            }
        });
    }
}
