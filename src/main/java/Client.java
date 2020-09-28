import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.gjt.jclasslib.io.ClassFileWriter;
//import org.gjt.jclasslib.structures.CPInfo;
import org.gjt.jclasslib.structures.ClassFile;
import org.gjt.jclasslib.structures.Constant;
import org.gjt.jclasslib.structures.InvalidByteCodeException;
import org.gjt.jclasslib.structures.constants.ConstantUtf8Info;

public class Client
{
    @SuppressWarnings("deprecation")
    public static void main(String[] args){
        modifyClassString("C:\\Users\\Administrator\\Desktop\\SaverUtil.class",893,"var Utility=Packages.com.artery.form.serverscript.Utility;\n");
    }

    /**
     *
     * @param filePath 修改class路径
     * @param post      修改的位置
     * @param val     新值
     */
    private static void modifyClassString(String filePath,int post,String val) {
//        String filePath = "C:\\Users\\Administrator\\Desktop\\SaverUtil.class";
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        DataInput di = new DataInputStream(fis);
        ClassFile cf = new ClassFile();
        try {
            cf.read(di);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        Constant[] infos = cf.getConstantPool();

        int count = infos.length;
        for (int i = 0; i < count; i++) {
            if (infos[i] != null) {
//                System.out.print(i);
//                System.out.print(" = ");
//                try {
//                    System.out.print(infos[i].getVerbose());
//                } catch (InvalidByteCodeException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                System.out.print(" = ");
//                System.out.println(infos[i].getConstantType());
                if(i == post){
                    ConstantUtf8Info uInfo = (ConstantUtf8Info)infos[i];
                    uInfo.setString(val);
                    infos[i]=uInfo;
                }
            }
        }
        cf.setConstantPool(infos);
        try {
            fis.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        File f = new File(filePath);
        try {
            ClassFileWriter.writeToFile(f, cf);
        } catch (InvalidByteCodeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}