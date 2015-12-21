import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by shiyu on 15-4-15.
 */
public class DoubleLinkedList {
    private static class Node{
        private int data;
        private Node pre;
        private Node next;
        public Node(int v,Node p,Node n){
            data=v;
            pre=p;
            next=n;
        }
    }
    private Node head;
    public DoubleLinkedList(){
        head=null;
    }
    public void addFirst(int v){
        head=new Node(v,null,head);
    }

    public void addLast(int v){
        Node p=head;
        if(p==null){
            head=new Node(v,null,null);

        }
        else{
            while(p.next!=null){
                p=p.next;
            }
            p.next=new Node(v,p,null);
        }

    }

    public void deleteFirst(){
        if(head==null){
            return;
        }
        head=head.next;

    }

    public void deleteLast(){
        if(head==null){
            return;
        }
        Node p=head;
        while(p.next.next!=null){
            p=p.next;
        }
        p.next=null;
    }

    public static void main(String[] args){
        DoubleLinkedList a=new DoubleLinkedList();
        BufferedReader in;
        try{
            int countOfDeleteLast=0;
            int countOfDeleteFirst=0;
            File file=new File("src/hw4b.inp");
            in=new BufferedReader(new FileReader(file));
            String line=in.readLine();
            String ch[]=line.split(" ");
            int num=Integer.parseInt(ch[0]);
            int numOfDeleteFirst=Integer.parseInt(ch[1]);
            int numOfDeleteLast=Integer.parseInt(ch[2]);

            for(int i=1;i<=num;i++){
                a.addLast(i);
            }
            System.out.println("grow array before delete:");

            Node temp;
            for(temp=a.head;temp.next!=null;temp=temp.next){
                System.out.print(temp.data+" ");
            }
            System.out.println(temp.data);
            while(countOfDeleteLast<numOfDeleteLast){
                a.deleteLast();
                countOfDeleteLast++;
            }
            while(countOfDeleteFirst<numOfDeleteFirst){
                a.deleteFirst();
                countOfDeleteFirst++;
            }
            System.out.println();
            System.out.println("grow array after delete:");
            Node temp1;
            for(temp1=a.head;temp1.next!=null;temp1=temp1.next){
                System.out.print(temp1.data+" ");
            }
            System.out.println(temp1.data);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
