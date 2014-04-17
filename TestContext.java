import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;


public class TestContext {
	public void def_context(String user_id , String query_id) throws IOException{
		BufferedWriter[] out = new BufferedWriter[6];
		File f1;
        BufferedReader query = null,output=null;
		for(int i=0;i<6;i++)
		{
			File file = new File("/home/cssumit/Desktop/IRE_MAJOR/Test/C"+i);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file,true);
			out[i]=new BufferedWriter(fw);			
		}
		f1 = new File("/home/cssumit/Desktop/IRE_MAJOR/Test/query");
        query = new BufferedReader(new FileReader(f1));
        //f2 = new FileInputStream("/home/meenal/padhai/IRE/output");
        RandomAccessFile f2 = new RandomAccessFile("/home/cssumit/Desktop/IRE_MAJOR/Test/output", "r");
        //output = new BufferedReader(new InputStreamReader(f2));
        
        String line= query.readLine();
        line=query.readLine();
        while(line!=null)
        {
        	String[] parts = line.split(" ");
        	if(parts[1].equals(user_id) && parts[3].equals(query_id))
        	{
        		long  offset = Long.parseLong(parts[4]);
        		f2.seek(offset);        		
        		String l=f2.readLine();
        		l=f2.readLine();
        		String[] prts = l.split(" ");
        		
        		while(!prts[0].equals("Q")&&l!=null)
        		{
        			
        			if(Integer.parseInt(prts[1])>0)
        			{
        				String s1=prts[0]+" "+prts[1]+"\n";
        				out[0].write(s1);
        				String s2=prts[2]+" "+prts[1]+"\n";
        				out[1].write(s2);
        			}
        			l=f2.readLine(); 
        			if(l!=null)
        			{
        				prts = l.split(" ");
        			}
            		
        		}
        		
        		
        	}
        	else if(parts[1].equals(user_id))
        	{
        		String q_id=parts[3];
        		String str="Q "+q_id+"\n";
        		out[2].write(str);
        		out[3].write(str);
        		long offset = Long.parseLong(parts[4]);
        		f2.seek(offset);
        		        		
        		String l=f2.readLine();
        		l=f2.readLine();
        		String[] prts = l.split(" ");        		        
        		while(prts[0].equals("Q")==false && l!=null)
        		{
        			int rel = Integer.parseInt(prts[1]);        			
        			if(rel > 0)
        			{
        				String s1=prts[0]+" "+prts[1]+"\n";
        				out[2].write(s1);
        				String s2=prts[2]+" "+prts[1]+"\n";
        				out[3].write(s2);
        			}
        			l=f2.readLine();
            		if(l!=null)
            		{
            			prts = l.split(" "); 
            		}
            			           		
            			
        		}        		
        	}
        	else if(parts[3].equals(query_id))
        	{
        		String u_id=parts[1];
        		String str="U "+u_id+"\n";
        		out[4].write(str);
        		out[5].write(str);
        		
        		long offset = Long.parseLong(parts[4]);
        		//System.out.println(offset);
        		f2.seek(offset);        		
        		String l=f2.readLine();
        		l=f2.readLine();
        		String[] prts = l.split(" ");
        		while(!prts[0].equals("Q"))
        		{
        			if(Integer.parseInt(prts[1])>0)
        			{
        				String s1=prts[0]+" "+prts[1]+"\n";
        				out[4].write(s1);
        				String s2=prts[2]+" "+prts[1]+"\n";
        				out[5].write(s2);
        			}
        			l=f2.readLine();
            		prts = l.split(" ");
        		}        		
        	}
        	line= query.readLine();
        }
        query.close();
        for(int i=0;i<6;i++)
        {
        	out[i].close();
        }        
	}
}