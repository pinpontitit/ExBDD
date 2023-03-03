package fr.fms.business;


	import java.util.ArrayList;
	import java.util.Arrays;
	public class Brouillon {
	    
	    //conversion en string:
	    public static void main(String args[]) {
	      ArrayList<Integer> ints = new ArrayList<>();
	      ints.add(1);
	      ints.add(2);
	      
	    String str = ints.toString();
	    System.out.println(str);
	    
	    
	    //déconversion en arraylist:
	    ArrayList<Integer> result = new ArrayList<>();
	    if (str.length() > 2) {
	       str = str.substring(1, str.length() - 1).replace(" ", "");
	       System.out.println(str);
	       String[] strArr = str.split(",");
	       System.out.println(Arrays.toString(strArr));
	       for (String str1 : strArr)
	         result.add(Integer.parseInt(str1));
	    }
	    System.out.println(result);
	      
	    }
	}

