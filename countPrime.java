import java.util.*;

/**
 * Created by shiyu on 15-1-26.
 */


public class countPrime {

    private static Vector<Long> storePrimes=new Vector<Long>();
    public static void main(String args[]){
        Long startTime=System.currentTimeMillis();


        Scanner in=new Scanner(System.in);
        System.out.println("Please input number a: ");
        Long a=in.nextLong();
        System.out.println("Please input number b: ");
        Long b=in.nextLong();
        if(a>=b){
            System.out.println("Input error! a should be smaller than b");
        }
        else if(b<=1){
            System.out.println("No primes between a and b!");
        }
        else{

            if(b==2){
                System.out.println("sum is 2");
            }
            else{
                storePrimes.add(2L);
                boolean isPrime;
                Long sum=0L;
                for(Long i=3L;i<=b;i=i+2){
                    isPrime=true;
                    for(int j=0;j<storePrimes.size();j++){
                        if(i%storePrimes.get(j)==0){
                            isPrime=false;
                            break;
                        }
                    }
                    if(isPrime){
                        storePrimes.add(i);
                        //System.out.println(i);
                        if(i>=a){
                            sum+=i;

                        }
                    }
                }
                sum=(a<=2?sum+2:sum);
                System.out.println("sum is "+sum);
            }
        }

        Long endTime=System.currentTimeMillis();
        System.out.println("Execution Time: "+(endTime-startTime));
    }
}
