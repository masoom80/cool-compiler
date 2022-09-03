import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CodeGeneratorImpl implements CodeGenerator {
    Scanner scanner;
    int scope;
    Discriptor inClass, inMethod;
    Stack<Object> semanticStack;
    static ArrayList<ArrayList<Discriptor>> discriptors;
    ArrayList<String> code;
    ArrayList<String> between;
    int adrs = 0;
    int jps = 0;
    int args = 0;

    CodeGeneratorImpl(Scanner scanner) {
        this.scanner = scanner;
        this.scope = 0;
        this.inClass = new Discriptor();
        this.inMethod = new Discriptor();
        this.semanticStack = new Stack<>();
        discriptors = new ArrayList<>();
        discriptors.add(new ArrayList<>());
        discriptors.get(0).add(new Discriptor("bool", Type.CLASS, null
                , null, null
                , "Adr" + adrs++, null,
                -1, 1,
                scope));
        discriptors.get(0).add(new Discriptor("string", Type.CLASS, null
                , null, null
                , "Adr" + adrs++, null,
                -1, 1,
                scope));
        discriptors.get(0).add(new Discriptor("int", Type.CLASS, null
                , null, null
                , "Adr" + adrs++, null,
                -1, 1,
                scope));
        discriptors.get(0).add(new Discriptor("real", Type.CLASS, null
                , null, null
                , "Adr" + adrs++, null,
                -1, 1,
                scope));
    }


    static public boolean isNameAlreadyTakenInScope(Discriptor name, int scope) {
        for (int i = 0; i < discriptors.get(scope).size(); i++) {
            if (discriptors.get(scope).get(i).name.equals(name.name) &&
                    discriptors.get(scope).get(i).method.equals(name.method) &&
                    discriptors.get(scope).get(i).Class.equals(name.Class)) {
                return true;
            }
        }
        return false;
    }

    static public Discriptor getDiscriptor(Discriptor discriptor, int scope) {

        for (int i = scope; i > -1; i--) {

            for (int j = 0; j < discriptors.get(i).size(); j++) {

                if (discriptors.get(i).get(j).name.equals(discriptor.name) &&
                        discriptors.get(i).get(j).method.equals(discriptor.method) &&
                        discriptors.get(i).get(j).Class.equals(discriptor.Class)) {
                    return discriptors.get(i).get(j);
                }

            }
        }
        return null;
    }

    static void generate(FileWriter fileWriter) throws Exception {

        Discriptor main = new Discriptor("main", Type.METHOD, "Main"
                , null, null
                , "Adr", null,
                -1, 1,
                0);
        try {
            main = getDiscriptor(main, 1);
            discriptors.get(1).remove(main);
        } catch (Exception e) {
            throw new Exception("no main function!");
        }
        fileWriter.write(".text\n" +
                ".globl main\n");

        ArrayList<String> datas = new ArrayList<>();
        ArrayList<String> codesMain = new ArrayList<>();
        int address = 0;
        int indexString = 0, indexArrays = 0;
        datas.add("\tstringsSpace: .space 50000");
        datas.add("\tarraysSpace: .space 50000");
        datas.add("\twhiteSpace: .asciiz \" \"");
        datas.add("\tdivideZero: .asciiz \"can not divide by zero!\"");
        datas.add("\toutOfBound: .asciiz \"the arrya index is out of bound!\"");

        for (ArrayList<Discriptor> list :
                discriptors) {
            for (Discriptor discriptor :
                    list) {
                switch (discriptor.type) {
                    case VAR:
                        switch (discriptor.data_type) {
                            case "int":
                            case "real":
                                datas.add("\t" + discriptor.Adr + ": .word 0");
                                break;
                            case "bool":
                                datas.add("\t" + discriptor.Adr + ": .space 1");
                                break;
                            case "string":
                                if (discriptor.args.size() > 0) {
                                    datas.add("\t" + discriptor.Adr + ": .word 0");
                                    datas.add("\tss" + address + ": .asciiz " + discriptor.args.get(0) );
                                    codesMain.add(0, "\tsw $t0 ," + discriptor.Adr);
                                    codesMain.add(0, "\tla $t0 , ss" + address++ + "");
                                } else {
                                    datas.add("\t" + discriptor.Adr + ": .word 0");
                                    codesMain.add(0, "\tsw $t0 ," + discriptor.Adr);
                                    codesMain.add(0, "\tla $t0 , stringsSpace+" + indexString);
                                    indexString += discriptor.size;
                                }
                                break;

                        }
                        break;
                    case CLASS:
                        break;
                    case ARRVAR:
                        break;
                    case METHOD:
                        fileWriter.write(discriptor.name + discriptor.Class + discriptor.scope + ":\n");
                        for (int i = 0; i < discriptor.args.size(); i++) {
                            Discriptor a = (Discriptor) discriptor.args.get(i);
                            discriptor.codes.add(0, "\tsw $a" + i + ", " + a.Adr);
                        }
                        if (discriptor.codes.size() > 0) {
                            fileWriter.write(discriptor.getCode());
                        }
                        fileWriter.write("\tli $v0, 0\n");
                        fileWriter.write("\tjr $ra\n");
                        break;
                }
            }
        }

        fileWriter.write("ErDZ:\n");
        fileWriter.write("\tli $v0 , 4\n");
        fileWriter.write("\tla $a0 , divideZero\n");
        fileWriter.write("\tsyscall\n");
        fileWriter.write("\tli $v0, 10\n\tsyscall\n");
        fileWriter.write("errorArrBound:\n");
        fileWriter.write("\tli $v0 , 4\n");
        fileWriter.write("\tla $a0 , outOfBound\n");
        fileWriter.write("\tsyscall\n");
        fileWriter.write("\tli $v0, 10\n\tsyscall\n");

        fileWriter.write("main:\n");
        StringBuilder ret = new StringBuilder();

        for (String line :
                codesMain) {
            ret.append(line);
            ret.append("\n");
        }

        fileWriter.write(ret.toString());
        try {
            fileWriter.write(main.getCode());
        } catch (Exception e) {
            throw new Exception("no main function!");
        }
        fileWriter.write("\tli $v0, 10\n\tsyscall\n");
        fileWriter.write(".data\n");
         ret = new StringBuilder();

        for (String line :
                datas) {
            ret.append(line);
            ret.append("\n");
        }

        fileWriter.write(ret.toString());

    }

    String returnType;
    Discriptor a;
    Discriptor b;
    Discriptor temp;
    long PC;
    Stack<Integer> inLoop = new Stack<>();

    @Override
    public void doSemantic(String sem) throws Exception {
        switch (sem) {

            case ("makeClassDiscriptor"):
                //try {

                inClass = new Discriptor(scanner.token.token, Type.CLASS, null
                        , null, null
                        , "Adr" + adrs++, null,
                        -1, 1,
                        scope);
                discriptors.get(0).add(inClass);
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;

            case ("newScope"):
                //try {
//                System.out.println(discriptors.toString());
                scope++;
                while (discriptors.size() <= scope) {
                    discriptors.add(new ArrayList<>());
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("endScope"):
                //try {
//                System.out.println(discriptors.toString());
                if (scope == 1) {
                    inClass = new Discriptor();
                } else if (scope == 2) {
                    inMethod = new Discriptor();
                }
                scope--;
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("declareVariable"):
                //try {
                temp = new Discriptor(scanner.token.token, Type.VAR, inClass.name
                        , inMethod.name, (String) semanticStack.peek()
                        , "Adr" + adrs++, null,
                        -1, 1,
                        scope);

                if (isNameAlreadyTakenInScope(temp, scope)) {
//                    //System.out.println(sem);//err
                    throw new Exception("in the " + sem + " exception occurred");
                }
                discriptors.get(scope).add(temp);
                if (inMethod.name == null) {
                    inClass.discrpts.add(temp);
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("declareArrVariable"):
                //try {

                temp = new Discriptor(scanner.token.token, Type.ARRVAR, inClass.name
                        , inMethod.name, (String) semanticStack.peek()
                        , "Adr" + adrs++, null,
                        0, 1,
                        scope);
                if (isNameAlreadyTakenInScope(temp, scope)) {
//                    //System.out.println(sem);//err
                    throw new Exception("in the " + sem + " exception occurred");
                }
                discriptors.get(scope).add(temp);
                if (inMethod.name == null) {
                    inClass.discrpts.add(temp);
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("popVarType"):
                //try {
                Object top = semanticStack.peek();
                if (top instanceof String) {
                    semanticStack.pop();
                } else {
                    throw new Exception("in the " + sem + " exception occurred");
//                    //System.out.println(sem);//err
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("pushType"):
            case ("pushCastType"):
                //try {
                temp = new Discriptor(scanner.token.token, Type.VAR, inClass.name
                        , inMethod.name, null
                        , "Adr", null,
                        -1, 0,
                        scope);
                if (!isNameAlreadyTakenInScope(temp, scope)) {
                    semanticStack.push(temp.name);
                } else {
                    throw new Exception("name already token in scope!");
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("makeFuncDiscriptor"):
                //try {

                inMethod = new Discriptor(scanner.token.token, Type.METHOD, inClass.name
                        , null, null
                        , "Adr" + adrs++, null,
                        -1, 0,
                        scope);
                code = new ArrayList<>();
                if (!isNameAlreadyTakenInScope(inMethod, scope)) {
                    discriptors.get(scope).add(inMethod);
                    semanticStack.push(scanner.token.token);
                } else {
                    throw new Exception("in the " + sem + " exception occurred");
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("popReturnType"):
                //try {
                semanticStack.pop();
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("endFunc"):
                //try {
                temp = new Discriptor((String) semanticStack.peek(), Type.VAR, inClass.name
                        , null, (String) semanticStack.peek()
                        , "Adr", null,
                        -1, 1,
                        scope);
                a = getDiscriptor(temp, scope);
                if (a != null) {
                    a.codes = code;
                    inClass.discrpts.add(inMethod);
                } else {
                    System.out.println((String) semanticStack.peek());
                    throw new Exception("in the " + sem + " exception occurred");

                }
                if (scope == 1) {
                    inClass = new Discriptor();
                } else if (scope == 2) {
                    inMethod = new Discriptor();
                }
                scope--;
                semanticStack.pop();
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("declareVarArgPopType"):
                //try {
                temp = new Discriptor(scanner.token.token, Type.VAR, inClass.name
                        , inMethod.name, (String) semanticStack.peek()
                        , "Adr" + adrs++, null,
                        -1, 1,
                        scope);
                if (!isNameAlreadyTakenInScope(temp, scope)) {
                    inMethod.args.add(temp);
                    discriptors.get(scope).add(temp);
                    semanticStack.pop();
                } else {
                    throw new Exception("in the " + sem + " exception occurred");
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("declareArrVarArgPopType"):
                //try {
                temp = new Discriptor(scanner.token.token, Type.ARRVAR, inClass.name
                        , inMethod.name, (String) semanticStack.peek()
                        , "Adr" + adrs++, null,
                        0, 1,
                        scope);
                if (!isNameAlreadyTakenInScope(temp, scope)) {
                    inMethod.args.add(temp);
                    discriptors.get(scope).add(temp);
                    semanticStack.pop();
                } else {
                    throw new Exception("in the " + sem + " exception occurred");
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("funcReturnTypeVarPopType"):
                //try {
                returnType = (String) semanticStack.peek();
                temp = new Discriptor((String) semanticStack.peek(), Type.VAR, ""
                        , "", (String) semanticStack.peek()
                        , "Adr", null,
                        -1, 1,
                        scope);
                a = getDiscriptor(temp, scope);
                if (a != null) {
                    inMethod.returnType = returnType;
                } else {
                    throw new Exception("in the " + sem + " exception occurred");
//                    //System.out.println(sem);//err
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("funcReturnTypeVarToArrVar"):
                //try {
                returnType = (String) semanticStack.peek();
                temp = new Discriptor((String) semanticStack.peek(), Type.VAR, ""
                        , "", (String) semanticStack.peek()
                        , "Adr", null,
                        -1, 1,
                        scope);
                a = getDiscriptor(temp, scope);
                semanticStack.pop();
                semanticStack.push(returnType + "$Arr");
                if (a != null) {
                    inMethod.returnType = returnType + "$Arr";
                } else {
                    throw new Exception("in the " + sem + " exception occurred");
//                    //System.out.println(sem);//err
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("voidReturnType"):
                //try {
                semanticStack.push("void");
                inMethod.returnType = "void";

//                } catch (Exception e) {
//                    //System.out.println(sem);
//                    System.out.println(semanticStack.peek());
//                    System.out.println(e.getClass());
//                }
                break;
            case ("continueLoop"):
                //try {
                if (!inLoop.empty()) {
                    PC = (long) semanticStack.peek();
                    code.add("\tb jps" + inLoop.peek());
                    //jp to check the if in while
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("breakOutLoop"):
                //try {

                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("returnNull"):
                //try {
                // make the return value null
                if (inMethod.returnType.equals("void")) {
                    code.add("\tli $v0, 0");
                    code.add("\tjr $ra");
                } else {
                    throw new Exception("in the " + sem + " exception occurred");
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("return"):
                //try {
                a = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                if (a != null) {
                    if (a.getType() != null) {
                        if (a.getType().equals(inMethod.returnType)) {
                            code.add("\tla $v0, " + a.Adr);
                            code.add("\tjr $ra");
                            semanticStack.pop();
                        } else {
                            throw new Exception("return type does not match in " + inMethod.name + "!");
                        }
                    } else {
                        throw new Exception("in the " + sem + " exception occurred");
                    }
                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                }
                //return the value in a.adr
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("makeOneMore"):
                //try {
                a = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                if (a != null) {
                    if (a.data_type.equals("int")) {
                        code.add("\tla $t0, " + a.Adr);
                        code.add("\tlw $t1, 0($t0)");
                        code.add("\taddi $t1, $t1, 1");
                        code.add("\tsw $t1, " + a.Adr);
                    } else {
                        throw new Exception("in the " + sem + " exception occurred");
                    }
                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                }
                //check a is int or sci or real
                //increment the value in a.adr
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("makeOneLess"):
                //try {
                a = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                if (a != null) {
                    if (a.data_type.equals("int")) {
                        code.add("\tla $t0, " + a.Adr);
                        code.add("\tlw $t1, 0($t0)");
                        code.add("\tli $t2, 1");
                        code.add("\tsub $t2, $t1, $t2");
                        code.add("\tsw $t2, " + a.Adr);
                    } else {
                        throw new Exception("in the " + sem + " exception occurred");
                    }
                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("checkTypeAssign"):
                //try {
                b = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                a = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                if (a != null && b != null) {
                    if (a.data_type.equals(b.data_type)) {
                        //string? size update => a.size = max(b.size,a.size) bonus
                        //arr? size => a.ub = max(b.ub, a.ub)
                        //put value that is in b.adr in a.adr
                        if (a.data_type.equals("string")) {
                            a.size = b.size;
                        } else if (a.type == Type.ARRVAR) {
                            a.upper_bound = b.upper_bound;
                        }
                        if (a.data_type.equals("bool")) {
                            code.add("\tlb $t1, " + b.Adr);
                            code.add("\tsb $t1, " + a.Adr);
                        } else {
                            if (a.data_type.equals("int") || a.data_type.equals("string")) {
                                code.add("\tlw $t1, " + b.Adr);
                                code.add("\tsw $t1, " + a.Adr);
                            } else {
                                code.add("\tl.s $f1, " + b.Adr);
                                code.add("\ts.s $f1, " + a.Adr);
                            }
                        }
                    } else {
                        System.out.println(a);
                        System.out.println(b);
                        throw new Exception("in the " + sem + " exception occurred");
                        //err
                    }
                } else {
                    throw new Exception("in the " + sem + " exception occurred");
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("checkTypePlus"):
                //try {
                //only for int and double
                b = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                a = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                if (a != null && b != null) {
                    if (a.data_type.equals(b.data_type) && (a.data_type.equals("real") || a.data_type.equals("int"))) {
                        //put value that is in b.adr + the value in a.adr  in a.adr
                        if (a.data_type.equals("int")) {
                            code.add("\tlw $t0, " + a.Adr);
                            code.add("\tlw $t1, " + b.Adr);
                            code.add("\tadd $t4, $t0, $t1");
                            code.add("\tsw $t4, " + a.Adr);
                        } else {
                            code.add("\tl.s $f0, " + a.Adr);
                            code.add("\tl.s $f1, " + b.Adr);
                            code.add("\tadd.s $f1, $f0, $f1");
                            code.add("\ts.s $f2, " + a.Adr);
                        }
                    } else {
                        throw new Exception("in the " + sem + " exception occurred");
                        //err
                    }

                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("checkTypeMinus"):
                //try {
                //only for int and double
                b = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                a = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                if (a != null && b != null) {
                    if (a.data_type.equals(b.data_type) && (a.data_type.equals("real") || a.data_type.equals("int"))) {
                        //put value that is in a.adr  - the value in b.adr  in a.adr
                        if (a.data_type.equals("int")) {
                            code.add("\tlw $t2, " + a.Adr);
                            code.add("\tlw $t3, " + b.Adr);
                            code.add("\tsub $t4, $t2, $t3");
                            code.add("\tsw $t4, " + a.Adr);
                        } else {
                            code.add("\tl.s $f0, " + a.Adr);
                            code.add("\tl.s $f1, " + b.Adr);
                            code.add("\tsub.s $f2, $f0, $f1");
                            code.add("\ts.s $f2, " + a.Adr);
                        }
                    } else {
                        throw new Exception("in the " + sem + " exception occurred");
                        //err
                    }

                } else {
                    throw new Exception("in the " + sem + " exception occurred");
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("checkTypeMult"):
                //try {
                //only for int and double
                b = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                a = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                if (a != null && b != null) {
                    if (a.data_type.equals(b.data_type) && (a.data_type.equals("real") || a.data_type.equals("int"))) {
                        //put value that is in b.adr * the value in a.adr  in a.adr
                        if (a.data_type.equals("int")) {
                            code.add("\tlw $t2, " + a.Adr);
                            code.add("\tlw $t3, " + b.Adr);
                            code.add("\tmul $t4, $t2, $t3");
                            code.add("\tsw $t4, " + a.Adr);
                        } else {
                            code.add("\tl.s $f0, " + a.Adr);
                            code.add("\tl.s $f1, " + b.Adr);
                            code.add("\tmul.s $f2, $f0, $f1");
                            code.add("\ts.s $f2, " + a.Adr);
                        }
                    } else {
                        throw new Exception("in the " + sem + " exception occurred");
                        //err
                    }

                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("checkTypeDiv"):
                //try {
                //only for int and double
                b = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                a = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                if (a != null && b != null) {
                    if (a.data_type.equals(b.data_type) && (a.data_type.equals("real") || a.data_type.equals("int"))) {
                        //put value that is in a.adr / the value in b.adr  in a.adr
                        if (a.data_type.equals("int")) {
                            code.add("\tlw $t2, " + a.Adr);
                            code.add("\tlw $t3, " + b.Adr);
                            code.add("\tbeq $t3, 0, ErDZ"); // add to spim
                            code.add("\tdiv $t4, $t2, $t3");
                            code.add("\tsw $t4, " + a.Adr);
                        } else {
                            code.add("\tl.s $f0, " + a.Adr);
                            code.add("\tl.s $f1, " + b.Adr);
                            code.add("\tli.s $f2, 0.0");
                            code.add("\tc.eq.s $f1, $f2"); // handle float
                            code.add("\tbc1t ErDZ"); // handle float
                            code.add("\tdiv.s $f2, $f0, $f1");
                            code.add("\ts.s $f2, " + a.Adr);
                        }
                    } else {
                        throw new Exception("in the " + sem + " exception occurred");

                        //err
                    }

                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("Or"):
                //try {
                b = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                a = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                if (a != null && b != null) {
                    if (a.data_type.equals(b.data_type) && a.data_type.equals("bool")) {
                        //make new discriptor() with type bool and calculate (a or b) and put it in new discriptor.adr
                        // then push it to discriptors
                        temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                                , inMethod.name, "bool"
                                , "Adr" + adrs++, null,
                                -1, 1,
                                scope);
                        code.add("\tla $t0, " + a.Adr);
                        code.add("\tla $t1, " + b.Adr);
                        code.add("\tlb $t2, 0($t0)");
                        code.add("\tlb $t3, 0($t1)");
                        code.add("\tor $t4, $t2, $t3");
                        code.add("\tsb $t4, " + temp.Adr);
                        semanticStack.push(temp);
                        discriptors.get(scope).add(temp);
                    } else {
                        throw new Exception("in the " + sem + " exception occurred");
                        //err
                    }

                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("And"):
                //try {
                b = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                a = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                if (a != null && b != null) {
                    if (a.data_type.equals(b.data_type) && a.data_type.equals("bool")) {
                        //make new discriptor() with type bool and calculate (a and b) and put it in new discriptor.adr
                        // then push it to discriptors
                        temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                                , inMethod.name, "bool"
                                , "Adr" + adrs++, null,
                                -1, 1,
                                scope);
                        code.add("\tla $t0, " + a.Adr);
                        code.add("\tla $t1, " + b.Adr);
                        code.add("\tlb $t2, 0($t0)");
                        code.add("\tlb $t3, 0($t1)");
                        code.add("\tand $t4, $t2, $t3");
                        code.add("\tsb $t4, " + temp.Adr);
                        semanticStack.push(temp);
                        discriptors.get(scope).add(temp);
                    } else {
                        throw new Exception("in the " + sem + " exception occurred");
                        //err
                    }

                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("isEqualNeg"):
                //try {
                b = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                temp = null;
                //check if b can be gharine :)
                //make a new discriptor with value = -(value in b.adr)
                //b = new discriptor above
                if (b != null && (b.data_type.equals("int") || b.data_type.equals("real"))) {
                    temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                            , inMethod.name, b.data_type.equals("int") ? "int" : "real"
                            , "Adr" + adrs++, null,
                            -1, 1,
                            scope);
                    if (a.data_type.equals("int")) {
                        code.add("\tlw $t1, " + b.Adr);
                        code.add("\tneg $t2, $t1");
                        code.add("\tsw $t2, " + temp.Adr);
                    } else {
                        code.add("\tl.s $f0, " + b.Adr);
                        code.add("\tneg.s $f1, $f0");
                        code.add("\ts.s $f1, " + temp.Adr);
                    }
                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                }
                a = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                if (a != null && b != null && temp != null && (a.data_type.equals("int") || a.data_type.equals("real"))) {
                    b = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                            , inMethod.name, "bool"
                            , "Adr" + adrs++, null,
                            -1, 1,
                            scope);
                    if (a.data_type.equals("int")) {
                        code.add("\tlw $t1, " + a.Adr);
                        code.add("\tseq $t3, $t1, $t2");
                        code.add("\tsb $t3, " + b.Adr);
                    } else {
                        code.add("\tl.s $f0, " + a.Adr);
                        code.add("\tc.eq.s $f0, $f1"); //handle float
                        code.add("\tli $t0, 0");
                        code.add("\tli $t1, 1");
                        code.add("\tbc1f, jps" + jps);
                        code.add("\tsb $t1, " + b.Adr);
                        code.add("\tb, jps" + (jps + 1));
                        code.add("jps" + jps++ + ":");
                        code.add("\tsb $t0, " + b.Adr);
                        code.add("jps" + jps++ + ":");
                    }
                    semanticStack.push(b);
                    discriptors.get(scope).add(b);
                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                }
//                if(check if they can be compared){
                //make new discriptor with type bool and calculate (a == b) and put it in new discriptor.adr
                // the push it to discriptors
//                }else{
                //err
//                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("isEqual"):
                //try {
                b = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                a = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                if (a != null && b != null && a.data_type.equals(b.data_type) && !a.data_type.equals("string")) {
                    temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                            , inMethod.name, "bool"
                            , "Adr" + adrs++, null,
                            -1, 1,
                            scope);
                    if (a.data_type.equals("bool")) {
                        code.add("\tla $t0, " + a.Adr);
                        code.add("\tla $t1, " + b.Adr);
                        code.add("\tlb $t2, 0($t0)");
                        code.add("\tlb $t3, 0($t1)");
                        code.add("\tseq $t4, $t2, $t3");
                        code.add("\tsb $t4, " + temp.Adr);
                    } else {
                        if (a.data_type.equals("int")) {
                            code.add("\tlw $t2, " + a.Adr);
                            code.add("\tlw $t3, " + b.Adr);
                            code.add("\tseq $t4, $t2, $t3");
                            code.add("\tsb $t4, " + temp.Adr);
                        } else {
                            code.add("\tl.s $f0, " + a.Adr);
                            code.add("\tl.s $f1, " + b.Adr);
                            code.add("\tc.eq.s $f0, $f1");//handle float
                            code.add("\tli $t0, 0");
                            code.add("\tli $t1, 1");
                            code.add("\tbc1f, jps" + jps);
                            code.add("\tsb $t1, " + temp.Adr);
                            code.add("\tb, jps" + (jps + 1));
                            code.add("jps" + jps++ + ":");
                            code.add("\tsb $t0, " + temp.Adr);
                            code.add("jps" + jps++ + ":");
                        }

                    }

                    semanticStack.push(temp);
                    discriptors.get(scope).add(temp);
                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                }
//                if(check if they can be compared){
                //make new discriptor with type bool and calculate (a == b) and put it in new discriptor.adr
                // then push it into discriptors
//                }else{
                //err
//                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("isEqualNot"):
                //try {
                b = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                a = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                if (a != null && b != null && a.data_type.equals(b.data_type) && a.data_type.equals("bool")) {
                    temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                            , inMethod.name, "bool"
                            , "Adr" + adrs++, null,
                            -1, 1,
                            scope);
                    code.add("\tla $t0, " + a.Adr);
                    code.add("\tla $t1, " + b.Adr);
                    code.add("\tlw $t2, 0($t0)");
                    code.add("\tlw $t3, 0($t1)");
                    code.add("\tsne $t4, $t2, $t3");
                    code.add("\tsb $t4, " + temp.Adr);
                    semanticStack.push(temp);
                    discriptors.get(scope).add(temp);
                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                }
//                if(check if they can be compared){
                //make new discriptor with type bool and calculate (a != b) and put it in new discriptor.adr
                // the push it to discriptors
//                }else{
                //err
//                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("isNotEqualNot"):
                //try {
                b = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                a = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                if (a != null && b != null && a.data_type.equals(b.data_type) && a.data_type.equals("bool")) {
                    temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                            , inMethod.name, "bool"
                            , "Adr" + adrs++, null,
                            -1, 1,
                            scope);
                    code.add("\tlw $t2, " + a.Adr);
                    code.add("\tlw $t3, " + b.Adr);
                    code.add("\tseq $t4, $t2, $t3");
                    code.add("\tsb $t4, " + temp.Adr);
                    semanticStack.push(temp);
                    discriptors.get(scope).add(temp);
                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("isNotEqual"):
                //try {
                b = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                a = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                if (a != null && b != null && a.data_type.equals(b.data_type) && !a.data_type.equals("string")) {
                    temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                            , inMethod.name, "bool"
                            , "Adr" + adrs++, null,
                            -1, 1,
                            scope);
                    if (a.data_type.equals("bool")) {
                        code.add("\tla $t0, " + a.Adr);
                        code.add("\tla $t1, " + b.Adr);
                        code.add("\tlb $t2, 0($t0)");
                        code.add("\tlb $t3, 0($t1)");
                        code.add("\tsne $t4, $t2, $t3");
                        code.add("\tsb $t4, " + temp.Adr);
                    } else {
                        if (a.data_type.equals("int")) {
                            code.add("\tla $t0, " + a.Adr);
                            code.add("\tla $t1, " + b.Adr);
                            code.add("\tlw $t2, 0($t0)");
                            code.add("\tlw $t3, 0($t1)");
                            code.add("\tsne $t4, $t2, $t3");
                            code.add("\tsb $t4, " + temp.Adr);
                        } else {
                            code.add("\tl.s $f0, " + a.Adr);
                            code.add("\tl.s $f1, " + b.Adr);
                            code.add("\tc.eq.s  $f0, $f1");
                            code.add("\tli $t0, 0");
                            code.add("\tli $t1, 1");
                            code.add("\tbc1t, jps" + jps);
                            code.add("\tsb $t1, " + temp.Adr);
                            code.add("\tb, jps" + (jps + 1));
                            code.add("jps" + jps++ + ":");
                            code.add("\tsb $t0, " + temp.Adr);
                            code.add("jps" + jps++ + ":");
                        }
                    }

                    semanticStack.push(temp);
                    discriptors.get(scope).add(temp);
                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("isNotEqualNeg"):
                //try {
                b = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                //check if b can be gharine :)
                //make a new discriptor with value = -(value in b.adr)
                //b = new discriptor above
                if (b != null && (b.data_type.equals("int") || b.data_type.equals("real"))) {
                    temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                            , inMethod.name, b.data_type.equals("int") ? "int" : "real"
                            , "Adr" + adrs++, null,
                            -1, 1,
                            scope);
                    if (a.data_type.equals("int")) {
                        code.add("\tla $t0, " + b.Adr);
                        code.add("\tlw $t1, 0($t0)");
                        code.add("\tneg $t2, $t1");
                        code.add("\tsw $t2, " + temp.Adr);
                    } else {
                        code.add("\tla $t0, " + b.Adr);
                        code.add("\tl.s $f0, 0($t0)");
                        code.add("\tneg.s $f1, $f0");
                        code.add("\ts.s $f1, " + temp.Adr);
                    }
                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                }
                a = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                if (a != null && b != null && temp != null && (a.data_type.equals("int") || a.data_type.equals("real"))) {
                    b = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                            , inMethod.name, "bool"
                            , "Adr" + adrs++, null,
                            -1, 1,
                            scope);
                    if (a.data_type.equals("int")) {
                        code.add("\tla $t0, " + a.Adr);
                        code.add("\tlw $t1, 0($t0)");
                        code.add("\tsne $t3, $t1, $t2");
                        code.add("\tsb $t3, " + b.Adr);
                    } else {
                        code.add("\tl.s $f0, " + a.Adr);
                        code.add("\tc.eq.s $f0, $f1");
                        code.add("\tli $t0, 0");
                        code.add("\tli $t1, 1");
                        code.add("\tbc1t, jps" + jps);
                        code.add("\tsb $t1, " + b.Adr);
                        code.add("\tb, jps" + (jps + 1));
                        code.add("jps" + jps++ + ":");
                        code.add("\tsb $t0, " + b.Adr);
                        code.add("jps" + jps++ + ":");
                    }

                    semanticStack.push(b);
                    discriptors.get(scope).add(b);
                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                }
//                if(check if they can be compared){
                //make new discriptor with type bool and calculate (a != b) and put it in new discriptor.adr
                // the push it to discriptors
//                discriptors.get(scope).add(temp)
//                }else{
                //err
//                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("isLess"):
                //try {
                b = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                a = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                if (a != null && b != null && a.data_type.equals(b.data_type) && !a.data_type.equals("string") && !a.data_type.equals("bool")) {
                    temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                            , inMethod.name, "bool"
                            , "Adr" + adrs++, null,
                            -1, 1,
                            scope);
                    if (a.data_type.equals("int")) {
                        code.add("\tla $t0, " + a.Adr);
                        code.add("\tla $t1, " + b.Adr);
                        code.add("\tlw $t2, 0($t0)");
                        code.add("\tlw $t3, 0($t1)");
                        code.add("\tslt $t4, $t2, $t3");
                        code.add("\tsb $t4, " + temp.Adr);
                    } else {
                        code.add("\tl.s $f0, " + a.Adr);
                        code.add("\tl.s $f1, " + b.Adr);
                        code.add("\tc.lt.s $f0, $f1");
                        code.add("\tli $t0, 0");
                        code.add("\tli $t1, 1");
                        code.add("\tbc1f, jps" + jps);
                        code.add("\tsb $t1, " + temp.Adr);
                        code.add("\tb, jps" + (jps + 1));
                        code.add("jps" + jps++ + ":");
                        code.add("\tsb $t0, " + temp.Adr);
                        code.add("jps" + jps++ + ":");
                    }
                    semanticStack.push(temp);
                    discriptors.get(scope).add(temp);
                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                }
//                if(check if they can be compared){
                //make new discriptor with type bool and calculate (a < b) and put it in new discriptor.adr
                // the push it to discriptors
//                }else{
                //err
//                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("isLessNeg"):
                //try {
                b = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                //check if b can be gharine :)
                //make a new discriptor with value = -(value in b.adr)
                //b = new discriptor above
                if (b != null && (b.data_type.equals("int") || b.data_type.equals("real"))) {
                    temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                            , inMethod.name, b.data_type.equals("int") ? "int" : "real"
                            , "Adr" + adrs++, null,
                            -1, 1,
                            scope);
                    if (a.data_type.equals("int")) {
                        code.add("\tla $t0, " + b.Adr);
                        code.add("\tlw $t1, 0($t0)");
                        code.add("\tneg $t2, $t1");
                        code.add("\tsw $t2, " + temp.Adr);
                    } else {
                        code.add("\tla $t0, " + b.Adr);
                        code.add("\tl.s $f0, 0($t0)");
                        code.add("\tneg.s $f1, $f0");
                        code.add("\ts.s $f1, " + temp.Adr);
                    }
                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                }
                a = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();

                if (a != null && b != null && temp != null && (a.data_type.equals("int") || a.data_type.equals("real"))) {
                    b = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                            , inMethod.name, "bool"
                            , "Adr" + adrs++, null,
                            -1, 1,
                            scope);
                    if (a.data_type.equals("int")) {
                        code.add("\tla $t0, " + a.Adr);
                        code.add("\tlw $t1, 0($t0)");
                        code.add("\tslt $t3, $t1, $t2");
                        code.add("\tsb $t3, " + b.Adr);
                    } else {
                        code.add("\tl.s $f0, " + a.Adr);
                        code.add("\tc.lt.s $f0, $f1");
                        code.add("\tli $t0, 0");
                        code.add("\tli $t1, 1");
                        code.add("\tbc1f, jps" + jps);
                        code.add("\tsb $t1, " + b.Adr);
                        code.add("\tb, jps" + (jps + 1));
                        code.add("jps" + jps++ + ":");
                        code.add("\tsb $t0, " + b.Adr);
                        code.add("jps" + jps++ + ":");
                    }
                    semanticStack.push(b);
                    discriptors.get(scope).add(b);
                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                }
//                if(check if they can be compared){
                //make new discriptor with type bool and calculate (a < b) and put it in new discriptor.adr
                // the push it to discriptors
//                }else{
                //err
//                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("isMore"):
                //try {
                b = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                a = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                if (a != null && b != null && a.data_type.equals(b.data_type) && !a.data_type.equals("string") && !a.data_type.equals("bool")) {
                    temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                            , inMethod.name, "bool"
                            , "Adr" + adrs++, null,
                            -1, 1,
                            scope);
                    if (a.data_type.equals("int")) {
                        code.add("\tla $t0, " + a.Adr);
                        code.add("\tla $t1, " + b.Adr);
                        code.add("\tlw $t2, 0($t0)");
                        code.add("\tlw $t3, 0($t1)");
                        code.add("\tsgt $t4, $t2, $t3");
                        code.add("\tsb $t4, " + temp.Adr);
                    } else {
                        code.add("\tl.s $f0, " + a.Adr);
                        code.add("\tl.s $f1, " + b.Adr);
                        code.add("\tc.le.s $f0, $f1");
                        code.add("\tli $t0, 0");
                        code.add("\tli $t1, 1");
                        code.add("\tbc1t, jps" + jps);
                        code.add("\tsb $t1, " + temp.Adr);
                        code.add("\tb, jps" + (jps + 1));
                        code.add("jps" + jps++ + ":");
                        code.add("\tsb $t0, " + temp.Adr);
                        code.add("jps" + jps++ + ":");
                    }
                    semanticStack.push(temp);
                    discriptors.get(scope).add(temp);
                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                }
//                if(check if they can be compared){
                //make new discriptor with type bool and calculate (a > b) and put it in new discriptor.adr
                // the push it to discriptors
//                }else{
                //err
//                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("isMoreNeg"):
                //try {
                b = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                //check if b can be gharine :)
                //make a new discriptor with value = -(value in b.adr)
                //b = new discriptor above
                if (b != null && (b.data_type.equals("int") || b.data_type.equals("real"))) {
                    temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                            , inMethod.name, b.data_type.equals("int") ? "int" : "real"
                            , "Adr" + adrs++, null,
                            -1, 1,
                            scope);
                    if (a.data_type.equals("int")) {
                        code.add("\tla $t0, " + b.Adr);
                        code.add("\tlw $t1, 0($t0)");
                        code.add("\tneg $t2, $t1");
                        code.add("\tsw $t2, " + temp.Adr);
                    } else {
                        code.add("\tla $t0, " + b.Adr);
                        code.add("\tl.s $f0, 0($t0)");
                        code.add("\tneg.s $f1, $f0");
                        code.add("\ts.s $f1, " + temp.Adr);
                    }
                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                }
                a = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();

                if (a != null && b != null && temp != null && (a.data_type.equals("int") || a.data_type.equals("real"))) {
                    b = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                            , inMethod.name, "bool"
                            , "Adr" + adrs++, null,
                            -1, 1,
                            scope);
                    if (a.data_type.equals("int")) {
                        code.add("\tla $t0, " + a.Adr);
                        code.add("\tlw $t1, 0($t0)");
                        code.add("\tsgt $t3, $t1, $t2");
                        code.add("\tsb $t3, " + b.Adr);
                    } else {
                        code.add("\tl.s $f0, " + a.Adr);

                        code.add("\tc.le.s $f0, $f1");
                        code.add("\tli $t0, 0");
                        code.add("\tli $t1, 1");
                        code.add("\tbc1t, jps" + jps);
                        code.add("\tsb $t1, " + b.Adr);
                        code.add("\tb, jps" + (jps + 1));
                        code.add("jps" + jps++ + ":");
                        code.add("\tsb $t0, " + b.Adr);
                        code.add("jps" + jps++ + ":");
                    }
                    semanticStack.push(b);
                    discriptors.get(scope).add(b);
                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                }
//                if(check if they can be compared){
                //make new discriptor with type bool and calculate (a > b) and put it in new discriptor.adr
                // the push it to discriptors
//                }else{
                //err
//                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("isLessEqualNeg"):
                //try {
                b = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                //check if b can be gharine :)
                //make a new discriptor with value = -(value in b.adr)
                //b = new discriptor above
                if (b != null && (b.data_type.equals("int") || b.data_type.equals("real"))) {
                    temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                            , inMethod.name, b.data_type.equals("int") ? "int" : "real"
                            , "Adr" + adrs++, null,
                            -1, 1,
                            scope);
                    if (a.data_type.equals("int")) {
                        code.add("\tla $t0, " + b.Adr);
                        code.add("\tlw $t1, 0($t0)");
                        code.add("\tneg $t2, $t1");
                        code.add("\tsw $t2, " + temp.Adr);
                    } else {
                        code.add("\tla $t0, " + b.Adr);
                        code.add("\tl.s $f0, 0($t0)");
                        code.add("\tneg.s $f1, $f0");
                        code.add("\ts.s $f1, " + temp.Adr);
                    }
                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                }
                a = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();

                if (a != null && b != null && temp != null && (a.data_type.equals("int") || a.data_type.equals("real"))) {
                    b = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                            , inMethod.name, "bool"
                            , "Adr" + adrs++, null,
                            -1, 1,
                            scope);
                    if (a.data_type.equals("int")) {
                        code.add("\tla $t0, " + a.Adr);
                        code.add("\tlw $t1, 0($t0)");
                        code.add("\tsle $t3, $t1, $t2");
                        code.add("\tsb $t3, " + b.Adr);
                    } else {
                        code.add("\tl.s $f0, " + a.Adr);
                        code.add("\tc.le.s $f0, $f1");
                        code.add("\tli $t0, 0");
                        code.add("\tli $t1, 1");
                        code.add("\tbc1f, jps" + jps);
                        code.add("\tsb $t1, " + b.Adr);
                        code.add("\tb, jps" + (jps + 1));
                        code.add("jps" + jps++ + ":");
                        code.add("\tsb $t0, " + b.Adr);
                        code.add("jps" + jps++ + ":");
                    }
                    semanticStack.push(b);
                    discriptors.get(scope).add(b);
                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                }
//                if(check if they can be compared){
                //make new discriptor with type bool and calculate (a <= b) and put it in new discriptor.adr
                // the push it to discriptors
//                }else{
                //err
//                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("isLessEqual"):
                //try {
                b = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                a = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                if (a != null && b != null && a.data_type.equals(b.data_type) && !a.data_type.equals("string") && !a.data_type.equals("bool")) {
                    temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                            , inMethod.name, "bool"
                            , "Adr" + adrs++, null,
                            -1, 1,
                            scope);
                    if (a.data_type.equals("int")) {
                        code.add("\tla $t0, " + a.Adr);
                        code.add("\tla $t1, " + b.Adr);
                        code.add("\tlw $t2, 0($t0)");
                        code.add("\tlw $t3, 0($t1)");
                        code.add("\tsle $t4, $t2, $t3");
                        code.add("\tsw $t4, " + temp.Adr);
                    } else {
                        code.add("\tl.s $f0, " + a.Adr);
                        code.add("\tl.s $f1, " + b.Adr);
                        code.add("\tc.lt.s $f0, $f1");
                        code.add("\tli $t0, 0");
                        code.add("\tli $t1, 1");
                        code.add("\tbc1f, jps" + jps);
                        code.add("\tsb $t1, " + temp.Adr);
                        code.add("\tb, jps" + (jps + 1));
                        code.add("jps" + jps++ + ":");
                        code.add("\tsb $t0, " + temp.Adr);
                        code.add("jps" + jps++ + ":");
                    }
                    semanticStack.push(temp);
                    discriptors.get(scope).add(temp);
                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                }
//                if(check if they can be compared){
                //make new discriptor with type bool and calculate (a <= b) and put it in new discriptor.adr
                // the push it to discriptors
//                }else{
                //err
//                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("isMoreEqual"):
                //try {
                b = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                a = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                if (a != null && b != null && a.data_type.equals(b.data_type) && !a.data_type.equals("string") && !a.data_type.equals("bool")) {
                    temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                            , inMethod.name, "bool"
                            , "Adr" + adrs++, null,
                            -1, 1,
                            scope);
                    if (a.data_type.equals("int")) {
                        code.add("\tla $t0, " + a.Adr);
                        code.add("\tla $t1, " + b.Adr);
                        code.add("\tlw $t2, 0($t0)");
                        code.add("\tlw $t3, 0($t1)");
                        code.add("\tsge $t4, $t2, $t3");
                        code.add("\tsb $t4, " + temp.Adr);
                    } else {
                        code.add("\tl.s $f0, " + a.Adr);
                        code.add("\tl.s $f1, " + b.Adr);
                        code.add("\tc.lt.s $f0, $f1");
                        code.add("\tli $t0, 0");
                        code.add("\tli $t1, 1");
                        code.add("\tbc1t, jps" + jps);
                        code.add("\tsb $t1, " + temp.Adr);
                        code.add("\tb, jps" + (jps + 1));
                        code.add("jps" + jps++ + ":");
                        code.add("\tsb $t0, " + temp.Adr);
                        code.add("jps" + jps++ + ":");
                    }
                    semanticStack.push(temp);
                    discriptors.get(scope).add(temp);
                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                }
//                if(check if they can be compared){
                //make new discriptor with type bool and calculate (a >= b) and put it in new discriptor.adr
                // the push it to discriptors
//                }else{
                //err
//                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("isMoreEqualNeg"):
                //try {
                b = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                //check if b can be gharine :)
                //make a new discriptor with value = -(value in b.adr)
                //b = new discriptor above
                if (b != null && (b.data_type.equals("int") || b.data_type.equals("real"))) {
                    temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                            , inMethod.name, b.data_type.equals("int") ? "int" : "real"
                            , "Adr" + adrs++, null,
                            -1, 1,
                            scope);
                    if (a.data_type.equals("int")) {
                        code.add("\tla $t0, " + b.Adr);
                        code.add("\tlw $t1, 0($t0)");
                        code.add("\tneg $t2, $t1");
                        code.add("\tsw $t2, " + temp.Adr);
                    } else {
                        code.add("\tla $t0, " + b.Adr);
                        code.add("\tl.s $f0, 0($t0)");
                        code.add("\tneg.s $f1, $f0");
                        code.add("\ts.s $f1, " + temp.Adr);
                    }
                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                }
                a = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();

                if (a != null && b != null && temp != null && (a.data_type.equals("int") || a.data_type.equals("real"))) {
                    b = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                            , inMethod.name, "bool"
                            , "Adr" + adrs++, null,
                            -1, 1,
                            scope);
                    if (a.data_type.equals("int")) {
                        code.add("\tla $t0, " + a.Adr);
                        code.add("\tlw $t1, 0($t0)");
                        code.add("\tsge $t3, $t1, $t2");
                        code.add("\tsb $t3, " + b.Adr);
                    } else {
                        code.add("\tl.s $f0, " + a.Adr);
                        code.add("\tc.lt.s $f0, $f1");
                        code.add("\tli $t0, 0");
                        code.add("\tli $t1, 1");
                        code.add("\tbc1t, jps" + jps);
                        code.add("\tsb $t1, " + b.Adr);
                        code.add("\tb, jps" + (jps + 1));
                        code.add("jps" + jps++ + ":");
                        code.add("\tsb $t0, " + b.Adr);
                        code.add("jps" + jps++ + ":");
                    }
                    semanticStack.push(b);
                    discriptors.get(scope).add(b);
                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                }
//                if(check if they can be compared){
                //make new discriptor with type bool and calculate (a >= b) and put it in new discriptor.adr
                // the push it to discriptors
//                }else{
                //err
//                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("Add"):
                //try {
                b = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                a = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                if (a != null && b != null && a.data_type.equals(b.data_type) && !a.data_type.equals("string") && !a.data_type.equals("bool")) {
                    temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                            , inMethod.name, a.data_type
                            , "Adr" + adrs++, null,
                            -1, 1,
                            scope);
                    if (a.data_type.equals("int")) {
                        code.add("\tla $t0, " + a.Adr);
                        code.add("\tla $t1, " + b.Adr);
                        code.add("\tlw $t2, 0($t0)");
                        code.add("\tlw $t3, 0($t1)");
                        code.add("\tadd $t4, $t2, $t3");
                        code.add("\tsw $t4, " + temp.Adr);
                    } else {
                        code.add("\tl.s $f0, " + a.Adr);
                        code.add("\tl.s $f1, " + b.Adr);
                        code.add("\tadd.s $f2 ,$f0 ,$f1");
                        code.add("\ts.s $f2, " + temp.Adr);

                    }
                    semanticStack.push(temp);
                    discriptors.get(scope).add(temp);
                    //make new discriptor with type a.data_type and calculate (a + b) and put it in new discriptor.adr
                    // the push it to discriptors
                } else {
                    System.out.println(a);
                    System.out.println(b);
                    throw new Exception("in the " + sem + " exception occurred");

                    //err
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("Minus"):
                //try {
                b = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                a = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                if (a != null && b != null && a.data_type.equals(b.data_type) && !a.data_type.equals("string") && !a.data_type.equals("bool")) {
                    temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                            , inMethod.name, a.data_type
                            , "Adr" + adrs++, null,
                            -1, 1,
                            scope);
                    if (a.data_type.equals("int")) {
                        code.add("\tla $t0, " + a.Adr);
                        code.add("\tla $t1, " + b.Adr);
                        code.add("\tlw $t2, 0($t0)");
                        code.add("\tlw $t3, 0($t1)");
                        code.add("\tsub $t4, $t2, $t3");
                        code.add("\tsw $t4, " + temp.Adr);
                    } else {
                        code.add("\tl.s $f0, " + a.Adr);
                        code.add("\tl.s $f1, " + b.Adr);
                        code.add("\tsub.s $f2 ,$f0 ,$f1");
                        code.add("\ts.s $f2, " + temp.Adr);
                    }
                    semanticStack.push(temp);
                    discriptors.get(scope).add(temp);
                    //make new discriptor with type a.data_type and calculate (a - b) and put it in new discriptor.adr
                    // the push it to discriptors
                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                    //err
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("Mult"):
                //try {
                b = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                a = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                if (a != null && b != null && a.data_type.equals(b.data_type) && !a.data_type.equals("string") && !a.data_type.equals("bool")) {
                    temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                            , inMethod.name, a.data_type
                            , "Adr" + adrs++, null,
                            -1, 1,
                            scope);
                    if (a.data_type.equals("int")) {
                        code.add("\tla $t0, " + a.Adr);
                        code.add("\tla $t1, " + b.Adr);
                        code.add("\tlw $t2, 0($t0)");
                        code.add("\tlw $t3, 0($t1)");
                        code.add("\tmul $t4, $t2, $t3");
                        code.add("\tsw $t4, " + temp.Adr);
                    } else {
                        code.add("\tl.s $f0, " + a.Adr);
                        code.add("\tl.s $f1, " + b.Adr);
                        code.add("\tmul.s $f2 ,$f0 ,$f1");
                        code.add("\ts.s $f2, " + temp.Adr);
                    }
                    semanticStack.push(temp);
                    discriptors.get(scope).add(temp);
                    //make new discriptor with type a.data_type and calculate (a * b) and put it in new discriptor.adr
                    // the push it to discriptors
                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                    //err
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("Div"):
                //try {
                b = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                a = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                if (a != null && b != null && a.data_type.equals(b.data_type) && !a.data_type.equals("string") && !a.data_type.equals("bool")) {
                    temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                            , inMethod.name, a.data_type
                            , "Adr" + adrs++, null,
                            -1, 1,
                            scope);
                    if (a.data_type.equals("int")) {
                        code.add("\tla $t0, " + a.Adr);
                        code.add("\tla $t1, " + b.Adr);
                        code.add("\tlw $t2, 0($t0)");
                        code.add("\tlw $t3, 0($t1)");
                        code.add("\tbeq $t3 , 0 , ErDZ"); //add to spim
                        code.add("\tdiv $t4, $t2, $t3");
                        code.add("\tsw $t4, " + temp.Adr);
                    } else {
                        code.add("\tl.s $f0, " + a.Adr);
                        code.add("\tl.s $f1, " + b.Adr);
                        code.add("\tli.s $f2, 0.0");
                        code.add("\tc.eq.s $f2, $f1");
                        code.add("\tbc1t , ErDZ");
                        code.add("\tdiv.s $f2 ,$f0 ,$f1");
                        code.add("\ts.s $f2, " + temp.Adr);
                        ;
                    }
                    semanticStack.push(temp);
                    discriptors.get(scope).add(temp);
                    //make new discriptor with type a.data_type and calculate (a / b) and put it in new discriptor.adr
                    // the push it to discriptors
                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                    //err
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("Remainder"):
                //try {
                b = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                a = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                if (a != null && b != null && a.data_type.equals(b.data_type) && a.data_type.equals("int")) {
                    temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                            , inMethod.name, a.data_type
                            , "Adr" + adrs++, null,
                            -1, 1,
                            scope);
                    code.add("\tla $t0, " + a.Adr);
                    code.add("\tla $t1, " + b.Adr);
                    code.add("\tlw $t2, 0($t0)");
                    code.add("\tlw $t3, 0($t1)");
                    code.add("\tbeq $t3, 0, ErDZ"); //add to spim
                    code.add("\trem $t4, $t2, $t3");
                    code.add("\tsw $t4, " + temp.Adr);
                    semanticStack.push(temp);
                    discriptors.get(scope).add(temp);
                    //make new discriptor with type a.data_type and calculate (a % b) and put it in new discriptor.adr
                    // the push it to discriptors
                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                    //err
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("cast"):
                //try {
                b = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                returnType = (String) semanticStack.peek();
                semanticStack.pop();
                if (b != null && returnType != null && returnType.equals("int") && ((b.data_type.equals("real") || b.data_type.equals("int")))) {
                    temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                            , inMethod.name, "int"
                            , "Adr" + adrs++, null,
                            -1, 1,
                            scope);
                    if (b.data_type.equals("real")) {
                        code.add("\tla $t0, " + b.Adr);
                        code.add("\tl.s $f1, 0($t0)");
                        code.add("\tcvt.w.s $f2, $f1");
                        code.add("\ts.s $f2, " + temp.Adr);
                    } else {
                        code.add("\tlw $t0, " + b.Adr);
                        code.add("\tsw $t0, " + temp.Adr);
                    }
                    semanticStack.push(temp);
                    discriptors.get(scope).add(temp);
                } else if (b != null && returnType != null && returnType.equals("real") && ((b.data_type.equals("real") || b.data_type.equals("int")))) {
                    temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                            , inMethod.name, "real"
                            , "Adr" + adrs++, null,
                            -1, 1,
                            scope);
                    if (b.data_type.equals("real")) {
                        code.add("\tl.s $f1, " + b.Adr);
                        code.add("\ts.s $f1, " + temp.Adr);
                    } else {
                        code.add("\tla $t0, " + b.Adr);
                        code.add("\tl.s $f1, 0($t0)");
                        code.add("\tcvt.s.w $f2, $f1");
                        code.add("\ts.s $f2, " + temp.Adr);
                    }
                    semanticStack.push(temp);
                    discriptors.get(scope).add(temp);
                } else {
                    throw new Exception("in the " + sem + " exception occurred");
                }
                //check if value in b.adr can be cast to type a(a is a string and b is discriptoe)
                //make new discriptor with type a and cast b.adr to type a  and put it in new discriptor.adr
                // the push it to discriptors

                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("readInputStr"):
                //try {
                temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                        , inMethod.name, "string"
                        , "Adr" + adrs++, null,
                        -1, 16,
                        scope);
                code.add("\tli $v0 , 8");
                code.add("\tlw $a0, " + temp.Adr);
                code.add("\tli $a1 , 16");
                code.add("\tsyscall");
                //make new discriptor with type string
                semanticStack.push(temp);
                discriptors.get(scope).add(temp);

                //getting input with mips and then put it in temp.adr
                //and push the new discriptor to semanticstack
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("readInputInt"):
                //try {
                temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                        , inMethod.name, "int"
                        , "Adr" + adrs++, null,
                        -1, 1,
                        scope);
                code.add("\tli $v0 , 5");
                code.add("\tsyscall");
                code.add("\tla $t0, " + temp.Adr);
                code.add("\tsw $v0 , 0($t0)");
                //make new discriptor with type int
                semanticStack.push(temp);
                discriptors.get(scope).add(temp);
                //getting input with mips and then put it in temp.adr
                //and push the new discriptor to semanticstack

                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("makeArrayOfSize"):
                //try {
                a = getDiscriptor((Discriptor) semanticStack.peek(), scope);
                semanticStack.pop();
                returnType = (String) semanticStack.peek();
                semanticStack.pop();
                if (a.data_type.equals("int")) {
                    temp = new Discriptor("Adr" + adrs, Type.ARRVAR, inClass.name
                            , inMethod.name, returnType
                            , "Adr" + adrs++, null,
                            10, 1,
                            scope);
                } else {
                    throw new Exception("in the " + sem + " exception occurred");
                }
                //check if a is int
                semanticStack.push(temp);
                discriptors.get(scope).add(temp);
                //make new discriptor with type Vararr and upper bound of a.adr
                //and push the new discriptor to semanticstack
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
//            case ("lenId")://try{
//                }catch(Exception e){//System.out.println(sem);}break;
            case ("pushId"):
