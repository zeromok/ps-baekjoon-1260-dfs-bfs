package impl;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

/// Queue를 사용한 BFS 구현
/// BFS의 핵심: "가까운 것부터" 탐색
/// - 레벨 0 처리 → 레벨 1을 큐에 추가 → 레벨 1 처리
/// - "먼저 들어간 것을 먼저 처리" = FIFO = Queue
/// 방문 체크 시점:
/// - 올바른 구현: offer 시점에 체크 (중복 삽입 방지)
/// - 잘못된 구현: poll 시점에 체크 (중복 삽입 발생)
/// 예시:
/// ```
/// 그래프: 1-2, 1-3, 2-3
/// 잘못된 구현: queue = [1] → [2, 3] → [3, 3] (중복!)
/// 올바른 구현: queue = [1] → [2, 3] → [3] (중복 없음)
/// ```
public class BfsQueue {
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

		// BFS 실행
		visited = new boolean[N + 1];
		bfs(V);

		System.out.println(sb);
	}

	/// Queue를 사용한 BFS
	/// 핵심: offer 시점에 방문 체크
	/// - 같은 노드가 여러 경로로 큐에 중복 삽입되는 것을 방지
	/// - poll 시점에는 출력만 수행
	private static void bfs(int start) {
		Queue<Integer> queue = new ArrayDeque<>();
		queue.offer(start);
		visited[start] = true; // 시작 노드 방문 체크

		while (!queue.isEmpty()) {
			int node = queue.poll();
			sb.append(node).append(" ");

			for (int next : graph[node]) {
				if (!visited[next]) {
					queue.offer(next);
					visited[next] = true; // offer 시점 체크 (중복 방지)
				}
			}
		}
	}
}

