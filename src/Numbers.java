public class Numbers {
    private Long num1;
    private Long num2;
    private Long num3;

    public Long getNum1() {
        return num1;
    }

    public void setNum1(Long num1) {
        this.num1 = num1;
    }

    public Long getNum2() {
        return num2;
    }

    public void setNum2(Long num2) {
        this.num2 = num2;
    }

    public Long getNum3() {
        return num3;
    }

    public void setNum3(Long num3) {
        this.num3 = num3;
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime*result+((this.getNum1()==null)? 0 : this.getNum1().hashCode())+((this.getNum2()==null)? 0 : this.getNum2().hashCode())+((this.getNum3()==null)? 0 : this.getNum3().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj){
        Numbers other = (Numbers) obj;
        if (this.getNum1().equals(other.getNum1()) && this.getNum2().equals(other.getNum2()) && this.getNum3().equals(other.getNum3())){
            return true;
        }
        if (this==obj) return true;
        if (this==other) return true;
        return false;
    }

    @Override
    public String toString(){
        String ret = "";
        if (this.getNum1()!=null) ret+="\""+this.getNum1()+"\";";
        else ret+="\"\";";
        if (this.getNum2()!=null) ret+="\""+this.getNum2()+"\";";
        else ret+="\"\";";
        if (this.getNum3()!=null) ret+="\""+this.getNum3()+"\"";
        else ret+="\"\"";
        return ret;
    }
}