//                try {
                temp = new Discriptor(scanner.token.token, Type.VAR, inClass.name
                        , inMethod.name, null
                        , "Adr", null,
                        -1, 1,
                        scope);


                a = getDiscriptor(temp, scope);
                if (a != null) {
                    semanticStack.push(a);
                } else {
                    temp = new Discriptor(scanner.token.token, Type.VAR, inClass.name
                            , "", null
                            , "Adr", null,
                            -1, 1,
                            scope);
                    a = getDiscriptor(temp, scope);
                    if (a != null) {
                        semanticStack.push(a);
                    } else {
                        throw new Exception("variable " + scanner.token.token +
                                " is not declared in this scope " + inMethod.name + " " + inClass.name + " !");
                    }
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("makeOneLessPush"):
                //try {
                a = (Discriptor) semanticStack.peek();
                if (a.data_type.equals("int")) {
                    code.add("\tlw $t0, " + a.Adr);
                    code.add("\tsubi $t0, $t0, 1");
                    code.add("\tsw $t0, " + a.Adr);
                } else {
                    throw new Exception("in the " + sem + " exception occurred");
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("makeOneMorePush"):
                //try {
                a = (Discriptor) semanticStack.peek();
                if (a.data_type.equals("int")) {
                    code.add("\tlw $t0, " + a.Adr);
                    code.add("\taddi $t0, $t0, 1");
                    code.add("\tsw $t0, " + a.Adr);
                } else {
                    throw new Exception("in the " + sem + " exception occurred");
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("pushCopyMakeOneLess"):
                //try {
                a = (Discriptor) semanticStack.peek();
                semanticStack.pop();
                temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                        , inMethod.name, "int"
                        , "Adr" + adrs++, null,
                        -1, 1,
                        scope);
                semanticStack.push(temp);
                if (a.data_type.equals("int")) {
                    code.add("\tlw $t0, " + a.Adr);
                    code.add("\tsw $t0, " + temp.Adr);
                    code.add("\tsubi $t0, $t0, 1");
                    code.add("\tsw $t0, " + a.Adr);

                } else {
                    throw new Exception("in the " + sem + " exception occurred");
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("pushCopyMakeOneMore"):
                //try {
                a = (Discriptor) semanticStack.peek();
                semanticStack.pop();
                temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                        , inMethod.name, "int"
                        , "Adr" + adrs++, null,
                        -1, 1,
                        scope);
                semanticStack.push(temp);
                if (a.data_type.equals("int")) {
                    code.add("\tlw $t0, " + a.Adr);
                    code.add("\tsw $t0, " + temp.Adr);
                    code.add("\taddi $t0, $t0, 1");
                    code.add("\tsw $t0, " + a.Adr);

                } else {
                    throw new Exception("in the " + sem + " exception occurred");
                }

                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("findField"):
                //try {
                a = (Discriptor) semanticStack.peek();
                semanticStack.pop();
                temp = new Discriptor(a.data_type, Type.CLASS, null
                        , null, null
                        , "Adr", null,
                        -1, 1,
                        scope);
                b = getDiscriptor(temp, 0);
                if (b != null) {
                    for (int i = 0; i < b.discrpts.size(); i++) {
                        if (b.discrpts.get(i).name.equals(scanner.token.token)) {
                            temp = b.discrpts.get(i);
                            break;
                        }
                    }
                    semanticStack.push(temp);
                } else {
                    throw new Exception("in the " + sem + " exception occurred");

                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("bitOr"):
                //try {
                b = (Discriptor) semanticStack.peek();
                semanticStack.pop();
                a = (Discriptor) semanticStack.peek();
                semanticStack.pop();
                temp = new Discriptor();
                if (a != null && b != null && a.data_type.equals(b.data_type) && !a.data_type.equals("string") && !a.data_type.equals("real")) {
                    if (!a.data_type.equals("bool")) {
                        temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                                , inMethod.name, a.data_type.equals("int") ? "int" : "real"
                                , "Adr" + adrs++, null,
                                -1, 1,
                                scope);
                        code.add("\tlw $t0," + a.Adr);
                        code.add("\tlw $t1," + b.Adr);
                        code.add("\tor $t2, $t0, $t1");
                        code.add("\tsw $t2," + temp.Adr);
                    } else {
                        temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                                , inMethod.name, "bool"
                                , "Adr" + adrs++, null,
                                -1, 1,
                                scope);
                        code.add("\tlb $t0," + a.Adr);
                        code.add("\tlb $t1," + b.Adr);
                        code.add("\tor $t2, $t0, $t1");
                        code.add("\tsb $t2," + temp.Adr);
                    }
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("bitAnd"):
                //try {
                b = (Discriptor) semanticStack.peek();
                semanticStack.pop();
                a = (Discriptor) semanticStack.peek();
                semanticStack.pop();
                temp = new Discriptor();
                if (a != null && b != null && a.data_type.equals(b.data_type) && !a.data_type.equals("string") && !a.data_type.equals("real")) {
                    if (!a.data_type.equals("bool")) {
                        temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                                , inMethod.name, a.data_type.equals("int") ? "int" : "real"
                                , "Adr" + adrs++, null,
                                -1, 1,
                                scope);
                        code.add("\tlw $t0," + a.Adr);
                        code.add("\tlw $t1," + b.Adr);
                        code.add("\tand $t2, $t0, $t1");
                        code.add("\tsw $t2," + temp.Adr);
                    } else {
                        temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                                , inMethod.name, "bool"
                                , "Adr" + adrs++, null,
                                -1, 1,
                                scope);
                        code.add("\tlb $t0," + a.Adr);
                        code.add("\tlb $t1," + b.Adr);
                        code.add("\tand $t2, $t0, $t1");
                        code.add("\tsb $t2," + temp.Adr);
                    }
                } else {
                    throw new Exception("in the " + sem + " exception occurred");
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("bitXor"):
                //try {
                b = (Discriptor) semanticStack.peek();
                semanticStack.pop();
                a = (Discriptor) semanticStack.peek();
                semanticStack.pop();
                temp = new Discriptor();
                if (a != null && b != null && a.data_type.equals(b.data_type) && !a.data_type.equals("string") && !a.data_type.equals("real")) {
                    if (!a.data_type.equals("bool")) {
                        temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                                , inMethod.name, a.data_type.equals("int") ? "int" : "real"
                                , "Adr" + adrs++, null,
                                -1, 1,
                                scope);
                        code.add("\tlw $t0," + a.Adr);
                        code.add("\tlw $t1," + b.Adr);
                        code.add("\txor $t2, $t0, $t1");
                        code.add("\tsw $t2," + temp.Adr);
                    } else {
                        temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                                , inMethod.name, "bool"
                                , "Adr" + adrs++, null,
                                -1, 1,
                                scope);
                        code.add("\tlb $t0," + a.Adr);
                        code.add("\tlb $t1," + b.Adr);
                        code.add("\txor $t2, $t0, $t1");
                        code.add("\tsb $t2 , " + temp.Adr);
                    }
                } else {
                    throw new Exception("in the " + sem + " exception occurred");
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("pushTrue"):
                //try {
                temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                        , inMethod.name, "bool"
                        , "Adr" + adrs++, null,
                        -1, 1,
                        scope);
                code.add("\tli $t1, 1");
                code.add("\tsb $t1, " + temp.Adr);
                discriptors.get(scope).add(temp);
                semanticStack.push(temp);
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
//            case ("pushSci")://try{
//                }catch(Exception e){//System.out.println(sem);}break;
            case ("pushReal"):
                //try {
                temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                        , inMethod.name, "real"
                        , "Adr" + adrs++, null,
                        -1, 1,
                        scope);
                code.add("\tli.s $f1, " + scanner.token.token);
                code.add("\ts.s $f1, " + temp.Adr);
                discriptors.get(scope).add(temp);
                semanticStack.push(temp);
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
//            case ("pushHex")://try{
//                }catch(Exception e){//System.out.println(sem);}break;
            case ("pushDecimal"):
                //try {
                temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                        , inMethod.name, "int"
                        , "Adr" + adrs++, null,
                        -1, 1,
                        scope);
                code.add("\tli $t1, " + scanner.token.token);
                code.add("\tsw $t1, " + temp.Adr);
                discriptors.get(scope).add(temp);
                semanticStack.push(temp);
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("PushString"):
                //try {
                temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                        , inMethod.name, "string"
                        , "Adr" + adrs++, null,
                        -1, 1 + scanner.string.length(),
                        scope);

                temp.args = new ArrayList<>();
                temp.args.add("\"" + scanner.string);
                discriptors.get(scope).add(temp);
                semanticStack.push(temp);
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("pushFalse"):
                //try {
                temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                        , inMethod.name, "bool"
                        , "Adr" + adrs++, null,
                        -1, 1,
                        scope);
                code.add("\tli $t1, 0");
                code.add("\tsb $t1, " + temp.Adr);
                discriptors.get(scope).add(temp);
                semanticStack.push(temp);
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("checkArgsandcallpop"):
                //try {
                ArrayList<Discriptor> discriptorsInCAAC = new ArrayList<>();
                for (int i = args - 1; i > -1; i--) {
                    temp = (Discriptor) semanticStack.peek();
                    semanticStack.pop();
                    discriptorsInCAAC.add(temp);
                }
                a = (Discriptor) semanticStack.peek();
                semanticStack.pop();
                if (a.args.size() == args && args < 4 && a.returnType.equals("void")) {
                    for (int i = discriptorsInCAAC.size() - 1; i > -1; i--) {
                        b = (Discriptor) a.args.get(args - i - 1);
                        if (discriptorsInCAAC.get(i).data_type.equals(b.data_type)) {
                            code.add("\tlw  $a" + (args - i - 1) + " ," + discriptorsInCAAC.get(i).Adr);
                            continue;
                        }
                        throw new Exception("in the " + sem + " exception occurred");
                    }
                    code.add("\taddi $sp, $sp, -24");
                    code.add("\tsw $t0, 20($sp)");
                    code.add("\tsw $t1, 16($sp)");
                    code.add("\tsw $t2, 12($sp)");
                    code.add("\tsw $t3, 8($sp)");
                    code.add("\tsw $t4, 4($sp)");
                    code.add("\tsw $ra, 0($sp)");
                    code.add("\tjal " + a.name + a.Class + a.scope);
                    code.add("\tlw $t0, 20($sp)");
                    code.add("\tlw $t1, 16($sp)");
                    code.add("\tlw $t2, 12($sp)");
                    code.add("\tlw $t3, 8($sp)");
                    code.add("\tlw $t4, 4($sp)");
                    code.add("\tlw $ra, 0($sp)");
                    code.add("\taddi $sp, $sp, 24");
                    temp = new Discriptor("Adr" + adrs, a.returnType.contains("$Arr") ? Type.ARRVAR : Type.VAR, inClass.name
                            , inMethod.name, a.returnType.contains("$Arr") ? a.returnType.replace("$Arr", "") : a.returnType
                            , "Adr" + adrs++, null,
                            -1, 1,
                            scope);

                    discriptors.get(scope).add(temp);
                } else {
                    throw new Exception("in the " + sem + " exception occurred");
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("checkArgsandcall"):
                //try {
                discriptorsInCAAC = new ArrayList<>();
                for (int i = args - 1; i > -1; i--) {
                    temp = (Discriptor) semanticStack.peek();
                    semanticStack.pop();
                    discriptorsInCAAC.add(temp);
                }
                a = (Discriptor) semanticStack.peek();
                semanticStack.pop();
                if (a.args.size() == args && args < 4) {
                    for (int i = discriptorsInCAAC.size() - 1; i > -1; i--) {
                        b = (Discriptor) a.args.get(args - i - 1);
                        if (discriptorsInCAAC.get(i).data_type.equals(b.data_type)) {
                            code.add("\tlw $a" + (args - i - 1) + " ," + discriptorsInCAAC.get(i).Adr);
                            continue;
                        }
                        throw new Exception("in the " + sem + " exception occurred");
                    }
                    code.add("\taddi $sp, $sp, -24");
                    code.add("\tsw $t0, 20($sp)");
                    code.add("\tsw $t1, 16($sp)");
                    code.add("\tsw $t2, 12($sp)");
                    code.add("\tsw $t3, 8($sp)");
                    code.add("\tsw $t4, 4($sp)");
                    code.add("\tsw $ra, 0($sp)");
                    code.add("\tjal " + a.name + a.Class + a.scope);
                    code.add("\tlw $t0, 20($sp)");
                    code.add("\tlw $t1, 16($sp)");
                    code.add("\tlw $t2, 12($sp)");
                    code.add("\tlw $t3, 8($sp)");
                    code.add("\tlw $t4, 4($sp)");
                    code.add("\tlw $ra, 0($sp)");
                    code.add("\taddi $sp, $sp, 24");
                    temp = new Discriptor("Adr" + adrs, a.returnType.contains("$Arr") ? Type.ARRVAR : Type.VAR, inClass.name
                            , inMethod.name, a.returnType.contains("$Arr") ? a.returnType.replace("$Arr", "") : a.returnType
                            , "Adr" + adrs++, null,
                            -1, 1,
                            scope);
                    code.add("\tlw $t0, 0($v0)");
                    code.add("\tsw $t0, " + temp.Adr);
                    discriptors.get(scope).add(temp);
                    semanticStack.push(temp);
                } else {

                    throw new Exception("in the " + sem + " exception occurred");
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("initArg"):
                //try {
                args = 0;
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("incArg"):
                //try {
                args++;
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("checkInBoundPush"):
                //try {
                a = (Discriptor) semanticStack.peek();
                if (a.data_type.equals("int")) {
                    semanticStack.pop();
                    b = (Discriptor) semanticStack.peek();
                    semanticStack.pop();
                    if (b.type == Type.ARRVAR) {
                        temp = new Discriptor("Adr" + adrs, Type.VAR, inClass.name
                                , inMethod.name, b.data_type
                                , "Adr" + adrs, null,
                                -1, 1,
                                scope);
                        code.add("\tlw $t0, " + a.Adr);
                        code.add("\tblt $t0, 0, errorArrBound");
                        code.add("\tbge $t0, " + b.upper_bound + ", errorArrBound");
                        code.add("\tlw $t0, " + b.Adr);
                        code.add("\tadd $t3, $t0, " + a.Adr);
                        code.add("\tlw $t1, $t3");
                        code.add("\tsw $t1, " + temp.Adr);
                        adrs++;
                        semanticStack.push(temp);
                        discriptors.get(scope).add(temp);
                    } else {
                        throw new Exception("in the " + sem + " exception occurred");
                    }
                } else {
                    throw new Exception("in the " + sem + " exception occurred");
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("print"):
                //try {
                a = (Discriptor) semanticStack.peek();
                switch (a.data_type) {
                    case ("int"):
                        //try {
                        code.add("\tli $v0 , 1");
                        code.add("\tlw $a0 , " + a.Adr);
                        //} catch (Exception e) {
                        //System.out.println(sem);
                        //             }
                        break;
                    case ("real"):
                        //try {
                        code.add("\tli $v0 , 2");
                        code.add("\tl.s $f12 , " + a.Adr);
                        //} catch (Exception e) {
                        //System.out.println(sem);
                        //             }
                        break;
                    case ("string"):
                        //try {
                        code.add("\tli $v0 , 4");
                        code.add("\tlw $a0 , " + a.Adr);
                        //} catch (Exception e) {
                        //System.out.println(sem);
                        //             }
                        break;
                    default:
                        throw new Exception("in the " + sem + " exception occurred");
                }
                code.add("\tsyscall");
                code.add("\tli $v0 , 4");
                code.add("\tla $a0 , whiteSpace");
                code.add("\tsyscall");
                semanticStack.pop();
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("pushPc"):
                //try {
                inLoop.push(jps);
                code.add("jps" + jps++ + ":");
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("checkBoolPushPc"):
            case ("jz"):
                //try {
                code.add("\tli $t0 , 0");
                a = (Discriptor) semanticStack.peek();
                if (a.data_type.equals("bool")) {
                    semanticStack.pop();
                    code.add("\tlb $t0 , " + a.Adr);
                    semanticStack.push(code.size());
                    code.add("\tbeqz $t0 , ");
                } else {
                    throw new Exception("if condition must be of bool type!");
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("completeLoop"):
                //try {
                code.add("\tb jps" + inLoop.peek());
                code.add("jps" + jps + ":");
                code.set((int) semanticStack.peek(), code.get((int) semanticStack.peek()) + "jps" + jps++);
                inLoop.pop();
                semanticStack.pop();
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("cjp"):
            case ("cjz"):

                //try {
                code.set((int) semanticStack.peek(), code.get((int) semanticStack.peek()) + "jps" + jps);
                code.add("jps" + jps++ + ":");
                semanticStack.pop();
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("jp"):
                //try {
                returnType = code.get(code.size() - 1);
                semanticStack.push(code.size() - 1);
                code.set(code.size() - 1, "\tb ");
                code.add(returnType);
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("getCodeBetweenCompeleteLoop"):
                //try {
                code.addAll(between);
                code.add("\tb jps" + inLoop.peek());
                code.add("jps" + jps + ":");
                code.set((int) semanticStack.peek(), code.get((int) semanticStack.peek()) + "jps" + jps++);
                inLoop.pop();
                semanticStack.pop();
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("pushBetween"):
                //try {
                between = new ArrayList<>();
                for (int i = (int) semanticStack.peek() + 1; i < code.size(); i++) {
                    between.add(code.get(i));
                }
                for (int i = (int) semanticStack.peek() + 1; i < code.size(); i++) {
                    code.remove(code.size() - 1);
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("makeNegetive"):
                //try {
                a = (Discriptor) semanticStack.peek();
                if (a.data_type.equals("int") || a.data_type.equals("real")) {
                    if (a.data_type.equals("int")) {
                        code.add("\tlw $t0 , " + a.Adr);
                        code.add("\tneg $t1 , $t0");
                        code.add("\tsw $t1 , " + a.Adr);
                    } else {
                        code.add("\tl.s $f0 , " + a.Adr);
                        code.add("\tneg.s $f1 , $f0");
                        code.add("\ts.s $f1 , " + a.Adr);
                    }
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            case ("not"):
                //try {
                a = (Discriptor) semanticStack.peek();
                if (a.data_type.equals("bool")) {
                    code.add("\tlb $t0 , " + a.Adr);
                    code.add("\tnot $t1 , $t0");
                    code.add("\tsb $t1 , " + a.Adr);
                }
                //} catch (Exception e) {
                //System.out.println(sem);
                //             }
                break;
            default:
                System.out.println("invalid semantic routine " + sem);

        }
    }
}
