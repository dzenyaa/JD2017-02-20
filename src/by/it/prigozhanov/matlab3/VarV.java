package by.it.prigozhanov.matlab3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VarV extends Var {
    public Double[] vector;

    @Override
    public Var add(Var var) {
        Double[] res = new Double[vector.length];
        if (var instanceof VarF) {
            for (int i = 0; i < res.length; i++) {
                res[i] = vector[i] + ((VarF) var).value;
            }
        } else if (var instanceof VarV) {
            for (int i = 0; i < res.length; i++) {
                res[i] = vector[i] + ((VarV) var).vector[i];
            }
        } else return super.add(var);

        return new VarV(res);
    }

    @Override
    public Var sub(Var var) {
        Double[] res = new Double[vector.length];
        if (var instanceof VarF) {
            for (int i = 0; i < res.length; i++) {
                res[i] = vector[i] - ((VarF) var).value;
            }
        } else if (var instanceof VarV) {
            for (int i = 0; i < res.length; i++) {
                res[i] = vector[i] - ((VarV) var).vector[i];
            }
        } else return super.add(var);

        return new VarV(res);
    }

    @Override
    public Var mul(Var var) {
        Double[] res = new Double[vector.length];
        if (var instanceof VarF) {
            for (int i = 0; i < res.length; i++) {
                res[i] = vector[i] * ((VarF) var).value;
            }
        } else if (var instanceof VarV) {
            Double sum=0.0;
            for (int i = 0; i < res.length; i++) {
                sum=sum+ vector[i] * ((VarV) var).vector[i];
            }
            return new VarF(sum);
        } else return super.add(var);

        return new VarV(res);
    }

    public Var div(Var var) {
        Double[] res = new Double[vector.length];
        if (var instanceof VarF) {
            for (int i = 0; i < res.length; i++) {
                res[i] = vector[i] / ((VarF) var).value;
            }
        } else return super.add(var);
        return new VarV(res);
    }


    public VarV(VarV copyFrom) {
        vector = new Double[copyFrom.vector.length];
        //for (int i = 0; i < copyFrom.vector.length; i++) {
        //    vector[i]=copyFrom.vector[i];
        //}
        System.arraycopy(copyFrom.vector, 0, vector, 0, vector.length);
    }

    public VarV(Double[] vector) {
        this.vector = new Double[vector.length];
        System.arraycopy(vector, 0, this.vector, 0, vector.length);
    }

    public VarV(String value) {
        fromString(value);
    }

    @Override
    public void fromString(String value) {
        Pattern p = Pattern.compile(Patterns.exVec);
        if (p.matcher(value).matches()) {
            p = Pattern.compile(Patterns.exVal);
            Matcher m = p.matcher(value);
            int size = 0;
            while (m.find()) size++;
            vector = new Double[size];
            m.reset();
            int i = 0;
            while (m.find()) {
                String oneValue = m.group();
                vector[i] = Double.parseDouble(oneValue);
                i++;
            }
        } else {
            new Error("Ошибка:" + value + " не является вектором");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        String delimiter = "";
        for (Double one : vector) {
            sb.append(delimiter);
            sb.append(one.toString());
            delimiter = ",";
        }
        sb.append("}");
        return sb.toString();
    }
}
