import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;


public class context {
	public void def_context(String user_id , String query_id) throws IOException{
		System.out.println("args user "+user_id + " query "+query_id);
		BufferedWriter[] out = new BufferedWriter[6];
		FileInputStream f1 = null;
        BufferedReader query = null,output=null;
		for(int i=0;i<6;i++)
		{
			File file = new File("/home/swapna/sem6/ire/project/C"+i);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			out[i]=new BufferedWriter(fw);			
		}
		f1 = new FileInputStream("/home/swapna/sem6/ire/project/query");
        query = new BufferedReader(new InputStreamReader(f1));
        //f2 = new FileInputStream("/home/meenal/padhai/IRE/output");
        RandomAccessFile f2 = new RandomAccessFile("/home/swapna/sem6/ire/project/output", "r");
        //output = new BufferedReader(new InputStreamReader(f2));
        
        String line= query.readLine();
        while(line!=null)
        {
        	String[] parts = line.split(" ");
        	if(parts[1].equals(user_id) && parts[3].equals(query_id))
        	{
        		long offset = Integer.parseInt(parts[4]);
        		f2.seek(offset);        		
        		String l=f2.readLine();
        		l=f2.readLine();
        		String[] prts = l.split(" ");
        		
        		while(!prts[0].equals("Q"))
        		{
        			
        			if(Integer.parseInt(prts[1])>0)
        			{
        				String s1=prts[0]+" "+prts[1]+"\n";
        				out[0].write(s1);
        				String s2=prts[2]+" "+prts[1]+"\n";
        				out[1].write(s2);
        			}
        			l=f2.readLine();        			
            		prts = l.split(" ");
        		}
        		
        		
        	}
        	else if(parts[1].equals(user_id))
        	{
        		String q_id=parts[3];
        		String str="Q "+q_id+"\n";
        		out[2].write(str);
        		out[3].write(str);
        		long offset = Integer.parseInt(parts[4]);
        		f2.seek(offset);
        		        		
        		String l=f2.readLine();
        		l=f2.readLine();
        		String[] prts = l.split(" ");        		        
        		while(prts[0].equals("Q")==false)
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
            		prts = l.split(" ");            		
        		}        		
        	}
        	else if(parts[3].equals(query_id))
        	{
        		String u_id=parts[1];
        		String str="U "+u_id+"\n";
        		out[4].write(str);
        		out[5].write(str);
        		
        		long offset = Integer.parseInt(parts[4]);
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
        for(int i=0;i<6;i++)
        {
        	out[i].close();
        }        
	}
}
