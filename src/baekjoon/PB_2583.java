package baekjoon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
//https://www.acmicpc.net/problem/2583
class PB_2583 {
    static final int[] dx = {0, 0, 1, -1};
    static final int[] dy = {-1, 1, 0, 0};
    static int height, width;

    public static int DFS(int[][] map,int x,int y){
        if(map[x][y] == 0){
            map[x][y] = 1;
            int count = 1;
            for (int index = 0; index < dx.length; index++) {
                int nx = x + dx[index];
                int ny = y + dy[index];
                if(nx >= 0 && nx < width && ny >= 0 && ny< height){
                    count += DFS(map, nx, ny);
                }
            }
            return count;
        }
        return 0;
    }

    public static List<Integer> solution(int[][] map) {
        List<Integer> answer = new ArrayList<>();
        for (int x = 0; x < width ; x++) {
            for (int y = 0; y < height; y++) {
                if(map[x][y] == 0){
                    answer.add(DFS(map,x,y));
                }
            }
        }
        Collections.sort(answer);
        return answer;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        height = in.nextInt();
        width = in.nextInt();
        int k = in.nextInt();

        int[][] map = new int[width][height];

        for (int i = 0; i < k; i++) {
            int startX = in.nextInt();
            int startY = in.nextInt();
            int endX = in.nextInt();
            int endY = in.nextInt();

            for (int x = startX; x < endX ; x++) {
                for (int y = startY; y < endY; y++) {
                    map[x][y] = 1;
                }
            }
        }

        List<Integer> result = solution(map);
        System.out.println(result.size());
        for (Integer i : result) {
            System.out.print(i+" ");
        }
    }
}