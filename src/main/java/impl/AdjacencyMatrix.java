package impl;

import java.util.LinkedList;
import java.util.Queue;

/// ## 인접 행렬을 사용한 DFS/BFS 구현
///
/// 인접 리스트 대신 2차원 배열을 사용한 그래프 표현
/// ### 인접 행렬의 특징
///
///     - 메모리: O(N²) - 정점 개수의 제곱
///     - 간선 존재 확인: O(1) - 상수 시간
///     - 전체 탐색: O(N²) - 모든 칸을 확인
///     - 구현 난이도: 쉬움
///
/// ### 언제 사용하는가?
///
///     - 밀집 그래프 (M ≈ N²)
///     - 간선 존재 여부를 자주 확인해야 할 때
///     - 정점 개수가 적을 때 (N ≤ 100)
///
/// ### 이 문제에서의 비교
/// <pre>
/// N = 1,000, M = 10,000
/// 인접 행렬: 1,000 × 1,000 = 1,000,000 (4MB)
/// 인접 리스트: 1,000 + 10,000 = 11,000 (44KB)
/// → 희소 그래프이므로 리스트가 더 효율적
/// </pre>
public class AdjacencyMatrix {
	static int[][] graph;
	static boolean[] visited;
	static StringBuilder sb = new StringBuilder();
	static int N;

	public static void main(String[] args) {
		// 정점의 개수
		N = 4;
		int V = 1;

		int[][] edges = {
			{1, 2},
			{1, 3},
			{1, 4},
			{2, 4},
			{3, 4}
		};


		// 인접 행렬 초기화
		graph = new int[N + 1][N + 1];

		// 간선 입력 (양방향)
		for (int[] edge : edges) {
			int x = edge[0];
			int y = edge[1];
			graph[x][y] = 1;
			graph[y][x] = 1;
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

	/// 인접 행렬 기반 DFS
	/// - 인접 리스트와 달리 정렬이 불필요함 (1부터 N까지 순회)
	/// @param node 현재 방문할 노드 번호
	private static void dfs(int node) {
		visited[node] = true;
		sb.append(node).append(" ");

		for (int i = 1; i <= N; i++) {
			if (graph[node][i] == 1 && !visited[i]) {
				dfs(i);
			}
		}
	}

	/// 인접 행렬 기반 BFS
	/// @param start 탐색 시작 노드 번호
	private static void bfs(int start) {

		Queue<Integer> queue = new LinkedList<>();
		queue.offer(start);
		visited[start] = true;

		while (!queue.isEmpty()) {
			int node = queue.poll();
			sb.append(node).append(" ");

			for (int i = 1; i <= N; i++) {
				if (graph[node][i] == 1 && !visited[i]) {
					queue.offer(i);
					visited[i] = true;
				}
			}
		}

	}
}
