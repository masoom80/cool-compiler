import java.util.ArrayList;

public class Discriptor {
    Type type;
    ArrayList<Object> args;
    ArrayList<Discriptor> discrpts;
    String name = null;
    String Class = null, method = null;
    String data_type = null;
    String Adr = null;
    String returnType = null;
    int upper_bound = 0;
    int size = 0;
    int scope = -1;
    ArrayList<String> codes = new ArrayList<>();

    Discriptor() {
        super();
        this.Class = "";
        this.method = "";
    }

    Discriptor(String name, Type type, String Class
            , String method, String data_type
            , String Adr, String returnType,
               int upper_bound, int size,
               int scope) {
        this.Adr = Adr;
        this.type = type;
        this.Class = Class;
        this.method = method;
        this.data_type = data_type;
        if (method == null) {
            this.method = "";
        }
        if (Class == null) {
            this.Class = "";
        }
        this.returnType = returnType;
        this.upper_bound = upper_bound;
        this.scope = scope;
        this.size = size;
        this.args = new ArrayList<>();
        this.discrpts = new ArrayList<>();
        this.name = name;

    }

    @Override
    public String toString() {
        return "Discriptor{" +
                "type=" + type +
                ", args=" + args +
                ", name='" + name + '\'' +
                ", Class='" + Class + '\'' +
                ", method='" + method + '\'' +
                ", data_type='" + data_type + '\'' +
                ", Adr='" + Adr + '\'' +
                ", returnType='" + returnType + '\'' +
                ", upper_bound=" + upper_bound +
                ", size=" + size +
                ", scope=" + scope +
                '}';
    }

    public String getType() {
        if (this.type == Type.ARRVAR) {
            return this.data_type + "$Arr";
        } else if (this.type == Type.VAR) {
            return this.data_type;
        } else {
            return null;
        }
    }

    public String getCode() {
        StringBuilder ret = new StringBuilder();
        for (String line :
                this.codes) {
            ret.append(line);
            ret.append("\n");
        }
        return ret.toString();
    }
}
