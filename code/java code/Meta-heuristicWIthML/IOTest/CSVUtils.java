package IOTest;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
 
/**   
 * CSV����(��ȡ��д��)
 * @author lq
 * @version 2018-04-23 
 */
public class CSVUtils {
    
    /**
     * ��ȡ 
     * @param file csv�ļ�(·��+�ļ���)��csv�ļ������ڻ��Զ�����
     * @param dataList ����
     * @return
     */
    public static boolean exportCsv(File file, List<String> dataList){
        boolean isSucess=false;
        
        FileOutputStream out=null;
        OutputStreamWriter osw=null;
        BufferedWriter bw=null;
        try {
            out = new FileOutputStream(file);
            osw = new OutputStreamWriter(out);
            bw =new BufferedWriter(osw);
            if(dataList!=null && !dataList.isEmpty()){
                for(String data : dataList){
                    bw.append(data).append("\r");
                }
            }
            isSucess=true;
        } catch (Exception e) {
            isSucess=false;
        }finally{
            if(bw!=null){
                try {
                    bw.close();
                    bw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(osw!=null){
                try {
                    osw.close();
                    osw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(out!=null){
                try {
                    out.close();
                    out=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        }
        
        return isSucess;
    }
    
    /**
     * д��
     * @param file csv�ļ�(·��+�ļ�)
     * @return
     */
    public static List<String> importCsv(File file){
        List<String> dataList=new ArrayList<String>();
        
        BufferedReader br=null;
        try { 
            br = new BufferedReader(new FileReader(file));
            String line = ""; 
            while ((line = br.readLine()) != null) { 
                dataList.add(line);
            }
        }catch (Exception e) {
        }finally{
            if(br!=null){
                try {
                    br.close();
                    br=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
 
        return dataList;
    }
    
    
    /**
     * ����
     * @param args
     */
    public static void main(String[] args){  
//        exportCsv();
        importCsv();
     } 
    
    
    
    
    /**
     * CSV��ȡ����
     * @throws Exception
     */
    public static void importCsv()  {
    	ArrayList<String> Listy=new ArrayList();
    	ArrayList<String> List80=new ArrayList();
    	ArrayList<String> List293=new ArrayList();
    	ArrayList<String> List328=new ArrayList();
    	ArrayList<String> List190=new ArrayList();
    	ArrayList<String> List373=new ArrayList();
    	ArrayList<String> List170=new ArrayList();
    	ArrayList<String> List402=new ArrayList();
    	ArrayList<String> List198=new ArrayList();
    	int diff80=0;
    	int diff293=0;
    	int diff328=0;
    	int diff190=0;
    	int diff373=0;
    	int diff170=0;
    	int diff402=0;
    	int diff198=0;
        List<String> dataList=CSVUtils.importCsv(new File("/Users/nanzhenghan/Desktop/Check.csv"));
        if(dataList!=null && !dataList.isEmpty()){
            for(int i=0; i<dataList.size();i++ ){
                if(i!=0){//����ȡ��һ��
                    String s=dataList.get(i); 
//                    System.out.println("s  "+s);
                      String[] as = s.split(",");
                      Listy.add(as[0]);
                      List80.add(as[1]);
                      List293.add(as[2]);
                      List328.add(as[3]);
                      List190.add(as[4]);
                      List373.add(as[5]);
                      List170.add(as[6]);
                      List402.add(as[7]);
                      List198.add(as[8]);
//                      System.out.println(as[0]+"       "+as[5]);
                }
            }
            for(int i=0;i<Listy.size();i++) {
            	String target=Listy.get(i);
            	if(!target.equals(List80.get(i))) {
            		diff80++;
            	}
            	if(!target.equals(List293.get(i))) {
            		diff293++;
            	}
            	if(!target.equals(List328.get(i))) {
            		diff328++;
            	}
            	if(!target.equals(List190.get(i))) {
            		diff190++;
            	}
            	if(!target.equals(List373.get(i))) {
            		diff373++;
            	}
            	if(!target.equals(List170.get(i))) {
            		diff170++;
            	}
            	if(!target.equals(List402.get(i))) {
            		diff402++;
            	}
            	if(!target.equals(List198.get(i))) {
            		diff198++;
            	}
            	
            }
            System.out.println("80: "+diff80);
            System.out.println("293: "+diff293);
            System.out.println("328: "+diff328);
            System.out.println("190: "+diff190);
            System.out.println("373: "+diff373);
            System.out.println("170: "+diff170);
            System.out.println("402: "+diff402);
            System.out.println("198: "+diff198);
        }
    }
    
    /**
     * CSVд�����
     * @throws Exception
     */
    public static void exportCsv() {
        List<String> dataList=new ArrayList<String>();
        dataList.add("number,name,sex");
        dataList.add("1,����,��");
        dataList.add("2,����,��");
        dataList.add("3,С��,Ů");
        boolean isSuccess=CSVUtils.exportCsv(new File("C:/Users/m/Desktop/TrainData.csv"), dataList);
        System.out.println(isSuccess);
    }
       
    
}