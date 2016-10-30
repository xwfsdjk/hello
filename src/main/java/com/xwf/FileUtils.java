package com.xwf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by xwf on 2015/11/18.
 */
public class FileUtils {
    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);
    private static byte[] read(InputStream in){
        byte[] data;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = 0;
        try {
            while ((len = in.read(buf)) != -1) {
                bout.write(buf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        data = bout.toByteArray();
        return data;
    }

    public static byte[] getByte(File file) {
        byte[] bytes = null;
        if (file != null) {
            InputStream is = null;
            try {
                is = new FileInputStream(file);
                int length = (int) file.length();
                if (length > Integer.MAX_VALUE)
                {
                    logger.info("this file is max ");
                    return null;
                }
                bytes = new byte[length];
                int offset = 0;
                int numRead = 0;
                while (offset < bytes.length
                        && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                    offset += numRead;
                }

                if (offset < bytes.length) {
                    logger.info("file length is error");
                    return null;
                }
                is.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }
        return bytes;
    }

    public static int writeFile2SDCard(String path,byte[] bytes) {
        int result = 0;
        File file = new File(path);
        if(file.exists()) {
            file.delete();
            logger.info( "existed  ");
            try {
                file.createNewFile();
            } catch (IOException e) {
                result = -1;
                e.printStackTrace();
            }
        }
        FileOutputStream out= null;
        try {
            out = new FileOutputStream(file);
            out.write(bytes);
            out.getFD().sync();
        } catch (FileNotFoundException e) {
            result = -1;
            e.printStackTrace();
        } catch (IOException e1) {
            result = -1;
            e1.printStackTrace();
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.info( "write file ok  ");
        return  result;
    }

    public static byte[] loadFile(String path) {
        File file = new File(path);
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        byte[] data = null ;
        if(file.isDirectory()) {
            return  null;
        }
        try {
            fis = new FileInputStream(file);
            baos = new ByteArrayOutputStream((int) file.length());

            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            data = baos.toByteArray() ;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                    fis = null;
                }
                baos.close() ;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
    /**
     * 编码
     * @param b
     * @return
     */
    public static String printHexString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 16进制字符串转换为相应的byte数组
     * @param src "81214f5888"
     * @return [-127, 33, 79, 88, -120]
     */
    public static byte[] HexString2Bytes(String src){

        char[] c = src.toCharArray();
        byte[] b = new byte[c.length/2];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) ((Character.digit((int) c[2 * i], 16) << 4) | Character.digit((int) c[2 * i + 1], 16));
        }
        return b;
    }

    public static void main(String[] args) {

    }
}
