import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TrainQuery {
	public static void main(String args[]) throws IOException {
		long position=0;
		for(int i=0;i<6;i++)
		{
			File file = new File("/home/cssumit/Desktop/IRE_MAJOR/Train/C"+i);
			if (file.exists()) {
				file.delete();
			}
					
		}
		File file = new File("/home/cssumit/Desktop/IRE_MAJOR/Train/features");
		if (file.exists()) {
			file.delete();
		}
		FileInputStream f1 = null;
        BufferedReader reader = null;
        File file1 = new File("/home/cssumit/Desktop/IRE_MAJOR/Train/output");
        File file2 = new File("/home/cssumit/Desktop/IRE_MAJOR/Train/query");
        
		
		if (!file1.exists()) {
			file1.createNewFile();
		}
		if (!file2.exists()) {
			file2.createNewFile();
		}

		FileWriter fw1 = new FileWriter(file1.getAbsoluteFile());
		BufferedWriter out = new BufferedWriter(fw1);
     
		FileWriter fw2 = new FileWriter(file2.getAbsoluteFile());
		BufferedWriter q1 = new BufferedWriter(fw2);
        try {
            f1 = new FileInputStream("/home/cssumit/Documents/nfs/train");
            reader = new BufferedReader(new InputStreamReader(f1));

            String line = reader.readLine();
            String query[];
            HashMap hm_rel = new HashMap();
            HashMap hm_dom = new HashMap();
            String session_id="",query_id="",user_id="";
            while(line != null){
            	String parts[]=line.split("\t");	            	 
            	 int len=parts.length;
            	 
            	 
            	 
            	 if(parts[1].equals("M"))
            	 {  
            		 int l=hm_rel.size();
                		if(l!=0)
                		{                			
                			String s1="Q "+user_id+" "+ session_id + " "+query_id;
                			String s1_query=s1+" "+Integer.toString((int) position)+"\n";
                			s1=s1+"\n";
                			out.write(s1);            			
                			q1.write(s1_query);
                			position+=s1.length();
                			String s2="";
                			for(Object key: hm_rel.keySet())
                			{
                				Integer rel = (Integer) hm_rel.get(key);
                				String dom_id = (String)hm_dom.get(key);
                				s2=key+" "+rel+" "+dom_id+"\n";
                				position+=s2.length();
                				out.write(s2);
                			}
                		}
                		hm_rel.clear();
                		hm_dom.clear();
            		// System.out.println(line);            		
             		user_id=parts[3];
            	 }
            	 else if(parts[2].equals("Q"))
            	{
            		 int l=hm_rel.size();
               		if(l!=0)
               		{               			
               			
               			String s1="Q "+user_id+" "+ session_id + " "+query_id;
               			String s1_query=s1+" "+Long.toString(position)+"\n";
               			s1=s1+"\n";
               			out.write(s1);            			
               			q1.write(s1_query);
               			position+=s1.length();
               			String s2="";
               			for(Object key: hm_rel.keySet())
               			{
               				Integer rel = (Integer) hm_rel.get(key);
               				String dom_id = (String)hm_dom.get(key);
               				s2=key+" "+rel+" "+dom_id+"\n";
               				position+=s2.length();
               				out.write(s2);
               			}
               		}
               		hm_rel.clear();
               		hm_dom.clear();

            		query=line.split("\t");            		
            		session_id=parts[0];
            		query_id=parts[4];            		
                    for(int i=6;i<len;i++)
                    {
                    	String p1[]=parts[i].split(",");             
                    	hm_rel.put(p1[0], 0);
                    	hm_dom.put(p1[0],p1[1]);
                    }                 
            	}
            	else if(parts[2].equals("C"))
                	{
                		int time=Integer.parseInt(parts[1]);

                		if(time<50)
                			hm_rel.put(parts[4], 0);
                		else if(time>=50 && time<399)
                		{
                			hm_rel.put(parts[4], 1);                			
                		}
                		else
                		{
                			hm_rel.put(parts[4],2);
                		}
                	}
            	line = reader.readLine();
            }
            int l=hm_rel.size();
    		if(l!=0)
    		{
    			String s1="Q "+user_id+" "+ session_id + " "+query_id;
    			String s1_query=s1+" "+Integer.toString((int) position)+"\n";
    			s1=s1+"\n";
    			out.write(s1);            			
    			q1.write(s1_query);
    			position+=s1.length();
    			String s2="";
    			for(Object key: hm_rel.keySet())
    			{
    				Integer rel = (Integer) hm_rel.get(key);
    				String dom_id = (String)hm_dom.get(key);
    				s2=key+" "+rel+" "+dom_id+"\n";
    				out.write(s2);
    				position+=s2.length();
    			}
    		}
        }
        finally
        {
        	out.close();
        	q1.close();
        	reader.close();
        	f1.close();        	
        }
	}
}