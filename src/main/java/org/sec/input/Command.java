package org.sec.input;

import com.beust.jcommander.Parameter;

public class Command {
    @Parameter(names = {"-h", "--help"}, description = "Help Info", help = true)
    public boolean help;

    @Parameter(names = {"-p", "--password"}, description = "Webshell Password", required = true)
    public String password;

    @Parameter(names = {"-o","--output"},description = "Output File")
    public String output;

    @Parameter(names = {"-u", "--unicode"}, description = "Use Unicode")
    public boolean unicode;

    @Parameter(names = {"--js"}, description = "Use JavaScript Engine")
    public boolean jsModule;

    @Parameter(names = {"--javac"}, description = "Use Javac Module")
    public boolean javacModule;

    @Parameter(names = {"--expr"}, description = "Use Expression Module")
    public boolean exprModule;

    @Parameter(names = {"--proxy"}, description = "Use Proxy Module")
    public boolean proxyModule;

    @Parameter(names = {"--asm"}, description = "Use ASM Module")
    public boolean asmModule;

    @Parameter(names = {"--ant"}, description = "Use Ant Sword")
    public boolean antSword;
}
