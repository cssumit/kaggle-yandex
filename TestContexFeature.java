import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.HashMap;

public class TestContexFeature
{
	public static void main(String arg[]) throws IOException
	{
		BufferedReader reader = null;
		FileInputStream f1 = null;
		TestContext ctxt=new TestContext();
        TestFeature query_f = new TestFeature();
        f1 = new FileInputStream("/home/cssumit/Desktop/IRE_MAJOR/Test/query");
        reader = new BufferedReader(new InputStreamReader(f1));
        String line = reader.readLine();
        while(line!=null)
        {
        	String[] split1=line.split(" ");
//        	System.out.println(split1[1]);
        	String u_id =split1[1];
        	String q_id = split1[3];
        	ctxt.def_context(u_id,q_id);
        	
        	RandomAccessFile fseek1 = new RandomAccessFile("/home/cssumit/Desktop/IRE_MAJOR/Test/output", "r");
        	long offset = Long.parseLong(split1[4]);
        	fseek1.seek(offset);
        	String l = fseek1.readLine();
            HashMap hm_send = new HashMap();
            HashMap hm_send_dom = new HashMap();
        	for(int i=0;i<10;i++){
            	l = fseek1.readLine();
            	String prts[]=l.split(" ");	            	
            	hm_send.put(prts[0],prts[1]);
            	hm_send_dom.put(prts[2],prts[1]);
        	}
        	query_f.calc_features(hm_send,hm_send_dom,u_id,q_id);
        	line = reader.readLine();
        }
        reader.close();
        
	}
}