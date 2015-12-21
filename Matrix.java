import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 * Created by shiyu on 15-4-17.
 */
public class Matrix {
    private int row;
    private int col;
    private double[] m;

    public Matrix(int row, int col){
        this.row=row;
        this.col=col;
        m=new double[row*col];
    }

    public Matrix(int row, int col, double[] n){
        this.row=row;
        this.col=col;
        this.m=n;
    }

    //convert a two-dimensional matrix to one-dimensional array
    public static double[] convert(int row,int col,double[][] a){
        double[] m=new double[row*col];
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                m[i*col+j]=a[i][j];
            }
        }
        return m;
    }

    public static Matrix add(Matrix a,Matrix b){
        if(a.row!=b.row||a.col!=b.col){
            System.out.println("Bad SIZE!");
            return null;
        }
        else{
            Matrix sum=new Matrix(a.row,a.col);
            for(int i=0;i<sum.row*sum.col;i++){
                sum.m[i]=a.m[i]+b.m[i];
            }
            return sum;
        }
    }

    public static Matrix sub(Matrix a,Matrix b){
        if(a.row!=b.row||a.col!=b.col){
            System.out.println("Bad SIZE!");
            return null;
        }
        else{
            Matrix sub=new Matrix(a.row,a.col);
            for(int i=0;i<sub.row*sub.col;i++){
                sub.m[i]=a.m[i]-b.m[i];
            }
            return sub;
        }
    }

    public static double[][] convert(Matrix p, double[] a){
        double[][] temp=new double[p.row][p.col+1];
        for(int i=0;i<p.row;i++){
            for(int j=0;j<p.col;j++){
                temp[i][j]=p.m[i*p.col+j];
            }
            temp[i][p.col]=a[i];
        }
        return temp;
    }

    public static Matrix multiply(Matrix a,Matrix b){
        if(a.col!=b.row){
            System.out.println("Bad SIZE!");
            return null;
        }
        else{
            Matrix mul=new Matrix(a.row,b.col);
            for(int i=0;i<a.row;i++){
                for(int j=0;j<b.col;j++){
                    double temp=0;
                    for(int p=0,q=0;p<a.col&&q<b.row;p++,q++){
                        temp+=a.m[i*a.col+p]*b.m[q*b.col+j];
                    }
                    mul.m[i*b.col+j]=temp;
                }
            }
            return mul;
        }
    }

    public static Matrix StrassenMul(Matrix a,Matrix b){
        if(a.col!=b.row){
            System.out.println("Bad SIZE!");
            return null;
        }
        else {
            Matrix pro = new Matrix(a.row, a.col);
            int n=a.row;
            if(n==1){
                pro.m[0]=a.m[0]*b.m[0];
            }
            else{
                Matrix a11=new Matrix(n/2,n/2);
                Matrix a12=new Matrix(n/2,n/2);
                Matrix a21=new Matrix(n/2,n/2);
                Matrix a22=new Matrix(n/2,n/2);
                Matrix b11=new Matrix(n/2,n/2);
                Matrix b12=new Matrix(n/2,n/2);
                Matrix b21=new Matrix(n/2,n/2);
                Matrix b22=new Matrix(n/2,n/2);

                partition(a,a11,0,0);
                partition(a,a12,0,n/2);
                partition(a,a21,n/2,0);
                partition(a,a22,n/2,n/2);

                partition(b,b11,0,0);
                partition(b,b12,0,n/2);
                partition(b,b21,n/2,0);
                partition(b,b22,n/2,n/2);

                Matrix p1=multiply(add(a11,a22),add(b11,b22));
                Matrix p2=multiply(add(a21,a22),b11);
                Matrix p3=multiply(a11,sub(b12,b22));
                Matrix p4=multiply(a22,sub(b21,b11));
                Matrix p5=multiply(add(a11,a12),b22);
                Matrix p6=multiply(sub(a21,a11),add(b11,b12));
                Matrix p7=multiply(sub(a12,a22),add(b21,b22));

                Matrix c11=add(add(p1,p4),sub(p7,p5));
                Matrix c12=add(p3,p5);
                Matrix c21=add(p2,p4);
                Matrix c22=add(add(p1,p3),sub(p6,p2));

                combine(c11,pro,0,0);
                combine(c12,pro,0,n/2);
                combine(c21,pro,n/2,0);
                combine(c22,pro,n/2,n/2);
            }
            return pro;
        }
    }

    public static void partition(Matrix a,Matrix b,int x,int y){
        for(int i=0,j=x;i<b.row;i++,j++){
            for(int p=0,q=y;p<b.row;p++,q++){
                b.m[i*b.col+p]=a.m[j*a.col+q];
            }
        }
    }

    public static void combine(Matrix a,Matrix b,int x,int y){
        for(int i=0,j=x;i<a.row;i++,j++){
            for(int p=0,q=y;p<a.row;p++,q++){
                b.m[j*b.col+q]=a.m[i*a.col+p];
            }
        }
    }

    public static void gauss(Matrix m, double[] d)
    {
        double[][] temp = convert(m,d);

        int rows = m.row;
        int cols = m.col+1;

        for (int row=0; row<rows; row++)
        {
            double factor = temp[row][row];
            for (int col=0; col<cols; col++)
            {
                if(temp[row][col]==0){
                    temp[row][col]=0;
                }
                else{
                    temp[row][col] /= factor;
                }
            }

            for (int row2=row+1; row2<rows; row2++)
            {
                factor = -temp[row2][row];
                for (int col=0; col<cols; col++){
                    temp[row2][col] += factor * temp[row][col];
                }
            }
        }

        for(int row3=0;row3<rows-1;row3++){
            for(int col2=row3+1;col2<m.col;col2++){
                if(temp[row3][col2]!=0){
                    double factor=-temp[row3][col2];
                    for(int col3=col2;col3<cols;col3++){
                        temp[row3][col3]+=factor*temp[col2][col3];
                    }
                }
            }
        }

        for (int i = 0; i < rows; i++) {
            System.out.print(temp[i][cols-1] + " ");
        }
        System.out.println();

    }

    public static void showMatrix(Matrix a){
        for(int i=0;i<a.row*a.col;i++){
            System.out.print(a.m[i]+" ");
            if((i+1)%a.col==0){
                System.out.println();
            }
        }
    }

    public static void main(String[] args){
        BufferedReader in;
        try{
            File file=new File("src/HW5c.txt");

            in=new BufferedReader(new FileReader(file));
            String line=in.readLine();
            String[] a=line.split(" ");
            int caseNum=Integer.parseInt(a[1]);
            int num=0;
            while(num<caseNum){
                line=in.readLine();
                String[] b=line.split(" ");
                int row=Integer.parseInt(b[0]);
                int col=Integer.parseInt(b[1]);

                int rowA=0,rowB=0;
                double[] m=new double[row*col];
                double[] n=new double[row*col];
                while(rowA<row){
                    line=in.readLine();
                    String[] p=line.split(" ");
                    for(int i=rowA*col;i<(rowA+1)*col;i++){
                        m[i]=Integer.parseInt(p[i%2]);
                    }
                    rowA++;
                }
                while(rowB<row){
                    line=in.readLine();
                    String[] p=line.split(" ");
                    for(int i=rowB*col;i<(rowB+1)*col;i++){
                        n[i]=Integer.parseInt(p[i%2]);
                    }
                    rowB++;
                }
                Matrix A=new Matrix(row,col,m);
                Matrix B=new Matrix(row,col,n);
                System.out.println("Matrix a is:");
                showMatrix(A);
                System.out.println("Matrix b is:");
                showMatrix(B);
                Matrix strassenMul=StrassenMul(A,B);
                System.out.println("after strassen multiplication:");
                showMatrix(strassenMul);
                num++;
            }

        }catch (IOException E){
            E.printStackTrace();
        }
        double[] a=new double[1024*1024];
        double[] b=new double[1024*1024];
        double[] c=new double[2048*2048];
        double[] d=new double[2048*2048];
        Random r=new Random();
        for(int i=0;i<1024*1024;i++){
            a[i]=r.nextInt(10);
        }
        for(int i=0;i<1024*1024;i++){
            b[i]=r.nextInt(10);
        }
        for(int i=0;i<2048*2048;i++){
            c[i]=r.nextInt(10);
        }
        for(int i=0;i<2048*2048;i++){
            d[i]=r.nextInt(10);
        }
        Matrix aa=new Matrix(1024,1024,a);
        Matrix bb=new Matrix(1024,1024,b);
        Matrix cc=new Matrix(2048,2048,c);
        Matrix dd=new Matrix(2048,2048,d);

        double startTime1=System.currentTimeMillis();
        multiply(aa,bb);
        double endTime1=System.currentTimeMillis();
        System.out.println("time for normal multiplication with 1024*1024 matrix is: "+(endTime1-startTime1)+"ms");

        double startTime2=System.currentTimeMillis();
        StrassenMul(aa,bb);
        double endTime2=System.currentTimeMillis();
        System.out.println("time for strassen multiplication with 1024*1024 matrix is: "+(endTime2-startTime2)+"ms");

        double startTime3=System.currentTimeMillis();
        multiply(cc,dd);
        double endTime3=System.currentTimeMillis();
        System.out.println("time for normal multiplication with 2048*2048 matrix is: "+(endTime3-startTime3)+"ms");

        double startTime4=System.currentTimeMillis();
        StrassenMul(cc,dd);
        double endTime4=System.currentTimeMillis();
        System.out.println("time for strassen multiplication with 2048*2048 matrix is: "+(endTime4-startTime4)+"ms");
    }
}
