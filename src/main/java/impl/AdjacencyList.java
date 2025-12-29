package impl;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

/// 인접 리스트를 사용한 DFS/BFS 구현
/// 인접 행렬과의 비교:
/// - 메모리: O(N+M) vs O(N²)
/// - 간선 확인: O(degree) vs O(1)
/// - 전체 탐색: O(N+M) vs O(N²)
/// 이 문제 (N=1000, M=10000)에서는 인접 리스트가 효율적
public class AdjacencyList {
	static List<Integer>[] graph;
	static boolean[] visited;
	static StringBuilder sb = new StringBuilder();
	static int N;

	public static void main(String[] args) {
		// 테스트 데이터
		N = 4;
		int V = 1;

		int[][] edges = {
			{1, 2},
			{1, 3},
			{1, 4},
			{2, 4},
			{3, 4}
		};

		// 인접 리스트 초기화
		List<Integer>[] graphArray = new ArrayList[N + 1];
		graph = graphArray;
		for (int i = 1; i <= N; i++) {
			graph[i] = new ArrayList<>();
		}

		// 간선 입력 (양방향)
		for (int[] edge : edges) {
			int u = edge[0];
			int v = edge[1];
			graph[u].add(v);
			graph[v].add(u); // 양방향
		}

		// 정렬 (작은 번호부터 방문)
		for (int i = 1; i <= N; i++) {
			Collections.sort(graph[i]);
		}

		// DFS
		visited = new boolean[N + 1];
		dfs(V);
		sb.append("\n");

		// BFS
		visited = new boolean[N + 1];
		bfs(V);

		System.out.println(sb);
	}

	/// 재귀 DFS
	private static void dfs(int node) {
		visited[node] = true;
		sb.append(node).append(" ");

		for (int next : graph[node]) {
			if (!visited[next]) {
				dfs(next);
			}
		}
	}

	/// Queue BFS
	private static void bfs(int start) {
		Queue<Integer> queue = new ArrayDeque<>();
		queue.offer(start);
		visited[start] = true;

		while (!queue.isEmpty()) {
			int node = queue.poll();
			sb.append(node).append(" ");

			for (int next : graph[node]) {
				if (!visited[next]) {
					queue.offer(next);
					visited[next] = true;
				}
			}
		}
	}
}

