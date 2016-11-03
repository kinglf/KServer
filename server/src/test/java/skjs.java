/**
 * Created by Kinglf on 2016/11/3.
 */
public class skjs {
    public static void main(String[] args) {
        String str="abc";

        byte[][] abc=new byte[][]{str.getBytes(),str.getBytes()};
        add(abc);
    }

    public static void add(byte[]... ints){
        for(byte[] i:ints){
            System.out.println(new String(i));
        }
    }
}
