import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by Lazo on 2021-10-21
 */

public class Main
{
    public static void main(String[] args) {

        Scanner myObj = new Scanner(System.in);
        System.out.print("Alice Enter p : ");
        int p = Integer.parseInt(myObj.nextLine());

        System.out.print("Alice Enter g : ");
        int g = Integer.parseInt(myObj.nextLine());

        System.out.print("Alice Enter a : ");
        int a = Integer.parseInt(myObj.nextLine());

        System.out.print("Bob Enter m : ");
        int m = Integer.parseInt(myObj.nextLine());

        System.out.print("Bob Enter k : ");
        int k = Integer.parseInt(myObj.nextLine());

        System.out.print("answer is: " + elgamal(p, g, a, m, k) + " (mod " + p + ")");
    }

    private static long[] elgamalEncryption(long p, long g, long A, long m, long k) {
        long c1, c2;
        long[] ans;

        c1 = fastPoweringAlgorithm(g, k, p);

        c2 = (m * fastPoweringAlgorithm(A, k, p)) % p;
        ans = new long[]{c1, c2};
        return ans;
    }

    private static long elgamalDecryption(long[] cc, long p, long a) {
        long x, xMinus1, ans;

        x = fastPoweringAlgorithm(cc[0], a, p);

        xMinus1 = fastPoweringAlgorithm(x, p-2, p);

        ans = (cc[1] * xMinus1) % p;

        return ans;
    }

    private static long elgamal(long p, long g, long a, long m, long k) {

        long A, ans;

        A = fastPoweringAlgorithm(g, a, p);

        long[] cc = elgamalEncryption(p, g, A, m, k);

        ans = elgamalDecryption(cc, p, a);

        return ans;
    }


    private static List<Character> convertStringToCharList(String str) {
        return str.chars().mapToObj(e -> (char)e).collect(Collectors.toList());
    }

    private static long fastPoweringAlgorithm(long g, long A, long mod) {
        List<Character> reversedA = convertStringToCharList(Long.toBinaryString(A));
        List<Long> aList = new ArrayList<>();
        aList.add(g);

        var ans = 1L;
        var index = 1;
        for(int i = 0; i < reversedA.size(); i++) {

            if (i < reversedA.size()-1)
                aList.add((aList.get(i) * aList.get(i)) % mod);

            index = reversedA.size() - (i +1);
            if (Integer.parseInt(String.valueOf(reversedA.get(index))) == 0)
                continue;

            ans = (ans * aList.get(i)) % mod;

        }

        return ans;
    }
}
