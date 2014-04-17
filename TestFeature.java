import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestFeature {
	public void calc_features(HashMap hm, HashMap hm_dom, String uid, String qid)
			throws IOException {
		
		File file1 = new File("/home/cssumit/Desktop/IRE_MAJOR/Test/features");
		FileWriter fw1 = new FileWriter(file1.getAbsoluteFile(),true);
		BufferedWriter out = new BufferedWriter(fw1);
		
		HashMap<String,String>ans =new HashMap<String,String>();
		
		List<String>doc_list=new ArrayList<String>();
		for(Object key:hm.keySet())
		{
			doc_list.add((String) key);
			String s = hm.get(key) + " qid:" + qid + " ";
			ans.put((String)key,s);
		}
		
		List<String>dom_list=new ArrayList<String>();
		for(Object key:hm_dom.keySet())
		{
			dom_list.add((String) key);
		}
		
		int fnum=1;
		for (int i = 0; i <6; i++) {
			
			//opening each of the 6 context files
			FileInputStream f1 = new FileInputStream("/home/cssumit/Desktop/IRE_MAJOR/Test/C" + i);
			BufferedReader reader = new BufferedReader(new InputStreamReader(f1));
			
			//hashmap for doc and corresponding feature value
			HashMap<String,Integer> hm_g1 = new HashMap<String,Integer>();
			
			//hashmap for doc and count for g2.
			HashMap<String,Integer> hm_count_g2 = new HashMap<String,Integer>();
			
			//hashmap for doc and max of relevance for g3
			HashMap<String,Integer> hm_g3 = new HashMap<String,Integer>();

			//hashmap for doc and max of relevance for g4
			HashMap<String,Integer> hm_g4 = new HashMap<String,Integer>();

			for (int j = 0; j < doc_list.size(); j++) {
				
				String doc = doc_list.get(j);
						
				hm_g1.put(doc, 0);
				hm_count_g2.put(doc, 0);
				hm_g3.put(doc, -1);
				hm_g4.put(doc,1);
				
			}
			
			//reading the context file
			String line = reader.readLine();
						
			//features g1-g4
			while (line != null) {
				String parts[] = line.split(" ");
				if (!parts[0].equals("Q")) {
				
					if (doc_list.contains(parts[0])) {
						int val = (int) hm_g1.get(parts[0]);
						val = val + Integer.parseInt(parts[1]);
						hm_g1.put(parts[0], val);
						

						val = (int) hm_count_g2.get(parts[0]);
						val = val + 1;
						hm_count_g2.put(parts[0], val);
						
						hm_g3.put(parts[0],Math.max(hm_g3.get(parts[0]),Integer.parseInt(parts[1])));
						
						hm_g4.put(parts[0],Math.min(hm_g4.get(parts[0]),Integer.parseInt(parts[1])));
					}
				}
				line = reader.readLine();
			}
			
			for(String key:hm_g1.keySet())
			{
			//	System.out.println(ans.get(key));
				String s=ans.get(key);
				s=s+fnum+":"+hm_g1.get(key)+" ";
				int num1=hm_g1.get(key);
				int num2=hm_count_g2.get(key);
				int f=fnum+1;
				if(num2!=0)
				{
					float avg=(float) ((num1*1.0)/num2);					
					s=s+f+":"+avg+" ";
				}
				else
					s=s+f+":"+'0'+" ";
				
				f=fnum+2;				
				s=s+f+":"+hm_g3.get(key)+" ";
				
				f=fnum+3;
				s=s+f+":"+hm_g4.get(key)+" ";
				
				ans.put(key, s);
			}		
			fnum+=4;
			
			reader.close();
			f1.close();
		}
		for(String key:ans.keySet())
		{
			String s=ans.get(key);
			s=s+"#\n";
			out.write(s);
		}
		out.close();
	}
	
}