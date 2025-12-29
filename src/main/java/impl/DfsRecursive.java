package impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

	/// 재귀를 사용한 DFS 구현
/// 재귀 vs Stack 비교:
/// - 재귀: 간결하고 직관적, 암묵적 Stack 사용
/// - Stack: 명시적 제어, 깊이 제한 없음
/// 재귀의 동작:
/// ```
/// - 함수 호출 스택 = 암묵적 Stack
/// - dfs(1) → dfs(2) → return → dfs(3) → return
/// ```
public class DfsRecursive {
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

		// 그래프 구성
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

		// 정렬
		for (int i = 1; i <= N; i++) {
			Collections.sort(graph[i]);
		}

		// DFS 실행
		visited = new boolean[N + 1];
		dfs(V);

		System.out.println(sb);
	}

	/// 재귀 DFS
	/// - 진입 시 방문 체크
	/// - 인접 노드 중 방문하지 않은 것만 재귀 호출
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

