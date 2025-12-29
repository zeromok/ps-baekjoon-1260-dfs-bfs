package mistakes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/// 공통 함정 1: 양방향 간선을 한쪽만 추가
///
/// 문제:
/// - 입력: 1 2 (양방향 간선)
/// - 잘못된 구현: `graph[1].add(2)`만 수행
/// - 결과: 1 → 2는 가능하지만 2 → 1은 불가능
///
/// 해결:
/// - 양방향 간선은 두 방향 모두 추가
/// - `graph[u].add(v)` AND `graph[v].add(u)`
public class WrongBidirectional {
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

		// ❌ 잘못된 구현: 한쪽만 추가
		System.out.println("=== 잘못된 구현 (한쪽만 추가) ===");
		for (int[] edge : edges) {
			int u = edge[0];
			int v = edge[1];
			graph[u].add(v); // 한쪽만!
			// graph[v].add(u); // 누락!
		}

		for (int i = 1; i <= N; i++) {
			Collections.sort(graph[i]);
		}

		visited = new boolean[N + 1];
		dfs(V);
		System.out.println("결과: " + sb.toString());
		System.out.println("문제: 2 → 1로 이동 불가능 (단방향 그래프)");

		// ✅ 올바른 구현: 양방향 추가
		System.out.println("\n=== 올바른 구현 (양방향 추가) ===");
		@SuppressWarnings("unchecked")
		List<Integer>[] graphArray2 = new ArrayList[N + 1];
		graph = graphArray2;
		for (int i = 1; i <= N; i++) {
			graph[i] = new ArrayList<>();
		}

		for (int[] edge : edges) {
			int u = edge[0];
			int v = edge[1];
			graph[u].add(v);
			graph[v].add(u); // 양방향!
		}

		for (int i = 1; i <= N; i++) {
			Collections.sort(graph[i]);
		}

		sb = new StringBuilder();
		visited = new boolean[N + 1];
		dfs(V);
		System.out.println("결과: " + sb.toString());
		System.out.println("정상: 모든 간선이 양방향으로 연결됨");
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
}

