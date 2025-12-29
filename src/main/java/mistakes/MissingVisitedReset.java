package mistakes;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

/// 공통 함정 4: visited 배열 초기화 누락\
/// 문제:
/// - DFS 후 visited 배열이 모두 true
/// - BFS 시작 시 `visited[V]`가 이미 true
/// - BFS가 실행되지 않음
///
/// 해결:
/// - DFS 후 visited 배열 초기화
/// - `Arrays.fill(visited, false)`
/// 또는:
/// - DFS와 BFS에 각각 별도의 visited 배열 사용
public class MissingVisitedReset {
	static List<Integer>[] graph;
	static boolean[] visited;
	static StringBuilder sb = new StringBuilder();
	static int N;

	public static void main(String[] args) {
		N = 4;
		int V = 1;

		int[][] edges = {
			{1, 2},
			{1, 3},
			{1, 4},
			{2, 4},
			{3, 4}
		};

		@SuppressWarnings("unchecked")
		List<Integer>[] graphArray = new ArrayList[N + 1];
		graph = graphArray;
		for (int i = 1; i <= N; i++) {
			graph[i] = new ArrayList<>();
		}

		for (int[] edge : edges) {
			int u = edge[0];
			int v = edge[1];
			graph[u].add(v);
			graph[v].add(u);
		}

		for (int i = 1; i <= N; i++) {
			Collections.sort(graph[i]);
		}

		// ❌ 잘못된 구현: visited 초기화 누락
		System.out.println("=== 잘못된 구현 (visited 초기화 없음) ===");
		visited = new boolean[N + 1];
		dfs(V);
		System.out.println("DFS 결과: " + sb.toString());

		// visited 초기화 없이 BFS 실행
		sb = new StringBuilder();
		bfs(V);
		System.out.println("BFS 결과: " + sb.toString());
		System.out.println("문제: BFS가 실행되지 않음 (모든 노드가 이미 방문됨)");

		// ✅ 올바른 구현: visited 초기화
		System.out.println("\n=== 올바른 구현 (visited 초기화) ===");
		visited = new boolean[N + 1];
		sb = new StringBuilder();
		dfs(V);
		System.out.println("DFS 결과: " + sb.toString());

		// visited 배열 초기화
		java.util.Arrays.fill(visited, false);

		sb = new StringBuilder();
		bfs(V);
		System.out.println("BFS 결과: " + sb.toString());
		System.out.println("정상: DFS와 BFS 모두 정상 실행");
	}

	private static void dfs(int node) {
		visited[node] = true;
		sb.append(node).append(" ");

		for (int next : graph[node]) {
			if (!visited[next]) {
				dfs(next);
			}
		}
	}

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

