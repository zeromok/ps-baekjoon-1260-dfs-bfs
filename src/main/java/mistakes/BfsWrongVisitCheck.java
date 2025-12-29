package mistakes;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

/// 공통 함정 3: BFS에서 poll 시점에 방문 체크
/// 문제:
/// - poll 시점에 방문 체크하면 같은 노드가 여러 경로로 큐에 중복 삽입됨
/// 예시:
/// ```
/// 그래프: 1-2, 1-3, 2-3
/// 잘못된 구현:
/// queue: [1]
/// 1 poll → 2, 3 offer → queue: [2, 3]
/// 2 poll → 3 offer → queue: [3, 3]  // 중복!
/// 올바른 구현:
/// queue: [1]
/// 1 poll → 2, 3 offer (체크) → queue: [2, 3]
/// 2 poll → 3은 이미 체크됨 → queue: [3]
/// ```
/// 해결:
/// - offer 시점에 방문 체크
/// - poll 시점에는 출력만 수행
public class BfsWrongVisitCheck {
	static List<Integer>[] graph;
	static boolean[] visited;
	static StringBuilder sb = new StringBuilder();
	static int N;

	public static void main(String[] args) {
		N = 3;
		int V = 1;

		// 중복 삽입이 발생하는 그래프
		// 1-2, 1-3, 2-3 (삼각형)
		int[][] edges = {
			{1, 2},
			{1, 3},
			{2, 3}
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

		// ❌ 잘못된 구현: poll 시점 체크
		System.out.println("=== 잘못된 구현 (poll 시점 체크) ===");
		visited = new boolean[N + 1];
		bfsWrong(V);
		System.out.println("결과: " + sb.toString());
		System.out.println("문제: 같은 노드가 중복 출력될 수 있음");
		System.out.println("큐 상태: 1 → [2, 3] → 2 poll → 3 offer → [3, 3] (중복!)");

		// ✅ 올바른 구현: offer 시점 체크
		System.out.println("\n=== 올바른 구현 (offer 시점 체크) ===");
		sb = new StringBuilder();
		visited = new boolean[N + 1];
		bfsCorrect(V);
		System.out.println("결과: " + sb.toString());
		System.out.println("정상: 중복 없이 모든 노드 방문");
	}

	/// ❌ 잘못된 구현: poll 시점에 방문 체크
	/// 문제:
	/// - 같은 노드가 여러 경로로 큐에 중복 삽입됨
	/// - 예: 1 → 2, 3 / 2 → 3 → queue에 3이 두 번 들어감
	private static void bfsWrong(int start) {
		Queue<Integer> queue = new ArrayDeque<>();
		queue.offer(start);

		while (!queue.isEmpty()) {
			int node = queue.poll();

			// ❌ poll 시점에 체크 (너무 늦음!)
			if (visited[node]) {
				continue;
			}

			visited[node] = true;
			sb.append(node).append(" ");

			for (int next : graph[node]) {
				if (!visited[next]) {
					queue.offer(next); // 중복 삽입 가능!
				}
			}
		}
	}

	/// ✅ 올바른 구현: offer 시점에 방문 체크
	/// 해결:
	/// - offer 전에 방문 체크
	/// - 같은 노드가 큐에 한 번만 들어감
	private static void bfsCorrect(int start) {
		Queue<Integer> queue = new ArrayDeque<>();
		queue.offer(start);
		visited[start] = true; // 시작 노드 체크

		while (!queue.isEmpty()) {
			int node = queue.poll();
			sb.append(node).append(" ");

			for (int next : graph[node]) {
				if (!visited[next]) {
					queue.offer(next);
					visited[next] = true; // ✅ offer 시점 체크
				}
			}
		}
	}
}

