import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 自定义ClassLoader
 */
public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) {
        try {
            Class<?> clazz = new HelloClassLoader().findClass("Hello");
            Method declaredMethod = clazz.getDeclaredMethod("hello");
            declaredMethod.invoke(clazz.getDeclaredConstructor().newInstance());
        } catch (ClassNotFoundException | NoSuchMethodException |
                IllegalAccessException | InstantiationException |
                InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String xlassPath = name + ".xlass";
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int size = 0;
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(xlassPath)) {
            while ((size = inputStream.read(buffer)) != -1) {
                bout.write(buffer, 0, size);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] decode = decode(bout.toByteArray());
        return defineClass(name, decode, 0, decode.length);
    }

    private byte[] decode(byte[] xlass) {
        for (int i = 0; i < xlass.length; i++) {
            xlass[i] = (byte) (255 - xlass[i]);
        }
        return xlass;
    }
}
