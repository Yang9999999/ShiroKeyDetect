package utils;
import org.apache.shiro.subject.SimplePrincipalCollection;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class KeyDetect {
    public byte[] getPayload() throws Exception {
        ByteArrayOutputStream barr = new ByteArrayOutputStream();
        SimplePrincipalCollection simplePrincipalCollection = new SimplePrincipalCollection();
        ObjectOutputStream obj = new ObjectOutputStream(barr);
        obj.writeObject(simplePrincipalCollection);
        obj.close();


        return barr.toByteArray();
    }
}
