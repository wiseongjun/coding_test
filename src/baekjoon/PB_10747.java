package baekjoon;

// https://www.acmicpc.net/problem/10747

import java.util.List;
import java.util.Scanner;

public class PB_10747 {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		List<Integer> result = solution(map);
		System.out.println(result.size());
		for (Integer i : result) {
			System.out.print(i+" ");
		}
	}
}
