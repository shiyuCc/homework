import java.io.*;

/**
 * Created by shiyu on 15-2-25.
 */
public class knuthQuickSortAnalysis {
    //final static int k=10;
    final static int num=100000000;
    public static void main(String[] a){
        //generate a list with random numbers
        int[] randomList=new int[num];
        //int[] originalList=new int[num];
        int[] sortedList=new int[num];
        for(int i=0;i<num;i++){
            randomList[i]=(int)(Math.random()*num);
        }

        //copy array and make k=1
        for(int i=0;i<num;i++){
            sortedList[i]=randomList[i];
        }
        long startTime1=System.currentTimeMillis();
        quickSort(sortedList,0,num-1,1);
        /*for(int i=0;i<num;i++){
            System.out.print(sortedList[i]+" ");
            if(i%10==0){
                System.out.println();
            }
        }
        System.out.println();*/
        long endTime1=System.currentTimeMillis();
        long executionTime1=endTime1-startTime1;
        System.out.println("when k=1,sorting time is :"+executionTime1);

        //copy array and make k=10
        for(int i=0;i<num;i++){
            sortedList[i]=randomList[i];
        }
        long startTime10=System.currentTimeMillis();
        quickSort(sortedList,0,num-1,10);

        long endTime10=System.currentTimeMillis();
        long executionTime10=endTime10-startTime10;
        System.out.println("when k=10,sorting time is :"+executionTime10);

        //copy array and make k=50
        for(int i=0;i<num;i++){
            sortedList[i]=randomList[i];
        }
        long startTime50=System.currentTimeMillis();
        quickSort(sortedList,0,num-1,50);

        long endTime50=System.currentTimeMillis();
        long executionTime50=endTime50-startTime50;
        System.out.println("when k=50,sorting time is :"+executionTime50);

        //copy array and make k=70
        for(int i=0;i<num;i++){
            sortedList[i]=randomList[i];
        }
        long startTime70=System.currentTimeMillis();
        quickSort(sortedList,0,num-1,70);

        long endTime70=System.currentTimeMillis();
        long executionTime70=endTime70-startTime70;
        System.out.println("when k=70,sorting time is :"+executionTime70);

        //copy array and make k=100
        for(int i=0;i<num;i++){
            sortedList[i]=randomList[i];
        }
        long startTime100=System.currentTimeMillis();
        quickSort(sortedList,0,num-1,100);

        long endTime100=System.currentTimeMillis();
        long executionTime100=endTime100-startTime100;
        System.out.println("when k=100,sorting time is :"+executionTime100);

        //copy array and make k=130
        for(int i=0;i<num;i++){
            sortedList[i]=randomList[i];
        }
        long startTime130=System.currentTimeMillis();
        quickSort(sortedList,0,num-1,130);

        long endTime130=System.currentTimeMillis();
        long executionTime130=endTime130-startTime130;
        System.out.println("when k=130,sorting time is :"+executionTime130);

        //copy array and make k=170
        for(int i=0;i<num;i++){
            sortedList[i]=randomList[i];
        }
        long startTime170=System.currentTimeMillis();
        quickSort(sortedList,0,num-1,170);

        long endTime170=System.currentTimeMillis();
        long executionTime170=endTime170-startTime170;
        System.out.println("when k=170,sorting time is :"+executionTime170);

        //copy array and make k=220
        for(int i=0;i<num;i++){
            sortedList[i]=randomList[i];
        }
        long startTime220=System.currentTimeMillis();
        quickSort(sortedList,0,num-1,220);

        long endTime220=System.currentTimeMillis();
        long executionTime220=endTime220-startTime220;
        System.out.println("when k=220,sorting time is :"+executionTime220);

    }

    public static void quickSort(int[] l,int first,int last,int k){
        int mid=partition(l,first,last);
        if(mid-1-first>k){
            quickSort(l,first,mid-1,k);
        }
        else{
            insertionSort(l,first,mid-1);
        }

        if(last-mid>k){
            quickSort(l,mid,last,k);
        }
        else{
            insertionSort(l,mid,last);
        }


    }

    public static int partition(int[] l,int first,int last){
        int temp;
        int i=first;
        int j=last;
        int pivot=l[(first+last)/2];
        while(i<=j){
            while(l[i]<pivot){
                i++;
            }
            while(l[j]>pivot){
                j--;
            }
            if(i<=j){
                temp=l[j];
                l[j]=l[i];
                l[i]=temp;
                i++;
                j--;
            }

        }
        return i;
    }

    public static void insertionSort(int[] l,int first,int last){
        for(int i=first+1;i<=last;i++){
            int num=l[i];
            int j=i;
            while(j>0&&l[j-1]>num){
                l[j]=l[j-1];
                j=j-1;
            }
            l[j]=num;
        }

    }
}
