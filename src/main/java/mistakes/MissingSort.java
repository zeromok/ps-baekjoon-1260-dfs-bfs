package mistakes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/// 공통 함정 2: 인접 리스트 정렬 누락
/// 문제 조건: "정점 번호가 작은 것을 먼저 방문"\
/// 문제:
/// - 입력 순서: 1-3, 1-2, 1-4
/// - 정렬 없이: `graph[1] = [3, 2, 4]`
/// - 결과: `1 → 3 → 2 → 4` (틀림!)
/// - 기대: `1 → 2 → 3 → 4`
///
/// 해결:
/// - 입력 완료 후 각 인접 리스트를 오름차순 정렬
/// - `Collections.sort(graph[i])`
public class MissingSort {
	static List<Integer>[] graph;
	static boolean[] visited;
	static StringBuilder sb = new StringBuilder();
	static int N;

	public static void main(String[] args) {
		N = 4;
		int V = 1;

		// 입력 순서가 오름차순이 아닌 경우
		int[][] edges = {
			{1, 3},  // 3이 먼저
			{1, 2},  // 2가 나중
			{1, 4},  // 4가 마지막
			{2, 4},
			{3, 4}
		};

		@SuppressWarnings("unchecked")
		List<Integer>[] graphArray = new ArrayList[N + 1];
		graph = graphArray;
		for (int i = 1; i <= N; i++) {
			graph[i] = new ArrayList<>();
		}

		// ❌ 잘못된 구현: 정렬 누락
		System.out.println("=== 잘못된 구현 (정렬 없음) ===");
		for (int[] edge : edges) {
			int u = edge[0];
			int v = edge[1];
			graph[u].add(v);
			graph[v].add(u);
		}
		// Collections.sort 누락!

		System.out.println("graph[1] = " + graph[1]); // [3, 2, 4]

		visited = new boolean[N + 1];
		dfs(V);
		System.out.println("결과: " + sb.toString());
		System.out.println("문제: 1 → 3 → 4 → 2 (오름차순 아님)");

		// ✅ 올바른 구현: 정렬 수행
		System.out.println("\n=== 올바른 구현 (정렬 수행) ===");
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
			graph[v].add(u);
		}

		// 정렬 수행
		for (int i = 1; i <= N; i++) {
			Collections.sort(graph[i]);
		}

		System.out.println("graph[1] = " + graph[1]); // [2, 3, 4]

		sb = new StringBuilder();
		visited = new boolean[N + 1];
		dfs(V);
		System.out.println("결과: " + sb.toString());
		System.out.println("정상: 1 → 2 → 3 → 4 (오름차순)");
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

